package com.hss.type;

public enum WorldStatus {

    wait(0),
    success(1),
    fail(2);

    private Integer code;

    WorldStatus(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
