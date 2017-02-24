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

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.event.MoonLakeListener;
import com.minecraft.moonlake.api.packet.listener.channel.PacketChannel;
import com.minecraft.moonlake.api.packet.listener.channel.PacketChannelWrapped;
import com.minecraft.moonlake.api.packet.listener.handler.PacketHandler;
import com.minecraft.moonlake.api.packet.listener.handler.PacketReceived;
import com.minecraft.moonlake.api.packet.listener.handler.PacketSent;
import com.minecraft.moonlake.event.EventHelper;
import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>PacketListenerFactory</h1>
 * 数据包监听器工厂实现类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public final class PacketListenerFactory {

    private final static PacketChannel PACKET_CHANNEL;
    private final static List<PacketHandler> PACKET_HANDLERS;
    private final static MoonLakeListener PACKET_CHANNEL_PLAYER_LISTENER;

    static {

        if(!MoonLakeAPI.getConfiguration().isPacketChannelListener()) {

            throw new MoonLakeException("The config.yml 'PacketChannelListener' value not is 'true', this api unavailable.");
        }
        PACKET_HANDLERS = new ArrayList<>();
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
        PACKET_CHANNEL_PLAYER_LISTENER = new MoonLakeListener() {

            @EventHandler(priority = EventPriority.HIGHEST)
            public void onJoin(PlayerJoinEvent event) {

                PACKET_CHANNEL.addChannel(event.getPlayer());
            }

            @EventHandler(priority = EventPriority.HIGHEST)
            public void onQuit(PlayerQuitEvent event) {

                PACKET_CHANNEL.removeChannel(event.getPlayer());
            }
        };
        EventHelper.registerEvent(PACKET_CHANNEL_PLAYER_LISTENER, MoonLakeAPI.getMoonLake());

        MoonLakeAPI.getLogger().info("月色之湖数据包通道监听器(PCL)成功加载.");
    }

    /**
     * 给数据包通道监听器添加数据包处理器
     *
     * @param handler 数据包处理器
     * @return 是否成功
     */
    public static boolean addHandler(PacketHandler handler) {

        Validate.notNull(handler, "The packet handler object is null.");

        boolean has = PACKET_HANDLERS.contains(handler);

        if(!has) {

            PacketHandler.handlerMethodOptions(handler);
        }
        PACKET_HANDLERS.add(handler);

        return !has;
    }

    /**
     * 给数据包通道监听器删除数据包处理器
     *
     * @param handler 数据包处理器
     * @return 是否成功
     */
    public static boolean removeHandler(PacketHandler handler) {

        return PACKET_HANDLERS.remove(handler);
    }

    /**
     * 将指定插件的所有数据包通道监听器处理器删除
     *
     * @param plugin 插件
     */
    public static void removeHandler(Plugin plugin) {

        if(plugin != null) {

            PACKET_HANDLERS.removeAll(getHandlersFromPlugin(plugin));
        }
    }

    /**
     * 清除所有的数据包通道监听器处理器
     */
    public static void removeAll() {

        PACKET_HANDLERS.clear();
    }

    /**
     * 通知所有的数据包处理器触发数据包发送
     *
     * @param packet 数据包发送
     */
    private static void notifyHandlers(PacketSent packet) {

        if(PACKET_HANDLERS.isEmpty()) return;

        PacketHandler.notifyHandlers(PACKET_HANDLERS, packet);
    }

    /**
     * 通知所有的数据包处理器触发数据包接收
     *
     * @param packet 数据包接收
     */
    private static void notifyHandlers(PacketReceived packet) {

        if(PACKET_HANDLERS.isEmpty()) return;

        PacketHandler.notifyHandlers(PACKET_HANDLERS, packet);
    }

    /**
     * 获取数据包通道监听器的所有数据包处理器
     *
     * @return 数据包处理器
     */
    public static List<PacketHandler> getHandlers() {

        return new ArrayList<>(PACKET_HANDLERS);
    }

    /**
     * 获取指定插件的数据包通道监听器的数据包处理器
     *
     * @param plugin 插件
     * @return 数据包处理器
     */
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

    /**
     * 获取数据包通道监听器的数据包处理器大小
     *
     * @return 数据包处理器大小
     */
    public static int getHandlersSize() {

        return PACKET_HANDLERS.size();
    }

    /**
     * 数据包监听器工厂实现类构造函数
     */
    private PacketListenerFactory() {
    }
}
