package com.test.gateway.route.dto.mapper;

import com.test.gateway.route.dto.model.TbRecordDO;
import com.test.gateway.route.dto.model.TbRecordDOExample;
import com.test.gateway.route.dto.model.TbRecordDOWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TbRecordDOMapper {
    long countByExample(TbRecordDOExample example);

    int deleteByExample(TbRecordDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbRecordDOWithBLOBs record);

    int insertSelective(TbRecordDOWithBLOBs record);

    List<TbRecordDOWithBLOBs> selectByExampleWithBLOBsWithRowbounds(TbRecordDOExample example, RowBounds rowBounds);

    List<TbRecordDOWithBLOBs> selectByExampleWithBLOBs(TbRecordDOExample example);

    List<TbRecordDO> selectByExampleWithRowbounds(TbRecordDOExample example, RowBounds rowBounds);

    List<TbRecordDO> selectByExample(TbRecordDOExample example);

    TbRecordDOWithBLOBs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbRecordDOWithBLOBs record, @Param("example") TbRecordDOExample example);

    int updateByExampleWithBLOBs(@Param("record") TbRecordDOWithBLOBs record, @Param("example") TbRecordDOExample example);

    int updateByExample(@Param("record") TbRecordDO record, @Param("example") TbRecordDOExample example);

    int updateByPrimaryKeySelective(TbRecordDOWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TbRecordDOWithBLOBs record);

    int updateByPrimaryKey(TbRecordDO record);
}