package com.webssky.jcseg.core;

/**
 * lexicon configuration class.
 *
 * @author chenxin <br />
 * @email chenxin619315@gmail.com <br />
 * {@link http://www.webssky.com}
 */
public interface ILexicon {

    public static final int T_LEN = 12;

    /**
     * China,JPanese,Korean words
     */
    public static final int CJK_WORDS = 0;

    /**
     * chinese single units
     */
    public static final int CJK_UNITS = 1;

    /**
     * chinese and english mix word.
     * like B超,SIM卡.
     */
    public static final int MIXED_WORD = 2;

    /**
     * chinese last name.
     */
    public static final int CN_LNAME = 3;

    /**
     * chinese single name.
     */
    public static final int CN_SNAME = 4;

    /**
     * fisrt word of chinese double name.
     */
    public static final int CN_DNAME_1 = 5;

    /**
     * sencond word of chinese double name.
     */
    public static final int CN_DNAME_2 = 6;

    /**
     * the adorn(修饰) char before the last name.
     */
    public static final int CN_LNAME_ADORN = 7;

    public static final int EN_PUN_WORDS = 8;

    public static final int STOP_WORDS = 9;

    public static final int EXT_WORDS = 10;

    /**
     * unmatched word
     */
    public static final int UNMATCH_CJK_WORD = 11;


}
