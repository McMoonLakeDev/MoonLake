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

import com.minecraft.moonlake.api.util.ComparisonChain
import com.minecraft.moonlake.api.parseDouble
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.configuration.serialization.ConfigurationSerializable

open class RegionVector2D(
        val x: Double,
        val z: Double) : ConfigurationSerializable, Comparable<RegionVector2D> {

    /** constructor */

    constructor(x: Float, z: Float) : this(x.toDouble(), z.toDouble())
    constructor(x: Int, z: Int) : this(x.toDouble(), z.toDouble())
    constructor(other: RegionVector2D) : this(other.x, other.z)
    constructor() : this(.0, .0)

    /** api */

    val blockX: Int
        get() = Math.round(x).toInt()

    val blockZ: Int
        get() = Math.round(z).toInt()

    fun setX(x: Double): RegionVector2D
            = RegionVector2D(x, z)

    fun setZ(z: Double): RegionVector2D
            = RegionVector2D(x, z)

    fun setX(x: Int): RegionVector2D
            = RegionVector2D(x.toDouble(), z)

    fun setZ(z: Int): RegionVector2D
            = RegionVector2D(x, z.toDouble())

    fun length(): Double
            = Math.sqrt(lengthSq())

    fun lengthSq(): Double
            = x * x + z * z

    fun distance(vector: RegionVector2D): Double
            = Math.sqrt(distanceSq(vector))

    fun distanceSq(vector: RegionVector2D): Double
            = Math.pow(x - vector.x, 2.0) + Math.pow(z - vector.z, 2.0)

    fun normalize(): RegionVector2D
            = div(length())

    fun toRegionVector(y: Double = .0): RegionVector
            = RegionVector(x, y, z)

    fun toRegionVector(y: Int = 0): RegionVector
            = RegionVector(x, y.toDouble(), z)

    fun toLocation(world: World, y: Double = .0, yaw: Float = .0f, pitch: Float = .0f): Location
            = Location(world, x, y, z, yaw, pitch)

    /** operator */

    operator fun inc(): RegionVector2D
            = RegionVector2D(x + 1.0, z + 1.0)

    operator fun dec(): RegionVector2D
            = RegionVector2D(x - 1.0, z - 1.0)

    operator fun plus(other: RegionVector2D): RegionVector2D
            = RegionVector2D(x + other.x, z + other.z)

    fun plus(x: Double, z: Double): RegionVector2D
            = RegionVector2D(this.x + x, this.z + z)

    fun plus(x: Int, z: Int): RegionVector2D
            = RegionVector2D(this.x + x, this.z + z)

    operator fun minus(other: RegionVector2D): RegionVector2D
            = RegionVector2D(x - other.x, z - other.z)

    fun minus(x: Double, z: Double): RegionVector2D
            = RegionVector2D(this.x - x, this.z - z)

    fun minus(x: Int, z: Int): RegionVector2D
            = RegionVector2D(this.x - x, this.z - z)

    operator fun times(other: RegionVector2D): RegionVector2D
            = RegionVector2D(x * other.x, z * other.z)

    operator fun times(other: Double): RegionVector2D
            = RegionVector2D(x * other, z * other)

    operator fun times(other: Float): RegionVector2D
            = RegionVector2D(x * other, z * other)

    operator fun times(other: Int): RegionVector2D
            = RegionVector2D(x * other, z * other)

    fun times(x: Double, z: Double): RegionVector2D
            = RegionVector2D(this.x * x, this.z * z)

    fun times(x: Int, z: Int): RegionVector2D
            = RegionVector2D(this.x * x, this.z * z)

    operator fun div(other: RegionVector2D): RegionVector2D
            = RegionVector2D(x / other.x, z / other.z)

    operator fun div(other: Double): RegionVector2D
            = RegionVector2D(x / other, z / other)

    operator fun div(other: Float): RegionVector2D
            = RegionVector2D(x / other, z / other)

    operator fun div(other: Int): RegionVector2D
            = RegionVector2D(x / other, z / other)

    fun div(x: Double, z: Double): RegionVector2D
            = RegionVector2D(this.x / x, this.z / z)

    fun div(x: Int, z: Int): RegionVector2D
            = RegionVector2D(this.x / x, this.z / z)

    /** significant */

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is RegionVector2D)
            return x == other.x && z == other.z
        return false
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }

    override fun toString(): String {
        return "RegionVector2D(x=$x, z=$z)"
    }

    override fun compareTo(other: RegionVector2D): Int {
        return ComparisonChain.start()
                .compare(x, other.x)
                .compare(z, other.z)
                .result
    }

    override fun serialize(): MutableMap<String, Any> {
        val result = LinkedHashMap<String, Any>()
        result.put("x", x)
        result.put("z", z)
        return result
    }

    /** static */

    companion object {

        @JvmField val ZERO = RegionVector2D(.0, .0)

        @JvmStatic
        @JvmName("deserialize")
        fun deserialize(args: Map<String, Any>): RegionVector2D {
            val x = args["x"]?.parseDouble() ?: .0
            val z = args["z"]?.parseDouble() ?: .0
            return RegionVector2D(x, z)
        }
    }
}
