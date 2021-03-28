package com.hss.type;

public enum SynOrAsy {
    //同步
    SYN("syn"),
    //异步
    ASY("asy");

    private String name;

    SynOrAsy(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
