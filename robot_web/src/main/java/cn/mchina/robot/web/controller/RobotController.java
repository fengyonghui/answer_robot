package cn.mchina.robot.web.controller;

import cn.mchina.robot.common.bean.RobotAnswer;
import cn.mchina.robot.service.RobotService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: yonghui.feng
 * Date: 14-2-11
 * Time: 下午4:52
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/")
public class RobotController {
    Logger logger = Logger.getLogger(RobotController.class.getName());

    @Resource
    RobotService robotService;

    @RequestMapping(method = RequestMethod.GET, value = "/getAnswer/{serviceId}")
    @ResponseBody
    public Object getAnswer(HttpServletRequest httpRequest, HttpServletResponse httpResponse_p, @PathVariable long serviceId, String keyword) {
        System.out.println("keyword:" + keyword);

        return robotService.findAnswer(serviceId, keyword);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/welcome")
    public String welcome(ModelMap model) {
        return "robot_answer";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/talk")
    public String talk(@RequestParam long serviceId,
                       @RequestParam String discussion,
                       @RequestParam String talk,
                       ModelMap model) {
        logger.info("/robot/talk");

        String answer = robotService.findAnswer(serviceId, talk);
        if (discussion == null){
            discussion="";
        }
        discussion += "=>" +talk + "\n\t\t" + (answer==null?"":answer + "<=") + "\n";

        model.addAttribute("serviceId", serviceId);
        model.addAttribute("discussion", discussion);
        return "robot_answer";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addAnswer")
    public String addAnswer(@RequestParam long serviceId, @RequestParam String keyword, @RequestParam String answer
                            ,@RequestParam(required = false) boolean overwrite, ModelMap model) throws Exception{
        logger.info("keyword:" + keyword + "/answer:" + answer);

        RobotAnswer robotAnswer = robotService.getRobotAnswer(serviceId, keyword);
        if (robotAnswer != null){
            if(overwrite){
                robotAnswer.setAnswer(answer);
                robotService.updateRobotAnswer(robotAnswer);
            }else {
                throw new Exception("This keyword:"+keyword+ " is already exists in " + serviceId);
            }
        }else {
            robotAnswer = new RobotAnswer();
            robotAnswer.setServiceId(serviceId);
            robotAnswer.setKeyWord(keyword);
            robotAnswer.setAnswer(answer);
            robotService.addRobotAnswer(robotAnswer);
        }

        logger.info("robotAnswer:" + robotAnswer.getId());
        model.addAttribute("robotAnswer", robotAnswer);
        return "robot_answer";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/removeAnswer")
    public String removeAnswer(@RequestParam long serviceId, @RequestParam String keyword, ModelMap model) throws Exception{
        logger.info("keyword:" + keyword + "/serviceId:" + serviceId);
        robotService.removeRobotAnswer(serviceId, keyword);
        return "robot_answer";
    }
}
