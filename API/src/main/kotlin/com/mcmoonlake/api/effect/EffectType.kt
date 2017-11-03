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

import com.mcmoonlake.api.Valuable
import com.mcmoonlake.api.adapter.AdapterBukkit
import com.mcmoonlake.api.version.MinecraftVersion
import org.bukkit.potion.PotionEffectType

enum class EffectType(val id: Int, val type: String, val mcVer: MinecraftVersion? = null) : AdapterBukkit<PotionEffectType>, Valuable<Int> {

    /**
     * Effect Type: Speed (效果类型: 速度)
     */
    SPEED(1, "SPEED"),
    /**
     * Effect Type: Slow (效果类型: 缓慢)
     */
    SLOW(2, "SLOW"),
    /**
     * Effect Type: Fast Digging (效果类型: 急迫)
     */
    FAST_DIGGING(3, "FAST_DIGGING"),
    /**
     * Effect Type: Slow Digging (效果类型: 挖掘疲劳)
     */
    SLOW_DIGGING(4, "SLOW_DIGGING"),
    /**
     * Effect Type: Increase Damage (效果类型: 力量)
     */
    INCREASE_DAMAGE(5, "INCREASE_DAMAGE"),
    /**
     * Effect Type: Heal (效果类型: 瞬间治疗)
     */
    HEAL(6, "HEAL"),
    /**
     * Effect Type: Harm (效果类型: 瞬间伤害)
     */
    HARM(7, "HARM"),
    /**
     * Effect Type: Jump (效果类型: 跳跃提升)
     */
    JUMP(8, "JUMP"),
    /**
     * Effect Type: Confusion (效果类型: 反胃)
     */
    CONFUSION(9, "CONFUSION"),
    /**
     * Effect Type: Regeneration (效果类型: 生命恢复)
     */
    REGENERATION(10, "REGENERATION"),
    /**
     * Effect Type: Damage Resistance (效果类型: 抗性提升)
     */
    DAMAGE_RESISTANCE(11, "DAMAGE_RESISTANCE"),
    /**
     * Effect Type: Fire Resistance (效果类型: 防火)
     */
    FIRE_RESISTANCE(12, "FIRE_RESISTANCE"),
    /**
     * Effect Type: Water Breathing (效果类型: 水下呼吸)
     */
    WATER_BREATHING(13, "WATER_BREATHING"),
    /**
     * Effect Type: Invisibility (效果类型: 隐身)
     */
    INVISIBILITY(14, "INVISIBILITY"),
    /**
     * Effect Type: Blindness (效果类型: 失明)
     */
    BLINDNESS(15, "BLINDNESS"),
    /**
     * Effect Type: Night Vision (效果类型: 夜视)
     */
    NIGHT_VISION(16, "NIGHT_VISION"),
    /**
     * Effect Type: Hunger (效果类型: 饥饿)
     */
    HUNGER(17, "HUNGER"),
    /**
     * Effect Type: Weakness (效果类型: 虚弱)
     */
    WEAKNESS(18, "WEAKNESS"),
    /**
     * Effect Type: Poison (效果类型: 中毒)
     */
    POISON(19, "POISON"),
    /**
     * Effect Type: Wither (效果类型: 凋零)
     */
    WITHER(20, "WITHER"),
    /**
     * Effect Type: Health Boost (效果类型: 生命提升)
     */
    HEALTH_BOOST(21, "HEALTH_BOOST"),
    /**
     * Effect Type: Absorption (效果类型: 伤害吸收)
     */
    ABSORPTION(22, "ABSORPTION"),
    /**
     * Effect Type: Saturation (效果类型: 饱和)
     */
    SATURATION(23, "SATURATION"),
    /**
     * Effect Type: Glowing (效果类型: 发光)
     */
    GLOWING(24, "GLOWING", MinecraftVersion.V1_9),
    /**
     * Effect Type: Levitation (效果类型: 漂浮)
     */
    LEVITATION(25, "LEVITATION", MinecraftVersion.V1_9),
    /**
     * Effect Type: Luck (效果类型: 幸运)
     */
    LUCK(26, "LUCK", MinecraftVersion.V1_9),
    /**
     * Effect Type: Un Luck (效果类型: 霉运)
     */
    UNLUCK(27, "UNLUCK", MinecraftVersion.V1_9),
    ;

    override fun value(): Int
            = id

    override fun cast(): PotionEffectType
            = PotionEffectType.getByName(type)

    companion object {

        @JvmStatic
        @JvmName("fromName")
        fun fromName(name: String): EffectType
                = EffectType.valueOf(name.toUpperCase())
    }
}
