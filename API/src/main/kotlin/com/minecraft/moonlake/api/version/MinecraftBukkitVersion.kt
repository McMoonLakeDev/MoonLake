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

package com.minecraft.moonlake.api.version

import com.minecraft.moonlake.api.ComparisonChain
import com.minecraft.moonlake.api.notNull
import org.bukkit.Bukkit
import java.util.*
import java.util.regex.Pattern

class MinecraftBukkitVersion(val major: Int, val minor: Int, val release: Int) : Comparable<MinecraftBukkitVersion> {

    /** static */

    companion object {

        @JvmField val V1_8_R1 = MinecraftBukkitVersion(1, 8, 1)
        @JvmField val V1_8_R2 = MinecraftBukkitVersion(1, 8, 2)
        @JvmField val V1_8_R3 = MinecraftBukkitVersion(1, 8, 3)
        @JvmField val V1_9_R1 = MinecraftBukkitVersion(1, 9, 1)
        @JvmField val V1_9_R2 = MinecraftBukkitVersion(1, 9, 2)
        @JvmField val V1_10_R1 = MinecraftBukkitVersion(1, 10, 1)
        @JvmField val V1_11_R1 = MinecraftBukkitVersion(1, 11, 1)
        @JvmField val V1_12_R1 = MinecraftBukkitVersion(1, 12, 1)
        @JvmField val UNKNOWN = MinecraftBukkitVersion(-1, -1, -1)

        private var currentVersion: MinecraftBukkitVersion? = null
        private val VERSION_PATTERN = Pattern.compile("(?i)^v(\\d+)_(\\d+)_r(\\d+)$")

        @JvmStatic
        @JvmName("currentVersion")
        fun currentVersion(): MinecraftBukkitVersion {
            currentVersion.let {
                if(it == null) {
                    val packageSplit = Bukkit.getServer().javaClass.`package`.name.split("\\.")
                    val matcher = VERSION_PATTERN.matcher(packageSplit.last())
                    if(!matcher.matches())
                        throw IllegalStateException("未成功匹配到的 Bukkit NMS 版本号.")
                    currentVersion = MinecraftBukkitVersion(matcher.group(1).toInt(), matcher.group(2).toInt(), matcher.group(3).toInt())
                }
                return it.notNull()
            }
        }

        private val lookup: NavigableMap<MinecraftVersion, MinecraftBukkitVersion> = createLookup()
        private fun createLookup(): NavigableMap<MinecraftVersion, MinecraftBukkitVersion> {
            val map = object: TreeMap<MinecraftVersion, MinecraftBukkitVersion>() {
                fun put(keys: Array<out MinecraftVersion>, value: MinecraftBukkitVersion)
                        = keys.forEach { put(it, value) }
            }
            map.put(arrayOf(
                    MinecraftVersion(1, 8, 0),
                    MinecraftVersion(1, 8, 1),
                    MinecraftVersion(1, 8, 2)
                    // ---> net.minecraft.server.v1_8_R1
            ), V1_8_R1)
            map.put(arrayOf(
                    MinecraftVersion(1, 8, 3),
                    MinecraftVersion(1, 8, 4),
                    MinecraftVersion(1, 8, 5),
                    MinecraftVersion(1, 8, 6),
                    MinecraftVersion(1, 8, 7)
                    // ---> net.minecraft.server.v1_8_R2
            ), V1_8_R2)
            map.put(arrayOf(
                    MinecraftVersion(1, 8, 8),
                    MinecraftVersion(1, 8, 9)
                    // ---> net.minecraft.server.v1_8_R3
            ), V1_8_R3)
            map.put(arrayOf(
                    MinecraftVersion(1, 9, 0),
                    MinecraftVersion(1, 9, 1),
                    MinecraftVersion(1, 9, 2),
                    MinecraftVersion(1, 9, 3)
                    // ---> net.minecraft.server.v1_9_R1
            ), V1_9_R1)
            map.put(arrayOf(
                    MinecraftVersion(1, 9, 4)
                    // ---> net.minecraft.server.v1_9_R2
            ), V1_9_R2)
            map.put(arrayOf(
                    MinecraftVersion(1, 10, 0),
                    MinecraftVersion(1, 10, 1),
                    MinecraftVersion(1, 10, 2)
                    // ---> net.minecraft.server.v1_10_R1
            ), V1_10_R1)
            map.put(arrayOf(
                    MinecraftVersion(1, 11, 0),
                    MinecraftVersion(1, 11, 1),
                    MinecraftVersion(1, 11, 2)
                    // ---> net.minecraft.server.v1_11_R1
            ), V1_11_R1)
            map.put(arrayOf(
                    MinecraftVersion(1, 12, 0),
                    MinecraftVersion(1, 12, 1)
                    // ---> net.minecraft.server.v1_12_R1
            ), V1_12_R1)
            return map
        }

        @JvmStatic
        @JvmName("lookup")
        fun lookup(mcVer: MinecraftVersion): MinecraftBukkitVersion {
            val entry = lookup.floorEntry(mcVer)
            return if(entry != null) entry.value else UNKNOWN
        }
    }

    /** function */

    fun getVersion(): String
            = "v${major}_${minor}_R$release"

    /** significant */

    override fun compareTo(other: MinecraftBukkitVersion): Int {
        return ComparisonChain.start()
                .compare(major, other.major)
                .compare(minor, other.minor)
                .compare(release, other.release)
                .result()
    }

    override fun hashCode(): Int {
        var result = major
        result = 31 * result + minor
        result = 31 * result + release
        return result
    }

    override fun equals(other: Any?): Boolean {
        if(other == this)
            return true
        if(other is MinecraftBukkitVersion)
            return major == other.major && minor == other.minor && release == other.release
        return false
    }

    override fun toString(): String {
        return "(MC Bukkit: ${getVersion()})"
    }
}
