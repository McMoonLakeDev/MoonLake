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

package com.minecraft.moonlake.api.item

import com.minecraft.moonlake.api.Valuable
import com.minecraft.moonlake.api.adapter.AdapterBukkit
import com.minecraft.moonlake.api.version.MinecraftVersion

enum class Enchantment(val id: Int, val max: Int, val type: String, val mcVer: MinecraftVersion? = null) : AdapterBukkit<org.bukkit.enchantments.Enchantment>, Valuable<String> {

    PROTECTION(0, 4, "PROTECTION_"),                                                                  	   保护(PROTECTION),
    PROTECTION_FIRE(1, 4, "PROTECTION_FIRE"),                                                        火焰保护(PROTECTION_FIRE),
    PROTECTION_FALL(2, 4, "PROTECTION_FALL"),                                                      摔落保护(PROTECTION_FALL),
    PROTECTION_EXPLOSIONS(3, 4, "PROTECTION_EXPLOSIONS"),                            爆炸保护(PROTECTION_EXPLOSIONS),
    PROTECTION_PROJECTILE(4, 4, "PROTECTION_PROJECTILE"),                                 弹射物保护(PROTECTION_PROJECTILE),
    OXYGEN(5, 3, "OXYGEN"),                                                                         			   水下呼吸(OXYGEN),
    WATER_WORKER(6, 1, "WATER_WORKER"),                                                             水下速倔(WATER_WORKER),
    THORNS(7, 3, "THORNS"),                                                                         			   荆棘(THORNS),
    DEPTH_STRIDER(8, 3, "DEPTH_STRIDER"),                                                                深海探索者(DEPTH_STRIDER),
    FROST_WALKER(9, 2, "FROST_WALKER", MinecraftVersion.V1_9),                            冰霜行者(FROST_WALKER),
    BINDING_CURSE(10, 1, "BINDING_CURSE", MinecraftVersion.V1_11),                    绑定诅咒(BINDING_CURSE),

    DAMAGE(16, 5, "DAMAGE_ALL"),                                                                      	   锋利(DAMAGE),
    DAMAGE_UNDEAD(17, 5, "DAMAGE_UNDEAD"),                                                   亡灵杀手(DAMAGE_UNDEAD),
    DAMAGE_ARTHROPODS(18, 5, "DAMAGE_ARTHROPODS"),                                   节肢杀手(DAMAGE_ARTHROPODS),
    KNOCKBACK(19, 2, "KNOCKBACK"),                                                                	   击退(KNOCKBACK),
    FIRE_ASPECT(20, 2, "FIRE_ASPECT"),                                                                 	   火焰附加(FIRE_ASPECT),
    LOOT_BONUS_MOBS(21, 3, "LOOT_BONUS_MOBS"),                                            抢夺(LOOT_BONUS_MOBS),
    SWEEPING_EDGE(22, 3, "SWEEPING_EDGE", MinecraftVersion(1, 11, 1)),                横扫之刃(SWEEPING_EDGE),

    DIG_SPEED(32, 5, "DIG_SPEED"),                                                                   			效率(DIG_SPEED),
    SILK_TOUCH(33, 1, "SILK_TOUCH"),                                                                 		精准采集(SILK_TOUCH),
    DURABILITY(34, 3, "DURABILITY"),                                                                  			耐久(DURABILITY),
    LOOT_BONUS_BLOCKS(35, 3, "LOOT_BONUS_BLOCKS"),                                       时运(LOOT_BONUS_BLOCKS),

    ARROW_DAMAGE(48, 5, "ARROW_DAMAGE"),                                                        力量(ARROW_DAMAGE),
    ARROW_KNOCKBACK(49, 2, "ARROW_KNOCKBACK"),                                             冲击(ARROW_KNOCKBACK),
    ARROW_FIRE(50, 1, "ARROW_FIRE"),                                                                 		火矢(ARROW_FIRE),
    ARROW_INFINITE(51, 1, "ARROW_INFINITE"),                                                          无限(ARROW_INFINITE),

    LUCK(61, 3, "LUCK"),                                                                             					海至眷顾(LUCK),
    LURE(62, 3, "LURE"),                                                                              					饵钓(LURE),

    MENDING(70, 1, "MENDING", MinecraftVersion.V1_9),                                 			经验修补(MENDING),
    VANISHING_CURSE(71, 1, "VANISHING_CURSE", MinecraftVersion.V1_11),           	消失诅咒(VANISHING_CURSE),
    ;

    constructor(equivalent: Enchantment) : this(equivalent.id, equivalent.max, equivalent.type, equivalent.mcVer)

    override fun value(): String
            = type

    /**
     * @throws IllegalArgumentException if the level is unsafe
     */
    @Throws(IllegalArgumentException::class)
    fun checkSafe(level: Int) // TODO check item enchantment support
            = if(level < 1 || level > max) throw IllegalArgumentException() else Unit

    override fun cast(): org.bukkit.enchantments.Enchantment
            = org.bukkit.enchantments.Enchantment.getByName(type)

    companion object {

        @JvmStatic
        private val ID_MAP: MutableMap<Int, Enchantment> = HashMap()

        init {
            val regex = Regex("(i?)[a-z]([a-z_\\d]*)")
            values().forEach { if(it.name.matches(regex)) ID_MAP.put(it.id, it) }
        }

        @JvmStatic
        @JvmName("fromName")
        fun fromName(name: String): Enchantment
                = Enchantment.valueOf(name.toUpperCase())

        @JvmStatic
        @JvmName("fromId")
        @Throws(IllegalArgumentException::class)
        fun fromId(id: Int): Enchantment
                = ID_MAP[id] ?: throw IllegalArgumentException("错误: 无效的附魔效果 ID $id 值.")

        @JvmStatic
        @JvmName("hasId")
        fun hasId(id: Int): Boolean
                = ID_MAP.containsKey(id)
    }
}
