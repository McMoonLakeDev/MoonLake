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

package com.mcmoonlake.impl.player

import com.mcmoonlake.api.attribute.Attribute
import com.mcmoonlake.api.attribute.AttributeType
import com.mcmoonlake.api.player.MoonLakePlayerAbstract
import com.mcmoonlake.api.utility.MinecraftPlayerMembers
import com.mcmoonlake.api.version.IllegalBukkitVersionException
import com.mcmoonlake.impl.player.attribute.AttributeBase
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

open class MoonLakePlayerBase(
        player: Player
) : MoonLakePlayerAbstract(player) {

    override val ping: Int
        get() = MinecraftPlayerMembers.PING.get(bukkitPlayer) as Int

    override val locale: String
        get() = MinecraftPlayerMembers.LOCALE.get(bukkitPlayer) as String

    override fun getAttribute(type: AttributeType): Attribute
            = AttributeBase(this, type)

    override var isInvulnerable: Boolean
        get() = throw IllegalBukkitVersionException()
        set(value) = throw IllegalBukkitVersionException()

    override var isGlowing: Boolean
        get() = throw IllegalBukkitVersionException()
        set(value) = throw IllegalBukkitVersionException()

    override var isGliding: Boolean
        get() = throw IllegalBukkitVersionException()
        set(value) = throw IllegalBukkitVersionException()

    override var isSilent: Boolean
        get() = throw IllegalBukkitVersionException()
        set(value) = throw IllegalBukkitVersionException()

    override var spectatorTarget: Entity?
        get() = throw IllegalBukkitVersionException()
        set(value) = throw IllegalBukkitVersionException()

    override var itemInMainHand: ItemStack
        get() = throw IllegalBukkitVersionException()
        set(value) = throw IllegalBukkitVersionException()

    override var itemInOffHand: ItemStack
        get() = throw IllegalBukkitVersionException()
        set(value) = throw IllegalBukkitVersionException()

    override fun setCooldown(type: Material, ticks: Int) {
        throw IllegalBukkitVersionException()
    }

    override fun getCooldown(type: Material): Int {
        throw IllegalBukkitVersionException()
    }

    override fun hasCooldown(type: Material): Boolean {
        throw IllegalBukkitVersionException()
    }

    override fun stopSound(sound: Sound) {
        throw IllegalBukkitVersionException()
    }

    override fun stopSound(sound: String) {
        throw IllegalBukkitVersionException()
    }
}
