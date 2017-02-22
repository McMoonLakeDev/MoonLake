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
 
 
package com.minecraft.moonlake.api.packet.wrapper;

import com.minecraft.moonlake.api.fancy.FancyMessage;
import com.minecraft.moonlake.api.packet.Packet;
import com.minecraft.moonlake.api.packet.PacketPlayOut;
import com.minecraft.moonlake.api.packet.PacketPlayOutBukkit;
import com.minecraft.moonlake.api.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.property.*;
import com.minecraft.moonlake.validate.Validate;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutChat</h1>
 * 数据包输出聊天消息（详细doc待补充...）
 *
 * @version 2.0
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketPlayOutBukkit
 */
public class PacketPlayOutChat extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTCHAT;
    private final static Class<?> CLASS_CHATSERIALIZER;
    private final static Method METHOD_CHARSERIALIZER_A;

    static {

        try {

            CLASS_PACKETPLAYOUTCHAT = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutChat");
            CLASS_CHATSERIALIZER =  PackageType.MINECRAFT_SERVER.getClass(getServerVersion().equals("v1_8_R1") ? "ChatSerializer" : "IChatBaseComponent$ChatSerializer");
            METHOD_CHARSERIALIZER_A = getMethod(CLASS_CHATSERIALIZER, "a", String.class);
        }
        catch (Exception e) {

            throw new PacketInitializeException("The net.minecraft.server packet play out chat reflect raw initialize exception.", e);
        }
    }

    private StringProperty message;
    private ObjectProperty<Mode> mode;
    private BooleanProperty isFancyMessage;

    /**
     * 数据包输出聊天消息构造函数
     */
    public PacketPlayOutChat() {

        this((String) null);
    }

    /**
     * 数据包输出聊天消息构造函数
     *
     * @param message 消息
     */
    public PacketPlayOutChat(String message) {

        this(message, Mode.CHAT);
    }

    /**
     * 数据包输出聊天消息构造函数
     *
     * @param fancyMessage 花式消息
     */
    public PacketPlayOutChat(FancyMessage fancyMessage) {

        this.message = new SimpleStringProperty(fancyMessage.toJsonString());
        this.mode = new SimpleObjectProperty<>(Mode.CHAT);
        this.isFancyMessage = new SimpleBooleanProperty(true);
    }

    /**
     * 数据包输出聊天消息构造函数
     *
     * @param message 消息
     * @param mode 模式
     */
    public PacketPlayOutChat(String message, Mode mode) {

        this.message = new SimpleStringProperty(message);
        this.mode = new SimpleObjectProperty<>(mode);
        this.isFancyMessage = null;
    }

    /**
     * 获取此数据包输出聊天消息的消息属性
     *
     * @return 消息属性
     */
    public StringProperty messageProperty() {

        return message;
    }

    /**
     * 获取此数据包输出聊天消息的模式属性
     *
     * @return 模式属性
     */
    public ObjectProperty<Mode> modeProperty() {

        return mode;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        // 触发事件判断如果为 true 则阻止发送
        if(super.fireEvent(this, players))
            return true;

        String message = messageProperty().get();
        Validate.notNull(message, "The message object is null.");

        try {
            // 先用调用 NMS 的 PacketPlayOutChat 构造函数, 参数 IChatBaseComponent, byte
            // 进行反射实例发送
            Object nmsChat = METHOD_CHARSERIALIZER_A.invoke(null, isFancyMessage == null ? ("{\"text\":\"" + message + "\"}") : message);
            Object packet = instantiateObject(CLASS_PACKETPLAYOUTCHAT, nmsChat, mode.get() == null ? (byte) 1 : mode.get().getMode());
            sendPacket(players, packet);
            return true;

        } catch (Exception e) {
            // 如果异常了说明 NMS 的 PacketPlayOutChat 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 判断字段数量大于等于 2 个的话就是有此方式
                // 这两个字段分别对应 IChatBaseComponent, byte 的 2 个属性
                // 貌似 PacketPlayOutChat 有一个 md_5 包的 BaseComponent 类数组字段需要忽略
                Object packet = instantiateObject(CLASS_PACKETPLAYOUTCHAT);
                Object nmsChat = METHOD_CHARSERIALIZER_A.invoke(null, isFancyMessage == null ? ("{\"text\":\"" + message + "\"}") : message);
                Object[] values = { nmsChat, mode.get().getMode() };
                Class<?>[] ignoreFieldTypes = { BaseComponent[].class }; // 忽略字段类型为 BaseComponent[] 数组
                setFieldAccessibleAndValueSend(ignoreFieldTypes, players, 2, CLASS_PACKETPLAYOUTCHAT, packet, values);
                return true;

            } catch (Exception e1) {
            }
        }
        // 否则前面的方式均不支持则返回 false 并抛出不支持运算异常
        return false;
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
        CHAT((byte) 0),
        /**
         * 系统消息: 聊天栏位置
         */
        SYSTEM((byte) 1),
        /**
         * 快捷栏: 快捷栏上面位置
         */
        HOTBAR((byte) 2),
        ;

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
