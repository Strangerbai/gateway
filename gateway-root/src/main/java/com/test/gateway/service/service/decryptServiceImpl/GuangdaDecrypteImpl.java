package com.test.gateway.service.service.decryptServiceImpl;

import com.test.gateway.service.utils.AESUtil;
import org.apache.commons.lang3.StringUtils;

import java.net.URLDecoder;


public class GuangdaDecrypteImpl extends DefaultDecryptImpl {


    @Override
    public String decrypt(String content, String type) {
        try{
            String contentProcess = content;
            if(type.equals("request")){
                logger.info("请求参数解密");
                String[] contentList = content.trim().split("=");
                contentProcess = URLDecoder.decode(contentList[1], "utf-8");
            }
            String aesKey = "sxD8KO79EDK0N0AJ";
            String decryptData = AESUtil.decryptAfterBase64Decode(contentProcess, aesKey);
            if (StringUtils.isBlank(decryptData)) {
                logger.error("广达解密参数为空！");
                return content;
            }
            return decryptData;
        } catch (Exception e){
            logger.error("解密失败返回原始数据");
            return content;
        }
    }

    public static void main(String[] args) {
        GuangdaDecrypteImpl guangdaDecrypte = new GuangdaDecrypteImpl();
        guangdaDecrypte.decrypt("content=GAFLNBRxJC2OkpLUOfGxwiDG1QxYzzx8a%2BU66E8ukDPeWzd8knjA9UFVD9AMOSdZ3fSx1SW4ne19tmTHWhXEU32diEtcgaEi9ETLtZ%2But8CNRFu7Js%2Bduf4DVNUPRJ3swdkeyUxRJ0r6a41lsq6P1A%3D%3D", "request");
    }
}
