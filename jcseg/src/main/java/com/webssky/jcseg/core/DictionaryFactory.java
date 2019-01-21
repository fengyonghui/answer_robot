package com.webssky.jcseg.core;

import com.webssky.jcseg.util.JcsegTagUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Dictionary Factory to create singleton Dictionary
 * object. a path of the class that has implemented the IDictionary
 * interface must be given first. <br />
 *
 * @author chenxin <br />
 * @email chenxin619315@gmail.com <br />
 * {@link http://www.webssky.com}
 */
public class DictionaryFactory {
    static Logger logger = Logger.getLogger(DictionaryFactory.class.getName());
    /**
     * lexicon property file
     */
    public static final String LEX_PROPERTY_FILE = "jcseg.properties";

    /**
     * Dictionary singleton quote
     */
    private static ADictionary __instance = null;

    /**
     * return the IDictionary instance
     *
     * @param __dicClass
     * @return IDictionary
     */
    public static ADictionary getDictionary(String __dicClass) {
        if (__instance == null)
            __instance = loadLexicon(__dicClass);
        return __instance;
    }

    private DictionaryFactory() {
    }

    /**
     * set the IDicontary instance.
     *
     * @param dic
     */
    public static void setDictionary(ADictionary dic) {
        __instance = dic;
    }

    /**
     * load the lexcicon
     *
     * @param __dicClass::the path of the class that has implemeted
     *                        the IDictionary interface.
     * @return IDictionary
     */
    private static ADictionary loadLexicon(String __dicClass) {
        ADictionary dic = null;
        try {
            Class<?> _class = Class.forName(__dicClass);
            Constructor<?> cons = _class.getConstructor();
            dic = (ADictionary) cons.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("can't load the ADictionary extends class " +
                    "with path [" + __dicClass + "]");
            System.exit(1);
        }

        try {
            File[] files = getLexiconFiles();
            if (files != null && files.length > 0) {
                for (int j = 0; j < files.length; j++) {
                    loadWordsFromFile(dic, files[j], "UTF-8");
                }
            } else {
                loadFromJar(dic);
            }

        } catch (LexiconException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dic;
    }

    /**
     * get all the lexicon file.
     * this will parse the the lexicon.property file first.
     *
     * @throws LexiconException
     * @throws java.io.IOException
     */
    private static File[] getLexiconFiles() throws LexiconException, IOException {
        File[] files = null;

        //the lexicon file prefix and suffix
        String suffix = "lex";
        String prefix = "lex";
        if (DictionaryConfig.getConfig().getDictFileSuffix() != null)
            suffix = DictionaryConfig.getConfig().getDictFileSuffix();
        if (DictionaryConfig.getConfig().getDictFilePrefix() != null)
            prefix = DictionaryConfig.getConfig().getDictFilePrefix();

        String dictFolder = DictionaryConfig.getConfig().getDictFolder();
        logger.debug("dictFolder:" + dictFolder);
        String lexPath = DictionaryFactory.class.getResource("/" + dictFolder).getPath();
        logger.debug("lexPath:" + lexPath);
        File path = new File(lexPath);
        if (path.exists()) {
            logger.info("get dictionary from lexicon path [" + lexPath + "]");
            /*
         * load all the lexicon file
		 * under the lexicon path,
		 * that start with __prefix and end with __suffix.
		 */
            final String __suffix = suffix;
            final String __prefix = prefix;
            files = path.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return (name.startsWith(__prefix)
                            && name.endsWith(__suffix));
                }
            });

        }

        return files;
    }

    public static void loadFromJar(ADictionary dic) throws IOException {
        String dirPath = DictionaryConfig.getConfig().getDictFolder();
        URL url = JcsegTagUtils.class.getClassLoader().getResource(dirPath);

        String urlStr = url.toString();
        logger.info("get dictionary from jar:" + urlStr);
        // 找到!/ 截断之前的字符串
        if (urlStr.indexOf("!/") == 0) {
            logger.error("could not load dictionary from jar!");
            return;
        }
        String jarPath = urlStr.substring(0, urlStr.indexOf("!/") + 2);
        URL jarURL = new URL(jarPath);
        JarURLConnection jarCon = (JarURLConnection) jarURL.openConnection();
        JarFile jarFile = jarCon.getJarFile();
        Enumeration<JarEntry> jarEntrys = jarFile.entries();
        while (jarEntrys.hasMoreElements()) {
            JarEntry entry = jarEntrys.nextElement();
            // 简单的判断路径，如果想做到想Spring，Ant-Style格式的路径匹配需要用到正则。
            String name = entry.getName();

            if (name.startsWith(dirPath) && !entry.isDirectory()) {
                if (name.startsWith("lex") && name.endsWith(".lex")) {
                    logger.info("load dictionary:" + name);
                    // 开始读取文件内容
                    InputStream is = DictionaryFactory.class.getClassLoader().getResourceAsStream(name);
                    loadWordsFromInputStream(dic, new InputStreamReader(is, "UTF-8"));

                    is.close();
                }

            }
        }
    }


    private static void loadWordsFromInputStream(ADictionary dic, InputStreamReader ir) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(ir);

            String line = null;
            boolean isFirstLine = true;
            int t = -1;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if ("".equals(line)) continue;
                //swept the notes
                if (line.indexOf('#') == 0) continue;
                //the first line fo the lexicon file.
                if (isFirstLine == true) {
                    t = ADictionary.getIndex(line);
                    isFirstLine = false;
                    if (t >= 0) continue;
                }

                //special lexicon
                if (line.indexOf('/') == -1) {
                    if (line.length() <= Config.MAX_LENGTH) {
                        dic.add(t, line, IWord.T_CJK_WORD);
                    }
                }
                //normal words lexicon file
                else {
                    String[] wd = line.split("/");
                    if (wd.length == 0) {
                        continue;
                    }
                    if (wd.length > 4)
                        dic.add(t, wd[0], Integer.parseInt(wd[4]), IWord.T_CJK_WORD);
                    else
                        dic.add(t, wd[0], IWord.T_CJK_WORD);

                    IWord w = dic.get(t, wd[0]);

                    //System.out.println(wd.length);
                    //set the pinying of the word.
                    if (Config.LOAD_CJK_PINYIN && !"null".equals(wd[2])) {
                        w.setPinyin(wd[2]);
                    }
                    //set the syn words.
                    if (Config.LOAD_CJK_SYN && !"null".equals(wd[3])) {
                        String[] syns = wd[3].split(",");
                        for (int j = 0; j < syns.length; j++)
                            w.addSyn(syns[j].trim());
                    }
                    //set the word's part of speech
                    if (Config.LOAD_CJK_POS && !"null".equals(wd[1])) {
                        String[] pos = wd[1].split(",");
                        for (int j = 0; j < pos.length; j++)
                            w.addPartSpeech(pos[j].trim());
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * load all the words in the lexicon file,
     * into the dictionary.
     *
     * @param ADictionary lex
     * @param file        file
     */
    private static void loadWordsFromFile(ADictionary dic, File file, String charset) {
        logger.info("load dictionary:" + file.getPath() + "==" + file.getName());
        InputStreamReader ir = null;
        BufferedReader br = null;

        try {
            ir = new InputStreamReader(
                    new FileInputStream(file), charset);
            br = new BufferedReader(ir);

            loadWordsFromInputStream(dic, ir);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ir != null) ir.close();
                if (br != null) br.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * configurate the lexicon property file.
     * the property file is under the path of,
     * System.getProperty("user.dir");
     *
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static void setLexiconCfg() throws FileNotFoundException, IOException {
        File file = new File(System.getProperty("user.dir") + "/" + LEX_PROPERTY_FILE);
        Properties pro = new Properties();
        pro.load(new FileReader(file));
        pro.setProperty("lexicon.path", "jar.dir");
        pro.setProperty("lexicon.dir", "src/main/lexicon");
        pro.setProperty("lexicon.suffix", "lex");
        pro.setProperty("lexicon.prefix", "lex");
        pro.setProperty("jcseg.maxlen", "4");
        pro.setProperty("jcseg.mixcnlen", "2");
        pro.setProperty("jcseg.icnname", "1");
        pro.setProperty("jcseg.cnmaxlnadron", "1");
        pro.setProperty("jcseg.nsthreshold", "1000000");
        pro.setProperty("jcseg.pptmaxlen", "15");
        pro.setProperty("jcseg.loadpinyin", "0");
        pro.setProperty("jcseg.loadsyn", "1");
        pro.setProperty("jcseg.loadpos", "0");

        pro.store(new FileWriter(file), "jcseg properties file.\n" +
                "                 \n" +
                "Attention: do not change the key name.\n" +
                "and better not change the lexicon.path, when it's value is jar.dir we will check\n" +
                "	path of the jcseg jar file.\n" +
                "if any error happen, LexiconException will be throws out..\n" +
                "  icnname default value is 0 - close the chinese name identify, 1 to start it.\n" +
                "@author chenxin\n" +
                "@link http://www.webssky.com\n");
    }
/*	
    public static void main(String args[]) throws FileNotFoundException, IOException {
		setLexiconCfg();
		//ADictionary dic = DictionaryFactory.createDictionary("com.webssky.jcseg.Dictionary");
		//System.out.println("size: "+dic.size(ILexicon.CN_LNAME_ADORN));
		//System.out.println("match(ILexicon.CN_LNAME_ADORN, 老): "+dic.match(ILexicon.CN_LNAME_ADORN, "老"));
	}*/
}