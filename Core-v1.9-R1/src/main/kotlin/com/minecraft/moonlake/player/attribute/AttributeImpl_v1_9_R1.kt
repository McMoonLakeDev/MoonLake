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

package com.minecraft.moonlake.player.attribute

import com.minecraft.moonlake.api.attribute.AttributeType
import com.minecraft.moonlake.api.notNull
import com.minecraft.moonlake.api.player.MoonLakePlayer
import org.bukkit.attribute.AttributeInstance

open class AttributeImpl_v1_9_R1(player: MoonLakePlayer, type: AttributeType) : AttributeBase(player, type) {

    override fun getValue(): Double = instance().let {
        when(it == null) {
            true -> super.getValue()
            else -> it.notNull().baseValue
        }
    }

    override fun setValue(value: Double) = instance().let {
        when(it == null) {
            true -> super.setValue(value)
            else -> it.notNull().baseValue = value
        }
    }

    private fun instance(): AttributeInstance? = adapter().let {
        when(it == null) {
            true -> null
            else -> player.getBukkitPlayer().getAttribute(it)
        }
    }

    open protected fun adapter(): org.bukkit.attribute.Attribute? {
        return when(getType()) {
            AttributeType.MAX_HEALTH -> org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH
            AttributeType.FOLLOW_RANGE -> org.bukkit.attribute.Attribute.GENERIC_FOLLOW_RANGE
            AttributeType.KNOCKBACK_RESISTANCE -> org.bukkit.attribute.Attribute.GENERIC_KNOCKBACK_RESISTANCE
            AttributeType.MOVEMENT_SPEED -> org.bukkit.attribute.Attribute.GENERIC_MOVEMENT_SPEED
            AttributeType.ATTACK_DAMAGE -> org.bukkit.attribute.Attribute.GENERIC_ATTACK_DAMAGE
            AttributeType.ATTACK_SPEED -> org.bukkit.attribute.Attribute.GENERIC_ATTACK_SPEED
            AttributeType.ARMOR -> org.bukkit.attribute.Attribute.GENERIC_ARMOR
            AttributeType.LUCK -> org.bukkit.attribute.Attribute.GENERIC_LUCK
            AttributeType.ARMOR_TOUGHNESS -> null
            else -> null
        }
    }
}
