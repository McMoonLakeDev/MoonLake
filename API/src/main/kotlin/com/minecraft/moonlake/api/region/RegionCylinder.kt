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
        private var _center: RegionVector2D,
        private var _radius: RegionVector2D,
        private var _minY: Int = 0,
        private var _maxY: Int = 0) : RegionAbstract(world), RegionFlat {

    private var hasY: Boolean = false

    /** api */

    override val center: RegionVector
        get() = _center.toRegionVector((_maxY + _minY) / 2.0)

    fun setCenter(center: RegionVector2D)
            { this._center = center }

    var radius: RegionVector2D
        get() = _radius.minus(.5, .5)
        set(value) { _radius = value.plus(.5, .5) }

    override val minimumPoint: RegionVector
        get() = (_center - _radius).toRegionVector(_minY)

    override val maximumPoint: RegionVector
        get() = (_center + _radius).toRegionVector(_maxY)

    override val area: Int
        get() = Math.floor(_radius.x * _radius.z * Math.PI * height).toInt()

    override val width: Int
        get() = (_radius.x * 2.0).toInt()

    override val height: Int
        get() = _maxY - _minY + 1

    override val length: Int
        get() = (_radius.z * 2.0).toInt()

    override fun contains(vector: RegionVector): Boolean {
        val blockY = vector.blockY
        if(blockY < _minY || blockY > _maxY)
            return false
        return (vector.toRegionVector2D() - _center / _radius).lengthSq() <= 1.0
    }

    override val minimumY: Int
        get() = _minY

    fun setMinimumY(minY: Int) {
        this._minY = minY
        this.hasY = true
    }

    override val maximumY: Int
        get() = _maxY

    fun setMaximumY(maxY: Int) {
        this._maxY = maxY
        this.hasY = true
    }

    fun setY(y: Int): Boolean {
        if(!hasY) {
            _maxY = y
            _minY = y
            hasY = true
            return true
        }
        if(y < _minY) {
            _minY = y
            return true
        }
        if(y > _maxY) {
            _maxY = y
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
            return super.equals(other) && _minY == other._minY && _maxY == other._maxY && hasY == other.hasY && _center == other._center && _radius == other._radius
        return false
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + _center.hashCode()
        result = 31 * result + _radius.hashCode()
        result = 31 * result + _minY.hashCode()
        result = 31 * result + _maxY.hashCode()
        result = 31 * result + hasY.hashCode()
        return result
    }

    override fun toString(): String {
        return "RegionCylinder(world=${world.name}, center=$_center, radius=$_radius, minY=$_minY, maxY=$_maxY)"
    }

    override fun serialize(): MutableMap<String, Any> {
        val result = LinkedHashMap<String, Any>()
        result.put("world", world.name)
        result.put("center", _center.serialize())
        result.put("radius", _radius.serialize())
        result.put("minY", _minY)
        result.put("maxY", _maxY)
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
