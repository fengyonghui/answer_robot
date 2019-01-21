package com.webssky.jcseg.util;

import com.webssky.jcseg.Word;
import com.webssky.jcseg.core.ADictionary;
import com.webssky.jcseg.core.DictionaryFactory;
import com.webssky.jcseg.core.ILexicon;
import com.webssky.jcseg.core.IWord;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yonghui.feng
 * Date: 13-7-8
 * Time: 下午8:38
 * To change this template use File | Settings | File Templates.
 */
public class DictionaryUtil {
    public static void convertDictionary(String srcFile, String destFile, String charset) {
        InputStreamReader ir = null;
        BufferedReader br = null;
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            ir = new InputStreamReader(
                    new FileInputStream(srcFile), charset);
            br = new BufferedReader(ir);

            ow = new OutputStreamWriter(new FileOutputStream(destFile), charset);
            bw = new BufferedWriter(ow);

            String line = null;
            boolean isFirstLine = true;
            int t = -1;
            bw.write("CJK_WORDS");
            bw.newLine();
            StringBuilder sb = new StringBuilder();
            sb.append("CJK_WORDS\n");
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if ("".equals(line)) continue;
                //swept the notes
                if (line.indexOf('#') == 0) continue;

                String[] ws = line.split(" ");
                bw.write(ws[0] + "/" + ws[2] + "/null/null/" + ws[1]);
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ir != null) ir.close();
                if (br != null) br.close();
                if (ow != null) {
                    ow.flush();
                    ow.close();
                }
                if (bw != null) {
                    bw.flush();
                    bw.close();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * * add customer keyword to ILexicon.EXT_WORDS dictionary in memory
     *
     * @param key
     * @param word
     */
    public static void addExtWord(String key, IWord word) {
        ADictionary dictionary = DictionaryFactory.getDictionary("com.webssky.jcseg.Dictionary");
        dictionary.add(ILexicon.EXT_WORDS, key, word);
    }

    /**
     * * add customer keyword to ILexicon.EXT_WORDS dictionary in memory
     *
     * @param key
     * @param word
     */
    public static void addStopWord(String key, IWord word) {
        ADictionary dictionary = DictionaryFactory.getDictionary("com.webssky.jcseg.Dictionary");
        dictionary.add(ILexicon.STOP_WORDS, key, word);
    }

    /**
     * remove keyword from extended dictionary in memory
     *
     * @param key
     */
    public static void removeExtWord(String key) {
        ADictionary dictionary = DictionaryFactory.getDictionary("com.webssky.jcseg.Dictionary");
        dictionary.remove(ILexicon.EXT_WORDS, key);
    }

    /**
     * remove keyword from stop word dictionary in memory
     *
     * @param key
     */
    public static void removeStopWord(String key) {
        ADictionary dictionary = DictionaryFactory.getDictionary("com.webssky.jcseg.Dictionary");
        dictionary.remove(ILexicon.STOP_WORDS, key);
    }

    public static void test1() {
        String userDir = System.getProperty("user.dir");
        String srcFile = userDir + "/jcseg/target/lexicon/dict.txt";
        String descFile = userDir + "/jcseg/target/lexicon/lex-dict.lex";
        convertDictionary(srcFile, descFile, "UTF-8");
    }

    private static void showResult(List<Map.Entry<String, Integer>> entryList) {
        System.out.println("*****排序结果：");
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Map.Entry<String, Integer> entry : entryList) {
            if (i % 5 == 0) {
                sb.append("\n");
            } else {
                sb.append("\t");
            }
            sb.append(entry.getKey() + ":" + entry.getValue());
            i++;
        }
        System.out.println(sb.toString());
    }

    private static void test2() {
        List<Map.Entry<String, Integer>> entryList = null;
        try {
            entryList = JcsegTagUtils.getKeyWords("好中的34他", "中的34英语句子成分有主语，谓语，表语，宾语");
            showResult(entryList);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        System.out.println("\n#################add ext key word###################");
        String key = "中的34";
        IWord word = new Word(key, IWord.T_CJK_WORD);
        DictionaryUtil.addExtWord(key, word);


        try {
            entryList = JcsegTagUtils.getKeyWords("好中的34他", "中的34英语句子成分有主语，谓语，表语，宾语");
            showResult(entryList);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        System.out.println("\n#################add stop key word###################");
        DictionaryUtil.addStopWord(key, word);
        try {
            entryList = JcsegTagUtils.getKeyWords("好中的34他", "中的34英语句子成分有主语，谓语，表语，宾语");
            showResult(entryList);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        System.out.println("\n#################remove stop key word###################");
        DictionaryUtil.removeStopWord(key);
        try {
            entryList = JcsegTagUtils.getKeyWords("好中的34他", "中的34英语句子成分有主语，谓语，表语，宾语");
            showResult(entryList);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        System.out.println("\n#################remove ext key word###################");
        DictionaryUtil.removeExtWord(key);
        try {
            entryList = JcsegTagUtils.getKeyWords("好中的34他", "中的34英语句子成分有主语，谓语，表语，宾语");
            showResult(entryList);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void main(String[] args) {
        test2();
    }
}
