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

/**
 * <h1>PacketListener</h1>
 * 数据包监听器接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface PacketListener {

    /**
     * 处理此数据包通道的数据包发送
     *
     * @param receiver 接收者
     * @param packet 数据包
     * @param cancellable 阻止器
     * @return 数据包
     */
    Object onPacketSend(Object receiver, Object packet, Cancellable cancellable);

    /**
     * 处理此数据包通道的数据包接收
     *
     * @param sender 发送者
     * @param packet 数据包
     * @param cancellable 阻止器
     * @return 数据包
     */
    Object onPacketReceive(Object sender, Object packet, Cancellable cancellable);
}
