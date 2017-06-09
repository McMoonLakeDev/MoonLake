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

import java.io.DataInputStream;
import java.io.IOException;

/**
 * <h1>PacketMessageDataListenerAdapter</h1>
 * 数据包消息数据监听器适配器类
 *
 * @version 1.0
 * @author Month_Light
 * @see PacketMessageDataListener
 */
public class PacketMessageDataListenerAdapter extends PacketMessageDataListener {

    @Override
    public void onPluginMessageReceived(String channel, Player sender, DataInputStream dis) throws IOException {
    }

    @Override
    protected void handleEx(IOException ex) {
        ex.printStackTrace();
    }
}
