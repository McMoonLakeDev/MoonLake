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


package com.minecraft.moonlake.api.packet.listener.handler;

import com.minecraft.moonlake.api.packet.exception.PacketException;
import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * <h1>PacketHandler</h1>
 * 数据包处理器（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public abstract class PacketHandler {

    private final Plugin plugin;
    private boolean hasSendOption;
    private boolean forcePlayerSend;
    private boolean forceServerSend;
    private boolean hasReceiveOption;
    private boolean forcePlayerReceive;
    private boolean forceServerReceive;

    /**
     * 数据包处理器类构造函数
     *
     * @param plugin 插件
     */
    public PacketHandler(Plugin plugin) {

        this.plugin = plugin;
    }

    /**
     * 获取此数据包处理器的插件
     *
     * @return 插件
     */
    public Plugin getPlugin() {

        return plugin;
    }

    /**
     * 数据包处理器的发送
     *
     * @param packet 数据包
     */
    public abstract void onSend(PacketSent packet);

    /**
     * 数据包处理器的接收
     *
     * @param packet 数据包
     */
    public abstract void onReceive(PacketReceived packet);

    //
    // Packet Handler Static Method

    public static void handlerMethodOptions(PacketHandler handler) throws IllegalArgumentException, PacketException {

        try {

            PacketOption onSendOption = handler.getClass().getMethod("onSend", PacketSent.class).getAnnotation(PacketOption.class);

            if(onSendOption != null) {

                handler.hasSendOption = true;

                if(onSendOption.forcePlayer() && onSendOption.forceServer()) {

                    throw new IllegalArgumentException("Cannot force player and server packets at the same time!");
                }
                if(onSendOption.forcePlayer()) {

                    handler.forcePlayerSend = true;
                }
                else if(onSendOption.forceServer()) {

                    handler.forceServerSend = true;
                }
            }
        }
        catch (Exception e) {

            throw new PacketException("The failed to register packet handler method 'onSend' options exception.", e);
        }
        try {

            PacketOption onReceiveOption = handler.getClass().getMethod("onReceive", PacketReceived.class).getAnnotation(PacketOption.class);

            if(onReceiveOption != null) {

                handler.hasReceiveOption = true;

                if(onReceiveOption.forcePlayer() && onReceiveOption.forceServer()) {

                    throw new IllegalArgumentException("Cannot force player and server packets at the same time!");
                }
                if(onReceiveOption.forcePlayer()) {

                    handler.forcePlayerReceive = true;
                }
                else if(onReceiveOption.forceServer()) {

                    handler.forceServerReceive = true;
                }
            }
        }
        catch (Exception e) {

            throw new PacketException("The failed to register packet handler method 'onReceive' options exception.", e);
        }
    }

    public static void notifyHandlers(List<PacketHandler> handlerList, PacketSent packet) throws PacketException {

        for(PacketHandler handler : handlerList) {

            try {

                if((!handler.hasSendOption) ||
                        (handler.forcePlayerSend ?
                        packet.hasPlayer() :
                        (!handler.forceServerSend) ||
                        (packet.hasChannel()))) {

                    handler.onSend(packet);
                }
            }
            catch (Exception e) {

                throw new PacketException("The exception occured while trying to execute 'onSend'" + (handler.getPlugin() != null ? " in plugin " + handler.getPlugin().getName() : ""), e);
            }
        }
    }

    public static void notifyHandlers(List<PacketHandler> handlerList, PacketReceived packet) throws PacketException {

        for(PacketHandler handler : handlerList) {

            try {

                if((!handler.hasReceiveOption) ||
                        (handler.forcePlayerReceive ?
                        packet.hasPlayer() :
                        (!handler.forceServerReceive) ||
                        (packet.hasChannel()))) {

                    handler.onReceive(packet);
                }
            }
            catch (Exception e) {

                throw new PacketException("The exception occured while trying to execute 'onReceive'" + (handler.getPlugin() != null ? " in plugin " + handler.getPlugin().getName() : ""), e);
            }
        }
    }
    ///

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PacketHandler that = (PacketHandler) o;

        if (hasSendOption != that.hasSendOption) return false;
        if (forcePlayerSend != that.forcePlayerSend) return false;
        if (forceServerSend != that.forceServerSend) return false;
        if (hasReceiveOption != that.hasReceiveOption) return false;
        if (forcePlayerReceive != that.forcePlayerReceive) return false;
        if (forceServerReceive != that.forceServerReceive) return false;
        return plugin.equals(that.plugin);
    }

    @Override
    public int hashCode() {
        int result = plugin.hashCode();
        result = 31 * result + (hasSendOption ? 1 : 0);
        result = 31 * result + (forcePlayerSend ? 1 : 0);
        result = 31 * result + (forceServerSend ? 1 : 0);
        result = 31 * result + (hasReceiveOption ? 1 : 0);
        result = 31 * result + (forcePlayerReceive ? 1 : 0);
        result = 31 * result + (forceServerReceive ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PacketHandler{" +
                "plugin=" + plugin +
                ", hasSendOption=" + hasSendOption +
                ", forcePlayerSend=" + forcePlayerSend +
                ", forceServerSend=" + forceServerSend +
                ", hasReceiveOption=" + hasReceiveOption +
                ", forcePlayerReceive=" + forcePlayerReceive +
                ", forceServerReceive=" + forceServerReceive +
                '}';
    }
}
