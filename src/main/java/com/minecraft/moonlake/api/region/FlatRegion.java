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

/**
 * <h1>FlatRegion</h1>
 * 平面区域接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @see Region
 * @see CuboidRegion
 * @see CylinderRegion
 */
public interface FlatRegion extends Region {

    /**
     * 获取此平面区域的最小 Y
     *
     * @return 最小 Y
     */
    int getMinimumY();

    /**
     * 获取此平面区域的最大 Y
     *
     * @return 最大 Y
     */
    int getMaximumY();

    /**
     * 获取此平面区域的区域迭代矢量 2D
     *
     * @return 迭代矢量 2D
     */
    Iterable<RegionVector2D> asFlatRegion();
}
