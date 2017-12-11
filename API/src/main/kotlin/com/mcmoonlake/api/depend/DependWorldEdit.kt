/*
 * Copyright (C) 2016-Present The MoonLake (mcmoonlake@hotmail.com)
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

package com.mcmoonlake.api.depend

import com.mcmoonlake.api.region.Region
import com.mcmoonlake.api.region.RegionCuboid
import com.mcmoonlake.api.region.RegionCylinder
import org.bukkit.entity.Player

/**
 * ## DependWorldEdit (依赖 WorldEdit 插件)
 *
 * @see [DependPlugin]
 * @author lgou2w
 * @since 2.0
 */
interface DependWorldEdit : DependPlugin {

    /**
     * * Get the `WorldEdit` player selected region.
     * * 获取 `WorldEdit` 玩家选择的区域.
     *
     * @see [Region]
     * @param player Player.
     * @param player 玩家.
     */
    fun getSelection(player: Player): Region?

    /**
     * * Set `WorldEdit` player selection region.
     * * 设置 `WorldEdit` 玩家选择的区域.
     *
     * @see [Region]
     * @see [RegionCuboid]
     * @see [RegionCylinder]
     * @param player Player.
     * @param player 玩家.
     * @param region Region.
     * @param region 区域.
     * @throws IllegalArgumentException If unsupported region type.
     * @throws IllegalArgumentException 如果不支持的区域类型.
     */
    @Throws(IllegalArgumentException::class)
    fun setSelection(player: Player, region: Region)

    companion object {
        /**
         * * The plugin name for WorldEdit
         */
        const val NAME = "WorldEdit"
    }
}
