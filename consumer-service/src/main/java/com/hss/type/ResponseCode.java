package com.hss.type;

public enum  ResponseCode{
    success(200),
    fail(500),
    noauthor(400);

    private Integer code;

    ResponseCode(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
