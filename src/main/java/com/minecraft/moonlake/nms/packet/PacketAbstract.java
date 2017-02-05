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

import com.minecraft.moonlake.api.event.core.MoonLakePacketDispatchEvent;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.manager.PlayerManager;
import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * <h1>PacketAbstract</h1>
 * 数据包抽象类
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link com.minecraft.moonlake.api.packet.wrapper.PacketPlayOutBukkitAbstract}
 */
@Deprecated
public abstract class PacketAbstract<T extends Packet> implements Packet<T> {

    /**
     * 数据包抽象类构造函数
     */
    public PacketAbstract() {

    }

    @Override
    public abstract void send(Player... players) throws PacketException;

    /**
     * 触发数据包派遣事件
     *
     * @param packet 数据包对象
     * @param players 目标玩家
     * @return 是否阻止
     * @throws IllegalArgumentException 如果数据包对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     */
    protected boolean fireEvent(Packet<?> packet, Player... players) throws IllegalArgumentException {

        Validate.notNull(packet, "The moonlake packet object is null.");
        Validate.notNull(players, "The player object is null.");

        MoonLakePacketDispatchEvent mpde = new MoonLakePacketDispatchEvent(packet, players);
        Bukkit.getServer().getPluginManager().callEvent(mpde);

        return mpde.isCancelled();
    }

    @Override
    public void send(String... players) throws PacketException {

        Validate.notNull(players, "The player object is null.");

        send(PlayerManager.adapter(players));
    }

    @Override
    public void send(MoonLakePlayer... players) throws PacketException {

        Validate.notNull(players, "The player object is null.");

        send(PlayerManager.adapter(players));
    }

    @Override
    public void sendAll() throws PacketException {

        send(PlayerManager.getOnlines());
    }
}
