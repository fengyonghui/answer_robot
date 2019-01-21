package cn.mchina.robot.test;

import cn.mchina.robot.common.bean.RobotAnswer;
import cn.mchina.robot.redis.RedisService;
import cn.mchina.robot.service.DictionaryManager;
import com.webssky.jcseg.core.ADictionary;
import com.webssky.jcseg.core.ILexicon;
import com.webssky.jcseg.core.IWord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: yonghui.feng
 * Date: 14-2-12
 * Time: 下午4:00
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-robot-service.xml"})
public class DictionaryManagerTest {
    @Resource
    DictionaryManager dictionaryManager;

    @Resource
    RedisService redisService;

    @Test
    public void testGetDictionary(){
        ADictionary dictionary = dictionaryManager.getDictionary(10000l);
        IWord word = dictionary.get(ILexicon.CJK_WORDS, "hello");
        assert word.getSyn()[0].toString().equals("hi");
    }

    @Test
    public void testClearDictionary(){
        ADictionary dictionary = dictionaryManager.getDictionary(10000l);
        assert dictionary!= null;
        dictionaryManager.clearDictionary(10000l);
        dictionary = (ADictionary) redisService.get(redisService.getRedisKey(RobotAnswer.class, 10000l));
        assert  dictionary == null;
    }
}
