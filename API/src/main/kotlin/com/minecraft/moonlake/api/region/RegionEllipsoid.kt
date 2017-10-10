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

import com.minecraft.moonlake.api.toBukkitWorld
import org.bukkit.World

open class RegionEllipsoid(
        world: World,
        private var _center: RegionVector,
        private var _radius: RegionVector) : RegionAbstract(world) {

    /** api */

    override val center: RegionVector
        get() = _center

    fun setCenter(center: RegionVector)
            { this._center = center }

    var radius: RegionVector
        get() = _radius.minus(.5, .5, .5)
        set(value) { _radius.plus(.5, .5, .5) }

    override val area: Int
        get() = Math.floor(4.1887902047863905 * _radius.x * _radius.y * _radius.z).toInt()

    override val width: Int
        get() = (_radius.x * 2.0).toInt()

    override val height: Int
        get() = (_radius.y * 2.0).toInt()

    override val length: Int
        get() = (_radius.z * 2.0).toInt()

    override val minimumPoint: RegionVector
        get() = _center - _radius

    override val maximumPoint: RegionVector
        get() = _center + _radius

    override fun contains(vector: RegionVector): Boolean
            = ((vector - _center) / _radius).lengthSq() <= 1.0

    /** significant */

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is RegionEllipsoid)
            return super.equals(other) && _center == other._center && _radius == other._radius
        return false
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + _center.hashCode()
        result = 31 * result + _radius.hashCode()
        return result
    }

    override fun toString(): String {
        return "RegionEllipsoid(world=${world.name}, center=$_center, radius=$_radius)"
    }

    override fun serialize(): MutableMap<String, Any> {
        val result = LinkedHashMap<String, Any>()
        result.put("world", world.name)
        result.put("center", center.serialize())
        result.put("radius", center.serialize())
        return result
    }

    /** static */

    companion object {

        @JvmStatic
        @JvmName("deserialize")
        @Suppress("UNCHECKED_CAST")
        fun deserialize(args: Map<String, Any>): RegionEllipsoid {
            val world = args["world"]?.toString()?.toBukkitWorld() ?: throw IllegalArgumentException("未知的世界: ${args["world"]}")
            val center = RegionVector.deserialize(args["center"] as Map<String, Any>)
            val radius = RegionVector.deserialize(args["radius"] as Map<String, Any>)
            return RegionEllipsoid(world, center, radius)
        }
    }
}
