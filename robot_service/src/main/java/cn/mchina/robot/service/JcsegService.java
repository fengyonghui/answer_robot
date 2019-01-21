package cn.mchina.robot.service;

import com.webssky.jcseg.core.IWord;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yonghui.feng
 * Date: 14-2-12
 * Time: 上午11:50
 * To change this template use File | Settings | File Templates.
 */
public interface JcsegService {
    /**
     * getKeywords
     * 根据服务号（serviceId）设置的字典对参数（text）进行分词。
     * @param serviceId
     * @param text
     * @return 分词后的结果，长词优先原则
     */
    List<IWord> getKeywords(long serviceId, String text);
}
