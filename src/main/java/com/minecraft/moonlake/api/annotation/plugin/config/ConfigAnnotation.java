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


package com.minecraft.moonlake.api.annotation.plugin.config;

import com.minecraft.moonlake.api.annotation.plugin.PluginAnnotation;
import com.minecraft.moonlake.exception.MoonLakeException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

/**
 * <h1>ConfigAnnotation</h1>
 * 配置文件注解接口类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @see PluginAnnotation
 */
public interface ConfigAnnotation extends PluginAnnotation {

    /**
     * 将指定插件的指定对象加载配置文件数据
     *
     * @param plugin 插件
     * @param file 文件名
     * @param obj 对象
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果文件名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果指定加载对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果加载错误则抛出异常
     */
    void load(Plugin plugin, String file, Object obj) throws MoonLakeException;

    /**
     * 将指定插件的指定对象加载指定配置文件数据
     *
     * @param plugin 插件
     * @param file 文件
     * @param obj 对象
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果文件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果指定加载对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果加载错误则抛出异常
     */
    void load(Plugin plugin, File file, Object obj) throws MoonLakeException;

    /**
     * 将指定插件的指定对象加载指定配置文件数据
     *
     * @param plugin 插件
     * @param config 配置文件
     * @param obj 对象
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果配置文件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果指定加载对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果加载错误则抛出异常
     */
    void load(Plugin plugin, FileConfiguration config, Object obj) throws MoonLakeException;

    /**
     * 将指定插件的指定对象保存配置文件数据
     *
     * @param plugin 插件
     * @param obj 对象
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果指定加载对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果保存错误则抛出异常
     */
    void save(Plugin plugin, Object obj) throws MoonLakeException;

    /**
     * 将指定插件的指定对象保存指定配置文件数据
     *
     * @param plugin 插件
     * @param file 文件名
     * @param obj 对象
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果文件名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果指定加载对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果保存错误则抛出异常
     */
    void save(Plugin plugin, String file, Object obj) throws MoonLakeException;

    /**
     * 将指定插件的指定对象保存指定配置文件数据
     *
     * @param plugin 插件
     * @param file 文件
     * @param obj 对象
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果文件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果指定加载对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果保存错误则抛出异常
     */
    void save(Plugin plugin, File file, Object obj) throws MoonLakeException;
}
