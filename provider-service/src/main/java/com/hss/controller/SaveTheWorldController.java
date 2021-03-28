package com.hss.controller;

import com.hss.dao.WorldDao;
import com.hss.domain.World;
import com.hss.service.SaveTheWorldService;
import com.hss.type.SynOrAsy;
import com.hss.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * [拯救世界]控制层
 */
@RestController
public class SaveTheWorldController {

    @Value("${myapp.config.synOrAsy}")
    private String synOrAsy;

    @Autowired
    private SaveTheWorldService saveTheWorldService;

    @Autowired
    private WorldDao worldDao;

    /**
     * 同一个接口，适配同步异步
     * @return
     */
    @RequestMapping(value = "/saveTheWorld")
    public Msg saveTheWorld(@RequestParam(value = "keyNo") String keyNo){

        if(synOrAsy.equals(SynOrAsy.SYN.getName())){//同步
            //核心逻辑处理
            String resultMessage = saveTheWorldService.synSaveTheWorld(keyNo);
            if(!StringUtils.isEmpty(resultMessage)){
                return Msg.success().add("resultMessage",resultMessage);
            }else {
                return Msg.fail().add("resultMessage","处理请求失败！");
            }
        }else if(synOrAsy.equals(SynOrAsy.ASY.getName())){//异步
            //保留请求数据，返回受理成功
            String resultMessage = saveTheWorldService.asySaveTheWorld(keyNo);
            return Msg.success().add("resultMessage",resultMessage);
        }else {
            return Msg.fail().add("resultMessage","程序异常");
        }
    }


    @RequestMapping(value = "/worldInfos")
    public Msg worldInfos(){
        //获取世界列表
        List<World> worldList = worldDao.findAll();
        return Msg.success().add("worldList",worldList);
    }

//----------------------------基础，供参考--------------------------------------
    /**
     * 同步请求【拯救世界】
     * @return
     */
    @RequestMapping(value = "/synSaveTheWorld")
    public Msg synSaveTheWorld(@RequestParam(value = "keyNo") String keyNo){
        //核心逻辑处理
        String resultMessage = saveTheWorldService.synSaveTheWorld(keyNo);
        if(!StringUtils.isEmpty(resultMessage)){
            return Msg.success().add("resultMessage",resultMessage);
        }else {
            return Msg.fail().add("resultMessage","处理请求失败！");
        }
    }

    /**
     * 异步请求【拯救世界】
     * @return
     */
    @RequestMapping(value = "/asySaveTheWorld")
    public Msg asySaveTheWorld(@RequestParam(value = "keyNo") String keyNo){
        //保留请求数据，返回受理成功
        String resultMessage = saveTheWorldService.asySaveTheWorld(keyNo);
        return Msg.success().add("resultMessage",resultMessage);
    }
}
