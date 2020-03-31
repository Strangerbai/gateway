package com.test.gateway.service.controller.request;

import java.util.Map;

public class CreateRouteRequest {

    private String service;

    private Map<String, Map<String,String>> predicates;

    private Integer priority;

    private String uri;

    private String memo;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Map<String, Map<String, String>> getPredicates() {
        return predicates;
    }

    public void setPredicates(Map<String, Map<String, String>> predicates) {
        this.predicates = predicates;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
