package com.minecraft.moonlake.api.nms.packet;

import com.minecraft.moonlake.reflect.Reflect;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by MoonLake on 2016/6/23.
 */
public class PacketPlayOutTitle extends PacketAbstract<PacketPlayOutTitle> {

    private String title;
    private String subTitle;
    private int fadeIn;
    private int stay;
    private int fadeOut;

    public PacketPlayOutTitle(String title) {

        this(title, null);
    }

    public PacketPlayOutTitle(String title, String subTitle) {

        this(title, subTitle, -1, -1, -1);
    }

    public PacketPlayOutTitle(String title, int fadeIn, int stay, int fadeOut) {

        this(title, null, fadeIn, stay, fadeOut);
    }

    public PacketPlayOutTitle(String title, String subTitle, int fadeIn, int stay, int fadeOut) {

        this.title = title;
        this.subTitle = subTitle;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getSubTitle() {

        return subTitle;
    }

    public void setSubTitle(String subTitle) {

        this.subTitle = subTitle;
    }

    public int getFadeIn() {

        return fadeIn;
    }

    public void setFadIn(int fadeIn) {

        this.fadeIn = fadeIn;
    }

    public int getStay() {

        return stay;
    }

    public void setStay(int stay) {

        this.stay = stay;
    }

    public int getFadeOut() {

        return fadeOut;
    }

    public void setFadeOut(int fadeOut) {

        this.fadeOut = fadeOut;
    }

    @Override
    public void send(String... names) {

        try {

            Class<?> PacketPlayOutTitle = Reflect.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutTitle");
            Class<?> ChatSerializer = Reflect.PackageType.MINECRAFT_SERVER.getClass("IChatBaseComponent$ChatSerializer");
            Class<?> EnumTitleAction = Reflect.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutTitle$EnumTitleAction");

            Method ChatSerializerA = Reflect.getMethod(ChatSerializer, "a", String.class);
            Object icbc = ChatSerializerA.invoke(null, "{\"text\": \"" + title + "\"}");
            Object icbc2 = subTitle != null ? ChatSerializerA.invoke(null, "{\"text\": \"" + subTitle + "\"}") : null;

            Method EnumTitleActionA = Reflect.getMethod(EnumTitleAction, "a", String.class);
            Object ppop = Reflect.instantiateObject(PacketPlayOutTitle, fadeIn, stay, fadeOut);
            Object ppop2 = Reflect.instantiateObject(PacketPlayOutTitle, EnumTitleActionA.invoke(null, "TITLE"), icbc);
            Object ppop3 = icbc2 != null ? Reflect.instantiateObject(PacketPlayOutTitle, EnumTitleActionA.invoke(null, "SUBTITLE"), icbc2) : null;

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

                sendPacket.invoke(playerConnection.get(NMSPlayer), ppop);
                sendPacket.invoke(playerConnection.get(NMSPlayer), ppop2);

                if(ppop3 != null) {

                    sendPacket.invoke(playerConnection.get(NMSPlayer), ppop3);
                }
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }
}
