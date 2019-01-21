package cn.mchina.robot.dao.mapper;

import cn.mchina.robot.common.bean.RobotAnswer;
import cn.mchina.robot.common.bean.RobotAnswerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RobotAnswerMapper {
    int countByExample(RobotAnswerExample example);

    int deleteByExample(RobotAnswerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RobotAnswer record);

    int insertSelective(RobotAnswer record);

    List<RobotAnswer> selectByExample(RobotAnswerExample example);

    RobotAnswer selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RobotAnswer record, @Param("example") RobotAnswerExample example);

    int updateByExample(@Param("record") RobotAnswer record, @Param("example") RobotAnswerExample example);

    int updateByPrimaryKeySelective(RobotAnswer record);

    int updateByPrimaryKey(RobotAnswer record);
}