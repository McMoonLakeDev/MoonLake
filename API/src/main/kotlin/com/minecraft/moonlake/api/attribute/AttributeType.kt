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

enum class AttributeType(val def: Double, val min: Double, val max: Double, val mcVer: MinecraftVersion? = null) : Valuable<String> {

    MAX_HEALTH(20.0, .0, 2048.0) {
        override fun value(): String
                = "generic.maxHealth"
    },
    FOLLOW_RANGE(32.0, .0, 2048.0) {
        override fun value(): String
                = "generic.followRange"
    },
    KNOCKBACK_RESISTANCE(.0, .0, 1.0) {
        override fun value(): String
                = "generic.knockbackResistance"
    },
    MOVEMENT_SPEED(0.699999988079071, .0, 2048.0) {
        override fun value(): String
                = "generic.movementSpeed"
    },
    ATTACK_DAMAGE(2.0, .0, 2048.0) {
        override fun value(): String
                = "generic.attackDamage"
    },
    ATTACK_SPEED(4.0, .0, 1024.0, MinecraftVersion.V1_9) {
        override fun value(): String
                = "generic.attackSpeed"
    },
    ARMOR(.0, .0, 30.0, MinecraftVersion.V1_9) {
        override fun value(): String
                = "generic.armor"
    },
    ARMOR_TOUGHNESS(.0, .0, 20.0, MinecraftVersion.V1_9) {
        override fun value(): String
                = "generic.armorToughness"
    },
    LUCK(.0, -1024.0, 1024.0, MinecraftVersion.V1_9) {
        override fun value(): String
                = "generic.luck"
    },
    FLYING_SPEED(0.4000000059604645, .0, 1024.0, MinecraftVersion.V1_12) {
        override fun value(): String
                = "generic.flyingSpeed"
    },
    ;

    @Throws(IllegalBukkitVersionException::class)
    fun validateSupport() = mcVer.let {
        if(it != null && !MinecraftVersion.currentVersion().isOrLater(it))
            throw IllegalBukkitVersionException("当前 Bukkit 版本不支持此 $this 属性类型.")
    }
}
