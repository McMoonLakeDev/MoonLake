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

import com.minecraft.moonlake.api.packet.PacketPlayOutBungee;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class MoonLakePacketOutBungeeEvent extends MoonLakePacketOutEvent {

    private final static HandlerList handlerList = new HandlerList();

    public MoonLakePacketOutBungeeEvent(PacketPlayOutBungee packet, Player... players) throws IllegalArgumentException {

        super(packet, players);
    }

    @Override
    public PacketPlayOutBungee getPacket() {

        return (PacketPlayOutBungee) super.getPacket();
    }

    @Override
    public HandlerList getHandlers() {

        return handlerList;
    }

    public static HandlerList getHandlerList() {

        return handlerList;
    }
}
