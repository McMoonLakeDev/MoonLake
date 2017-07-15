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

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.chat.ChatComponent;
import com.minecraft.moonlake.api.fancy.FancyMessage;
import com.minecraft.moonlake.api.packet.Packet;
import com.minecraft.moonlake.api.packet.PacketPlayOut;
import com.minecraft.moonlake.api.packet.PacketPlayOutBukkit;
import com.minecraft.moonlake.api.utility.MinecraftBukkitVersion;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.builder.SingleParamBuilder;
import com.minecraft.moonlake.property.*;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import com.minecraft.moonlake.validate.Validate;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

/**
 * <h1>PacketPlayOutChat</h1>
 * 数据包输出聊天消息（详细doc待补充...）
 *
 * @version 2.0.1
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketPlayOutBukkit
 */
public class PacketPlayOutChat extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTCHAT;
    private static volatile ConstructorAccessor<?> packetPlayOutChatVoidConstructor;
    private static volatile ConstructorAccessor<?> packetPlayOutChatConstructor;

    static {

        CLASS_PACKETPLAYOUTCHAT = MinecraftReflection.getMinecraftClass("PacketPlayOutChat");
        Class<?> iChatBaseComponentClass = MinecraftReflection.getIChatBaseComponentClass();
        packetPlayOutChatVoidConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTCHAT);
        packetPlayOutChatConstructor = Accessors.getConstructorAccessorBuilderBukkitVer(new SingleParamBuilder<ConstructorAccessor<?>, MinecraftBukkitVersion>() {
            @Override
            public ConstructorAccessor<?> build(MinecraftBukkitVersion param) {
                if(param.isOrLater(MinecraftBukkitVersion.V1_12_R1))
                    return Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTCHAT, iChatBaseComponentClass, MinecraftReflection.getChatMessageTypeClass());
                return Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTCHAT, iChatBaseComponentClass, byte.class);
            }
        });
    }

    private StringProperty message;
    private ObjectProperty<Mode> mode;
    private BooleanProperty isJson;

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
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #PacketPlayOutChat(ChatComponent)}
     */
    @Deprecated
    public PacketPlayOutChat(FancyMessage fancyMessage) {

        this.message = new SimpleStringProperty(fancyMessage.toJsonString());
        this.mode = new SimpleObjectProperty<>(Mode.CHAT);
        this.isJson = new SimpleBooleanProperty(true);
    }

    /**
     * 数据包输出聊天消息构造函数
     *
     * @param chatComponent 聊天组件
     */
    public PacketPlayOutChat(ChatComponent chatComponent) {

        this.message = new SimpleStringProperty(chatComponent.toJson());
        this.mode = new SimpleObjectProperty<>(Mode.CHAT);
        this.isJson = new SimpleBooleanProperty(true);
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
        this.isJson = null;
    }

    /**
     * 获取此数据包输出聊天消息的消息属性 (注: 如果是 Json 格式不建议二次修改值, 非法格式会抛出异常)
     *
     * @return 消息属性
     * @see #isJson()
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

    /**
     * 获取此数据包数据聊天消息是否为 Json 格式内容
     *
     * @return 是否为 Json 格式内容
     */
    public boolean isJson() {

        return isJson != null && isJson.get();
    }

    /**
     * 获取此数据包输出聊天消息是否为花式消息
     *
     * @return 是否为花式消息
     * @deprecated @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #isJson()}
     */
    public boolean isFancyMessage() {

        return isJson();
    }

    @Override
    public Class<?> getPacketClass() {

        return CLASS_PACKETPLAYOUTCHAT;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        // 触发事件判断如果为 true 则阻止发送
        if(super.fireEvent(this, players))
            return true;

        String message = messageProperty().get();
        Validate.notNull(message, "The message object is null.");

        try {
            MinecraftReflection.sendPacket(players, packet());
            return true;
        } catch (Exception e) {
            printException(e);
        }
        // 否则前面的方式均不支持则返回 false 并抛出不支持运算异常
        return false;
    }

    @Nullable
    @Override
    public Object packet() {

        String message = messageProperty().get();
        Validate.notNull(message, "The message object is null.");

        try {
            // 先用调用 NMS 的 PacketPlayOutChat 构造函数, 参数 IChatBaseComponent, byte
            // 进行反射实例发送
            Object nmsChat = isJson == null ? MinecraftReflection.getIChatBaseComponentFromString(message) : MinecraftReflection.getIChatBaseComponentFromJson(message);
            if(nmsChat == null) MinecraftReflection.getIChatBaseComponentFromString(message); // 如果为 null 的话再调用一次进行格式化
            return packetPlayOutChatConstructor.invoke(nmsChat, adapter(mode.get() == null ? (byte) 1 : mode.get().getMode()));

        } catch (Exception e) {
            printException(e);
            // 如果异常了说明 NMS 的 PacketPlayOutChat 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 判断字段数量大于等于 2 个的话就是有此方式
                // 这两个字段分别对应 IChatBaseComponent, byte 的 2 个属性
                // 貌似 PacketPlayOutChat 有一个 md_5 包的 BaseComponent 类数组字段需要忽略
                Object packet = packetPlayOutChatVoidConstructor.invoke();
                Object nmsChat = isJson == null ? MinecraftReflection.getIChatBaseComponentFromString(message) : MinecraftReflection.getIChatBaseComponentFromJson(message);
                if(nmsChat == null) throw new IllegalArgumentException("The message object is illegal value: " + message);
                Object[] values = { nmsChat, adapter(mode.get().getMode()) };
                Class<?>[] ignoreFieldTypes = { BaseComponent[].class }; // 忽略字段类型为 BaseComponent[] 数组
                return setFieldAccessibleAndValueGet(ignoreFieldTypes, 3, CLASS_PACKETPLAYOUTCHAT, packet, values);

            } catch (Exception e1) {
                printException(e1);
            }
        }
        return null;
    }

    private static Object adapter(Object param) {
        // 版本参数适配器
        if(MoonLakeAPI.currentBukkitVersion().isOrLater(MinecraftBukkitVersion.V1_12_R1))
            return MinecraftReflection.chatMessageTypeFromByte((Byte) param);
        return param;
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
         *
         * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #ACTIONBAR}
         * @see #ACTIONBAR
         */
        @Deprecated
        HOTBAR((byte) 2),
        /**
         * 交互栏: 交互栏位置
         */
        ACTIONBAR((byte) 2),
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
