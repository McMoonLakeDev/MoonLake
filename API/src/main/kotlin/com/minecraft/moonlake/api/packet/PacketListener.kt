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

import org.bukkit.plugin.Plugin

interface PacketListener {

    val plugin: Plugin

    val priority: PacketListenerPriority

    val sendingTypes: Set<Class<out PacketOut>>

    val receivingTypes: Set<Class<out PacketIn>>

    /**
     * Execute asynchronously on non-main threads when the server sends packets to the client.
     * If the [event] is cancelled, the client will not receive the packet.
     */
    fun onSending(event: PacketEvent)

    /**
     * Execute asynchronously on non-main threads when the client sends packets to the server.
     * If the [event] is cancelled, the server will not receive the packet.
     */
    fun onReceiving(event: PacketEvent)

    /**
     * If an exception is executed when [onSending] or [onReceiving], this method is called.
     * @param [ex] Exception Cause
     */
    fun handlerException(ex: Exception)
}
