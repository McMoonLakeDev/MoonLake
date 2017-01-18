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


package com.minecraft.moonlake.api.region;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Entity;

import java.util.Iterator;

/**
 * <h1>Region</h1>
 * 区域接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @see FlatRegion
 * @see CuboidRegion
 * @see CylinderRegion
 */
public interface Region extends Iterable<RegionBlockVector>, ConfigurationSerializable, Cloneable {

    /**
     * 获取此区域的世界
     *
     * @return 世界
     */
    World getWorld();

    /**
     * 获取此区域的最小点矢量
     *
     * @return 最小点矢量
     */
    RegionVector getMinimumPoint();

    /**
     * 获取此区域的最大点矢量
     *
     * @return 最大点矢量
     */
    RegionVector getMaximumPoint();

    /**
     * 获取此区域的中心点矢量
     *
     * @return 中心点矢量
     */
    RegionVector getCenter();

    /**
     * 获取此区域的面积 (总方块数)
     *
     * @return 面积
     */
    int getArea();

    /**
     * 获取此区域的宽度
     *
     * @return 宽度
     */
    int getWidth();

    /**
     * 获取此区域的高度
     *
     * @return 高度
     */
    int getHeight();

    /**
     * 获取此区域的长度
     *
     * @return 长度
     */
    int getLength();

    /**
     * 判断指定区域矢量是否存在此区域
     *
     * @param vector 区域矢量
     * @return true 则存在
     */
    boolean contains(RegionVector vector);

    /**
     * 判断指定位置是否存在此区域
     *
     * @param location 位置
     * @return true 则存在
     */
    boolean contains(Location location);

    /**
     * 判断指定实体的位置是否存在此区域
     *
     * @param entity 实体
     * @return true 则存在
     */
    boolean contains(Entity entity);

    /**
     * 判断指定方块的位置是否存在此区域
     *
     * @param block 方块
     * @return true 则存在
     */
    boolean contains(Block block);

    @Override
    Iterator<RegionBlockVector> iterator();

    /**
     * 克隆此区域对象
     *
     * @return 区域
     */
    Region clone();
}
