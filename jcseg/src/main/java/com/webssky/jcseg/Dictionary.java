package com.webssky.jcseg;

import com.webssky.jcseg.core.ADictionary;
import com.webssky.jcseg.core.ILexicon;
import com.webssky.jcseg.core.IWord;
import com.webssky.jcseg.core.WeightConfig;

import java.util.HashMap;
import java.util.Map;
//import com.webssky.jcseg.core.JHashMap;

/**
 * Dictionary class. <br />
 *
 * @author chenxin <br />
 * @email chenxin619315@gmail.com <br />
 * {@link http://www.webssky.com}
 */
public class Dictionary extends ADictionary {

    /**
     * hash table for the words
     */
    private Map<String, IWord>[] dics = null;

    /**
     * put the weight value of the dictionary
     */
    private int[] weight = new int[ILexicon.T_LEN];

    @SuppressWarnings("unchecked")
    public Dictionary() {
        super();
        dics = new Map[ILexicon.T_LEN];
        for (int j = 0; j < ILexicon.T_LEN; j++) {
            dics[j] = new HashMap<String, IWord>(16, 0.85F);
            weight[j] = 1;

            if (j == ILexicon.EXT_WORDS) {
                weight[j] = WeightConfig.getWeightConfig().getExtDicWeight();
            }
        }
    }

    @Override
    public int getWeight(int t) {
        if (t < 0 || t >= ILexicon.T_LEN) return 0;
        return (t >= 0 && t < ILexicon.T_LEN) ? weight[t] : 1;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setWeight(int t, int weight) {
        if (t < 0 || t >= ILexicon.T_LEN) return;
        this.weight[t] = weight;//To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @see ADictionary#match(int, String)
     */
    @Override
    public boolean match(int t, String key) {
        if (t < 0 || t >= ILexicon.T_LEN) return false;
        if (key != null) {
            key = key.toLowerCase();
        }
        return dics[t].containsKey(key);
    }

    /**
     * @see ADictionary#add(int, String, int)
     */
    @Override
    public void add(int t, String key, int type) {
        if (t < 0 || t >= ILexicon.T_LEN) return;
        if (key != null) {
            key = key.toLowerCase();
        }

        dics[t].put(key, new Word(key, type));
    }

    @Override
    public void add(int t, String key, IWord word) {
        dics[t].put(key, word);
    }

    /**
     * @see ADictionary#add(int, String, int, int)
     */
    @Override
    public void add(int t, String key, int fre, int type) {
        if (t < 0 || t >= ILexicon.T_LEN) return;
        if (key != null) {
            key = key.toLowerCase();
        }

        dics[t].put(key, new Word(key, fre, type));
    }

    /**
     * @see ADictionary#get(int, String)
     */
    @Override
    public IWord get(int t, String key) {
        if (t < 0 || t >= ILexicon.T_LEN) return null;
        return dics[t].get(key);
    }

    /**
     * @see ADictionary#remove(int, String)
     */
    @Override
    public void remove(int t, String key) {
        if (t < 0 || t >= ILexicon.T_LEN) return;
        dics[t].remove(key);
    }

    /**
     * @see ADictionary#size(int)
     */
    @Override
    public int size(int t) {
        if (t < 0 || t >= ILexicon.T_LEN) return 0;
        return dics[t].size();
    }

}
