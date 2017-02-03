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

import com.minecraft.moonlake.MoonLakePlugin;
import com.minecraft.moonlake.MoonLakePluginConfig;

import java.util.logging.Logger;

/**
 * <hr />
 * <div>
 *     <h1>主实例对象API接口提供 (子API、特殊函数等等)</h1>
 *     <p>By Month_Light Ver: 1.0</p>
 * </div>
 * <hr />
 *
 * @version 1.0
 * @author Month_Light
 */
public interface MoonLake extends MoonLakeCore, MoonLakeInfo, BukkitInfo {

    /**
     * 获取控制台日志对象
     *
     * @return 日志对象
     */
    Logger getLogger();

    /**
     * 获取月色之湖插件对象
     *
     * @return 插件对象
     */
    MoonLakePlugin getPlugin();

    /**
     * 获取月色之湖插件配置对象
     *
     * @return 插件配置对象
     */
    MoonLakePluginConfig getConfiguration();
}
