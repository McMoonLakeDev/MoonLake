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

package com.mcmoonlake.api.packet

import com.mcmoonlake.api.player.MoonLakePlayer
import org.bukkit.Location
import org.bukkit.entity.Player

interface PacketOut : Packet {

    @Throws(PacketException::class)
    fun send(receiver: Player)

    @Throws(PacketException::class)
    fun send(receiver: MoonLakePlayer)

    @Throws(PacketException::class)
    fun send(receivers: Array<Player>)

    @Throws(PacketException::class)
    fun send(receivers: Array<MoonLakePlayer>)

    @Throws(PacketException::class)
    fun sendToAllPlayer()

    @Throws(PacketException::class)
    fun sendToNearby(center: Location, radius: Double)

    @Throws(PacketException::class)
    fun sendToNearbyExcept(target: Player?, center: Location, radius: Double)

    @Throws(PacketException::class)
    fun sendToNearbyExcept(target: MoonLakePlayer?, center: Location, radius: Double)
}
