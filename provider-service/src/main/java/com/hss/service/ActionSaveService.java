package com.hss.service;


import javafx.util.Pair;

/**
 * 执行保护接口
 */
public interface ActionSaveService {

    /**
     * 在本服务上的逻辑处理，只需要返回true或false
     * @param worldName
     * @return
     */
    //Boolean actionSaveWorld(String worldName);

    /**
     * 请求第三方
     * @param worldName
     * @return
     */
    Pair<Boolean,Object> actionSaveWorld(String worldName);
}
