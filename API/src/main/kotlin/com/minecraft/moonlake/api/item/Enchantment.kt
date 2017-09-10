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

import com.minecraft.moonlake.api.version.MinecraftVersion

enum class Enchantment(val id: Int, val max: Int, val mcVer: MinecraftVersion? = null) {

    PROTECTION(0, 4),                                                                  保护(PROTECTION),
    PROTECTION_FIRE(1, 4),                                                          火焰保护(PROTECTION_FIRE),
    PROTECTION_FALL(2, 4),                                                         摔落保护(PROTECTION_FALL),
    PROTECTION_EXPLOSIONS(3, 4),                                            爆炸保护(PROTECTION_EXPLOSIONS),
    PROTECTION_PROJECTILE(4, 4),                                              弹射物保护(PROTECTION_PROJECTILE),
    OXYGEN(5, 3),                                                                         水下呼吸(OXYGEN),
    WATER_WORKER(6, 1),                                                            水下速倔(WATER_WORKER),
    THORNS(7, 3),                                                                         荆棘(THORNS),
    DEPTH_STRIDER(8, 3),                                                              深海探索者(DEPTH_STRIDER),
    FROST_WALKER(9, 2, MinecraftVersion.V1_9),                         冰霜行者(FROST_WALKER),
    BINDING_CURSE(10, 1, MinecraftVersion.V1_11),                   绑定诅咒(BINDING_CURSE),

    DAMAGE(16, 5),                                                                      锋利(DAMAGE),
    DAMAGE_UNDEAD(17, 5),                                                      亡灵杀手(DAMAGE_UNDEAD),
    DAMAGE_ARTHROPODS(18, 5),                                              节肢杀手(DAMAGE_ARTHROPODS),
    KNOCKBACK(19, 2),                                                                击退(KNOCKBACK),
    FIRE_ASPECT(20, 2),                                                                 火焰附加(FIRE_ASPECT),
    LOOT_BONUS_MOBS(21, 3),                                                   抢夺(LOOT_BONUS_MOBS),
    SWEEPING_EDGE(22, 3, MinecraftVersion(1, 11, 1)),                横扫之刃(SWEEPING_EDGE),

    DIG_SPEED(32, 5),                                                                   效率(DIG_SPEED),
    SILK_TOUCH(33, 1),                                                                 精准采集(SILK_TOUCH),
    DURABILITY(34, 3),                                                                  耐久(DURABILITY),
    LOOT_BONUS_BLOCKS(35, 3),                                                时运(LOOT_BONUS_BLOCKS),

    ARROW_DAMAGE(48, 5),                                                         力量(ARROW_DAMAGE),
    ARROW_KNOCKBACK(49, 2),                                                   冲击(ARROW_KNOCKBACK),
    ARROW_FIRE(50, 1),                                                                 火矢(ARROW_FIRE),
    ARROW_INFINITE(51, 1),                                                          无限(ARROW_INFINITE),

    LUCK(61, 3),                                                                             海至眷顾(LUCK),
    LURE(62, 3),                                                                              饵钓(LURE),

    MENDING(70, 1, MinecraftVersion.V1_9),                                 经验修补(MENDING),
    VANISHING_CURSE(71, 1, MinecraftVersion.V1_11),            	消失诅咒(VANISHING_CURSE),
    ;

    constructor(equivalent: Enchantment) : this(equivalent.id, equivalent.max, equivalent.mcVer)

    /**
     * @throws IllegalArgumentException if the level is unsafe
     */
    @Throws(IllegalArgumentException::class)
    fun checkSafe(level: Int) // TODO check item enchantment support
            = if(level < 1 || level > max) throw IllegalArgumentException() else Unit
}
