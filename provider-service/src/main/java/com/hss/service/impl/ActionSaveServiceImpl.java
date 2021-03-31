package com.hss.service.impl;

import com.hss.service.ActionSaveService;
import com.hss.type.SynOrAsy;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 执行保护逻辑
 */
@Service
@Slf4j
public class ActionSaveServiceImpl implements ActionSaveService {

    @Value("${myapp.config.synOrAsy}")
    private String synOrAsy;

    @Value("${myapp.config.coreSynOrAsy}")
    private String coreSynOrAsy;

    /*@Override
    public Boolean actionSaveWorld(String worldName) {
        log.info("核心逻辑处理：拯救{}！",worldName);
        try {
            //模拟处理耗时3秒
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }*/

    @Override
    public Pair<Boolean,Object> actionSaveWorld(String worldName) {
        log.info("核心逻辑处理：拯救{}！",worldName);
        try {
            //模拟处理耗时3秒
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //逻辑处理同步
        if(coreSynOrAsy.equals(SynOrAsy.SYN.getName())){
            Integer status = 1;
            //key是true代表请求成功，tatus代表处理结果
            return new Pair(true,status);
        }else if(synOrAsy.equals(SynOrAsy.ASY.getName()) && coreSynOrAsy.equals(SynOrAsy.ASY.getName())){//逻辑处理异步
            //key是true代表请求成功，status代表处理结果
            return new Pair(true,null);
        }else {
            throw new RuntimeException("同步/异步配置异常");
        }
    }
}
