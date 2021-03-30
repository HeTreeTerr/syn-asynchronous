package com.hss.util;

import lombok.Data;

@Data
public class PairUtil<T> {

    private Boolean first;

    private T second;

    public PairUtil(){

    }

    public PairUtil(Boolean first,T second){
        this.first = first;
        this.second = second;
    }
}
