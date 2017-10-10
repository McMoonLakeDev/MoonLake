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

import com.minecraft.moonlake.api.util.ComparisonChain
import com.minecraft.moonlake.api.parseDouble
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.configuration.serialization.ConfigurationSerializable

open class RegionVector(
        val x: Double,
        val y: Double,
        val z: Double) : ConfigurationSerializable, Comparable<RegionVector> {

    /** constructor */

    constructor(x: Float, y: Float, z: Float) : this(x.toDouble(), y.toDouble(), z.toDouble())
    constructor(x: Int, y: Int, z: Int) : this(x.toDouble(), y.toDouble(), z.toDouble())
    constructor(other: RegionVector) : this(other.x, other.y, other.z)
    constructor() : this(.0, .0, .0)

    /** api */

    val blockX: Int
        get() = Math.round(x).toInt()

    val blockY: Int
        get() = Math.round(y).toInt()

    val blockZ: Int
        get() = Math.round(z).toInt()

    fun setX(x: Double): RegionVector
            = RegionVector(x, y, z)

    fun setY(y: Double): RegionVector
            = RegionVector(x, y, z)

    fun setZ(z: Double): RegionVector
            = RegionVector(x, y, z)

    fun setX(x: Int): RegionVector
            = RegionVector(x.toDouble(), y, z)

    fun setY(y: Int): RegionVector
            = RegionVector(x, y.toDouble(), z)

    fun setZ(z: Int): RegionVector
            = RegionVector(x, y, z.toDouble())

    fun length(): Double
            = Math.sqrt(lengthSq())

    fun lengthSq(): Double
            = x * x + y * y + z * z

    fun distance(vector: RegionVector): Double
            = Math.sqrt(distanceSq(vector))

    fun distanceSq(vector: RegionVector): Double
            = Math.pow(x - vector.x, 2.0) + Math.pow(y - vector.y, 2.0) + Math.pow(z - vector.z, 2.0)

    fun normalize(): RegionVector
            = div(length())

    fun toRegionVector2D(): RegionVector2D
            = RegionVector2D(x, z)

    fun toLocation(world: World, yaw: Float = .0f, pitch: Float = .0f): Location
            = Location(world, x, y, z, yaw, pitch)

    /** operator */

    operator fun inc(): RegionVector
            = RegionVector(x + 1.0, y + 1.0, z + 1.0)

    operator fun dec(): RegionVector
            = RegionVector(x - 1.0, y - 1.0, z - 1.0)

    operator fun plus(other: RegionVector): RegionVector
            = RegionVector(x + other.x, y + other.y, z + other.z)

    fun plus(x: Double, y: Double, z: Double): RegionVector
            = RegionVector(this.x + x, this.y + y, this.z + z)

    fun plus(x: Int, y: Int, z: Int): RegionVector
            = RegionVector(this.x + x, this.y + y, this.z + z)

    operator fun minus(other: RegionVector): RegionVector
            = RegionVector(x - other.x, y - other.y, z - other.z)

    fun minus(x: Double, y: Double, z: Double): RegionVector
            = RegionVector(this.x - x, this.y - y, this.z - z)

    fun minus(x: Int, y: Int, z: Int): RegionVector
            = RegionVector(this.x - x, this.y - y, this.z - z)

    operator fun times(other: RegionVector): RegionVector
            = RegionVector(x * other.x, y * other.y, z * other.z)

    operator fun times(other: Double): RegionVector
            = RegionVector(x * other, y * other, z * other)

    operator fun times(other: Float): RegionVector
            = RegionVector(x * other, y * other, z * other)

    operator fun times(other: Int): RegionVector
            = RegionVector(x * other, y * other, z * other)

    fun times(x: Double, y: Double, z: Double): RegionVector
            = RegionVector(this.x * x, this.y * y, this.z * z)

    fun times(x: Int, y: Int, z: Int): RegionVector
            = RegionVector(this.x * x, this.y * y, this.z * z)

    operator fun div(other: RegionVector): RegionVector
            = RegionVector(x / other.x, y / other.y, z / other.z)

    operator fun div(other: Double): RegionVector
            = RegionVector(x / other, y / other, z / other)

    operator fun div(other: Float): RegionVector
            = RegionVector(x / other, y / other, z / other)

    operator fun div(other: Int): RegionVector
            = RegionVector(x / other, y / other, z / other)

    fun div(x: Double, y: Double, z: Double): RegionVector
            = RegionVector(this.x / x, this.y / y, this.z / z)

    fun div(x: Int, y: Int, z: Int): RegionVector
            = RegionVector(this.x / x, this.y / y, this.z / z)

    /** significant */

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is RegionVector)
            return x == other.x && y == other.y && z == other.z
        return false
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }

    override fun toString(): String {
        return "RegionVector(x=$x, y=$y, z=$z)"
    }

    override fun compareTo(other: RegionVector): Int {
        return ComparisonChain.start()
                .compare(x, other.x)
                .compare(y, other.y)
                .compare(z, other.z)
                .result
    }

    override fun serialize(): MutableMap<String, Any> {
        val result = LinkedHashMap<String, Any>()
        result.put("x", x)
        result.put("y", y)
        result.put("z", z)
        return result
    }

    /** static */

    companion object {

        @JvmField val ZERO = RegionVector(.0, .0, .0)

        @JvmStatic
        @JvmName("deserialize")
        fun deserialize(args: Map<String, Any>): RegionVector {
            val x = args["x"]?.parseDouble() ?: .0
            val y = args["y"]?.parseDouble() ?: .0
            val z = args["z"]?.parseDouble() ?: .0
            return RegionVector(x, y, z)
        }
    }
}
