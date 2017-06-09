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


package com.minecraft.moonlake.api;

import com.minecraft.moonlake.api.utility.MinecraftBukkitVersion;
import com.minecraft.moonlake.api.utility.MinecraftVersion;

/**
 * <h1>VersionAdapter</h1>
 * 版本适配器接口
 *
 * @version 1.0
 * @author Month_Light
 */
public interface VersionAdapter {

    /**
     * Minecraft 版本适配器接口
     */
    interface Minecraft extends VersionAdapter {

        /**
         * 获取此版本适配器的 Minecraft 版本
         *
         * @return MinecraftVersion
         */
        MinecraftVersion mcVersion();
    }

    /**
     * Minecraft Bukkit 版本适配器接口
     */
    interface MinecraftBukkit extends VersionAdapter {

        /**
         * 获取此版本适配器的 Minecraft Bukkit 版本
         *
         * @return MinecraftBukkitVersion
         */
        MinecraftBukkitVersion mcBukkitVersion();
    }
}
