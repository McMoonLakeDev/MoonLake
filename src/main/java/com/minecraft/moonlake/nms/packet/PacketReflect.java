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
import com.minecraft.moonlake.nms.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketReflect</h1>
 * 数据包反射实现类
 *
 * @version 1.0
 * @author Month_Light
 */
class PacketReflect {

    private Class<?> CLASS_PACKET;
    private Class<?> CLASS_CRAFTPLAYER;
    private Class<?> CLASS_ENTITYPLAYER;
    private Class<?> CLASS_PLAYERCONNECTION;
    private Method METHOD_GETHANDLE;
    private Method METHOD_SENDPACKET;
    private Field FIELD_PLAYERCONNECTION;

    private static PacketReflect packetReflectInstance;

    private PacketReflect() {

        try {

            // Packet Class
            CLASS_PACKET = PackageType.MINECRAFT_SERVER.getClass("Packet");
            CLASS_CRAFTPLAYER = PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftPlayer");
            CLASS_ENTITYPLAYER = PackageType.MINECRAFT_SERVER.getClass("EntityPlayer");
            CLASS_PLAYERCONNECTION = PackageType.MINECRAFT_SERVER.getClass("PlayerConnection");

            // Packet Method
            METHOD_GETHANDLE = getMethod(CLASS_CRAFTPLAYER, "getHandle");
            METHOD_SENDPACKET = getMethod(CLASS_PLAYERCONNECTION, "sendPacket", CLASS_PACKET);

            // Packet Field
            FIELD_PLAYERCONNECTION = getField(CLASS_ENTITYPLAYER, true, "playerConnection");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet reflect raw initialize exception.", e);
        }
    }

    /**
     * 获取 PacketReflect 对象
     *
     * @return PacketReflect
     * @throws PacketException 如果获取错误则抛出异常
     */
    public static PacketReflect get() throws PacketException {

        if(packetReflectInstance == null) {

            packetReflectInstance = new PacketReflect();
        }
        return packetReflectInstance;
    }

    /**
     * 获取指定玩家的 NMS 玩家对象
     *
     * @param player 玩家
     * @return NMSPlayer
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     * @throws PacketException 如果获取错误则抛出异常
     */
    public Object getNMSPlayer(Player player) throws PacketException {

        Validate.notNull(player, "The player object is null.");

        try {

            return METHOD_GETHANDLE.invoke(player);
        }
        catch (Exception e) {

            throw new PacketException("The get nms player handle exception.", e);
        }
    }

    /**
     * 将指定数据包对象发送到给玩家
     *
     * @param players 玩家
     * @param packet 数据包
     * @throws PacketException 如果发送错误则抛出异常
     */
    public void send(Player[] players, Object packet) throws PacketException {

        Validate.notNull(players, "The player object is null.");
        Validate.notNull(packet, "The packet object is null.");

        try {

            for(final Player player : players) {

                METHOD_SENDPACKET.invoke(FIELD_PLAYERCONNECTION.get(METHOD_GETHANDLE.invoke(player)), packet);
            }
        }
        catch (Exception e) {

            throw new PacketException("The nms packet send exception.", e);
        }
    }
}
