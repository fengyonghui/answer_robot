package cn.mchina.robot.test.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created with IntelliJ IDEA.
 * User: yonghui.feng
 * Date: 14-2-11
 * Time: 下午5:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring-web.xml")
public class RobotControllerTest {
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void simple() throws Exception {
        String test = mockMvc.perform(get("/getAnswer/10000")
                .param("keyword", "hello"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println(test);
        assert test.equals("\"hi\"");
    }

    @Test(expected = Exception.class)
    public void testAddAnswer() throws Exception {
        String test = mockMvc.perform(post("/addAnswer")
                .param("serviceId", "10000")
                .param("keyword", "3")
                .param("answer", "是什么"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }
}
