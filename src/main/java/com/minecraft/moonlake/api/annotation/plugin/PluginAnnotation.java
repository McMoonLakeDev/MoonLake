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


package com.minecraft.moonlake.api.annotation.plugin;

import com.minecraft.moonlake.exception.MoonLakeException;
import org.bukkit.plugin.Plugin;

/**
 * <h1>PluginAnnotation</h1>
 * 插件注解类接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface PluginAnnotation {

    /**
     * 将指定插件的指定对象加载数据
     *
     * @param plugin 插件
     * @param obj 对象
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果指定加载对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果加载错误则抛出异常
     */
    void load(Plugin plugin, Object obj) throws MoonLakeException;
}
