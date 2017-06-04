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
 
 
package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.nms.packet.exception.PacketException;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;

/**
 * <h1>PacketPlayOutCrashClient</h1>
 * 数据包输出崩溃客户端（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v1.9-a5 删除. 请使用 {@link com.minecraft.moonlake.api.packet.wrapper.PacketPlayOutCrashClient}
 */
@Deprecated
public class PacketPlayOutCrashClient extends PacketAbstract<PacketPlayOutCrashClient> {

    /**
     * 数据包输出崩溃客户端类构造函数
     */
    public PacketPlayOutCrashClient() {

    }

    @Override
    public void send(Player... players) throws PacketException {

        if(super.fireEvent(this, players)) return;

        for(final Player player : players) {

            PacketPlayOutExplosion packetPlayOutExplosion = new PacketPlayOutExplosion(player.getLocation(), Float.MAX_VALUE, new ArrayList<>(), new Vector(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE));
            packetPlayOutExplosion.send(player);
        }
    }
}
