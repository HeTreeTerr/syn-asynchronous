package com.hss.message;

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
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 新启动一个线程
 * 模拟消息中间件，异步处理请求并通知消费者
 */
@Component
@Slf4j
public class MessageNotice implements Runnable {

    @Autowired
    private WorldDao worldDao;

    @Autowired
    private SaveTheWorldService saveTheWorldService;

    @Autowired
    private ActionSaveService actionSaveService;

    @Value("${myapp.config.coreSynOrAsy}")
    private String coreSynOrAsy;

    private String keyNo;

    @Override
    public void run() {
        log.info("====使用消息队列迅速处理请求并返回结果start====");
        List<World> worldList = worldDao.findListBySubmitFlag(WorldSubmitFlag.accper.getCode());
        for(World world : worldList){
            if(keyNo.equals(world.getKeyNo())){
                //核心业务逻辑处理
                Pair<Boolean,Object> pair = actionSaveService.actionSaveWorld(keyNo);
                if(pair.getKey()){
                    Integer status = WorldStatus.wait.getCode();
                    Integer submitFlag = WorldSubmitFlag.actionSuccess.getCode();
                    if(coreSynOrAsy.equals(SynOrAsy.SYN.getName())) {//逻辑处理同步
                        //判断处理结果
                        status = Integer.valueOf(pair.getValue().toString());
                        if(StringUtils.isEmpty(status)){
                            throw new RuntimeException("逻辑处理异常");
                        }
                        //通知消费者处理结果
                        Boolean noticeRes = saveTheWorldService.resultNotice(world.getKeyNo(),status);
                        if(noticeRes){//通知消费者成功
                            submitFlag = WorldSubmitFlag.noticeSuccess.getCode();
                        }
                    }
                    //修改通知标识（处理成功）
                    worldDao.update(world.getKeyNo(),status,submitFlag);
                }
            }
        }
        log.info("====使用消息队列迅速处理请求并返回结果end====");
    }

    public void setKeyNo(String keyNo) {
        this.keyNo = keyNo;
    }
}
