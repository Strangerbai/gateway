package com.test.gateway.route.repository;

import com.test.gateway.route.dto.mapper.TbRecordDOMapper;
import com.test.gateway.route.dto.model.TbRecordDO;
import com.test.gateway.route.dto.model.TbRecordDOExample;
import com.test.gateway.route.dto.model.TbRecordDOWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecordRepositoryImpl implements RecordRepository {

    @Autowired
    TbRecordDOMapper tbRecordDOMapper;

    @Override
    public List<TbRecordDO> getRecordByService(String service) {
        TbRecordDOExample example = new TbRecordDOExample();
        example.createCriteria().andServiceEqualTo(service);
        return tbRecordDOMapper.selectByExample(example);
    }

    @Override
    public boolean createRecord(TbRecordDOWithBLOBs tbRecordDO) {
        return  tbRecordDOMapper.insertSelective(tbRecordDO)==1;
    }

    @Override
    public List<TbRecordDO> getRecordByPath(String requestPath) {
        TbRecordDOExample example = new TbRecordDOExample();
        example.createCriteria().andRequestPathEqualTo(requestPath);
        return tbRecordDOMapper.selectByExample(example);
    }

    @Override
    public boolean updateResponseBodyByMemo(String bodyString, String memo) {
        TbRecordDOWithBLOBs tbRecordDOWithBLOBs = new TbRecordDOWithBLOBs();
        tbRecordDOWithBLOBs.setResponseBody(bodyString);
        TbRecordDOExample example = new TbRecordDOExample();
        example.createCriteria().andMemoEqualTo(memo);
        return tbRecordDOMapper.updateByExampleSelective(tbRecordDOWithBLOBs, example)==1;
    }

    @Override
    public List<TbRecordDOWithBLOBs> getRecordByMemo(String memo) {
        TbRecordDOExample example = new TbRecordDOExample();
        example.createCriteria().andMemoEqualTo(memo);
        return tbRecordDOMapper.selectByExampleWithBLOBs(example);
    }
}
