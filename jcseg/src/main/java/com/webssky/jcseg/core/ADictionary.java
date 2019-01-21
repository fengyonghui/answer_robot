package com.webssky.jcseg.core;

import java.io.Serializable;

/**
 * Dictionary interface. <br />
 *
 * @author chenxin <br />
 * @email chenxin619315@gmail.com <br />
 * {@link http://www.webssky.com}
 */
public abstract class ADictionary implements Serializable{

    public ADictionary() {
    }

    /**
     * get the weight in the dictionary
     *
     * @param t
     * @return
     */
    public abstract int getWeight(int t);

    /**
     * set the weight for the words in the dictionary
     *
     * @param t
     * @param weitht
     */
    public abstract void setWeight(int t, int weight);

    /**
     * loop up the dictionary,
     * check the given key is in the dictionary or not. <br />
     *
     * @param t   <br />
     * @param key <br />
     * @return true for matched,
     *         false for not match. <br />
     */
    public abstract boolean match(int t, String key);

    /**
     * add a new word to the dictionary. <br />
     *
     * @param t    <br />
     * @param key  <br />
     * @param type <br />
     */
    public abstract void add(int t, String key, int type);

    /**
     * add a new word to the dictionary with
     * its statistics frequency. <br />
     *
     * @param t    <br />
     * @param key  <br />
     * @param fre  <br />
     * @param type <br />
     */
    public abstract void add(int t, String key, int fre, int type);

    /**
     * return the IWord asscociate with the given key.
     * if there is not mapping for the key null will be return. <br />
     *
     * @param t   <br />
     * @param key <br />
     */
    public abstract IWord get(int t, String key);

    /**
     * remove the mapping associate with the given key. <br />
     *
     * @param t   <br />
     * @param key <br />
     */
    public abstract void remove(int t, String key);

    /**
     * return the size of the dictionary <br />
     *
     * @param t <br />
     * @return int <br />
     */
    public abstract int size(int t);


    /**
     * get the key's type index located in ILexicon interface. <br />
     *
     * @param key <br />
     * @return int <br />
     */
    public static int getIndex(String key) {
        if (key == null)
            return -1;
        key = key.toUpperCase();

        if (key.equals("CJK_WORDS"))
            return ILexicon.CJK_WORDS;
        else if (key.equals("CJK_UNITS"))
            return ILexicon.CJK_UNITS;
        else if (key.equals("MIXED_WORD"))
            return ILexicon.MIXED_WORD;
        else if (key.equals("CN_LNAME"))
            return ILexicon.CN_LNAME;
        else if (key.equals("CN_SNAME"))
            return ILexicon.CN_SNAME;
        else if (key.equals("CN_DNAME_1"))
            return ILexicon.CN_DNAME_1;
        else if (key.equals("CN_DNAME_2"))
            return ILexicon.CN_DNAME_2;
        else if (key.equals("CN_LNAME_ADORN"))
            return ILexicon.CN_LNAME_ADORN;
        else if (key.equals("EN_PUN_WORDS"))
            return ILexicon.EN_PUN_WORDS;
        else if (key.equals("STOP_WORDS"))
            return ILexicon.STOP_WORDS;
        else if (key.equals("EXT_WORDS"))
            return ILexicon.EXT_WORDS;

        //System.out.println("no match");
        return ILexicon.CJK_WORDS;
    }

    public abstract void add(int t, String key, IWord word);

}
