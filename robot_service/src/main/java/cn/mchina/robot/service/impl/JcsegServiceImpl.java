package cn.mchina.robot.service.impl;

import cn.mchina.robot.service.DictionaryManager;
import cn.mchina.robot.service.JcsegService;
import com.webssky.jcseg.ComplexSeg;
import com.webssky.jcseg.core.ADictionary;
import com.webssky.jcseg.core.IWord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: yonghui.feng
 * Date: 14-2-12
 * Time: 上午11:52
 * To change this template use File | Settings | File Templates.
 */
@Service("jcsegService")
public class JcsegServiceImpl implements JcsegService {
    Logger logger = Logger.getLogger(JcsegServiceImpl.class.getName());
    @Resource
    DictionaryManager dictionaryManager;

    @Override
    public List<IWord> getKeywords(long serviceId, String text) {

        ADictionary dictionary = dictionaryManager.getDictionary(serviceId);
        ComplexSeg complexSeg = new ComplexSeg(dictionary);
        IWord word = null;
        List<IWord> retList = new ArrayList<IWord>();
        try {
            complexSeg.reset(new StringReader(text));
            while ((word = complexSeg.next()) != null) {
                retList.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(retList, new MyComparator<IWord>());
        for(IWord s : retList){
            System.out.println(s.getValue()+" size:"+s.getValue().length());
        }
        return retList;
    }

    class MyComparator<IWord> implements Comparator<com.webssky.jcseg.core.IWord>{
        @Override
        public int compare(com.webssky.jcseg.core.IWord o1, com.webssky.jcseg.core.IWord o2) {
            if (o1 != null && o2 != null ){
                return o2.getValue().length() - o1.getValue().length();
            }else if(o1 != null){
                return -1;
            }else if(o2 != null){
                return  1;
            }
            return 0;
        }
    }
}
