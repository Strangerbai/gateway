package com.test.gateway.route.dto.mapper;

import com.test.gateway.route.dto.model.TbRouteDO;
import com.test.gateway.route.dto.model.TbRouteDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TbRouteDOMapper {
    long countByExample(TbRouteDOExample example);

    int deleteByExample(TbRouteDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbRouteDO record);

    int insertSelective(TbRouteDO record);

    List<TbRouteDO> selectByExampleWithRowbounds(TbRouteDOExample example, RowBounds rowBounds);

    List<TbRouteDO> selectByExample(TbRouteDOExample example);

    TbRouteDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbRouteDO record, @Param("example") TbRouteDOExample example);

    int updateByExample(@Param("record") TbRouteDO record, @Param("example") TbRouteDOExample example);

    int updateByPrimaryKeySelective(TbRouteDO record);

    int updateByPrimaryKey(TbRouteDO record);
}