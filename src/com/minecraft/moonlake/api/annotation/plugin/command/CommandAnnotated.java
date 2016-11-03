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

/**
 * <h1>CommandAnnotated</h1>
 * 注解命令类接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface CommandAnnotated {

    /**
     * 获取此命令的名称
     *
     * @return 名称
     */
    String getName();

    /**
     * 获取此命令的别称
     *
     * @return 别称
     */
    String[] getAliases();

    /**
     * 获取此命令的用法
     *
     * @return 用法
     */
    String getUsage();

    /**
     * 获取此命令的注解
     *
     * @return 注解
     */
    String getDescription();

    /**
     * 获取此命令的权限
     *
     * @return 权限
     */
    String getPermission();

    /**
     * 获取此命令的权限消息
     *
     * @return 权限消息
     */
    String getPermissionMessage();
}
