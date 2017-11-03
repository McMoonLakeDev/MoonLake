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

package com.mcmoonlake.impl.depend

import com.mcmoonlake.api.depend.DependPluginAbstract
import com.mcmoonlake.api.depend.DependWorldEdit
import com.mcmoonlake.api.getPlugin
import com.mcmoonlake.api.region.*
import com.sk89q.worldedit.BlockVector2D
import com.sk89q.worldedit.Vector
import com.sk89q.worldedit.Vector2D
import com.sk89q.worldedit.bukkit.WorldEditPlugin
import com.sk89q.worldedit.bukkit.selections.CuboidSelection
import com.sk89q.worldedit.bukkit.selections.CylinderSelection
import com.sk89q.worldedit.bukkit.selections.Selection
import com.sk89q.worldedit.regions.selector.CuboidRegionSelector
import com.sk89q.worldedit.regions.selector.CylinderRegionSelector
import org.bukkit.entity.Player

class DependWorldEditImpl : DependPluginAbstract<WorldEditPlugin>(getPlugin(DependWorldEdit.NAME)), DependWorldEdit {

    private fun Vector.toRegionVectorBlock(): RegionVectorBlock
            = RegionVectorBlock(x, y, z)
    private fun Vector.toRegionVector2D(): RegionVector2D
            = RegionVector2D(x, z)
    private fun Vector2D.toRegionVector2D(): RegionVector2D
            = RegionVector2D(x, z)
    private fun RegionVector.toVector(): Vector
            = Vector(x, y, z)
    private fun RegionVector.toVector2D(): BlockVector2D
            = BlockVector2D(x, z)

    override fun getSelection(player: Player): Region? {
        val selection = plugin.getSelection(player) ?: return null
        val world = selection.world ?: return null
        return when(selection) {
            is CuboidSelection -> {
                val region0 = (selection.regionSelector as CuboidRegionSelector).incompleteRegion
                RegionCuboid(world, region0.pos1.toRegionVectorBlock(), region0.pos2.toRegionVectorBlock())
            }
            is CylinderSelection -> {
                val region0 = (selection.regionSelector as CylinderRegionSelector).incompleteRegion
                RegionCylinder(world, region0.center.toRegionVector2D(), region0.radius.toRegionVector2D(), region0.minimumY, region0.maximumY)
            }
            else -> null
        }
    }

    override fun setSelection(player: Player, region: Region) {
        val world = region.world
        val selection: Selection = when(region) {
            is RegionCuboid -> CuboidSelection(world, region.pos1.toVector(), region.pos2.toVector())
            is RegionCylinder -> CylinderSelection(world, region.center.toVector2D(), region.center.toVector2D(), region.minimumY, region.maximumY)
            else -> throw IllegalArgumentException("不支持的区域类型, WorldEdit 仅支持矩形和圆柱.")
        }
        plugin.setSelection(player, selection)
    }
}
