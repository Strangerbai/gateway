package com.test.gateway.service.service.decryptServiceImpl;

import com.test.gateway.service.service.DecryptFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultDecryptImpl implements DecryptFacade {

    protected static final Logger logger = LoggerFactory.getLogger(DefaultDecryptImpl.class);

    @Override
    public String decrypt(String content, String type) {
        logger.info("默认解密器, {}", content);
        return content;
    }
}
