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

import com.minecraft.moonlake.api.event.MoonLakeEvent;
import com.minecraft.moonlake.nms.packet.Packet;
import com.minecraft.moonlake.validate.Validate;

public abstract class MoonLakePacketEvent extends MoonLakeEvent {

    private final Packet<?> packet;

    public MoonLakePacketEvent(Packet<?> packet) {

        Validate.notNull(packet, "The moonlake packet object is null.");

        this.packet = packet;
    }

    public final Packet<?> getPacket() {

        return packet;
    }
}
