package cn.mchina.robot.test;

import cn.mchina.robot.service.JcsegService;
import com.webssky.jcseg.core.IWord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yonghui.feng
 * Date: 14-2-12
 * Time: 下午4:31
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-robot-service.xml"})
public class JcsegServiceTest {
    @Resource
    JcsegService jcsegService;

    @Test
    public void testGetKeywords(){
        List<IWord> keywords = jcsegService.getKeywords(10000l, "hello你好你在哪？");
        for(IWord keyword : keywords){
            System.out.println(keyword.getValue());
        }

        keywords = jcsegService.getKeywords(10001l, "hello你好你在哪？");
        for(IWord keyword : keywords){
            System.out.println(keyword.getValue());
        }
    }
}
