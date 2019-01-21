package com.webssky.jcseg.lucence;

import com.webssky.jcseg.core.*;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

import java.io.IOException;
import java.io.Reader;

/**
 * jcsge tokennizer for lucene.
 *
 * @author chenxin <br />
 * @email chenxin619315@gmail.com <br />
 * {@link http://www.webssky.com}
 */
public class JcsegTokenizer extends Tokenizer {

    private ISegment seg;

    private CharTermAttribute termAtt;
    private OffsetAttribute offsetAtt;

    public JcsegTokenizer(int mode, Reader input) throws JCSegException, IOException {
        super(input);

        String __segClass;
        if (mode == Config.SIMPLE_MODE)
            __segClass = "com.webssky.jcseg.SimpleSeg";
        else if (mode == Config.COMPLEX_MODE)
            __segClass = "com.webssky.jcseg.ComplexSeg";
        else
            throw new JCSegException("No Such Algorithm");

        seg = SegmentFacotry.createSegment(__segClass);
        seg.reset(input);
        termAtt = addAttribute(CharTermAttribute.class);
        offsetAtt = addAttribute(OffsetAttribute.class);
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        seg.reset(input);
    }

    @Override
    public boolean incrementToken() throws IOException {
        clearAttributes();
        IWord word = seg.next();
        if (word != null) {
            termAtt.append(word.getValue());
            termAtt.setLength(word.getLength());
            offsetAtt.setOffset(word.getPosition(), word.getPosition() + word.getLength());
            return true;
        } else {
            end();
            return false;
        }
    }

}
