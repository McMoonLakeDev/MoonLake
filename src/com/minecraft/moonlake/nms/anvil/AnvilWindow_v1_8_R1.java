package com.minecraft.moonlake.nms.anvil;

import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.ContainerAnvil;
import net.minecraft.server.v1_8_R1.EntityHuman;

/**
 * Created by MoonLake on 2016/10/4.
 */
class AnvilWindow_v1_8_R1 extends ContainerAnvil {

    public AnvilWindow_v1_8_R1(EntityHuman entityHuman) {

        super(entityHuman.inventory, entityHuman.world, BlockPosition.ZERO, entityHuman);
    }

    @Override
    public boolean a(EntityHuman entityhuman) {

        return true;
    }
}
