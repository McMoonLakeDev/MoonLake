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

package com.minecraft.moonlake.api.effect

class EffectBase private constructor(val value: String, val effect: EffectType? = null) {

    private constructor(equivalent: EffectBase) : this(equivalent.value, equivalent.effect)

    init {
        NAME_MAP.put(value, this)
    }

    fun hasEffect(): Boolean
            = effect != null

    override fun toString(): String {
        return "EffectBase(value='$value', effect=$effect)"
    }

    companion object {

        @JvmStatic
        private val NAME_MAP: MutableMap<String, EffectBase> = HashMap()

        @JvmStatic
        @JvmName("fromName")
        fun fromName(name: String): EffectBase?
                = NAME_MAP[name]

        @JvmField
        val WATER = EffectBase("water")

        @JvmField
        val 水瓶 = EffectBase(WATER)

        @JvmField
        val WATER_MUNDANE = EffectBase("mundane")

        @JvmField
        val 平凡药水 = EffectBase(WATER_MUNDANE)

        @JvmField
        val WATER_THICK = EffectBase("thick")

        @JvmField
        val 浑浊药水 = EffectBase(WATER_THICK)

        @JvmField
        val WATER_AWKWAARD = EffectBase("awkward")

        @JvmField
        val 粗制药水 = EffectBase(WATER_AWKWAARD)

        @JvmField
        val NIGHT_VISION = EffectBase("night_vision", EffectType.NIGHT_VISION)

        @JvmField
        val 夜视药水 = EffectBase(NIGHT_VISION)

        @JvmField
        val NIGHT_VISION_LONG = EffectBase("long_night_vision", EffectType.NIGHT_VISION)

        @JvmField
        val 夜视药水延长版 = EffectBase(NIGHT_VISION_LONG)

        @JvmField
        val INVISIBILITY = EffectBase("invisibility", EffectType.INVISIBILITY)

        @JvmField
        val 隐身药水 = EffectBase(INVISIBILITY)

        @JvmField
        val INVISIBILITY_LONG = EffectBase("long_invisibility", EffectType.INVISIBILITY)

        @JvmField
        val 隐身药水延长版 = EffectBase(INVISIBILITY_LONG)

        @JvmField
        val LEAPING = EffectBase("leaping", EffectType.JUMP)

        @JvmField
        val 跳跃药水 = EffectBase(LEAPING)

        @JvmField
        val LEAPING_LONG = EffectBase("long_leaping", EffectType.JUMP)

        @JvmField
        val 跳跃药水延长版 = EffectBase(LEAPING_LONG)

        @JvmField
        val LEAPING_STRONG = EffectBase("strong_leaping", EffectType.JUMP)

        @JvmField
        val 跳跃药水增强版 = EffectBase(LEAPING_STRONG)

        @JvmField
        val FIRE_RESISTANCE = EffectBase("fire_resistance", EffectType.FIRE_RESISTANCE)

        @JvmField
        val 抗火药水 = EffectBase(FIRE_RESISTANCE)

        @JvmField
        val FIRE_RESISTANCE_LONG = EffectBase("long_fire_resistance", EffectType.FIRE_RESISTANCE)

        @JvmField
        val 抗火药水延长版 = EffectBase(FIRE_RESISTANCE_LONG)

        @JvmField
        val SWIFTNESS = EffectBase("swiftness", EffectType.SPEED)

        @JvmField
        val 迅捷药水 = EffectBase(SWIFTNESS)

        @JvmField
        val SWIFTNESS_LONG = EffectBase("long_swiftness", EffectType.SPEED)

        @JvmField
        val 迅捷药水延长版 = EffectBase(SWIFTNESS_LONG)

        @JvmField
        val SWIFTNESS_STRONG = EffectBase("strong_swiftness", EffectType.SPEED)

        @JvmField
        val 迅捷药水增强版 = EffectBase(SWIFTNESS_STRONG)

        @JvmField
        val SLOWNESS = EffectBase("slowness", EffectType.SLOW)

        @JvmField
        val 缓慢药水 = EffectBase(SLOWNESS)

        @JvmField
        val SLOWNESS_LONG = EffectBase("long_slowness", EffectType.SLOW)

        @JvmField
        val 缓慢药水延长版 = EffectBase(SLOWNESS_LONG)

        @JvmField
        val WATER_BREATHING = EffectBase("water_breathing", EffectType.WATER_BREATHING)

        @JvmField
        val 水下呼吸药水 = EffectBase(WATER_BREATHING)

        @JvmField
        val WATER_BREATHING_LONG = EffectBase("long_water_breathing", EffectType.WATER_BREATHING)

        @JvmField
        val 水下呼吸药水延长版 = EffectBase(WATER_BREATHING_LONG)

        @JvmField
        val HEALING = EffectBase("healing", EffectType.HEAL)

        @JvmField
        val 瞬间治疗药水 = EffectBase(HEALING)

        @JvmField
        val HEALING_STRONG = EffectBase("strong_healing", EffectType.HEAL)

        @JvmField
        val 瞬间治疗药水增强版 = EffectBase(HEALING_STRONG)

        @JvmField
        val HARMING = EffectBase("harming", EffectType.HARM)

        @JvmField
        val 瞬间伤害药水 = EffectBase(HARMING)

        @JvmField
        val HARMING_STRONG = EffectBase("strong_harming", EffectType.HARM)

        @JvmField
        val 瞬间伤害药水增强版 = EffectBase(HARMING_STRONG)

        @JvmField
        val POISON = EffectBase("poison", EffectType.POISON)

        @JvmField
        val 中毒药水 = EffectBase(POISON)

        @JvmField
        val POISON_LONG = EffectBase("long_poison", EffectType.POISON)

        @JvmField
        val 中毒药水延长版 = EffectBase(POISON_LONG)

        @JvmField
        val POISON_STRONG = EffectBase("strong_poison", EffectType.POISON)

        @JvmField
        val 中毒药水增强版 = EffectBase(POISON_STRONG)

        @JvmField
        val REGENERATION = EffectBase("regeneration", EffectType.REGENERATION)

        @JvmField
        val 再生药水 = EffectBase(REGENERATION)

        @JvmField
        val REGENERATION_LONG = EffectBase("long_regeneration", EffectType.REGENERATION)

        @JvmField
        val 再生药水延长版 = EffectBase(REGENERATION)

        @JvmField
        val REGENERATION_STRONG = EffectBase("strong_regeneration", EffectType.REGENERATION)

        @JvmField
        val 再生药水增强版 = EffectBase(REGENERATION_STRONG)

        @JvmField
        val STRENGTH = EffectBase("strength", EffectType.INCREASE_DAMAGE)

        @JvmField
        val 力量药水 = EffectBase(STRENGTH)

        @JvmField
        val STRENGTH_LONG = EffectBase("long_strength", EffectType.INCREASE_DAMAGE)

        @JvmField
        val 力量药水延长版 = EffectBase(STRENGTH_LONG)

        @JvmField
        val STRENGTH_STRONG = EffectBase("strong_strength", EffectType.INCREASE_DAMAGE)

        @JvmField
        val 力量药水增强版 = EffectBase(STRENGTH_STRONG)

        @JvmField
        val WEAKNESS = EffectBase("weakness", EffectType.WEAKNESS)

        @JvmField
        val 虚弱药水 = EffectBase(WEAKNESS)

        @JvmField
        val WEAKNESS_LONG = EffectBase("long_weakness", EffectType.WEAKNESS)

        @JvmField
        val 虚弱药水延长版 = EffectBase(WEAKNESS_LONG)

        @JvmField
        val LUCK = EffectBase("luck", EffectType.WEAKNESS)

        @JvmField
        val 幸运药水 = EffectBase(LUCK)
    }
}
