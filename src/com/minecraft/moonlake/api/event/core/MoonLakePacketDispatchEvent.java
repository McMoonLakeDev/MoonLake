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

import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.manager.PlayerManager;
import com.minecraft.moonlake.nms.packet.Packet;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class MoonLakePacketDispatchEvent extends MoonLakePacketEvent implements Cancellable {

    private final static HandlerList handlerList = new HandlerList();
    private final Player[] players;
    private boolean cancel = false;

    public MoonLakePacketDispatchEvent(Packet<?> packet, Player... players) {

        super(packet);

        this.players = players;
    }

    public Player[] getBukkitPlayers() {

        return players;
    }

    public MoonLakePlayer[] getPlayers() {

        return PlayerManager.adapter(players);
    }

    public boolean isOnlyOneTarget() {

        return players.length == 1;
    }

    public Player getFirstBukkitTarget() {

        return players[0];
    }

    public MoonLakePlayer getFirstTarget() {

        return PlayerManager.adapter(players[0]);
    }

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
