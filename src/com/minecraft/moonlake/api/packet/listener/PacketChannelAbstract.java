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


package com.minecraft.moonlake.api.packet.listener;

import com.minecraft.moonlake.api.packet.listener.channel.PacketChannel;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.nms.exception.NMSException;
import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.minecraft.moonlake.reflect.Reflect.*;

abstract class PacketChannelAbstract implements PacketChannel {

    final static String KEY_HANDLER;
    final static String KEY_PLAYER;
    final static String KEY_SERVER;
    final static Class<?> CLASS_PACKET;
    final static Class<?> CLASS_CRAFTPLAYER;
    final static Class<?> CLASS_CRAFTSERVER;
    final static Class<?> CLASS_ENTITYPLAYER;
    final static Class<?> CLASS_NETWORKMANAGER;
    final static Class<?> CLASS_MINECRAFTSERVER;
    final static Class<?> CLASS_PLAYERCONNECTION;
    final static Class<?> CLASS_SERVERCONNECTION;
    final static Method METHOD_GETHANDLE;
    final static Field FIELD_CONSOLE;
    final static Field FIELD_PLAYERCONNECTION;
    final static Field FIELD_NETWORKMANAGER;
    final static Field FIELD_SERVERCONNECTION;
    final static Field FIELD_NETWORKMANAGERLIST;

    static {

        KEY_HANDLER = "packet_handler";
        KEY_PLAYER = "packet_listener_player";
        KEY_SERVER = "packet_listener_server";

        try {

            CLASS_PACKET = PackageType.MINECRAFT_SERVER.getClass("Packet");
            CLASS_CRAFTPLAYER = PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftPlayer");
            CLASS_CRAFTSERVER = PackageType.CRAFTBUKKIT.getClass("CraftServer");
            CLASS_ENTITYPLAYER = PackageType.MINECRAFT_SERVER.getClass("EntityPlayer");
            CLASS_NETWORKMANAGER = PackageType.MINECRAFT_SERVER.getClass("NetworkManager");
            CLASS_MINECRAFTSERVER = PackageType.MINECRAFT_SERVER.getClass("MinecraftServer");
            CLASS_PLAYERCONNECTION = PackageType.MINECRAFT_SERVER.getClass("PlayerConnection");
            CLASS_SERVERCONNECTION = PackageType.MINECRAFT_SERVER.getClass("ServerConnection");
            METHOD_GETHANDLE = getMethod(CLASS_CRAFTPLAYER, "getHandle");
            FIELD_CONSOLE = getField(CLASS_CRAFTSERVER, true, "console");
            FIELD_PLAYERCONNECTION = getField(CLASS_ENTITYPLAYER, true, "playerConnection");
            FIELD_NETWORKMANAGER = getField(CLASS_PLAYERCONNECTION, true, "networkManager");
            FIELD_SERVERCONNECTION = getField(CLASS_MINECRAFTSERVER, true, getServerVersionNumber() <= 8 ? "q" : "p");
            FIELD_NETWORKMANAGERLIST = getField(CLASS_SERVERCONNECTION, true, getServerVersionNumber() <= 8 ? "g" : "h");
        }
        catch (Exception e) {

            throw new NMSException("The packet listener channel reflect raw initialize exception.", e);
        }
    }

    private final Executor addChannelExecutor;
    private final Executor removeChannelExecutor;
    private PacketListener packetListener;

    public PacketChannelAbstract(PacketListener packetListener) {

        this.packetListener = packetListener;
        this.addChannelExecutor = Executors.newSingleThreadExecutor();
        this.removeChannelExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void addChannel(MoonLakePlayer moonLakePlayer) throws PacketException {

        Validate.notNull(moonLakePlayer, "The moonlake player object is null.");

        addChannel(moonLakePlayer.getBukkitPlayer());
    }

    @Override
    public void removeChannel(MoonLakePlayer moonLakePlayer) throws PacketException {

        Validate.notNull(moonLakePlayer, "The moonlake player object is null.");

        removeChannel(moonLakePlayer.getBukkitPlayer());
    }

    @Override
    public void addServerChannel() throws PacketException {

        try {

            Object obcServer = Bukkit.getServer();
            Object nmsConsole = FIELD_CONSOLE.get(obcServer);
            Object nmsServerConnection = FIELD_SERVERCONNECTION.get(nmsConsole);
            List currentList = (List) FIELD_NETWORKMANAGERLIST.get(nmsServerConnection);
            List newList = Collections.synchronizedList(newListenerList());

            for(Object obj : currentList) {

                newList.add(obj);
            }
            FIELD_NETWORKMANAGERLIST.set(nmsServerConnection, newList);
        }
        catch (Exception e) {

            throw new PacketException("The add server packet listener channel exception.", e);
        }
    }

    protected final Executor getAddChannelExecutor() {

        return addChannelExecutor;
    }

    protected final Executor getRemoveChannelExecutor() {

        return removeChannelExecutor;
    }

    protected final Object onPacketSend(Object receiver, Object packet, Cancellable cancellable) {

        return packetListener.onPacketSend(receiver, packet, cancellable);
    }

    protected final Object onPacketReceive(Object sender, Object packet, Cancellable cancellable) {

        return packetListener.onPacketReceive(sender, packet, cancellable);
    }
}
