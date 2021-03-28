package com.hss.type;

public enum WorldStatus {
    //受理成功
    accper(1),
    //逻辑处理成功
    actionSuccess(2),
    //通知消费者成功
    noticeSuccess(3);

    private Integer code;

    WorldStatus(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
