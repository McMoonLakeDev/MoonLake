/*
 * Copyright (C) 2016 The MoonLake Authors
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


package com.minecraft.moonlake.api.packet.listener.channel;

import java.net.SocketAddress;

/**
 * <h1>PacketChannelWrapped</h1>
 * 数据包通道包装类接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface PacketChannelWrapped<T> {

    /**
     * 获取数据包的通道
     *
     * @return 通道
     */
    T channel();

    /**
     * 获取数据包通道的远程地址
     *
     * @return 数据包通道远程地址
     */
    SocketAddress getRemoteAddress();

    /**
     * 获取数据包通道的本地地址
     *
     * @return 数据包通道本地地址
     */
    SocketAddress getLocalAddress();
}
