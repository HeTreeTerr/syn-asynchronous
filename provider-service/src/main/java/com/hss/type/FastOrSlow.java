package com.hss.type;

public enum FastOrSlow {

    FAST("fast"),
    SLOW("slow");

    private String name;

    FastOrSlow(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
