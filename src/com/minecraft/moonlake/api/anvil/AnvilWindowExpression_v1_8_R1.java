/*
 * Copyright (C) 2016 The MoonLake Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
 
 
package com.minecraft.moonlake.api.anvil;

import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.ContainerAnvil;
import net.minecraft.server.v1_8_R1.EntityHuman;

/**
 * <h1>AnvilWindowExpression_v1_8_R1</h1>
 * 铁砧窗口 v1.8-R1 版容器类
 *
 * @version 1.0
 * @author Month_Light
 */
class AnvilWindowExpression_v1_8_R1 extends ContainerAnvil {

    /**
     * 铁砧窗口 v1.8-R1 版容器类构造函数
     *
     * @param entityHuman EntityHuman
     */
    public AnvilWindowExpression_v1_8_R1(EntityHuman entityHuman) {

        super(entityHuman.inventory, entityHuman.world, BlockPosition.ZERO, entityHuman);
    }

    @Override
    public boolean a(EntityHuman entityhuman) {

        return true;
    }
}
