package com.test.gateway.route.repository;

import com.test.gateway.route.dto.model.TbRecordDO;
import com.test.gateway.route.dto.model.TbRecordDOWithBLOBs;

import java.util.List;

public interface RecordRepository {

    List<TbRecordDO> getRecordByService(String service);

    boolean createRecord(TbRecordDOWithBLOBs tbRecordDO);

    List<TbRecordDO> getRecordByPath(String requestPath);

    boolean updateResponseBodyByMemo(String bodyString, String memo);

    List<TbRecordDOWithBLOBs> getRecordByMemo(String memo);


}
