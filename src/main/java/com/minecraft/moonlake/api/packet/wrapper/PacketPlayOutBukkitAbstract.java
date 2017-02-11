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


package com.minecraft.moonlake.api.packet.wrapper;

import com.minecraft.moonlake.api.packet.PacketPlayOutBukkit;
import com.minecraft.moonlake.api.packet.exception.PacketException;
import com.minecraft.moonlake.api.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.manager.PlayerManager;
import com.minecraft.moonlake.reflect.Reflect;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.getField;
import static com.minecraft.moonlake.reflect.Reflect.getMethod;

public abstract class PacketPlayOutBukkitAbstract extends PacketPlayOutAbstract implements PacketPlayOutBukkit {

    private final static Class<?> CLASS_PACKET;
    private final static Class<?> CLASS_CRAFTPLAYER;
    private final static Class<?> CLASS_ENTITYPLAYER;
    private final static Class<?> CLASS_PLAYERCONNECTION;
    private final static Method METHOD_GETHANDLE;
    private final static Method METHOD_SENDPACKET;
    private final static Field FIELD_PLAYERCONNECTION;

    static {

        try {

            CLASS_PACKET = Reflect.PackageType.MINECRAFT_SERVER.getClass("Packet");
            CLASS_CRAFTPLAYER = Reflect.PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftPlayer");
            CLASS_ENTITYPLAYER = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityPlayer");
            CLASS_PLAYERCONNECTION = Reflect.PackageType.MINECRAFT_SERVER.getClass("PlayerConnection");
            METHOD_GETHANDLE = getMethod(CLASS_CRAFTPLAYER, "getHandle");
            METHOD_SENDPACKET = getMethod(CLASS_PLAYERCONNECTION, "sendPacket", CLASS_PACKET);
            FIELD_PLAYERCONNECTION = getField(CLASS_ENTITYPLAYER, true, "playerConnection");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The net.minecraft.server packet reflect raw initialize exception.", e);
        }
    }

    /**
     * 将指定数据包对象发送到给玩家
     *
     * @param players 玩家
     * @param packet 数据包
     * @throws PacketException 如果发送错误则抛出异常
     */
    protected static void sendPacket(Player[] players, Object packet) {

        Validate.notNull(players, "The player object is null.");
        Validate.notNull(packet, "The packet object is null.");

        try {

            for(final Player player : players)
                METHOD_SENDPACKET.invoke(FIELD_PLAYERCONNECTION.get(METHOD_GETHANDLE.invoke(player)), packet);
        }
        catch (Exception e) {

            throw new PacketException("The net.minecraft.server packet play out send exception.", e);
        }
    }

    /**
     * 获取指定玩家的 NMS 玩家对象
     *
     * @param player 玩家
     * @return NMSPlayer
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     * @throws PacketException 如果获取错误则抛出异常
     */
    protected static Object getNMSPlayer(Player player) throws PacketException {

        Validate.notNull(player, "The player object is null.");

        try {

            return METHOD_GETHANDLE.invoke(player);
        }
        catch (Exception e) {

            throw new PacketException("The get nms player handle exception.", e);
        }
    }

    protected static void setFieldAccessibleAndValueSend(Player[] players, int dest, Class<?> packetClass, Object packet, Object... values) throws Exception {

        setFieldAccessibleAndValueSend(players, 0, dest, packetClass, packet, values);
    }

    protected static void setFieldAccessibleAndValueSend(Player[] players, int pos, int dest, Class<?> packetClass, Object packet, Object... values) throws Exception {

        setFieldAccessibleAndValueSend(null, players, pos, dest, packetClass, packet, values);
    }

    protected static void setFieldAccessibleAndValueSend(@Nullable Class<?>[] ignoreFieldTypes, Player[] players, int dest, Class<?> packetClass, Object packet, Object... values) throws Exception {

        setFieldAccessibleAndValueSend(ignoreFieldTypes, players, 0, dest, packetClass, packet, values);
    }

    protected static void setFieldAccessibleAndValueSend(@Nullable Class<?>[] ignoreFieldTypes, Player[] players, int pos, int dest, Class<?> packetClass, Object packet, Object... values) throws Exception {

        Field[] fields = packetClass.getDeclaredFields();
        main:for(int i = pos; i < dest; i++) {
            Field field = fields[i];
            if(ignoreFieldTypes != null)
                for(Class<?> ignoreFieldType : ignoreFieldTypes)
                    if(ignoreFieldType.isAssignableFrom(field.getType())) {
                        i--; // 由于字段类型属于忽略类型则让 i 自减
                        continue main;
                    }

            if(!field.isAccessible())
                field.setAccessible(true);
            field.set(packet, values[i]);
        }
        sendPacket(players, packet);
    }

    PacketPlayOutBukkitAbstract() {
    }

    @Override
    public void send(Player... players) throws PacketException {

        try {

            if(!sendPacket(players)) throw new UnsupportedOperationException();
        }
        catch (Exception e) {

            throw new PacketException("The net.minecraft.server packet play out send exception.", e);
        }
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
