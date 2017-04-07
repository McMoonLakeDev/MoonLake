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
 
 
package com.minecraft.moonlake.api;

import com.minecraft.moonlake.api.utility.MinecraftBukkitVersion;
import com.minecraft.moonlake.api.utility.MinecraftVersion;

/**
 * <h1>BukkitInfo</h1>
 * Bukkit 服务端信息接口
 *
 * @version 1.1
 * @author Month_Light
 */
public interface BukkitInfo {

    /**
     * 获取 Bukkit 服务器的版本
     *
     * @return 版本
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #currentBukkitVersion()}
     */
    @Deprecated
    String getBukkitVersion(); // TODO 2.0

    /**
     * 获取 Bukkit 服务器的版本号
     *
     * @return 版本号
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #currentBukkitVersion()}
     */
    @Deprecated
    int getReleaseNumber(); // TODO 2.0

    /**
     * 获取此 Minecraft Bukkit 服务器的版本
     *
     * @return Bukkit 版本 | {@code null}
     */
    MinecraftBukkitVersion currentBukkitVersion();

    /**
     * 获取此 Minecraft 服务器的版本
     *
     * @return MC 版本 | {@code null}
     */
    MinecraftVersion currentMCVersion();
}
