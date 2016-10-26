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
import com.minecraft.moonlake.property.SimpleStringProperty;
import com.minecraft.moonlake.property.StringProperty;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutPlayerListHeaderFooter</h1>
 * 数据包输出玩家列表头脚文本（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
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

    /**
     * 数据包输出玩家列表头脚文本类构造函数
     *
     * @deprecated 已过时，请使用 {@link #PacketPlayOutPlayerListHeaderFooter(String)}
     * @see #PacketPlayOutPlayerListHeaderFooter(String)
     */
    @Deprecated
    public PacketPlayOutPlayerListHeaderFooter() {

        this("");
    }

    /**
     * 数据包输出玩家列表头脚文本类构造函数
     *
     * @param header 头文本
     */
    public PacketPlayOutPlayerListHeaderFooter(String header) {

        this(header, null);
    }

    /**
     * 数据包输出玩家列表头脚文本类构造函数
     *
     * @param header 头文本
     * @param footer 脚文本
     */
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

        if(super.fireEvent(this, players)) return;

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
