package cn.mchina.robot.service;

import cn.mchina.robot.common.bean.RobotAnswer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yonghui.feng
 * Date: 14-2-11
 * Time: 下午4:07
 * To change this template use File | Settings | File Templates.
 */
public interface RobotService {
    /**
     * findAnswer
     * 根据keyword,获取指定服务号(serviceId)的回复信息。
     *  1. 首先查看数据库中是否存在关键字的回复信息，有则返回；
     *  2. 没有，则对关键字（keyword）进行分词，取长，再进行数据库匹配。
     * @param serviceId 微信服务号
     * @param keyword 自动回复关键字
     * @return  回复信息
     */
    public String findAnswer(long serviceId, String keyword);

    /**
     *  getDirectAnswer
     *  根据keyword,从数据库（DB）获取指定服务号(serviceId)的回复信息。
     * @param serviceId 微信服务号
     * @param keyword 自动回复关键字
     * @return 回复信息
     */
    public String getDirectAnswer(long serviceId, String keyword);

    /**
     *  getRobotAnswer
     *  根据服务号（serviceId）和关键字（keyword）获取自动回复对象（RobotAnswer）
     * @param serviceId 微信服务号
     * @param keyword 自动回复关键字
     * @return
     */
    public RobotAnswer getRobotAnswer(long serviceId, String keyword);

    /**
     * getRobotAnswers
     * 根据服务号（serviceId）查找所有回复对象
     * @param serviceId 微信服务号
     * @return
     */
    public List<RobotAnswer> getRobotAnswers(long serviceId);

    /**
     * addRobotAnswer
     * 向数据库中添加一条自动回复
     * @param robotAnswer  自动回复对象
     * @return  自动回复对象
     */
    public RobotAnswer addRobotAnswer(RobotAnswer robotAnswer);

    /**
     * updateRobotAnswer
     * 更新数据库中的一条自动回复
     * @param robotAnswer  自动回复对象
     * @return  自动回复对象
     */
    public RobotAnswer updateRobotAnswer(RobotAnswer robotAnswer);

    /**
     * removeRobotAnswer
     * 删除数据库中的一条回复信息
     * @param serviceId
     * @param keyword
     */
    public void removeRobotAnswer(long serviceId, String keyword);
}
