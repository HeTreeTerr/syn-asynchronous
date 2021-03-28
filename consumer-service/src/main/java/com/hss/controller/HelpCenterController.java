package com.hss.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hss.type.ResponseCode;
import com.hss.util.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 宇宙和平护卫队
 */
@RestController
@Slf4j
public class HelpCenterController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 消费者要求同步
     * @param keyNo
     * @return
     */
    @RequestMapping(value = "/synHelpTheWorld")
    public Msg synHelpTheWorld(@RequestParam(value = "keyNo") String keyNo){
        log.info("----消费者同步请求----");
        String forObject = restTemplate.getForObject("http://127.0.0.1:9002/saveTheWorld?keyNo="+keyNo, String.class);
        JSONObject jsonObject = JSON.parseObject(forObject);
        Integer code = jsonObject.getInteger("code");
        if(ResponseCode.success.getCode().equals(code)){
            log.info("----提供者处理请求，并返回结果----");
            JSONObject result = jsonObject.getJSONObject("extend");
            String resultMessage = result.getString("resultMessage");
            return Msg.success().add("resultMessage",resultMessage);
        }
        return Msg.fail().add("resultMessage","请求失败！");
    }

    /**
     * 消费者要求异步
     * @param keyNo
     * @return
     */
    @RequestMapping(value = "/asyHelpTheWorld")
    public Msg asyHelpTheWorld(@RequestParam(value = "keyNo") String keyNo){
        log.info("----消费者异步请求----");
        String forObject = restTemplate.getForObject("http://127.0.0.1:9002/saveTheWorld?keyNo="+keyNo, String.class);
        JSONObject jsonObject = JSON.parseObject(forObject);
        Integer code = jsonObject.getInteger("code");
        if(ResponseCode.success.getCode().equals(code)){
            log.info("----提供者保存数据，并返回受理成功----");
            JSONObject result = jsonObject.getJSONObject("extend");
            String resultMessage = result.getString("resultMessage");
            return Msg.success().add("resultMessage",resultMessage);
        }
        return Msg.fail().add("resultMessage","请求失败！");
    }

    /**
     * 提供者异步通知消费者处理成功
     * @param keyNo
     * @return
     */
    @RequestMapping(value = "/saveTheWorldResultNotice")
    public Msg saveTheWorldResultNotice(@RequestParam(value = "keyNo") String keyNo){
        log.info("提供者通知消费者{}处理成功",keyNo);
        //模拟消费者接收结果失败
        /*if(!false){
            return Msg.fail();
        }*/
        return Msg.success().add("resultMessage","提供者通知消费者"+ keyNo +"处理成功");
    }
}
