package com.test.gateway.service.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    public static boolean getRegEx(String originalText,String regEx ){
        Matcher matcher = Pattern.compile(regEx).matcher(originalText);
        if(matcher.find()){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        RegexUtil.getRegEx("http://10.37.50.8:8080/thirdparty-open/api/partners/weidian/v1/installments", "/thirdparty-open/api/partners.*?");
        RegexUtil.getRegEx("http://10.37.50.8:8080/mashang/test", "/thirdparty-open/api/partners.*?");
    }


}
