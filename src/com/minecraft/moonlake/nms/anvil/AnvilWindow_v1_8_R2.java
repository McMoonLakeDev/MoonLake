package com.minecraft.moonlake.nms.anvil;

import net.minecraft.server.v1_8_R2.BlockPosition;
import net.minecraft.server.v1_8_R2.ContainerAnvil;
import net.minecraft.server.v1_8_R2.EntityHuman;

/**
 * Created by MoonLake on 2016/10/4.
 */
class AnvilWindow_v1_8_R2 extends ContainerAnvil {

    public AnvilWindow_v1_8_R2(EntityHuman entityHuman) {

        super(entityHuman.inventory, entityHuman.world, BlockPosition.ZERO, entityHuman);
    }

    @Override
    public boolean a(EntityHuman entityhuman) {

        return true;
    }
}
