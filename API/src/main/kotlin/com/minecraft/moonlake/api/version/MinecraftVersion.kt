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
import java.util.regex.Pattern

class MinecraftVersion(val major: Int, val minor: Int, val build: Int, private val pre: Int? = null) : Comparable<MinecraftVersion> {

    /** static */

    companion object {

        @JvmField val V1_12 = MinecraftVersion(1, 12, 0)
        @JvmField val V1_11 = MinecraftVersion(1, 11, 0)
        @JvmField val V1_10 = MinecraftVersion(1, 10, 0)
        @JvmField val V1_9 = MinecraftVersion(1, 9, 0)
        @JvmField val V1_8 = MinecraftVersion(1, 8, 0)

        private var currentVersion: MinecraftVersion? = null
        private val VERSION_PATTERN = Pattern.compile(".*\\(.*MC.\\s*([a-zA-Z0-9\\-\\.]+)\\s*\\)")

        @JvmStatic
        @JvmName("currentVersion")
        fun currentVersion(): MinecraftVersion {
            currentVersion.let {
                if(it == null) {
                    val matcher = VERSION_PATTERN.matcher(Bukkit.getServer().version)
                    if(!matcher.matches() || matcher.group(1) == null)
                        throw IllegalStateException("未成功匹配到的 Bukkit Minecraft 版本号.")
                    val versionOnly = matcher.group(1)
                    val numbers = IntArray(3)
                    var per: Int? = null
                    try {
                        val index = versionOnly.lastIndexOf("-pre")
                        val elements = if(index == -1) versionOnly.split(Pattern.compile("\\.")) else versionOnly.substring(0, index).split(Pattern.compile("\\."))
                        if(elements.size == -1)
                            throw IllegalStateException("无效的 Bukkit Minecraft 版本号: $versionOnly")
                        for(i in 0 until Math.min(numbers.size, elements.size))
                            numbers[i] = elements[i].trim().toInt()
                        if(index != -1)
                            per = versionOnly.substring(index + 4).toInt()
                    } catch (e: Exception) {
                        if(e is NumberFormatException)
                            throw IllegalStateException("无法解析的 Bukkit Minecraft 版本号: $versionOnly")
                        else
                            throw e
                    }
                    currentVersion = MinecraftVersion(numbers[0], numbers[1], numbers[2], per)
                }
                return currentVersion.notNull()
            }
        }
    }

    /** function */

    fun isPre(): Boolean
            = pre != null

    fun getVersion(): String
            = "$major.$minor.$build" + if(pre != null) "-pre$pre" else ""

    fun getBukkitVersion(): MinecraftBukkitVersion
            = MinecraftBukkitVersion.lookup(this)

    /** significant */

    override fun compareTo(other: MinecraftVersion): Int {
        return ComparisonChain.start()
                .compare(major, other.major)
                .compare(minor, other.minor)
                .compare(build, other.build)
                .compare(pre ?: -1, other.pre ?: -1)
                .result()
    }

    override fun hashCode(): Int {
        var result = major
        result = 31 * result + minor
        result = 31 * result + build
        result = 31 * result + (pre?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is MinecraftVersion)
            return major == other.major && minor == other.minor && build == other.build && isPre() == other.isPre()
        return false
    }

    override fun toString(): String {
        return "(MC: ${getVersion()})"
    }
}
