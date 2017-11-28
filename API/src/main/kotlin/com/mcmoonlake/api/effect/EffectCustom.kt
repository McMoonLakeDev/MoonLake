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

package com.mcmoonlake.api.effect

import com.mcmoonlake.api.parseBoolean
import com.mcmoonlake.api.parseInt
import com.mcmoonlake.api.util.ComparisonChain
import org.bukkit.Color
import org.bukkit.configuration.serialization.ConfigurationSerializable

data class EffectCustom(
        val type: EffectType,
        val duration: Int,
        val amplifier: Int,
        val ambient: Boolean = true,
        val particle: Boolean = true,
        val color: Color? = null) : ConfigurationSerializable, Comparable<EffectCustom> {

    override fun compareTo(other: EffectCustom): Int {
        return ComparisonChain.start()
                .compare(type, other.type)
                .compare(duration, other.duration)
                .compare(amplifier, other.amplifier)
                .compare(ambient, other.ambient)
                .compare(particle, other.particle)
                .compare(color?.asRGB() ?: -1, other.color?.asRGB() ?: -1)
                .result
    }

    override fun serialize(): MutableMap<String, Any> {
        val result = LinkedHashMap<String, Any>()
        result.put("type", type.type)
        result.put("duration", duration)
        result.put("amplifier", amplifier)
        result.put("ambient", ambient)
        result.put("particle", particle)
        if(color != null) result.put("color", color.asRGB())
        return result
    }

    /** static */

    companion object {

        @JvmStatic
        @JvmName("deserialize")
        fun deserialize(args: Map<String, Any>): EffectCustom {
            val type = EffectType.fromName(args["type"].toString())
            val duration = args["duration"]?.parseInt() ?: 0
            val amplifier = args["amplifier"]?.parseInt() ?: 0
            val ambient = args["ambient"]?.parseBoolean() ?: false
            val particle = args["particle"]?.parseBoolean() ?: false
            val color = args["color"]?.parseInt() ?: -1
            return EffectCustom(type, duration, amplifier, ambient, particle, if(color == -1) null else Color.fromBGR(color))
        }
    }
}
