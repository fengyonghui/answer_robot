package com.webssky.jcseg;

import com.webssky.jcseg.core.ADictionary;
import com.webssky.jcseg.core.IChunk;
import com.webssky.jcseg.core.IWord;

import java.io.IOException;
import java.io.Reader;

/**
 * simplex segment for JCSeg,
 * has extend from ASegment. <br />
 *
 * @author chenxin <br />
 * @email chenxin619315@gmail.com <br />
 * {@link http://www.webssky.com} <br />
 */
public class SimpleSeg extends ASegment {

    public SimpleSeg() throws IOException {
        super();
    }

    public SimpleSeg(Reader input) throws IOException {
        super(input);
    }

    public SimpleSeg(Reader input, ADictionary dic) throws IOException {
        super(input, dic);
    }

    /**
     * @see ASegment#getBestCJKChunk(char[], int)
     */
    @Override
    public IChunk getBestCJKChunk(char[] chars, int index) {
        IWord[] words = getNextMatch(chars, index);
        return new Chunk(new IWord[]{words[words.length - 1]});
    }

}
