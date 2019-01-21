package com.webssky.jcseg.test;

import com.webssky.jcseg.core.*;

import java.io.*;

/**
 * @author chenxin <br />
 * @email chenxin619315@gmail.com <br />
 */
public class SpeedTest {

    public static ISegment seg = SegmentFacotry.createSegment("com.webssky.jcseg.ComplexSeg");

    public static String segment(Reader reader, int type) throws JCSegException, IOException {

        StringBuffer sb = new StringBuffer();

        long start = System.currentTimeMillis();
        seg.reset(reader);
        System.out.println("Diciontary Loaded, cost:" + (System.currentTimeMillis() - start) + " msec");
        //seg.setLastRule(null);
        IWord word = null;

        long _start = System.currentTimeMillis();
        while ((word = seg.next()) != null) {
            sb.append(word.getValue());
            sb.append("  ");
        }
        System.out.println("Done, cost:" + (System.currentTimeMillis() - _start) + " msec");
        System.out.println("总字数：" + seg.getStreamPosition());
        return sb.toString();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String filename = "/java/products/jcseg_o/article/article";
        if (args.length >= 1)
            filename = args[0];
        try {
            segment(new StringReader("jcseg中文分词组建。"), Config.COMPLEX_MODE);
            segment(new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(filename), "UTF-8")),
                    Config.COMPLEX_MODE);
            //System.out.println("Complex-> "+segment(sb.toString(), Config.COMPLEX_MODE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
