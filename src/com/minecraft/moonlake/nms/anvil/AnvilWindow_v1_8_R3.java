package com.minecraft.moonlake.nms.anvil;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ContainerAnvil;
import net.minecraft.server.v1_8_R3.EntityHuman;

/**
 * Created by MoonLake on 2016/10/4.
 */
class AnvilWindow_v1_8_R3 extends ContainerAnvil {

    public AnvilWindow_v1_8_R3(EntityHuman entityHuman) {

        super(entityHuman.inventory, entityHuman.world, BlockPosition.ZERO, entityHuman);
    }

    @Override
    public boolean a(EntityHuman entityhuman) {

        return true;
    }
}
