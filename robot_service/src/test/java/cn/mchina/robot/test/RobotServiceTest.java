package cn.mchina.robot.test;

import cn.mchina.robot.common.bean.RobotAnswer;
import cn.mchina.robot.common.util.JSONUtil;
import cn.mchina.robot.service.RobotService;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yonghui.feng
 * Date: 14-2-11
 * Time: 下午4:25
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-robot-service.xml"})
public class RobotServiceTest {
    @Resource
    private RobotService robotService;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

    }

    @Test (expected = DuplicateKeyException.class)
    public void testAdd() throws Exception {
        RobotAnswer robotAnswer = new RobotAnswer();
        robotAnswer.setServiceId(10001l);
        robotAnswer.setKeyWord("你在哪？");
        robotAnswer.setAnswer("北京");

        robotService.addRobotAnswer(robotAnswer);
        System.out.println("robotAnswer:" + robotAnswer.getId());
        assert robotAnswer.getId() != null;
    }

    @Test
    public void testFindAnswer(){
        long serviceId = 10000l;
        String keyWord = "hello";
        String answer = robotService.findAnswer(serviceId, keyWord);

        System.out.println("answer:" + answer);
        assert answer!= null;
    }

    @Test
    public void testGetRobotAnswers(){
        List<RobotAnswer> answers = robotService.getRobotAnswers(10000l);
        for (RobotAnswer answer : answers){
            System.out.println(JSONUtil.toJson(answer));
        }
    }

    @Test
    public void testRemoveAnswer(){
        long serviceId = 10001l;
        String keyword = "测试123";
        String answer = "北京";
        RobotAnswer robotAnswer = new RobotAnswer();
        robotAnswer.setServiceId(serviceId);
        robotAnswer.setKeyWord(keyword);
        robotAnswer.setAnswer(answer);

        robotService.addRobotAnswer(robotAnswer);
        RobotAnswer answer1 = robotService.getRobotAnswer(serviceId, keyword);
        assert answer1 != null;

        robotService.removeRobotAnswer(serviceId, keyword);
        answer1 = robotService.getRobotAnswer(serviceId, keyword);
        assert answer1 == null;
    }

}
