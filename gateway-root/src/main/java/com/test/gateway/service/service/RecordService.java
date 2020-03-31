package com.test.gateway.service.service;

//import com.vdian.redis.api.VdianRedisClient;
//import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSON;
import com.test.gateway.route.dto.model.TbRecordDO;
import com.test.gateway.route.dto.model.TbRecordDOWithBLOBs;
import com.test.gateway.route.repository.RecordRepository;
import com.test.gateway.service.utils.ThreadLocalUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class RecordService {

	@Autowired
	RecordRepository recordRepository;

	@Autowired
	DecryptService decryptService;

	private static final Logger logger = LoggerFactory.getLogger(RecordService.class);

	public boolean saveRecord(){
		TbRecordDOWithBLOBs tbRecordDOWithBLOBs = new TbRecordDOWithBLOBs();
		if(ThreadLocalUtil.getRequestContent() !=null && ThreadLocalUtil.getRequestContent().getBytes(StandardCharsets.UTF_8).length<65535) {
			String decryptRequest = decryptService.decrypt(ThreadLocalUtil.getRequestContent(), ThreadLocalUtil.getUri(), "request");
			tbRecordDOWithBLOBs.setRequestBody(decryptRequest);
			tbRecordDOWithBLOBs.setService(ThreadLocalUtil.getService());
		}
		tbRecordDOWithBLOBs.setRequestHeader(ThreadLocalUtil.getRequestHeader());
		tbRecordDOWithBLOBs.setResponseBody(ThreadLocalUtil.getResponseContent());
		tbRecordDOWithBLOBs.setResponseHeader(ThreadLocalUtil.getResponseHeader());
		tbRecordDOWithBLOBs.setRequestMethod(ThreadLocalUtil.getRequestMethod());
		tbRecordDOWithBLOBs.setRequestPath(ThreadLocalUtil.getUri());
		tbRecordDOWithBLOBs.setMemo(ThreadLocalUtil.getFromIp());
		if(StringUtils.isNotBlank(ThreadLocalUtil.getHttpCode())){
			tbRecordDOWithBLOBs.setHttpCode(ThreadLocalUtil.getHttpCode());
		}
//		logger.info("saveRecord : {}", JSON.toJSONString(tbRecordDOWithBLOBs));

		return recordRepository.createRecord(tbRecordDOWithBLOBs);
	}

	public boolean updateRecord(String bodyString, String memo){
		List<TbRecordDOWithBLOBs> tbRecordDOS = recordRepository.getRecordByMemo(memo);
		TbRecordDOWithBLOBs record = tbRecordDOS.get(0);
		String decryptBody = decryptService.decrypt(bodyString, record.getRequestPath(), "response");
		return recordRepository.updateResponseBodyByMemo(decryptBody, memo);
	}
}
