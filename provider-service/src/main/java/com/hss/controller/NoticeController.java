package com.hss.controller;

import com.hss.dao.WorldDao;
import com.hss.domain.World;
import com.hss.type.SynOrAsy;
import com.hss.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 处理结果通知
 */
@RestController
public class NoticeController {

    @Value("${myapp.config.coreSynOrAsy}")
    private String coreSynOrAsy;

    @Autowired
    private WorldDao worldDao;

    @RequestMapping(value = "/resNotice")
    public Msg saveTheWorld(@RequestParam(value = "keyNo") String keyNo,
                            @RequestParam(value = "status") Integer status){
        if(coreSynOrAsy.equals(SynOrAsy.SYN.getName())){
            return Msg.fail().add("resultMessage","同步请求，不能回执");
        }
        List<World> worldList = worldDao.findAll();
        for(World world : worldList){
            if(keyNo.equals(world.getKeyNo())){
                //保存处理结果
                worldDao.update(keyNo,status,world.getSubmitFlag());
            }
        }
        return Msg.success();
    }
}
