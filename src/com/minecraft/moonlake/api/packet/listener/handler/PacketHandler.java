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


package com.minecraft.moonlake.api.packet.listener.handler;

import org.bukkit.plugin.Plugin;

/**
 * <h1>PacketHandler</h1>
 * 数据包处理器（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public abstract class PacketHandler {

    private final Plugin plugin;

    /**
     * 数据包处理器类构造函数
     *
     * @param plugin 插件
     */
    public PacketHandler(Plugin plugin) {

        this.plugin = plugin;
    }

    /**
     * 获取此数据包处理器的插件
     *
     * @return 插件
     */
    public Plugin getPlugin() {

        return plugin;
    }

    /**
     * 数据包处理器的发送
     *
     * @param packet 数据包
     */
    public abstract void onSend(PacketSent packet);

    /**
     * 数据包处理器的接收
     *
     * @param packet 数据包
     */
    public abstract void onReceive(PacketReceived packet);
}
