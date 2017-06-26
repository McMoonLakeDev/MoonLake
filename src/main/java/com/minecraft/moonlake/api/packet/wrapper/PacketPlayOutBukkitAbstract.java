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

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.MoonLakePluginDebug;
import com.minecraft.moonlake.api.event.core.MoonLakePacketOutBukkitEvent;
import com.minecraft.moonlake.api.packet.Packet;
import com.minecraft.moonlake.api.packet.PacketPlayOut;
import com.minecraft.moonlake.api.packet.PacketPlayOutBukkit;
import com.minecraft.moonlake.api.packet.exception.PacketException;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.manager.PlayerManager;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

/**
 * <h1>PacketPlayOutBukkitAbstract</h1>
 * Minecraft 数据包输出 Bukkit 抽象类
 *
 * @version 1.0
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketPlayOutBukkit
 */
public abstract class PacketPlayOutBukkitAbstract extends PacketPlayOutAbstract implements PacketPlayOutBukkit {

    protected static Object setFieldAccessibleAndValueGet(int dest, Class<?> packetClass, Object packet, Object... values) throws Exception {

        return setFieldAccessibleAndValueGet(0, dest, packetClass, packet, values);
    }

    protected static Object setFieldAccessibleAndValueGet(int pos, int dest, Class<?> packetClass, Object packet, Object... values) throws Exception {

        return setFieldAccessibleAndValueGet(null, pos, dest, packetClass, packet, values);
    }

    protected static Object setFieldAccessibleAndValueGet(@Nullable Class<?>[] ignoreFieldTypes, int dest, Class<?> packetClass, Object packet, Object... values) throws Exception {

        return setFieldAccessibleAndValueGet(ignoreFieldTypes, 0, dest, packetClass, packet, values);
    }

    protected static Object setFieldAccessibleAndValueGet(@Nullable Class<?>[] ignoreFieldTypes, int pos, int dest, Class<?> packetClass, Object packet, Object... values) throws Exception {

        Field[] fields = packetClass.getDeclaredFields();
        main:for(int i = pos; i < dest; i++) {
            Field field = fields[i];
            if(ignoreFieldTypes != null)
                for(Class<?> ignoreFieldType : ignoreFieldTypes)
                    if(ignoreFieldType.isAssignableFrom(field.getType()))
                        continue main;

            if(!field.isAccessible())
                field.setAccessible(true);
            field.set(packet, values[i]);
        }
        return packet;
    }

    protected static void printException(Throwable e) {
        if(e == null)
            return;
        MoonLakePluginDebug.debug(e.getMessage(), e);
    }

    /**
     * Minecraft 数据包输出 Bukkit 抽象类构造函数
     */
    PacketPlayOutBukkitAbstract() {
    }

    @Override
    protected boolean fireEvent(PacketPlayOut packet, Player... players) {

        if(super.fireEvent(packet, players))
            return true;

        MoonLakePacketOutBukkitEvent mlpobe = new MoonLakePacketOutBukkitEvent((PacketPlayOutBukkit) packet, players);
        MoonLakeAPI.callEvent(mlpobe);
        return mlpobe.isCancelled();
    }

    @Override
    public String getPacketName() {

        return getClass().getSimpleName();
    }

    @Override
    @Nullable
    public Class<?> getPacketClass() {

        return null;
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
