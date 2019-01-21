package cn.mchina.robot.test;

import cn.mchina.robot.common.bean.RobotAnswer;
import cn.mchina.robot.dao.mapper.RobotAnswerMapper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


/**
 * Created with IntelliJ IDEA.
 * User: yonghui.feng
 * Date: 14-2-11
 * Time: 下午3:40
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-dao.xml"})
public class RobotAnswerTest {
    @Resource(type =RobotAnswerMapper.class)
    private RobotAnswerMapper robotMapper;
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
        robotAnswer.setServiceId(10000l);
        robotAnswer.setKeyWord("1");
        robotAnswer.setAnswer("你正在玩游戏");

        robotMapper.insert(robotAnswer);
        System.out.println("robotAnswer:" + robotAnswer.getId());
        assert robotAnswer.getId() != null;
    }

}
