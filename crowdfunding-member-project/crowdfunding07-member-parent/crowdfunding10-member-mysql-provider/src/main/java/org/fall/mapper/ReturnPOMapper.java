package org.fall.mapper;

import org.apache.ibatis.annotations.Param;
import org.fall.entity.po.ReturnPO;
import org.fall.entity.po.ReturnPOExample;

import java.util.List;

public interface ReturnPOMapper {
    int countByExample(ReturnPOExample example);

    int deleteByExample(ReturnPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ReturnPO record);

    int insertSelective(ReturnPO record);

    List<ReturnPO> selectByExample(ReturnPOExample example);

    ReturnPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ReturnPO record, @Param("example") ReturnPOExample example);

    int updateByExample(@Param("record") ReturnPO record, @Param("example") ReturnPOExample example);

    int updateByPrimaryKeySelective(ReturnPO record);

    int updateByPrimaryKey(ReturnPO record);

    void insertReturnPOList(@Param("projectId") Integer projectId, @Param("returnPOList") List<ReturnPO> returnPOList);
}