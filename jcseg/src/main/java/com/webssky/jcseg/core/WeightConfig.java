package com.webssky.jcseg.core;

import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: yonghui.feng
 * Date: 13-5-14
 * Time: 上午10:00
 * To change this template use File | Settings | File Templates.
 */
public final class WeightConfig {
    private static WeightConfig _instance = null;

    public static final String WEIGHT_PROPERTY_FILE = "weight.properties";

    private WeightConfig() {
    }

    ;

    private int titleWeight;
    private int txtWeight;
    private int extDicWeight;
    private int adornNameWeight;

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    private String partOfSpeech;

    public int getWeightThredhold() {
        return weightThredhold;
    }

    public void setWeightThredhold(int weightThredhold) {
        this.weightThredhold = weightThredhold;
    }

    private int weightThredhold;

    public static WeightConfig getWeightConfig() {
        if (_instance == null) {
            _instance = new WeightConfig();
            try {
                Properties p = new Properties();
                p.load(WeightConfig.class.getResourceAsStream("/" + WEIGHT_PROPERTY_FILE));

                _instance.setAdornNameWeight(Integer.parseInt(p.getProperty("dic.name.adorn.weight")));
                _instance.setExtDicWeight(Integer.parseInt(p.getProperty("dic.ext.weight")));
                _instance.setTitleWeight(Integer.parseInt(p.getProperty("keyword.title.weight")));
                _instance.setTxtWeight(Integer.parseInt(p.getProperty("keyword.text.weight")));
                _instance.setWeightThredhold(Integer.parseInt(p.getProperty("weight.thredhold")));
                _instance.setPartOfSpeech(p.getProperty("part.of.speech"));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return _instance;
    }

    public int getTxtWeight() {
        return txtWeight;
    }

    public void setTxtWeight(int txtWeight) {
        this.txtWeight = txtWeight;
    }

    public int getExtDicWeight() {
        return extDicWeight;
    }

    public void setExtDicWeight(int extDicWeight) {
        this.extDicWeight = extDicWeight;
    }

    public int getAdornNameWeight() {
        return adornNameWeight;
    }

    public void setAdornNameWeight(int adornNameWeight) {
        this.adornNameWeight = adornNameWeight;
    }

    public int getTitleWeight() {
        return titleWeight;
    }

    public void setTitleWeight(int titleWeight) {
        this.titleWeight = titleWeight;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TitleWeight:").append(this.getTitleWeight()).append("\n");
        sb.append("TxtWeight:").append(this.getTxtWeight()).append("\n");
        sb.append("ExtWeight:").append(this.getExtDicWeight()).append("\n");
        sb.append("AdornNameWeight:").append(this.getAdornNameWeight()).append("\n");

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(WeightConfig.getWeightConfig().toString());
    }
}
