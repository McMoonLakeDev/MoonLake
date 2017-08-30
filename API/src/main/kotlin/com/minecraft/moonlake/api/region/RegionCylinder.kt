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

import com.minecraft.moonlake.api.parseInt
import com.minecraft.moonlake.api.toBukkitWorld
import org.bukkit.World

open class RegionCylinder(
        world: World,
        private var center: RegionVector2D,
        private var radius: RegionVector2D,
        private var minY: Int = 0,
        private var maxY: Int = 0) : RegionAbstract(world), RegionFlat {

    private var hasY: Boolean = false

    /** api */

    override fun getCenter(): RegionVector
            = center.toRegionVector((maxY + minY) / 2.0)

    fun setCenter(center: RegionVector2D)
            { this.center = center }

    fun getRadius(): RegionVector2D
            = radius.minus(.5, .5)

    fun setRadius(radius: RegionVector2D)
            { this.radius = radius.plus(.5, .5) }

    override fun getMinimumPoint(): RegionVector
            = (center - radius).toRegionVector(minY)

    override fun getMaximumPoint(): RegionVector
            = (center + radius).toRegionVector(maxY)

    override fun getArea(): Int
            = Math.floor(radius.x * radius.z * Math.PI * getHeight()).toInt()

    override fun getWidth(): Int
            = (radius.x * 2.0).toInt()

    override fun getHeight(): Int
            = maxY - minY + 1

    override fun getLength(): Int
            = (radius.z * 2.0).toInt()

    override fun contains(vector: RegionVector): Boolean {
        val blockY = vector.getBlockY()
        if(blockY < minY || blockY > maxY)
            return false
        return (vector.toRegionVector2D() - center / radius).lengthSq() <= 1.0
    }

    override fun getMinimumY(): Int
            = minY

    fun setMinimumY(minY: Int) {
        this.minY = minY
        this.hasY = true
    }

    override fun getMaximumY(): Int
            = maxY

    fun setMaximumY(maxY: Int) {
        this.maxY = maxY
        this.hasY = true
    }

    fun setY(y: Int): Boolean {
        if(!hasY) {
            maxY = y
            minY = y
            hasY = true
            return true
        }
        if(y < minY) {
            minY = y
            return true
        }
        if(y > maxY) {
            maxY = y
            return true
        }
        return false
    }

    override fun iterator(): MutableIterator<RegionVectorBlock>
            = RegionIteratorFlat3D(this)

    override fun asRegionFlat(): MutableIterable<RegionVector2D> {
        return object: MutableIterable<RegionVector2D> {
            override fun iterator(): MutableIterator<RegionVector2D>
                    = RegionIteratorFlat(this@RegionCylinder)
        }
    }

    /** significant */

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is RegionCylinder)
            return super.equals(other) && minY == other.minY && maxY == other.maxY && hasY == other.hasY && center == other.center && radius == other.radius
        return false
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + center.hashCode()
        result = 31 * result + result.hashCode()
        result = 31 * result + minY.hashCode()
        result = 31 * result + maxY.hashCode()
        result = 31 * result + hasY.hashCode()
        return result
    }

    override fun toString(): String {
        return "RegionCylinder(world=${getWorld().name}, center=$center, radius=$radius, minY=$minY, maxY=$maxY)"
    }

    override fun serialize(): MutableMap<String, Any> {
        val result = LinkedHashMap<String, Any>()
        result.put("world", getWorld().name)
        result.put("center", center.serialize())
        result.put("radius", radius.serialize())
        result.put("minY", minY)
        result.put("maxY", maxY)
        return result
    }

    /** static */

    companion object {

        @JvmStatic
        @JvmName("deserialize")
        @Suppress("UNCHECKED_CAST")
        fun deserialize(args: Map<String, Any>): RegionCylinder {
            val world = args["world"]?.toString()?.toBukkitWorld() ?: throw IllegalArgumentException("未知的世界: ${args["world"]}")
            val center = RegionVector2D.deserialize(args["center"] as Map<String, Any>)
            val radius = RegionVector2D.deserialize(args["radius"] as Map<String, Any>)
            val minY = args["minY"]?.toString()?.parseInt() ?: 0
            val maxY = args["maxY"]?.toString()?.parseInt() ?: 0
            return RegionCylinder(world, center, radius, minY, maxY)
        }
    }
}
