package com.minecraft.moonlake.nms.anvil;

import net.minecraft.server.v1_9_R2.BlockPosition;
import net.minecraft.server.v1_9_R2.ContainerAnvil;
import net.minecraft.server.v1_9_R2.EntityHuman;

/**
 * <h1>AnvilWindow_v1_9_R2</h1>
 * 铁砧窗口 v1.9-R2 版容器类
 *
 * @version 1.0
 * @author Month_Light
 */
class AnvilWindow_v1_9_R2 extends ContainerAnvil {

    public AnvilWindow_v1_9_R2(EntityHuman entityHuman) {

        super(entityHuman.inventory, entityHuman.world, BlockPosition.ZERO, entityHuman);
    }

    @Override
    public boolean a(EntityHuman entityhuman) {
        
        return true;
    }
}
