package com.webssky.jcseg.core;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: yonghui.feng
 * Date: 13-7-5
 * Time: 下午4:53
 * To change this template use File | Settings | File Templates.
 */
public final class DictionaryConfig {
    public static final String CONFIG_FILE = "jcseg.properties";
    public static final String KEY_SUFFIX = "lexicon.suffix";
    public static final String KEY_PREFIX = "lexicon.prefix";
    public static final String KEY_LEX_DIR = "lexicon.dir";
    public static final String KEY_LEX_PATH = "lexicon.path";

    private static DictionaryConfig _instance = null;

    private Properties properties = null;

    public static DictionaryConfig getConfig() {
        if (_instance == null) {
            _instance = new DictionaryConfig();
                    /*
         * load the mapping from the property file.
		 * 1.load the the jcseg.properties located with the jar file.
		 * 2.load the jcseg.propertiess from the current directory.
		 */
            try {
                _instance.properties = new Properties();
                File pro_file = new File(System.getProperty("user.dir") + "/" + CONFIG_FILE);
                if (pro_file.exists()) {
                    _instance.properties.load(new FileReader(pro_file));
                } else {
                    _instance.properties.load(WeightConfig.class.getResourceAsStream("/" + CONFIG_FILE));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return _instance;
    }

    public String getDictFileSuffix() {
        return (String) properties.get(KEY_SUFFIX);
    }

    public String getDictFilePrefix() {
        return (String) properties.get(KEY_PREFIX);
    }

    public String getDictBaseDir() {
        return (String) properties.get(KEY_LEX_PATH);
    }

    public String getDictFolder() {
        return (String) properties.get(KEY_LEX_DIR);
    }
}
