/*
 * Copyright (C) 2016-2017 The MoonLake (mcmoonlake@hotmail.com)
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

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.entity.Entity

interface Region : ConfigurationSerializable, MutableIterable<RegionVectorBlock> {

    val world: World

    val minimumPoint: RegionVector

    val maximumPoint: RegionVector

    val center: RegionVector

    val area: Int

    val width: Int

    val height: Int

    val length: Int

    fun contains(vector: RegionVector): Boolean

    fun contains(location: Location): Boolean

    fun contains(entity: Entity): Boolean

    fun contains(block: Block): Boolean

    override fun iterator(): MutableIterator<RegionVectorBlock>
}
