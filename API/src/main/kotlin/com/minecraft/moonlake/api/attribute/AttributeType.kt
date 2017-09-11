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

package com.minecraft.moonlake.api.attribute

import com.minecraft.moonlake.api.Valuable
import com.minecraft.moonlake.api.isOrLater
import com.minecraft.moonlake.api.version.IllegalBukkitVersionException
import com.minecraft.moonlake.api.version.MinecraftVersion

enum class AttributeType(val type: String, val def: Double, val min: Double, val max: Double, val mcVer: MinecraftVersion? = null) : Valuable<String> {

    MAX_HEALTH("generic.maxHealth", 20.0, .0, 2048.0), 															最大生命(MAX_HEALTH),
    FOLLOW_RANGE("generic.followRange", 32.0, .0, 2048.0), 													追踪范围(FOLLOW_RANGE),
    KNOCKBACK_RESISTANCE("generic.knockbackResistance", .0, .0, 1.0), 								击退抗性(KNOCKBACK_RESISTANCE),
    MOVEMENT_SPEED("generic.movementSpeed", 0.699999988079071, .0, 2048.0), 				移动速度(MOVEMENT_SPEED),
    ATTACK_DAMAGE("generic.attackDamage", 2.0, .0, 2048.0), 												攻击伤害(ATTACK_DAMAGE),
    ATTACK_SPEED("generic.attackSpeed", 4.0, .0, 1024.0, MinecraftVersion.V1_9), 					攻击速度(ATTACK_SPEED),
    ARMOR("generic.armor", .0, .0, 30.0, MinecraftVersion.V1_9), 												护甲(ARMOR),
    ARMOR_TOUGHNESS("generic.armorToughness", .0, .0, 20.0, MinecraftVersion.V1_9), 		护甲韧性(ARMOR_TOUGHNESS),
    LUCK("generic.luck", .0, -1024.0, 1024.0, MinecraftVersion.V1_9), 										幸运(LUCK),


    /** FLYING_SPEED("generic.flyingSpeed", 0.4000000059604645, .0, 1024.0, MinecraftVersion.V1_12), */
    ;

    constructor(equivalent: AttributeType) : this(equivalent.type, equivalent.def, equivalent.min, equivalent.max, equivalent.mcVer)

    override fun value(): String
            = type

    @Throws(IllegalBukkitVersionException::class)
    fun validateSupport() = mcVer.let {
        if(it != null && !MinecraftVersion.currentVersion().isOrLater(it))
            throw IllegalBukkitVersionException("当前 Bukkit 版本不支持此 $this 属性类型.")
    }
}
