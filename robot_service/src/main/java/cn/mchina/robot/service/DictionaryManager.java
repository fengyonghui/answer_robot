package cn.mchina.robot.service;

import cn.mchina.robot.common.bean.RobotAnswer;
import cn.mchina.robot.redis.RedisService;
import com.webssky.jcseg.Dictionary;
import com.webssky.jcseg.Word;
import com.webssky.jcseg.core.ADictionary;
import com.webssky.jcseg.core.ILexicon;
import com.webssky.jcseg.core.IWord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: yonghui.feng
 * Date: 14-2-12
 * Time: 下午1:51
 * To change this template use File | Settings | File Templates.
 */
@Service("dictionaryManager")
public class DictionaryManager {
    @Resource
    RedisService redisService;

    @Resource
    RobotService robotService;

    /**
     * getDictionary
     * 获取服务号serviceId所对应的字典
     * 首先从Redis中获取，若没有，则从数据库中初始化。
     * @param serviceId
     * @return
     */
    public ADictionary getDictionary(long serviceId) {
        Logger logger = Logger.getLogger(ADictionary.class.getName());

        ADictionary dictionary = (ADictionary) redisService.get(redisService.getRedisKey(RobotAnswer.class, serviceId));

        if (dictionary == null) {
            logger.info("get dictionary from db:" + serviceId);
            dictionary = _getDictionaryFromDB(serviceId);
            if (dictionary != null) {
                redisService.set(redisService.getRedisKey(RobotAnswer.class, serviceId), dictionary);
            }
        } else {
            logger.info("get dictionary from Redis:" + serviceId);
        }

        return dictionary;
    }

    /**
     * clearDictionary
     * 清除Redis缓存中服务号为serviceId的用户字典
     * @param serviceId
     */
    public void clearDictionary(long serviceId) {
        redisService.remove(redisService.getRedisKey(RobotAnswer.class, serviceId));
    }

    /**
     * removeKeyword
     * 删除Redis中服务号为serviceId下的用户字典中的关键字keyword
     * @param serviceId
     * @param keyword
     */
    public void removeKeyword(long serviceId, String keyword){
        ADictionary dictionary = getDictionary(serviceId);
        if (dictionary != null){
            dictionary.remove(ILexicon.CJK_WORDS, keyword);
        }
        redisService.set(redisService.getRedisKey(RobotAnswer.class, serviceId), dictionary);
    }

    /**
     * setDictionary
     * 在radis中设置服务号为serviceId的用户字典为dictionary
     * @param serviceId
     * @param dictionary
     */
    public void setDictionary(long serviceId, ADictionary dictionary) {
        redisService.set(redisService.getRedisKey(RobotAnswer.class, serviceId), dictionary);
    }

    /**
     * addRobotAnswer
     * 在Redis中将用户定义的关键字放入字典
     * @param answer
     */
    public void addRobotAnswer(RobotAnswer answer) {
        _putRobotAnswer(answer);
    }

    /**
     * updateRobotAnswer
     * 在Redis中更新用户定义的关键字字典
     * @param answer
     */
    public void updateRobotAnswer(RobotAnswer answer) {
        _putRobotAnswer(answer);
    }

    private void _putRobotAnswer(RobotAnswer answer) {
        ADictionary dictionary = getDictionary(answer.getServiceId());
        if (dictionary == null) {
            dictionary = new Dictionary();
        }
        Word word = new Word(answer.getKeyWord(), IWord.T_CJK_WORD);
        word.addSyn(answer.getAnswer());
        dictionary.add(ILexicon.CJK_WORDS, answer.getKeyWord(), word);
        setDictionary(answer.getServiceId(), dictionary);
    }

    private ADictionary _getDictionaryFromDB(long serviceId) {
        List<RobotAnswer> answers = robotService.getRobotAnswers(serviceId);
        Dictionary dictionary = new Dictionary();
        for (RobotAnswer answer : answers) {
            Word word = new Word(answer.getKeyWord(), IWord.T_CJK_WORD);
            word.addSyn(answer.getAnswer());
            dictionary.add(ILexicon.CJK_WORDS, answer.getKeyWord(), word);
        }

        return dictionary;
    }
}
