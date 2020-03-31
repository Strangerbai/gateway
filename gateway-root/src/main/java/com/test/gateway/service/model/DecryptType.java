package com.test.gateway.service.model;

public enum DecryptType {

    DEFAULT("0", "default", "default"),
    MASHANG("1", "/mashang/api.*?", "mashang"),
    GUANGDA("2", "/ex/api/v2.*?", "guangda"),
    ;


    private String code;

    private String msg;

    private String memo;

    DecryptType(String code, String msg, String memo) {
        this.code = code;
        this.msg = msg;
        this.memo = memo;
    }

    public static DecryptType getByMsg(String msg) {
        for (DecryptType decryptType : DecryptType.values()) {
            if (decryptType.getMsg().equals(msg)) {
                return decryptType;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
