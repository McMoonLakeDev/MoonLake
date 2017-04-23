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


package com.minecraft.moonlake.api.packet;

/**
 * <h1>PacketPlayIn</h1>
 * Minecraft 数据包输入
 *
 * @version 1.0.1
 * @author Month_Light
 * @see Packet
 * @see PacketNameable
 * @see PacketPlayInExecutor
 */
public interface PacketPlayIn extends Packet, PacketNameable, PacketPlayInExecutor {
}
