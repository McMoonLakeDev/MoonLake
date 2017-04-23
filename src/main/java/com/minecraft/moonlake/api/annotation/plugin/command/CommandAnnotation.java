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


package com.minecraft.moonlake.api.annotation.plugin.command;

import com.minecraft.moonlake.api.annotation.plugin.PluginAnnotation;
import com.minecraft.moonlake.exception.MoonLakeException;
import org.bukkit.plugin.Plugin;

import java.util.Set;

/**
 * <h1>CommandAnnotation</h1>
 * 命令注解接口类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @see PluginAnnotation
 */
public interface CommandAnnotation extends PluginAnnotation {

    /**
     * 将指定插件的指定对象加载数据
     *
     * @param plugin 插件
     * @param obj 对象
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果指定加载对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果加载错误则抛出异常
     * @see #load(Plugin, MoonLakeCommand)
     */
    @Override
    @Deprecated
    void load(Plugin plugin, Object obj) throws MoonLakeException;

    /**
     * 将指定插件的指定对象加载数据
     *
     * @param plugin 插件
     * @param obj 对象命令
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果指定加载对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果加载错误则抛出异常
     * @see #registerCommand(Plugin, MoonLakeCommand)
     */
    void load(Plugin plugin, MoonLakeCommand obj) throws MoonLakeException;

    /**
     * 将指定插件的指定对象注册命令
     *
     * @param plugin 插件
     * @param obj 对象
     * @return 命令
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果指定加载对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果注册错误则抛出异常
     * @deprecated @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #registerCommand(Plugin, MoonLakeCommand)}
     * @see #registerCommand(Plugin, MoonLakeCommand)
     */
    @Deprecated
    Set<CommandAnnotated> registerCommand(Plugin plugin, Object obj) throws MoonLakeException; // TODO v2.0

    /**
     * 将指定插件的指定对象注册命令
     *
     * @param plugin 插件
     * @param obj 对象命令
     * @return 命令
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果指定加载对象为 {@code null} 则抛出异常
     * @throws MoonLakeException 如果注册错误则抛出异常
     */
    Set<CommandAnnotated> registerCommand(Plugin plugin, MoonLakeCommand obj) throws MoonLakeException;
}
