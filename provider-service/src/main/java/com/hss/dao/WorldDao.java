package com.hss.dao;

import com.hss.domain.World;
import com.hss.table.WorldRegistryTable;
import com.hss.type.WorldStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WorldDao {

    @Autowired
    private WorldRegistryTable worldRegistryTable;

    //新增
    public void insert(World world){
        worldRegistryTable.setWorld(world);
    };

    //删除
    public void delete(String keyNo){
        List<World> worldList = worldRegistryTable.getWorldList();
        for (int i = 0; i < worldList.size(); i++) {
            //根据keyNo查询并唯一确认记录
            World world = worldList.get(i);
            if(world.getKeyNo().equals(keyNo)){
                worldRegistryTable.getWorldList().remove(i);
            }
        }
    }

    //修改
    public void update(String keyNo,Integer status,Integer submitFlag){
        List<World> worldList = worldRegistryTable.getWorldList();
        for (int i = 0; i < worldList.size(); i++) {
            //根据keyNo查询并唯一确认记录
            World world = worldList.get(i);
            if(world.getKeyNo().equals(keyNo)){
                world.setStatus(status);
                world.setSubmitFlag(submitFlag);
            }
        }
    }

    //查询
    public List<World> findAll(){
        List<World> worldList = worldRegistryTable.getWorldList();
        return worldList;
    }

    //由通知标识查询
    public List<World> findListBySubmitFlag(Integer submitFlag){
        List<World> worldList = worldRegistryTable.getWorldList();
        List<World> worldListBySubmitFlag = new ArrayList<>();
        for(World world : worldList){
            if(submitFlag == world.getSubmitFlag()){
                worldListBySubmitFlag.add(world);
            }
        }
        return worldListBySubmitFlag;
    }
}
