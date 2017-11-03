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

package com.mcmoonlake.impl.service

import com.mcmoonlake.api.getMoonLake
import io.netty.channel.Channel
import org.bukkit.entity.Player
import org.spigotmc.CustomTimingsHandler

/**
 * 为 Spigot 服务器提供数据包监听器的 Timings 数据统计
 */
class ServicePacketListenerSpigotImpl : ServicePacketListenerImpl() {

    private val receivingTimings = CustomTimingsHandler("MoonLake Packet Listener - onReceiving")
    private val sendingTimings = CustomTimingsHandler("MoonLake Packet Listener - onSending")

    override fun onInitialized() {
        super.onInitialized()
        getMoonLake().logger.info("数据包监听器服务已为 Spigot 服务端启用 Timings 统计.")
    }

    override fun onReceivingAsync(sender: Player?, channel: Channel, packet: Any): Any? {
        receivingTimings.startTiming()
        val result = super.onReceivingAsync(sender, channel, packet)
        receivingTimings.stopTiming()
        return result
    }

    override fun onSendingAsync(receiver: Player?, channel: Channel, packet: Any): Any? {
        sendingTimings.startTiming()
        val result = super.onSendingAsync(receiver, channel, packet)
        sendingTimings.stopTiming()
        return result
    }
}
