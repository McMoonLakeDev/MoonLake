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
 
 
package com.minecraft.moonlake.api.packet.wrapper;

import com.minecraft.moonlake.api.packet.Packet;
import com.minecraft.moonlake.api.packet.PacketPlayOut;
import com.minecraft.moonlake.api.packet.PacketPlayOutBukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * <h1>PacketPlayOutCrashClient</h1>
 * 数据包输出崩溃客户端（详细doc待补充...）
 *
 * @version 2.0
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketPlayOutBukkit
 */
public class PacketPlayOutCrashClient extends PacketPlayOutBukkitAbstract {

    /**
     * 数据包输出崩溃客户端构造函数
     */
    public PacketPlayOutCrashClient() {
    }

    @Override
    public Class<?> getPacketClass() {

        return super.getPacketClass();
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        // 触发事件判断如果为 true 则阻止发送
        if(super.fireEvent(this, players))
            return true;

        for(Player player : players)
            new PacketPlayOutExplosion(player.getLocation(), Float.MAX_VALUE, new ArrayList<>(), new Vector(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE)).send(player);

        return true;
    }

    @Nullable
    @Override
    public Object packet() {
        return null;
    }
}
