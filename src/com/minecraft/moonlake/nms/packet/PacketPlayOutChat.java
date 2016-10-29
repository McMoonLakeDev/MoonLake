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
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.property.SimpleStringProperty;
import com.minecraft.moonlake.property.StringProperty;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutChat</h1>
 * 数据包输出聊天消息（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class PacketPlayOutChat extends PacketAbstract<PacketPlayOutChat> {

    private final static Class<?> CLASS_PACKETPLAYOUTCHAT;
    private final static Class<?> CLASS_CHATSERIALIZER;
    private final static Method METHOD_CHARSERIALIZER_A;

    static {

        try {

            CLASS_PACKETPLAYOUTCHAT = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutChat");
            CLASS_CHATSERIALIZER = PackageType.MINECRAFT_SERVER.getClass("IChatBaseComponent$ChatSerializer");
            METHOD_CHARSERIALIZER_A = getMethod(CLASS_CHATSERIALIZER, "a", String.class);
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out chat reflect raw initialize exception.", e);
        }
    }

    private StringProperty message;
    private ObjectProperty<Mode> mode;

    /**
     * 数据包输出聊天消息类构造函数
     *
     * @deprecated 已过时，请使用 {@link #PacketPlayOutChat(String)}
     * @see #PacketPlayOutChat(String)
     */
    @Deprecated
    public PacketPlayOutChat() {

        this("");
    }

    /**
     * 数据包输出聊天消息类构造函数
     *
     * @param message 消息
     */
    public PacketPlayOutChat(String message) {

        this(message, Mode.CHAT);
    }

    /**
     * 数据包输出聊天消息类构造函数
     *
     * @param message 消息
     * @param mode 消息模式
     */
    public PacketPlayOutChat(String message, Mode mode) {

        this.message = new SimpleStringProperty(message);
        this.mode = new SimpleObjectProperty<>(mode);
    }

    /**
     * 获取此数据包输出聊天的消息
     *
     * @return 消息
     */
    public StringProperty getMessage() {

        return message;
    }

    /**
     * 获取此数据包输出聊天的模式
     *
     * @return 模式
     */
    public ObjectProperty<Mode> getMode() {

        return mode;
    }

    @Override
    public void send(Player... players) throws PacketException {

        if(super.fireEvent(this, players)) return;

        try {

            Object nmsChat = METHOD_CHARSERIALIZER_A.invoke(null, "{\"text\":\"" + message.get() + "\"}");
            Object packet = instantiateObject(CLASS_PACKETPLAYOUTCHAT, nmsChat, mode.get() == null ? (byte) 1 : mode.get().getMode());

            PacketReflect.get().send(players, packet);
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out chat send exception.", e);
        }
    }

    /**
     * <h1>Mode</h1>
     * 聊天模式（详细doc待补充...）
     *
     * @version 1.0
     * @author Month_Light
     */
    public enum Mode {

        /**
         * 聊天: 聊天栏位置
         */
        CHAT((byte)0),
        /**
         * 系统消息: 聊天栏位置
         */
        SYSTEM((byte)1),
        /**
         * 快捷栏: 快捷栏上面位置
         */
        HOTBAR((byte)2),

        /**
         * 默认的聊天消息显示位置
         *
         * @deprecated 已过时, 将于 v2.0 去除. 请使用 {@link #CHAT}
         */
        @Deprecated
        DEFAULT((byte)1),
        /**
         * 在玩家快捷栏上方显示位置
         *
         * @deprecated 已过时, 将于 v2.0 去除. 请使用 {@link #HOTBAR}
         */
        @Deprecated
        MAIN((byte)2),;

        private byte mode;

        /**
         * 聊天模式类构造函数
         *
         * @param mode 模式
         */
        Mode(byte mode) {

            this.mode = mode;
        }

        /**
         * 获取聊天模式的模式值
         *
         * @return 模式值
         */
        public byte getMode() {

            return mode;
        }
    }
}
