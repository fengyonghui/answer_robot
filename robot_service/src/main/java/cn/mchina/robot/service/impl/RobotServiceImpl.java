package cn.mchina.robot.service.impl;

import cn.mchina.robot.common.bean.RobotAnswer;
import cn.mchina.robot.common.bean.RobotAnswerExample;
import cn.mchina.robot.dao.mapper.RobotAnswerMapper;
import cn.mchina.robot.service.DictionaryManager;
import cn.mchina.robot.service.JcsegService;
import cn.mchina.robot.service.RobotService;
import com.webssky.jcseg.core.IWord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: yonghui.feng
 * Date: 14-2-11
 * Time: 下午4:09
 * To change this template use File | Settings | File Templates.
 */
@Service("robotService")
@Transactional
public class RobotServiceImpl implements RobotService {
    Logger logger = Logger.getLogger(RobotServiceImpl.class.getName());
    @Resource(type =cn.mchina.robot.dao.mapper.RobotAnswerMapper.class)
    private RobotAnswerMapper robotMapper;

    @Resource
    private JcsegService jcsegService;

    @Resource
    private DictionaryManager dictionaryManager;

    @Override
    public String findAnswer(long serviceId, String keyword) {
        String answer = getDirectAnswer(serviceId, keyword);
        if (answer == null){
            logger.info("get answer from dictionary:" + serviceId + "-" + keyword);
            answer = _getDictionaryAnswer(serviceId, keyword);
        }else {
            logger.info("get answer from db:" + serviceId + "-" + keyword);
        }


        return answer;
    }

    private String _getDictionaryAnswer(long serviceId, String keyword){
        List<IWord> words = jcsegService.getKeywords(serviceId, keyword);

        String answer = null;
        if (words.size() > 0){
            String[] syns = words.get(0).getSyn();
            if (syns != null){
                answer =syns[0];
            }
        }
        return answer;
    }

    @Override
    public RobotAnswer getRobotAnswer(long serviceId, String keyword){
        RobotAnswerExample re = new RobotAnswerExample();
        re.createCriteria().andServiceIdEqualTo(serviceId).andKeyWordEqualTo(keyword);
        List<RobotAnswer> robotAnswers = robotMapper.selectByExample(re);

        return robotAnswers.size()>0?robotAnswers.get(0):null;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public String getDirectAnswer(long serviceId, String keyword){
        RobotAnswer robotAnswer = getRobotAnswer(serviceId, keyword);
        return robotAnswer==null? null:robotAnswer.getAnswer();
    }

    @Override
    public List<RobotAnswer> getRobotAnswers(long serviceId) {
        RobotAnswerExample re = new RobotAnswerExample();
        re.createCriteria().andServiceIdEqualTo(serviceId);

        List<RobotAnswer> answers = robotMapper.selectByExample(re);
        return answers;
    }

    @Override
    public RobotAnswer addRobotAnswer(RobotAnswer robotAnswer) {
        robotMapper.insert(robotAnswer);
        dictionaryManager.addRobotAnswer(robotAnswer);
        return robotAnswer;
    }

    @Override
    public RobotAnswer updateRobotAnswer(RobotAnswer robotAnswer){
         robotMapper.updateByPrimaryKey(robotAnswer);
        dictionaryManager.updateRobotAnswer(robotAnswer);
        return robotAnswer;
    }

    @Override
    public void removeRobotAnswer(long serviceId, String keyword){
        RobotAnswerExample robotAnswerExample = new RobotAnswerExample();
        robotAnswerExample.createCriteria().andServiceIdEqualTo(serviceId)
                .andKeyWordEqualTo(keyword);
       robotMapper.deleteByExample(robotAnswerExample);
       dictionaryManager.removeKeyword(serviceId, keyword);
    }
}
