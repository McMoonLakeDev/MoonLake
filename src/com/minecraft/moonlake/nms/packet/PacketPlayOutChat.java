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

        this(message, Mode.DEFAULT);
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

    public StringProperty getMessage() {

        return message;
    }

    public ObjectProperty<Mode> getMode() {

        return mode;
    }

    @Override
    public void send(Player... players) throws PacketException {

        try {

            Object nmsChat = METHOD_CHARSERIALIZER_A.invoke(null, "{\"text\":\"" + message.get() + "\"}");
            Object packet = instantiateObject(CLASS_PACKETPLAYOUTCHAT, nmsChat, mode.get() == null ? (byte) 1 : mode.get().getMode());

            PacketReflect.get().send(players, packet);
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out chat send exception.", e);
        }
    }

    public enum Mode {

        /**
         * 默认的聊天消息显示位置
         */
        DEFAULT((byte)1),
        /**
         * 在玩家经验条上方显示位置
         */
        MAIN((byte)2),;

        private byte mode;

        Mode(byte mode) {

            this.mode = mode;
        }

        public byte getMode() {

            return mode;
        }
    }
}
