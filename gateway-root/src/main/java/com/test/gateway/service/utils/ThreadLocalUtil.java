package com.test.gateway.service.utils;

import com.test.gateway.service.model.GatewayThreadContext;
import com.test.gateway.service.model.ThreadLocalConstant;

public class ThreadLocalUtil {

    // 请求参数
    public static void setRequestContent(String requestContent) {

        GatewayThreadContext.put(ThreadLocalConstant.REQUEST_CONTENT, requestContent);
    }
    public static String getRequestContent() {
        return (String) GatewayThreadContext.get(ThreadLocalConstant.REQUEST_CONTENT);
    }

    // 请求方法
    public static void setRequestMethod(String requestMethod){
        GatewayThreadContext.put(ThreadLocalConstant.REQUEST_METHOD, requestMethod);
    }
    public static String getRequestMethod() {
        return (String) GatewayThreadContext.get(ThreadLocalConstant.REQUEST_METHOD);
    }

    // 请求路径
    public static void setUri(String uri){
        GatewayThreadContext.put(ThreadLocalConstant.URI, uri);
    }
    public static String getUri(){
        return (String) GatewayThreadContext.get(ThreadLocalConstant.URI);
    }

    // 请求地址
    public static void setHost(String host){
        GatewayThreadContext.put(ThreadLocalConstant.HOST, host);
    }
    public static String getHost(){
        return (String) GatewayThreadContext.get(ThreadLocalConstant.HOST);
    }

    // 请求头
    public static void setRequestHeader(String requestHeader){
        GatewayThreadContext.put(ThreadLocalConstant.REQUEST_HEADER, requestHeader);
    }
    public static String getRequestHeader(){
        return (String) GatewayThreadContext.get(ThreadLocalConstant.REQUEST_HEADER);
    }

    // cookie
    public static void setCookie(String cookie){
        GatewayThreadContext.put(ThreadLocalConstant.COOKIE, cookie);
    }
    public static String getCookie(){
        return (String) GatewayThreadContext.get(ThreadLocalConstant.COOKIE);
    }

    // 响应状态码
    public static void setHttpCode(String httpCode){
        if (httpCode == null) {
            return;
        }
        GatewayThreadContext.put(ThreadLocalConstant.RESPONSE_CODE, httpCode);
    }
    public static String getHttpCode(){
        return (String) GatewayThreadContext.get(ThreadLocalConstant.RESPONSE_CODE);
    }

    // 响应头
    public static void setResponseHeader(String responseHeader){
        if (responseHeader == null) {
            return;
        }
        GatewayThreadContext.put(ThreadLocalConstant.RESPONSE_HEADER, responseHeader);
    }
    public static String getResponseHeader(){
        return (String) GatewayThreadContext.get(ThreadLocalConstant.RESPONSE_HEADER);
    }

    // 响应体
    public static void setResponseContent(String responseContent){
        GatewayThreadContext.put(ThreadLocalConstant.RESPONSE_CONTENT, responseContent);
    }
    public static String getResponseContent(){
        return (String) GatewayThreadContext.get(ThreadLocalConstant.RESPONSE_CONTENT);
    }

    // 请求地址
    public static void setFromIp(String fromIp){
        GatewayThreadContext.put(ThreadLocalConstant.FORWARDED_IP, fromIp);
    }
    public static String getFromIp(){
        return (String) GatewayThreadContext.get(ThreadLocalConstant.FORWARDED_IP);
    }

    public static String getService(){
        return (String) GatewayThreadContext.get(ThreadLocalConstant.SERVICE);
    }
    public static void setService(String service){
        GatewayThreadContext.put(ThreadLocalConstant.SERVICE, service);
    }


    /**
     * 线程结束时清除ThreadLocal数据
     */
    public static void clear() {
        GatewayThreadContext.clearContext();
    }


}
