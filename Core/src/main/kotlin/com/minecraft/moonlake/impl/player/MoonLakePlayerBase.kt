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

package com.minecraft.moonlake.impl.player

import com.minecraft.moonlake.api.attribute.Attribute
import com.minecraft.moonlake.api.attribute.AttributeType
import com.minecraft.moonlake.api.player.IllegalOfflinePlayerException
import com.minecraft.moonlake.api.player.MoonLakePlayerAbstract
import com.minecraft.moonlake.api.utility.MinecraftPlayerMembers
import com.minecraft.moonlake.api.version.IllegalBukkitVersionException
import com.minecraft.moonlake.impl.player.attribute.AttributeBase
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

open class MoonLakePlayerBase : MoonLakePlayerAbstract {

    /** constructor */

    @Throws(IllegalOfflinePlayerException::class)
    constructor(uuid: UUID) : super(uuid)

    @Throws(IllegalOfflinePlayerException::class)
    constructor(player: Player) : super(player)

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

    override fun stopSound(sound: Sound) {
        throw IllegalBukkitVersionException()
    }

    override fun stopSound(sound: String) {
        throw IllegalBukkitVersionException()
    }
}
