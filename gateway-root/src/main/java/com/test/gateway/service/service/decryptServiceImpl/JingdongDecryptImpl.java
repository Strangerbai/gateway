package com.test.gateway.service.service.decryptServiceImpl;

public class JingdongDecryptImpl extends DefaultDecryptImpl {

    @Override
    public String decrypt(String content, String type) {
        logger.info("默认解密器, {}", content);
        return content;
    }
}
