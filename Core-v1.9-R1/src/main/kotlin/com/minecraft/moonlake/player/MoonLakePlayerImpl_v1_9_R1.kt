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

package com.minecraft.moonlake.player

import com.minecraft.moonlake.api.attribute.Attribute
import com.minecraft.moonlake.api.attribute.AttributeType
import com.minecraft.moonlake.api.player.IllegalOfflinePlayerException
import org.bukkit.Color
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*

class MoonLakePlayerImpl_v1_9_R1 : MoonLakePlayerBase {

    /** constructor */

    @Throws(IllegalOfflinePlayerException::class)
    constructor(uuid: UUID) : super(uuid)

    @Throws(IllegalOfflinePlayerException::class)
    constructor(player: Player) : super(player)

    override fun getAttribute(type: AttributeType): Attribute
            = throw UnsupportedOperationException() // TODO

    override fun isInvulnerable(): Boolean
            = getBukkitPlayer().isInvulnerable

    override fun setInvulnerable(invulnerable: Boolean)
            { getBukkitPlayer().isInvulnerable = invulnerable }

    override fun isGlowing(): Boolean
            = getBukkitPlayer().isGlowing

    override fun setGlowing(glowing: Boolean)
            { getBukkitPlayer().isGlowing = glowing }

    override fun isGliding(): Boolean
            = getBukkitPlayer().isGliding

    override fun setGliding(gliding: Boolean)
            { getBukkitPlayer().isGliding = gliding }

    override fun getSpectatorTarget(): Entity
            = getBukkitPlayer().spectatorTarget

    override fun setSpectatorTarget(target: Entity)
            { getBukkitPlayer().spectatorTarget = target }

    override fun addPotionEffect(type: PotionEffectType, amplifier: Int, duration: Int, ambient: Boolean, particles: Boolean, color: Color): Boolean
            = getBukkitPlayer().addPotionEffect(PotionEffect(type, duration, amplifier, ambient, particles, color))

    override fun getItemInHand(): ItemStack
            = getItemInMainHand()

    override fun setItemInHand(itemStack: ItemStack?)
            = setItemInMainHand(itemStack)

    override fun getItemInMainHand(): ItemStack
            = inventory.itemInMainHand

    override fun setItemInMainHand(itemStack: ItemStack?)
            { inventory.itemInMainHand = itemStack }

    override fun getItemInOffHand(): ItemStack
            = inventory.itemInOffHand

    override fun setItemInOffHand(itemStack: ItemStack?)
            { inventory.itemInOffHand = itemStack }
}
