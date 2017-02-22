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


package com.minecraft.moonlake.api.packet.wrapper;

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.event.core.MoonLakePacketOutEvent;
import com.minecraft.moonlake.api.packet.Packet;
import com.minecraft.moonlake.api.packet.PacketPlayOut;
import org.bukkit.entity.Player;

/**
 * <h1>PacketPlayOutAbstract</h1>
 * Minecraft 数据包输出抽象类
 *
 * @version 1.0
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 */
public abstract class PacketPlayOutAbstract implements PacketPlayOut {

    /**
     * Minecraft 数据包输出抽象类构造函数
     */
    PacketPlayOutAbstract() {
    }

    /**
     * 触发数据包输出事件
     *
     * @param packet 数据包对象
     * @param players 玩家
     * @return 是否阻止
     */
    protected boolean fireEvent(PacketPlayOut packet, Player... players) {

        MoonLakePacketOutEvent mlpoe = new MoonLakePacketOutEvent(packet, players);
        MoonLakeAPI.callEvent(mlpoe);
        return mlpoe.isCancelled();
    }

    /**
     * 将此数据包输出发送到指定玩家
     *
     * @param players 玩家
     * @return 是否成功
     * @throws Exception 如果发送时错误则抛出异常
     */
    protected abstract boolean sendPacket(Player... players) throws Exception;
}
