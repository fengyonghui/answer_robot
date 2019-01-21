package com.webssky.jcseg.util;

import java.io.File;

/**
 * static method for jcseg.
 *
 * @author chenxin <br />
 * @email chenxin619315@gmail.com <br />
 * {@link http://www.webssky.com}
 */
public class Util {

    /**
     * get the absolute parent path for the jar file.
     *
     * @param o
     * @return String
     */
    public static String getJarHome(Object o) {
        String path = o.getClass().getProtectionDomain()
                .getCodeSource().getLocation().getFile();
        File jarFile = new File(path);
        return jarFile.getParentFile().getAbsolutePath();
    }

}
