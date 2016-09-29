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
 * Created by MoonLake on 2016/9/29.
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

            throw new PacketInitializeException("The nms packet play out chat reflect raw initialize exception.");
        }
    }

    private StringProperty message;
    private ObjectProperty<Mode> mode;

    @Deprecated
    public PacketPlayOutChat() {

        this("");
    }

    public PacketPlayOutChat(String message) {

        this(message, Mode.DEFAULT);
    }

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
