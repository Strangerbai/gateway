package com.test.gateway.service.service;

import com.test.gateway.service.model.DecryptType;
import com.test.gateway.service.utils.RegexUtil;
import com.test.gateway.service.utils.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class DecryptService{

    protected static final Logger logger = LoggerFactory.getLogger(DecryptService.class);

    protected Map<String, DecryptFacade> decryptFacadeMap;


    public void setDecryptFacadeMap(Map<String, DecryptFacade> methodValidateStrategyMap) {
        this.decryptFacadeMap = methodValidateStrategyMap;
    }

    private DecryptFacade getStrategy(String decryptTypeMsg) {
        for (DecryptType decryptType : DecryptType.values()) {
            if(RegexUtil.getRegEx(decryptTypeMsg, decryptType.getMsg())) {
                ThreadLocalUtil.setService(decryptType.getMemo());
                return this.decryptFacadeMap.get(decryptType.getCode());
            }
        }
        ThreadLocalUtil.setService("default");
        return this.decryptFacadeMap.get("0");
    }

    public String decrypt(String content, String uri, String type){
        logger.info("content : {}, uri : {}", content, uri);
        DecryptFacade decryptFacade = this.getStrategy(uri);
        return decryptFacade.decrypt(content, type);
    }



}
