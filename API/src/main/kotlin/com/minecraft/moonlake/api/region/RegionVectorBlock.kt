/*
 * WorldEdit, a Minecraft world manipulation toolkit
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldEdit team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

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

package com.minecraft.moonlake.api.region

import com.minecraft.moonlake.api.parseDouble
import org.bukkit.World
import org.bukkit.block.Block

open class RegionVectorBlock : RegionVector {

    /** constructor */

    constructor(x: Double, y: Double, z: Double) : super(x, y, z)
    constructor(x: Float, y: Float, z: Float) : super(x, y, z)
    constructor(x: Int, y: Int, z: Int) : super(x, y, z)
    constructor(other: RegionVector) : super(other)
    constructor() : super()

    /** api */

    fun getBlock(world: World): Block
            = world.getBlockAt(blockX, blockY, blockZ)

    /** significant */

    override fun toString(): String {
        return "RegionVectorBlock(x=$x, y=$y, z=$z)"
    }

    /** static */

    companion object {

        @JvmField val ZERO = RegionVectorBlock(.0, .0, .0)

        @JvmStatic
        @JvmName("deserialize")
        fun deserialize(args: Map<String, Any>): RegionVectorBlock {
            val x = args["x"]?.parseDouble() ?: .0
            val y = args["y"]?.parseDouble() ?: .0
            val z = args["z"]?.parseDouble() ?: .0
            return RegionVectorBlock(x, y, z)
        }
    }
}
