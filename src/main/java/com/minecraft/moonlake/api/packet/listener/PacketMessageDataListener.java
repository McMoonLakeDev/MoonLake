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

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * <h1>PacketMessageDataListener</h1>
 * 数据包消息数据监听器抽象类
 *
 * @version 1.0
 * @author Month_Light
 * @see PacketMessageListener
 */
public abstract class PacketMessageDataListener implements PacketMessageListener {

    @Override
    public final void onPluginMessageReceived(String channel, Player sender, byte[] data) {
        try {
            DataInputStream dis = null;
            try {
                dis = new DataInputStream(new ByteArrayInputStream(data));
                onPluginMessageReceived(channel, sender, dis);
            } finally {
                if(dis != null)
                    dis.close();
            }
        } catch (IOException e) {
            handleEx(e);
        }
    }

    /**
     * 当接收到数据包消息时此函数会被调用
     *
     * @param channel 通道
     * @param sender 发送者
     * @param dis 数据输入流
     */
    public abstract void onPluginMessageReceived(String channel, Player sender, DataInputStream dis) throws IOException;

    /**
     * 当处理数据包消息函数抛出 IO 异常时调用
     *
     * @param ex 异常
     */
    protected abstract void handleEx(IOException ex);
}
