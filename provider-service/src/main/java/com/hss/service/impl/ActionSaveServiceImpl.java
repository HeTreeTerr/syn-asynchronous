package com.hss.service.impl;

import com.hss.service.ActionSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
