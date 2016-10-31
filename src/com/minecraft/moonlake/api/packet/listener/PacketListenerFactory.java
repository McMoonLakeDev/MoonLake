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

import com.minecraft.moonlake.MoonLakePlugin;
import com.minecraft.moonlake.api.event.MoonLakeListener;
import com.minecraft.moonlake.api.packet.listener.channel.PacketChannel;
import com.minecraft.moonlake.api.packet.listener.channel.PacketChannelWrapped;
import com.minecraft.moonlake.api.packet.listener.handler.PacketHandler;
import com.minecraft.moonlake.api.packet.listener.handler.PacketReceived;
import com.minecraft.moonlake.api.packet.listener.handler.PacketSent;
import com.minecraft.moonlake.event.EventHelper;
import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public final class PacketListenerFactory {

    private final static PacketChannel PACKET_CHANNEL;
    private final static List<PacketHandler> HANDLERS;

    static {

        PACKET_CHANNEL = new PacketINCChannel(new PacketListener() {

            @Override
            public Object onPacketSend(Object receiver, Object packet, Cancellable cancellable) {

                PacketSent packetSent = null;

                if(receiver instanceof Player) {

                    packetSent = new PacketSentExpression(packet, cancellable, (Player) receiver);
                }
                else {

                    packetSent = new PacketSentExpression(packet, cancellable, (PacketChannelWrapped) receiver);
                }
                notifyHandlers(packetSent);

                if(packetSent.getPacket() != null) {

                    return packetSent.getPacket();
                }
                return packet;
            }

            @Override
            public Object onPacketReceive(Object sender, Object packet, Cancellable cancellable) {

                PacketReceived packetReceived = null;

                if(sender instanceof Player) {

                    packetReceived = new PacketReceivedExpression(packet, cancellable, (Player) sender);
                }
                else {

                    packetReceived = new PacketReceivedExpression(packet, cancellable, (PacketChannelWrapped) sender);
                }
                notifyHandlers(packetReceived);

                if(packetReceived.getPacket() != null) {

                    return packetReceived.getPacket();
                }
                return packet;
            }
        });
        PACKET_CHANNEL.addServerChannel();

        EventHelper.registerEvent(new MoonLakeListener() {

            @EventHandler(priority = EventPriority.HIGHEST)
            public void onJoin(PlayerJoinEvent event) {

                PACKET_CHANNEL.addChannel(event.getPlayer());
            }

            @EventHandler(priority = EventPriority.HIGHEST)
            public void onQuit(PlayerQuitEvent event) {

                PACKET_CHANNEL.removeChannel(event.getPlayer());
            }

        }, MoonLakePlugin.getInstances().getPlugin());

        HANDLERS = new ArrayList<>();
    }

    public static boolean addHandler(PacketHandler handler) {

        Validate.notNull(handler, "The packet handler object is null.");

        boolean has = HANDLERS.contains(handler);

        if(!has) {

        }
        HANDLERS.add(handler);

        return !has;
    }

    public static boolean removeHandler(PacketHandler handler) {

        return HANDLERS.remove(handler);
    }

    private static void notifyHandlers(PacketSent packet) {

        for(PacketHandler handler : HANDLERS) {

            try {

                handler.onSend(packet);
            }
            catch (Exception e) {

                throw new PacketException("The exception occured while trying to execute 'onSend'" + (handler.getPlugin() != null ? " in plugin " + handler.getPlugin().getName() : ""), e);
            }
        }
    }

    private static void notifyHandlers(PacketReceived packet) {

        for(PacketHandler handler : HANDLERS) {

            try {

                handler.onReceive(packet);
            }
            catch (Exception e) {

                throw new PacketException("The exception occured while trying to execute 'onReceive'" + (handler.getPlugin() != null ? " in plugin " + handler.getPlugin().getName() : ""), e);
            }
        }
    }

    public static List<PacketHandler> getHandlers() {

        return new ArrayList<>(HANDLERS);
    }

    public static List<PacketHandler> getHandlersFromPlugin(Plugin plugin) {

        List<PacketHandler> handlers = new ArrayList<>();

        if(plugin == null) {

            return handlers;
        }
        for(PacketHandler handler : getHandlers()) {

            if(plugin.equals(handler.getPlugin())) {

                handlers.add(handler);
            }
        }
        return handlers;
    }

    public static int getHandlersSize() {

        return HANDLERS.size();
    }

    private PacketListenerFactory() {
    }
}
