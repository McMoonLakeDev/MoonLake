/*
 * Copyright (C) 2017 The MoonLake Authors
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


package com.minecraft.moonlake.api.packet;

import javax.annotation.Nullable;

/**
 * <h1>PacketNameable</h1>
 * Minecraft 数据包有名的接口
 *
 * @version 1.0
 * @author Month_Light
 */
public interface PacketNameable {

    /**
     * 获取此 Minecraft 数据包的名称
     *
     * @return 数据包名称
     */
    String getPacketName();

    /**
     * 获取此 Minecraft 数据包的类
     *
     * @return 类
     */
    @Nullable
    Class<?> getPacketClass();
}
