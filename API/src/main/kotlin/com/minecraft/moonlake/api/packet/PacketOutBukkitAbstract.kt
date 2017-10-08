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

package com.minecraft.moonlake.api.packet

import com.minecraft.moonlake.api.player.MoonLakePlayer
import com.minecraft.moonlake.api.utility.MinecraftReflection
import org.bukkit.entity.Player

abstract class PacketOutBukkitAbstract(clazzName: String) : PacketBukkitAbstract(MinecraftReflection.getMinecraftClass(clazzName)), PacketOutBukkit {

    override fun send(receiver: MoonLakePlayer)
            = send(receiver.bukkitPlayer)

    override fun send(receiver: Player) = try {
        Packets.sendPacket(receiver, handle)
    } catch(e: Exception) {
        throw PacketException(e)
    }

    override fun send(receivers: Array<Player>) {
        val packet = handle
        receivers.forEach {
            try {
                Packets.sendPacket(it, packet)
            } catch(e: Exception) {
                throw PacketException(e)
            }
        }
    }

    override fun send(receivers: Array<MoonLakePlayer>)
            = send(receivers.map { it.bukkitPlayer }.toTypedArray())
}
