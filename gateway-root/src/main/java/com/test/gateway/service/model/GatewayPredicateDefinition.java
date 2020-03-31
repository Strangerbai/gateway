package com.test.gateway.service.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class GatewayPredicateDefinition {
    private String name;

    private Map<String, String> args = new LinkedHashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getArgs() {
        return args;
    }

    public void setArgs(Map<String, String> args) {
        this.args = args;
    }


}
