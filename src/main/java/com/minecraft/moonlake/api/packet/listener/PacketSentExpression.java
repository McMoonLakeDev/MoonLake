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
import com.minecraft.moonlake.api.packet.listener.handler.PacketSent;
import org.bukkit.entity.Player;

/**
 * <h1>PacketSentExpression</h1>
 * 数据包发送接口实现类
 *
 * @version 1.0
 * @author Month_Light
 */
class PacketSentExpression extends PacketHandlerAbstractExpression implements PacketSent {

    /**
     * 数据包发送接口实现类构造函数
     *
     * @param packet 数据包
     * @param cancellable 阻止器
     * @param player 玩家
     */
    public PacketSentExpression(Object packet, Cancellable cancellable, Player player) {

        super(packet, cancellable, player);
    }

    /**
     * 数据包发送接口实现类构造函数
     *
     * @param packet 数据包
     * @param cancellable 阻止器
     * @param channelWrapped 通道包装
     */
    public PacketSentExpression(Object packet, Cancellable cancellable, PacketChannelWrapped channelWrapped) {

        super(packet, cancellable, channelWrapped);
    }
}
