package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.nms.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.property.SimpleStringProperty;
import com.minecraft.moonlake.property.StringProperty;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * Created by MoonLake on 2016/9/29.
 */
public class PacketPlayOutPlayerListHeaderFooter extends PacketAbstract<PacketPlayOutPlayerListHeaderFooter> {

    private final static Class<?> CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER;
    private final static Class<?> CLASS_CHATSERIALIZER;
    private final static Method METHOD_CHARSERIALIZER_A;
    private final static Field FIELD_PACKETPLAYOUTPLAYERLISTHEADERFOOTER_B;

    static {

        try {

            CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutPlayerListHeaderFooter");
            CLASS_CHATSERIALIZER = PackageType.MINECRAFT_SERVER.getClass("IChatBaseComponent$ChatSerializer");
            METHOD_CHARSERIALIZER_A = getMethod(CLASS_CHATSERIALIZER, "a", String.class);
            FIELD_PACKETPLAYOUTPLAYERLISTHEADERFOOTER_B = getField(CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER, true, "b");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out player list header footer reflect raw initialize exception.", e);
        }
    }

    private StringProperty header;
    private StringProperty footer;

    @Deprecated
    public PacketPlayOutPlayerListHeaderFooter() {

        this("");
    }

    public PacketPlayOutPlayerListHeaderFooter(String header) {

        this(header, null);
    }

    public PacketPlayOutPlayerListHeaderFooter(String header, String footer) {

        this.header = new SimpleStringProperty(header);
        this.footer = new SimpleStringProperty(footer);
    }

    public StringProperty getHeader() {

        return header;
    }

    public StringProperty getFooter() {

        return footer;
    }

    @Override
    public void send(Player... players) throws PacketException {

        try {

            Object nmsHeader = METHOD_CHARSERIALIZER_A.invoke(null, "{\"text\":\"" + header.get() + "\"}");
            Object nmsFooter = footer.get() != null ? METHOD_CHARSERIALIZER_A.invoke(null, "{\"text\":\"" + footer.get() + "\"}") : null;

            Object packet = instantiateObject(CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER, nmsHeader);

            if(nmsFooter != null) {

                FIELD_PACKETPLAYOUTPLAYERLISTHEADERFOOTER_B.set(packet, nmsFooter);
            }
            PacketReflect.get().send(players, packet);
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out player list header footer send exception.", e);
        }
    }
}
