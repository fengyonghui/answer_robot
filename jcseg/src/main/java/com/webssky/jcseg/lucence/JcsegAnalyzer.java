package com.webssky.jcseg.lucence;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

import java.io.Reader;

/**
 * jcseg analyzer for lucene.
 *
 * @author chenxin <br />
 * @email chenxin619315@gmail.com <br />
 * {@link http://www.webssky.com}
 */
public class JcsegAnalyzer extends Analyzer {

    private int mode;

    public JcsegAnalyzer(int mode) {
        this.mode = mode;
    }

    @Override
    public TokenStream tokenStream(String fieldName, Reader reader) {
        try {
            return new JcsegTokenizer(mode, reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
