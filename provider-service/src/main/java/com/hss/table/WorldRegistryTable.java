package com.hss.table;

import com.hss.domain.World;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Vector;

/**
 * 世界注册信息表
 */
@Component
public class WorldRegistryTable {

    private List<World> worldList = new Vector<>();

    public List<World> getWorldList() {
        return worldList;
    }

    public void setWorld(World world){
        worldList.add(world);
    }
}
