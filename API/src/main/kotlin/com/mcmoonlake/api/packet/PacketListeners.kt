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

import com.mcmoonlake.api.getMoonLake
import com.mcmoonlake.api.service.ServicePacketListener
import org.bukkit.plugin.Plugin

object PacketListeners {

    /**
     * @throws UnsupportedOperationException If the service is not available.
     */
    @JvmStatic
    @JvmName("registerListener")
    @Throws(UnsupportedOperationException::class)
    fun registerListener(listener: PacketListener): Boolean
            = checkServiceAndGet().registerListener(listener)

    /**
     * @throws UnsupportedOperationException If the service is not available.
     */
    @JvmStatic
    @JvmName("unregisterListener")
    @Throws(UnsupportedOperationException::class)
    fun unregisterListener(listener: PacketListener): Boolean
            = checkServiceAndGet().unregisterListener(listener)

    /**
     * @throws UnsupportedOperationException If the service is not available.
     */
    @JvmStatic
    @JvmName("unregisterListener")
    @Throws(UnsupportedOperationException::class)
    fun unregisterListener(plugin: Plugin): Boolean
            = checkServiceAndGet().unregisterListener(plugin)

    /**
     * @throws UnsupportedOperationException If the service is not available.
     */
    @JvmStatic
    @JvmName("unregisterListenerAll")
    @Throws(UnsupportedOperationException::class)
    fun unregisterListenerAll()
            = checkServiceAndGet().unregisterListenerAll()

    @JvmStatic
    @JvmName("checkServiceAndGet")
    @Throws(UnsupportedOperationException::class)
    private fun checkServiceAndGet(): ServicePacketListener
            = getMoonLake().serviceManager.getServiceSafe(ServicePacketListener::class.java) ?: throw UnsupportedOperationException("错误: 数据包监听器服务不可用, 请检查配置文件是否开启.")
}
