package com.hss.service.impl;

import com.hss.service.ActionSaveService;
import com.hss.util.PairUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 执行保护逻辑
 */
@Service
@Slf4j
public class ActionSaveServiceImpl implements ActionSaveService {
    @Override
    public Boolean actionSaveWorld(String worldName) {
        log.info("核心逻辑处理：拯救{}！",worldName);
        try {
            //模拟处理耗时3秒
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public PairUtil actionSaveWorldAsy(String worldName) {
        log.info("核心逻辑处理：拯救{}！",worldName);
        PairUtil pairUtil = new PairUtil();
        try {
            //模拟处理耗时3秒
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Map<String,Object> map = new HashMap();
        map.put("status",1);
        pairUtil.setFirst(true);
        pairUtil.setSecond(map);
        return pairUtil;
    }
}
