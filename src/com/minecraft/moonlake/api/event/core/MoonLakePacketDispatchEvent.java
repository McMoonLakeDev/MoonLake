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


package com.minecraft.moonlake.api.event.core;

import com.minecraft.moonlake.nms.packet.Packet;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * <h1>MoonLakePacketDispatchEvent</h1>
 * 月色之湖数据包派遣事件类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @see MoonLakePacketEvent
 * @see Packet
 */
public class MoonLakePacketDispatchEvent extends MoonLakePacketEvent implements Cancellable {

    private final static HandlerList handlerList = new HandlerList();
    private final Player[] players;
    private boolean cancel = false;

    /**
     * 月色之湖数据包派遣事件类构造函数
     *
     * @param packet 数据包对象
     * @param players 玩家对象
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     */
    public MoonLakePacketDispatchEvent(Packet<?> packet, Player... players) throws IllegalArgumentException {

        super(packet);

        Validate.notNull(players, "The player object is null.");

        this.players = players;
    }

    /**
     * 获取此数据包派遣事件的 Bukkit 玩家对象
     *
     * @return BukkitPlayer
     */
    public Player[] getBukkitPlayers() {

        return players;
    }

    /*/**
     * 获取此数据包派遣事件的 MoonLake 玩家对象
     *
     * @return MoonLakePlayer
     */
    /*public MoonLakePlayer[] getPlayers() {

        return PlayerManager.adapter(players);
    }*/

    /**
     * 获取此数据包派遣事件是否只有一个目标
     *
     * @return 是否只有一个目标
     */
    public boolean isOnlyOneTarget() {

        return players.length == 1;
    }

    /**
     * 获取此数据包派遣事件的第一个 Bukkit 玩家对象
     *
     * @return BukkitPlayer
     */
    public Player getFirstBukkitTarget() {

        return players[0];
    }

    /*/**
     * 获取此数据包派遣事件的第一个 MoonLake 玩家对象
     *
     * @return MoonLakePlayer
     */
    /*public MoonLakePlayer getFirstTarget() {

        return PlayerManager.adapter(players[0]);
    }*/

    @Override
    public HandlerList getHandlers() {

        return handlerList;
    }

    public static HandlerList getHandlerList() {

        return handlerList;
    }

    @Override
    public boolean isCancelled() {

        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {

        this.cancel = cancel;
    }
}
