package com.hss.table;

import com.hss.domain.World;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 世界注册信息表
 */
@Component
public class WorldRegistryTable {
    /*
    高并发优化
    volatile 保证可见性、禁止指令重排
    CopyOnWriteArrayList  保证高并发安全
     */
    private volatile List<World> worldList = new CopyOnWriteArrayList<>();

    public List<World> getWorldList() {
        return worldList;
    }

    public void setWorld(World world){
        worldList.add(world);
    }
}
