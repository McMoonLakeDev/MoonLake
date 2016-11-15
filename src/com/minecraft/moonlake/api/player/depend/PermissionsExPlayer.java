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


package com.minecraft.moonlake.api.player.depend;

import com.minecraft.moonlake.exception.CannotDependException;

import java.util.List;

/**
 * <h1>PermissionsExPlayer</h1>
 * 依赖权限插件玩家接口类 # 依赖 <a href="https://github.com/PEXPlugins/PermissionsEx" target="_blank">PermissionsEx</a> 插件
 *
 * @version 1.0
 * @author Month_Light
 */
public interface PermissionsExPlayer {

    /**
     * 将此玩家添加指定权限组
     *
     * @param group 权限组名
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     * @throws IllegalArgumentException 如果权限组名对象为 {@code null} 则抛出异常
     */
    void addPermissionsExGroup(String group) throws CannotDependException;

    /**
     * 将此玩家在指定世界添加指定权限组
     *
     * @param group 权限组名
     * @param world 世界名
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     * @throws IllegalArgumentException 如果权限组名对象为 {@code null} 则抛出异常
     */
    void addPermissionsExGroup(String group, String world) throws CannotDependException;

    /**
     * 将此玩家删除指定权限组
     *
     * @param group 权限组名
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     * @throws IllegalArgumentException 如果权限组名对象为 {@code null} 则抛出异常
     */
    void removePermissionsExGroup(String group) throws CannotDependException;

    /**
     * 将此玩家在指定世界删除指定权限组
     *
     * @param group 权限组名
     * @param world 世界名
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     * @throws IllegalArgumentException 如果权限组名对象为 {@code null} 则抛出异常
     */
    void removePermissionsExGroup(String group, String world) throws CannotDependException;

    /**
     * 获取此玩家是否在指定权限组
     *
     * @param group 权限组名
     * @return 是否在指定权限组
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     * @throws IllegalArgumentException 如果权限组名对象为 {@code null} 则抛出异常
     */
    boolean inPermissionsExGroup(String group) throws CannotDependException;

    /**
     * 获取此玩家是否在指定世界的指定权限组
     *
     * @param group 权限组名
     * @param world 世界名
     * @return 是否在指定世界的指定权限组
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     * @throws IllegalArgumentException 如果权限组名对象为 {@code null} 则抛出异常
     */
    boolean inPermissionsExGroup(String group, String world) throws CannotDependException;

    /**
     * 获取此玩家是否在指定权限组
     *
     * @param group 权限组名
     * @param checkInheritance 是否子权限组也进行检测
     * @return 是否在指定权限组
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     * @throws IllegalArgumentException 如果权限组名对象为 {@code null} 则抛出异常
     */
    boolean inPermissionsExGroup(String group, boolean checkInheritance) throws CannotDependException;

    /**
     * 获取此玩家是否在指定世界的指定权限组
     *
     * @param group 权限组名
     * @param world 世界名
     * @param checkInheritance 是否子权限组也进行检测
     * @return 是否在指定世界的指定权限组
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     * @throws IllegalArgumentException 如果权限组名对象为 {@code null} 则抛出异常
     */
    boolean inPermissionsExGroup(String group, String world, boolean checkInheritance) throws CannotDependException;

    /**
     * 获取此玩家拥有的权限列表
     *
     * @return 权限列表
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    List<String> getPermissionsExPer() throws CannotDependException;

    /**
     * 获取此玩家在指定世界拥有的权限列表
     *
     * @param world 世界名
     * @return 权限列表
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    List<String> getPermissionsExPer(String world) throws CannotDependException;

    /**
     * 获取此玩家的权限前缀
     *
     * @return 前缀
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    String getPermissionsExPrefix() throws CannotDependException;

    /**
     * 获取此玩家在指定世界的权限前缀
     *
     * @param world 世界名
     * @return 前缀
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    String getPermissionsExPrefix(String world) throws CannotDependException;

    /**
     * 获取此玩家的自己权限前缀
     *
     * @return 前缀
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    String getPermissionsExOwnPrefix() throws CannotDependException;

    /**
     * 获取此玩家在指定世界的自己权限前缀
     *
     * @param world 世界名
     * @return 前缀
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    String getPermissionsExOwnPrefix(String world) throws CannotDependException;

    /**
     * 获取此玩家的权限后缀
     *
     * @return 后缀
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    String getPermissionsExSuffix() throws CannotDependException;

    /**
     * 获取此玩家在指定世界的权限后缀
     *
     * @param world 世界名
     * @return 后缀
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    String getPermissionsExSuffix(String world) throws CannotDependException;

    /**
     * 获取此玩家的自己权限后缀
     *
     * @return 后缀
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    String getPermissionsExOwnSuffix() throws CannotDependException;

    /**
     * 获取此玩家在指定世界的权限后缀
     *
     * @param world 世界名
     * @return 后缀
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    String getPermissionsExOwnSuffix(String world) throws CannotDependException;

    /**
     * 获取此玩家拥有的权限组名数组
     *
     * @return 权限组名数组
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    @Deprecated
    String[] getPermissionsExGroupNames() throws CannotDependException;

    /**
     * 获取此玩家在指定世界拥有的权限组名数组
     *
     * @param world 世界名
     * @return 权限组名数组
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    @Deprecated
    String[] getPermissionsExGroupNames(String world) throws CannotDependException;

    /**
     * 设置此玩家的权限组
     *
     * @param group 权限组名
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     * @throws IllegalArgumentException 如果权限组名对象为 {@code null} 则抛出异常
     */
    @Deprecated
    void setPermissionsExGroup(String group) throws CannotDependException;

    /**
     * 设置此玩家在指定世界的权限组
     *
     * @param group 权限组名
     * @param world 世界名
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     * @throws IllegalArgumentException 如果权限组名对象为 {@code null} 则抛出异常
     */
    @Deprecated
    void setPermissionsExGroup(String group, String world) throws CannotDependException;

    /**
     * 设置此玩家的权限组数组
     *
     * @param groups 权限组名数组
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     * @throws IllegalArgumentException 如果权限组名数组对象为 {@code null} 则抛出异常
     */
    @Deprecated
    void setPermissionsExGroups(String[] groups) throws CannotDependException;

    /**
     * 设置此玩家在指定世界的权限组数组
     *
     * @param groups 权限组名数组
     * @param world 世界名
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     * @throws IllegalArgumentException 如果权限组名数组对象为 {@code null} 则抛出异常
     */
    @Deprecated
    void setPermissionsExGroups(String[] groups, String world) throws CannotDependException;
}
