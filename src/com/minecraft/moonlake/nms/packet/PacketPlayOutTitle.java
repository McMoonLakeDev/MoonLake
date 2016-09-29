package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.nms.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.property.IntegerProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import com.minecraft.moonlake.property.SimpleStringProperty;
import com.minecraft.moonlake.property.StringProperty;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * Created by MoonLake on 2016/9/29.
 */
public class PacketPlayOutTitle extends PacketAbstract<PacketPlayOutTitle> {

    private final static Class<?> CLASS_PACKETPLAYOUTTITLE;
    private final static Class<?> CLASS_ENUMTITLEACTION;
    private final static Class<?> CLASS_CHATSERIALIZER;
    private final static Method METHOD_CHARSERIALIZER_A;
    private final static Method METHOD_ENUMTITLEACTION_A;

    static {

        try {

            CLASS_PACKETPLAYOUTTITLE = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutTitle");
            CLASS_ENUMTITLEACTION = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutTitle$EnumTitleAction");
            CLASS_CHATSERIALIZER = PackageType.MINECRAFT_SERVER.getClass("IChatBaseComponent$ChatSerializer");
            METHOD_CHARSERIALIZER_A = getMethod(CLASS_CHATSERIALIZER, "a", String.class);
            METHOD_ENUMTITLEACTION_A = getMethod(CLASS_ENUMTITLEACTION, "a", String.class);
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out title reflect raw initialize exception.", e);
        }
    }

    private StringProperty title;
    private StringProperty subTitle;
    private IntegerProperty fadeIn;
    private IntegerProperty stay;
    private IntegerProperty fadeOut;

    @Deprecated
    public PacketPlayOutTitle() {

        this("");
    }

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

        this.title = new SimpleStringProperty(title);
        this.subTitle = new SimpleStringProperty(subTitle);
        this.fadeIn = new SimpleIntegerProperty(fadeIn);
        this.stay = new SimpleIntegerProperty(stay);
        this.fadeOut = new SimpleIntegerProperty(fadeOut);
    }

    public StringProperty getTitle() {

        return title;
    }

    public StringProperty getSubTitle() {

        return subTitle;
    }

    public IntegerProperty getFadeIn() {

        return fadeIn;
    }

    public IntegerProperty getStay() {

        return stay;
    }

    public IntegerProperty getFadeOut() {

        return fadeOut;
    }

    @Override
    public void send(Player... players) throws PacketException {

        try {

            Object nmsTitle = METHOD_CHARSERIALIZER_A.invoke(null, "{\"text\":\"" + title.get() + "\"}");
            Object nmsSubTitle = subTitle.get() != null ? METHOD_CHARSERIALIZER_A.invoke(null, "{\"text\":\"" + subTitle.get() + "\"}") : null;

            Object packet0 = instantiateObject(CLASS_PACKETPLAYOUTTITLE, fadeIn.get(), stay.get(), fadeOut.get());
            Object packet1 = instantiateObject(CLASS_PACKETPLAYOUTTITLE, METHOD_ENUMTITLEACTION_A.invoke(null, "TITLE"), nmsTitle);
            Object packet2 = nmsSubTitle != null ? instantiateObject(CLASS_PACKETPLAYOUTTITLE, METHOD_ENUMTITLEACTION_A.invoke(null, "SUBTITLE"), nmsSubTitle) : null;

            PacketReflect.get().send(players, packet0);
            PacketReflect.get().send(players, packet1);

            if(packet2 != null) {

                PacketReflect.get().send(players, packet2);
            }
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out title send exception.", e);
        }
    }
}
