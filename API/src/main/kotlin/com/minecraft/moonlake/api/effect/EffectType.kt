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

import com.minecraft.moonlake.api.Valuable
import com.minecraft.moonlake.api.adapter.AdapterBukkit
import com.minecraft.moonlake.api.version.MinecraftVersion
import org.bukkit.potion.PotionEffectType

enum class EffectType(val id: Int, val type: String, val mcVer: MinecraftVersion? = null) : AdapterBukkit<PotionEffectType>, Valuable<Int> {

    SPEED(1, "SPEED"), 														  			 速度(SPEED),
    SLOW(2, "SLOW"), 														  			 缓慢(SLOW),
    FAST_DIGGING(3, "FAST_DIGGING"), 										 急迫(FAST_DIGGING),
    SLOW_DIGGING(4, "SLOW_DIGGING"), 									 挖掘疲劳(SLOW_DIGGING),
    INCREASE_DAMAGE(5, "INCREASE_DAMAGE"), 						 力量(INCREASE_DAMAGE),
    HEAL(6, "HEAL"), 															  			 瞬间治疗(HEAL),
    HARM(7, "HARM"), 														  			 瞬间伤害(HARM),
    JUMP(8, "JUMP"), 															  		 跳跃提升(JUMP),
    CONFUSION(9, "CONFUSION"), 											 反胃(CONFUSION),
    REGENERATION(10, "REGENERATION"), 									 生命恢复(REGENERATION),
    DAMAGE_RESISTANCE(11, "DAMAGE_RESISTANCE"),				 抗性提升(DAMAGE_RESISTANCE),
    FIRE_RESISTANCE(12, "FIRE_RESISTANCE"), 							 防火(FIRE_RESISTANCE),
    WATER_BREATHING(13, "WATER_BREATHING"), 						 水下呼吸(WATER_BREATHING),
    INVISIBILITY(14, "INVISIBILITY"), 											 隐身(INVISIBILITY),
    BLINDNESS(15, "BLINDNESS"), 												 失明(BLINDNESS),
    NIGHT_VISION(16, "NIGHT_VISION"), 									 夜视(NIGHT_VISION),
    HUNGER(17, "HUNGER"), 													  	 饥饿(HUNGER),
    WEAKNESS(18, "WEAKNESS"), 												 虚弱(WEAKNESS),
    POISON(19, "POISON"), 													  		 中毒(POISON),
    WITHER(20, "WITHER"), 													  		 凋零(WITHER),
    HEALTH_BOOST(21, "HEALTH_BOOST"), 									 生命提升(HEALTH_BOOST),
    ABSORPTION(22, "ABSORPTION"), 											 伤害吸收(ABSORPTION),
    SATURATION(23, "SATURATION"), 											 饱和(SATURATION),
    GLOWING(24, "GLOWING", MinecraftVersion.V1_9), 			  	 发光(GLOWING),
    LEVITATION(25, "LEVITATION", MinecraftVersion.V1_9),           漂浮(LEVITATION),
    LUCK(26, "LUCK", MinecraftVersion.V1_9), 				      			 幸运(LUCK),
    UNLUCK(27, "UNLUCK", MinecraftVersion.V1_9),          		  	 霉运(UNLUCK),
    ;

    constructor(equivalent: EffectType) : this(equivalent.id, equivalent.type, equivalent.mcVer)

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
