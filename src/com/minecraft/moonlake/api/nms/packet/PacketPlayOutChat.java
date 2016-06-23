package com.minecraft.moonlake.api.nms.packet;

import com.minecraft.moonlake.reflect.Reflect;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by MoonLake on 2016/6/23.
 */
public class PacketPlayOutChat implements Packet<PacketPlayOutChat> {

    private String chat;
    private Mode mode;

    public PacketPlayOutChat(String chat) {

        this(chat, Mode.DEFAULT);
    }

    public PacketPlayOutChat(String chat, Mode mode) {

        this.chat = chat;
        this.mode = mode;
    }

    @Override
    public void send(String... names) {

        try {

            Class<?> PacketPlayOutChat = Reflect.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutChat");
            Class<?> ChatSerializer = Reflect.PackageType.MINECRAFT_SERVER.getClass("IChatBaseComponent$ChatSerializer");

            Method ChatSerializerA = Reflect.getMethod(ChatSerializer, "a", String.class);
            Object icbc = ChatSerializerA.invoke(null, "{\"text\": \"" + chat + "\"}");

            Object ppoc = Reflect.instantiateObject(PacketPlayOutChat, icbc, mode != null ? mode.getMode() : (byte)1);

            Class<?> Packet = Reflect.PackageType.MINECRAFT_SERVER.getClass("Packet");
            Class<?> CraftPlayer = Reflect.PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftPlayer");
            Class<?> EntityPlayer = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityPlayer");
            Class<?> PlayerConnection = Reflect.PackageType.MINECRAFT_SERVER.getClass("PlayerConnection");

            Method getHandle = Reflect.getMethod(CraftPlayer, "getHandle");
            Player[] players = PacketManager.getPlayersfromNames(names);

            Method sendPacket = Reflect.getMethod(PlayerConnection, "sendPacket", Packet);

            for(Player player : players) {

                Object NMSPlayer = getHandle.invoke(player);
                Field playerConnection = Reflect.getField(EntityPlayer, true, "playerConnection");

                sendPacket.invoke(playerConnection.get(NMSPlayer), ppoc);
            }
        }
        catch (Exception e) {

            e.printStackTrace();
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
