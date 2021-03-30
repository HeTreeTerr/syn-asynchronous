package com.hss.type;

public enum WorldSubmitFlag {

    //受理成功
    accper(1),
    //逻辑处理成功
    actionSuccess(2),
    //通知消费者成功
    noticeSuccess(3);

    private Integer code;

    WorldSubmitFlag(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
