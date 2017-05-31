/*
 * Copyright (C) 2017 The MoonLake Authors
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

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

/**
 * <h1>PacketMessageListener</h1>
 * 数据包消息监听器接口
 *
 * @version 1.0
 * @author Month_Light
 * @see PluginMessageListener
 */
public interface PacketMessageListener extends PluginMessageListener {

    /**
     * 当接收到数据包消息时此函数会被调用
     *
     * @param channel 通道
     * @param sender 发送者
     * @param data 数据
     */
    @Override
    void onPluginMessageReceived(String channel, Player sender, byte[] data);
}
