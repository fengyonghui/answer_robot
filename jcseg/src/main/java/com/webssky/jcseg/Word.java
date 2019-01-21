package com.webssky.jcseg;

import cn.mchina.robot.common.util.JSONUtil;
import com.webssky.jcseg.core.IWord;

import java.io.Serializable;


/**
 * word class for jcseg has implements IWord interface
 *
 * @author chenxin <br />
 * @email chenxin619315@gmail.com <br />
 * {@link http://www.webssky.com}
 */
public class Word implements IWord, Serializable{

    private String value;
    private int fre = 0;
    private int type;
    private int position;
    private String pinyin = null;
    private String[] partspeech = null;
    private String[] syn = null;

    public Word(String value, int type) {
        this.value = value;
        this.type = type;
    }

    public Word(String value, int fre, int type) {
        this.value = value;
        this.fre = fre;
        this.type = type;
    }

    /**
     * @see IWord#getValue()
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * @see IWord#getLength()
     */
    @Override
    public int getLength() {
        return value.length();
    }

    /**
     * @see IWord#getFrequency()
     */
    @Override
    public int getFrequency() {
        return fre;
    }

    /**
     * @see IWord#getType()
     */
    @Override
    public int getType() {
        return type;
    }

    /**
     * @see IWord#setPosition(int)
     */
    @Override
    public void setPosition(int pos) {
        position = pos;
    }

    /**
     * @see IWord#getPosition()
     */
    public int getPosition() {
        return position;
    }

    /**
     * @see IWord#getPinying()
     */
    @Override
    public String getPinyin() {
        return pinyin;
    }

    /**
     * @see IWord#getSyn()
     */
    @Override
    public String[] getSyn() {
        return syn;
    }

    /**
     * @see IWord#getPartSpeech()
     */
    @Override
    public String[] getPartSpeech() {
        return partspeech;
    }

    public String getPartSpeechString() {
        if (partspeech == null) return "";
        StringBuilder sb = new StringBuilder();
        for (String s : partspeech) {
            sb.append(s).append(";");
        }

        return sb.toString();
    }


    /**
     * @see IWord#setPinying(String)
     */
    public void setPinyin(String py) {
        pinyin = py;
    }

    /**
     * @see IWord#addPartSpeech(String);
     */
    @Override
    public void addPartSpeech(String ps) {
        if (partspeech == null) {
            partspeech = new String[1];
            partspeech[0] = ps;
        } else {
            String[] bak = partspeech;
            partspeech = new String[partspeech.length + 1];
            int j;
            for (j = 0; j < bak.length; j++)
                partspeech[j] = bak[j];
            partspeech[j] = ps;
            bak = null;
        }
    }

    /**
     * @see IWord#addSyn(String)
     */
    @Override
    public void addSyn(String s) {
        if (syn == null) {
            syn = new String[1];
            syn[0] = s;
        } else {
            String[] tycA = syn;
            syn = new String[syn.length + 1];
            int j;
            for (j = 0; j < tycA.length; j++)
                syn[j] = tycA[j];
            syn[j] = s;
            tycA = null;
        }
    }

    @Override
    public int hashCode() {
        return this.getValue().hashCode() + this.getType();
    }

    /**
     * @see Object#equals(Object)
     */
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o instanceof IWord) {
            IWord word = (IWord) o;
            boolean bool = word.getValue().equalsIgnoreCase(this.getValue());
            /*
             * value equals and the type of the word must
			 * be equals too, for there is many words in
			 * different lexicon with a same value but 
			 * in different use. 
			 */
            return (bool && (word.getType() == this.getType()));
        }
        return false;
    }

    /**
     * @see Object#toString()
     */
    public String toString() {
        return JSONUtil.toJson(this);
    }

    public boolean inPartSpeech(String[] poss) {
        String[] ps = getPartSpeech();
        if (ps != null && ps.length > 0) {
            for (String t : ps) {
                for (String t2 : poss) {
                    if (t.equals(t2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
