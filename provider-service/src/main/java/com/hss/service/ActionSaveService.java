package com.hss.service;

import com.hss.util.PairUtil;

/**
 * 执行保护接口
 */
public interface ActionSaveService {

    Boolean actionSaveWorld(String worldName);

    PairUtil actionSaveWorldAsy(String worldName);
}
