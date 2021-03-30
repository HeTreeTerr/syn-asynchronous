package com.hss.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hss.dao.WorldDao;
import com.hss.domain.World;
import com.hss.message.MessageNotice;
import com.hss.service.ActionSaveService;
import com.hss.service.SaveTheWorldService;
import com.hss.type.FastOrSlow;
import com.hss.type.ResponseCode;
import com.hss.type.WorldStatus;
import com.hss.type.WorldSubmitFlag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Slf4j
@Service
public class SaveTheWorldServiceImpl implements SaveTheWorldService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${myapp.config.fastOrSlow}")
    private String fastOrSlow;

    @Autowired
    private MessageNotice messageNotice;

    @Autowired
    private WorldDao worldDao;

    @Autowired
    private ActionSaveService actionSaveService;

    @Override
    public String synSaveTheWorld(String keyNo) {
        //执行保护逻辑
        Boolean res = actionSaveService.actionSaveWorld(keyNo);
        if(res){
            World world = new World();
            //名称
            world.setKeyNo(keyNo);
            //拯救状态
            world.setStatus(WorldStatus.success.getCode());
            //状态【通知消费者】
            world.setSubmitFlag(WorldSubmitFlag.noticeSuccess.getCode());
            //时间
            world.setActionTime(new Date());
            //添加到注册表
            worldDao.insert(world);
            return "处理请求结果：成功拯救"+ keyNo +"！";
        }
        return null;
    }

    @Override
    public String asySaveTheWorld(String keyNo) {
        //记录请求数据，返回受理成功
        World world = new World();
        //名称
        world.setKeyNo(keyNo);
        //状态【待处理】
        world.setStatus(WorldStatus.wait.getCode());
        //通知标识【受理成功】
        world.setSubmitFlag(WorldSubmitFlag.accper.getCode());
        //时间
        world.setActionTime(new Date());
        //添加到注册表
        worldDao.insert(world);
        //快速通知[异步第一时间处理请求，并返回结果]
        if(fastOrSlow.equals(FastOrSlow.FAST.getName())){
            //启动一个新线程，逻辑处理并通知结果
            messageNotice.setKeyNo(keyNo);
            Thread thread = new Thread(messageNotice);
            thread.start();
        }
        return "受理请求结果:成功受理"+ keyNo +"！";
    }

    @Override
    public Boolean resultNotice(String keyNo) {
        String forObject = restTemplate.getForObject("http://127.0.0.1:8099/saveTheWorldResultNotice?keyNo=" + keyNo, String.class);
        JSONObject jsonObject = JSON.parseObject(forObject);
        Integer code = jsonObject.getInteger("code");
        if(ResponseCode.success.getCode().equals(code)){
            return true;
        }
        return false;
    }


}
