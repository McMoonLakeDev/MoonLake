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


package com.minecraft.moonlake.api.player.depend;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import javax.annotation.Nullable;

/**
 * <h1>WorldEditSelection</h1>
 * 创世神选择区域接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface WorldEditSelection {

    /**
     * 获取此选择区域的最低点位置
     *
     * @return 最低点位置
     */
    Location getMinimumPoint();

    /**
     * 获取此选择区域的最高点位置
     *
     * @return 最高点位置
     */
    Location getMaximumPoint();

    /**
     * 获取此选择区域的本机最低点矢量
     *
     * @return 本机最低点矢量
     */
    WorldEditVector getNativeMinimumPoint();

    /**
     * 获取此选择区域的本机最高点矢量
     *
     * @return 本机最高点矢量
     */
    WorldEditVector getNativeMaximumPoint();

    /**
     * 获取此选择区域的世界
     *
     * @return 世界
     */
    @Nullable
    World getWorld();

    /**
     * 获取此选择区域的面积
     *
     * @return 面积
     */
    int getArea();

    /**
     * 获取此选择区域的宽度
     *
     * @return 宽度
     */
    int getWidth();

    /**
     * 获取此选择区域的高度
     *
     * @return 高度
     */
    int getHeight();

    /**
     * 获取此选择区域的长度
     *
     * @return 长度
     */
    int getLength();

    /**
     * 获取此选择区域是否包含指定位置
     *
     * @param location 位置
     * @return 包含位置则返回 true
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     */
    boolean contains(Location location);

    /**
     * 获取此选择区域是否包含指定实体位置
     *
     * @param entity 实体
     * @return 包含实体位置则返回 true
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    boolean contains(Entity entity);
}
