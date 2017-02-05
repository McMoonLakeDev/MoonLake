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


package com.minecraft.moonlake.api.event.core;

import com.minecraft.moonlake.api.event.Cancellable;
import com.minecraft.moonlake.api.packet.PacketPlayOut;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class MoonLakePacketOutEvent extends MoonLakePacketEvent implements Cancellable {

    private final static HandlerList handlerList = new HandlerList();
    private final Player[] players;
    private boolean cancel = false;

    public MoonLakePacketOutEvent(PacketPlayOut packet, Player... players) throws IllegalArgumentException {

        super(packet);

        this.players = players;
    }

    public Player[] getPlayers() {

        return players;
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
