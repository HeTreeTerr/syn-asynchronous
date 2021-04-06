package com.hss.task;

import com.hss.dao.WorldDao;
import com.hss.domain.World;
import com.hss.service.ActionSaveService;
import com.hss.service.SaveTheWorldService;
import com.hss.type.SynOrAsy;
import com.hss.type.WorldStatus;
import com.hss.type.WorldSubmitFlag;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
@Slf4j
public class SubmitTask {

    @Value("${myapp.config.synOrAsy}")
    private String synOrAsy;

    @Value("${myapp.config.coreSynOrAsy}")
    private String coreSynOrAsy;

    @Autowired
    private WorldDao worldDao;

    @Autowired
    private SaveTheWorldService saveTheWorldService;

    @Autowired
    private ActionSaveService actionSaveService;

    /**
     * 每10秒，扫描受理成功，等待逻辑处理的记录
     * 逻辑处理并修改标识
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void actionSaveTheWorldTask(){
        if(synOrAsy.equals(SynOrAsy.ASY.getName())){//消费者异步
            log.info("=====异步处理请求，核心逻辑处理start====");
            List<World> worldList = worldDao.findListBySubmitFlag(WorldSubmitFlag.accper.getCode());
            for(World world : worldList){
                //核心业务逻辑处理
                Pair<Boolean,Object> pair = actionSaveService.actionSaveWorld(world.getKeyNo());
                //判断请求结果
                if(pair.getKey()){
                    Integer submitFlag = WorldSubmitFlag.actionSuccess.getCode();
                    Integer status = WorldStatus.wait.getCode();
                    if(coreSynOrAsy.equals(SynOrAsy.SYN.getName())){//逻辑处理同步
                        //判断处理结果
                        status = Integer.valueOf(pair.getValue().toString());
                        if(StringUtils.isEmpty(status)){
                            throw new RuntimeException("逻辑处理异常");
                        }
                    }
                    //修改通知标识（逻辑处理成功）
                    worldDao.update(world.getKeyNo(),status,submitFlag);
                }
            }
            log.info("====异步处理请求，核心逻辑处理end====");
        }
    }

    /**
     * 每10秒，扫描逻辑处理成功，等待通知消费者的记录
     * 通知消费者并修改标识
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void noticeConsumerSaveResultTask(){
        if(synOrAsy.equals(SynOrAsy.ASY.getName())){//消费者异步
            log.info("=====异步通知消费者处理结果start====");
            List<World> worldList = worldDao.findListBySubmitFlag(WorldSubmitFlag.actionSuccess.getCode());
            for(World world : worldList){
                if(world.getStatus() == WorldStatus.wait.getCode()){
                    continue;
                }
                //通知消费者处理结果
                Boolean noticeRes = saveTheWorldService.resultNotice(world.getKeyNo(),world.getStatus());
                if(noticeRes){
                    Integer submitFlag = WorldSubmitFlag.noticeSuccess.getCode();
                    //修改通知标识（通知消费者成功）
                    worldDao.update(world.getKeyNo(),world.getStatus(),submitFlag);
                }
            }
            log.info("====异步通知消费者处理结果end====");
        }
    }
}
