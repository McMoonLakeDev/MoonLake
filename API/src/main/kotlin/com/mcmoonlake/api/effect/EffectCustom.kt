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

import com.mcmoonlake.api.funs.Function
import com.mcmoonlake.api.isCombatOrLaterVer
import com.mcmoonlake.api.item.ItemBuilder
import com.mcmoonlake.api.parseBoolean
import com.mcmoonlake.api.parseInt
import com.mcmoonlake.api.util.ComparisonChain
import org.bukkit.Color
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.entity.LivingEntity
import org.bukkit.potion.PotionEffect

/**
 * * ## EffectCustom (效果自定义)
 *
 * * Allow to create a custom duration, level potion effect.
 * * 允许创建出自定义持续时间、等级的药水效果.
 *
 * @author lgou2w
 * @since 2.0
 * @see [ItemBuilder.addPotionEffect]
 * @constructor EffectCustom
 * @param type Effect type.
 * @param type 效果类型.
 * @param duration Effect duration.
 * @param duration 效果持续时间.
 * @param amplifier Effect amplifier.
 * @param amplifier 效果等级.
 * @param ambient Whether the effect produces more particles.
 * @param ambient 效果是否产生更多的粒子效果.
 * @param particle Whether the effect has a particle.
 * @param particle 效果是否拥有粒子效果.
 * @param color Particle color for this custom effect.
 * @param color 效果的粒子颜色.
 */
data class EffectCustom(
        /**
         * * The type of custom effect.
         * * 此自定义效果的类型.
         */
        val type: EffectType,
        /**
         * * The duration of custom effect.
         * * 此自定义效果的持续时间
         */
        val duration: Int,
        /**
         * * The amplifier of custom effect.
         * * 此自定义效果的等级.
         */
        val amplifier: Int,
        /**
         * * Whether this custom effect produces more particles.
         * * 此自定义效果是否产生更多的粒子效果.
         */
        val ambient: Boolean = true,
        /**
         * * Whether this custom effect has a particle.
         * * 此自定义效果是否拥有粒子效果.
         */
        val particle: Boolean = true,
        /**
         * * Particle color for this custom effect.
         * * 此自定义效果的粒子颜色.
         */
        val color: Color? = null) : ConfigurationSerializable, Comparable<EffectCustom>, Function<LivingEntity, Boolean> {

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

    override fun apply(param: LivingEntity): Boolean = if(isCombatOrLaterVer) { // Colors are supported only after 1.9 or later
        param.addPotionEffect(PotionEffect(type.cast(), duration, amplifier, ambient, particle, color))
    } else {
        param.addPotionEffect(PotionEffect(type.cast(), duration, amplifier, ambient, particle))
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
