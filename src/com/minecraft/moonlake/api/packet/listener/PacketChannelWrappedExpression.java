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


package com.minecraft.moonlake.api.packet.listener;

import com.minecraft.moonlake.api.packet.listener.channel.PacketChannelWrapped;

import java.net.SocketAddress;

/**
 * <h1>PacketChannelWrappedExpression</h1>
 * 数据包通道包装类接口实现类
 *
 * @version 1.0
 * @author Month_Light
 */
class PacketChannelWrappedExpression<T> implements PacketChannelWrapped<T> {

    private T channel;

    /**
     * 数据包通道包装类接口实现类构造函数
     *
     * @param channel 通道
     */
    public PacketChannelWrappedExpression(T channel) {

        this.channel = channel;
    }

    public T channel() {

        return channel;
    }

    @Override
    public SocketAddress getRemoteAddress() {

        return null;
    }

    @Override
    public SocketAddress getLocalAddress() {

        return null;
    }
}
