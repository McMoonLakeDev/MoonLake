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

package com.minecraft.moonlake.api.effect

class EffectBase private constructor(val value: String, val effect: EffectType? = null) {

    private constructor(equivalent: EffectBase) : this(equivalent.value, equivalent.effect)

    fun hasEffect(): Boolean
            = effect != null

    companion object {

        @JvmStatic
        val WATER = EffectBase("water")

        @JvmStatic
        val 水瓶 = EffectBase(WATER)

        @JvmStatic
        val WATER_MUNDANE = EffectBase("mundane")

        @JvmStatic
        val 平凡药水 = EffectBase(WATER_MUNDANE)

        @JvmStatic
        val WATER_THICK = EffectBase("thick")

        @JvmStatic
        val 浑浊药水 = EffectBase(WATER_THICK)

        @JvmStatic
        val WATER_AWKWAARD = EffectBase("awkward")

        @JvmStatic
        val 粗制药水 = EffectBase(WATER_AWKWAARD)

        @JvmStatic
        val NIGHT_VISION = EffectBase("night_vision", EffectType.NIGHT_VISION)

        @JvmStatic
        val 夜视药水 = EffectBase(NIGHT_VISION)

        @JvmStatic
        val NIGHT_VISION_LONG = EffectBase("long_night_vision", EffectType.NIGHT_VISION)

        @JvmStatic
        val 夜视药水延长版 = EffectBase(NIGHT_VISION_LONG)

        @JvmStatic
        val INVISIBILITY = EffectBase("invisibility", EffectType.INVISIBILITY)

        @JvmStatic
        val 隐身药水 = EffectBase(INVISIBILITY)

        @JvmStatic
        val INVISIBILITY_LONG = EffectBase("long_invisibility", EffectType.INVISIBILITY)

        @JvmStatic
        val 隐身药水延长版 = EffectBase(INVISIBILITY_LONG)

        @JvmStatic
        val LEAPING = EffectBase("leaping", EffectType.JUMP)

        @JvmStatic
        val 跳跃药水 = EffectBase(LEAPING)

        @JvmStatic
        val LEAPING_LONG = EffectBase("long_leaping", EffectType.JUMP)

        @JvmStatic
        val 跳跃药水延长版 = EffectBase(LEAPING_LONG)

        @JvmStatic
        val LEAPING_STRONG = EffectBase("strong_leaping", EffectType.JUMP)

        @JvmStatic
        val 跳跃药水增强版 = EffectBase(LEAPING_STRONG)

        @JvmStatic
        val FIRE_RESISTANCE = EffectBase("fire_resistance", EffectType.FIRE_RESISTANCE)

        @JvmStatic
        val 抗火药水 = EffectBase(FIRE_RESISTANCE)

        @JvmStatic
        val FIRE_RESISTANCE_LONG = EffectBase("long_fire_resistance", EffectType.FIRE_RESISTANCE)

        @JvmStatic
        val 抗火药水延长版 = EffectBase(FIRE_RESISTANCE_LONG)

        @JvmStatic
        val SWIFTNESS = EffectBase("swiftness", EffectType.SPEED)

        @JvmStatic
        val 迅捷药水 = EffectBase(SWIFTNESS)

        @JvmStatic
        val SWIFTNESS_LONG = EffectBase("long_swiftness", EffectType.SPEED)

        @JvmStatic
        val 迅捷药水延长版 = EffectBase(SWIFTNESS_LONG)

        @JvmStatic
        val SWIFTNESS_STRONG = EffectBase("strong_swiftness", EffectType.SPEED)

        @JvmStatic
        val 迅捷药水增强版 = EffectBase(SWIFTNESS_STRONG)

        @JvmStatic
        val SLOWNESS = EffectBase("slowness", EffectType.SLOW)

        @JvmStatic
        val 缓慢药水 = EffectBase(SLOWNESS)

        @JvmStatic
        val SLOWNESS_LONG = EffectBase("long_slowness", EffectType.SLOW)

        @JvmStatic
        val 缓慢药水延长版 = EffectBase(SLOWNESS_LONG)

        @JvmStatic
        val WATER_BREATHING = EffectBase("water_breathing", EffectType.WATER_BREATHING)

        @JvmStatic
        val 水下呼吸药水 = EffectBase(WATER_BREATHING)

        @JvmStatic
        val WATER_BREATHING_LONG = EffectBase("long_water_breathing", EffectType.WATER_BREATHING)

        @JvmStatic
        val 水下呼吸药水延长版 = EffectBase(WATER_BREATHING_LONG)

        @JvmStatic
        val HEALING = EffectBase("healing", EffectType.HEAL)

        @JvmStatic
        val 瞬间治疗药水 = EffectBase(HEALING)

        @JvmStatic
        val HEALING_STRONG = EffectBase("strong_healing", EffectType.HEAL)

        @JvmStatic
        val 瞬间治疗药水增强版 = EffectBase(HEALING_STRONG)

        @JvmStatic
        val HARMING = EffectBase("harming", EffectType.HARM)

        @JvmStatic
        val 瞬间伤害药水 = EffectBase(HARMING)

        @JvmStatic
        val HARMING_STRONG = EffectBase("strong_harming", EffectType.HARM)

        @JvmStatic
        val 瞬间伤害药水增强版 = EffectBase(HARMING_STRONG)

        @JvmStatic
        val POISON = EffectBase("poison", EffectType.POISON)

        @JvmStatic
        val 中毒药水 = EffectBase(POISON)

        @JvmStatic
        val POISON_LONG = EffectBase("long_poison", EffectType.POISON)

        @JvmStatic
        val 中毒药水延长版 = EffectBase(POISON_LONG)

        @JvmStatic
        val POISON_STRONG = EffectBase("strong_poison", EffectType.POISON)

        @JvmStatic
        val 中毒药水增强版 = EffectBase(POISON_STRONG)

        @JvmStatic
        val REGENERATION = EffectBase("regeneration", EffectType.REGENERATION)

        @JvmStatic
        val 再生药水 = EffectBase(REGENERATION)

        @JvmStatic
        val REGENERATION_LONG = EffectBase("long_regeneration", EffectType.REGENERATION)

        @JvmStatic
        val 再生药水延长版 = EffectBase(REGENERATION)

        @JvmStatic
        val REGENERATION_STRONG = EffectBase("strong_regeneration", EffectType.REGENERATION)

        @JvmStatic
        val 再生药水增强版 = EffectBase(REGENERATION_STRONG)

        @JvmStatic
        val STRENGTH = EffectBase("strength", EffectType.INCREASE_DAMAGE)

        @JvmStatic
        val 力量药水 = EffectBase(STRENGTH)

        @JvmStatic
        val STRENGTH_LONG = EffectBase("long_strength", EffectType.INCREASE_DAMAGE)

        @JvmStatic
        val 力量药水延长版 = EffectBase(STRENGTH_LONG)

        @JvmStatic
        val STRENGTH_STRONG = EffectBase("strong_strength", EffectType.INCREASE_DAMAGE)

        @JvmStatic
        val 力量药水增强版 = EffectBase(STRENGTH_STRONG)

        @JvmStatic
        val WEAKNESS = EffectBase("weakness", EffectType.WEAKNESS)

        @JvmStatic
        val 虚弱药水 = EffectBase(WEAKNESS)

        @JvmStatic
        val WEAKNESS_LONG = EffectBase("long_weakness", EffectType.WEAKNESS)

        @JvmStatic
        val 虚弱药水延长版 = EffectBase(WEAKNESS_LONG)

        @JvmStatic
        val LUCK = EffectBase("luck", EffectType.WEAKNESS)

        @JvmStatic
        val 幸运药水 = EffectBase(LUCK)
    }
}
