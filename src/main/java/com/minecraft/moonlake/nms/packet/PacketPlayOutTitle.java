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
import com.minecraft.moonlake.property.IntegerProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import com.minecraft.moonlake.property.SimpleStringProperty;
import com.minecraft.moonlake.property.StringProperty;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutTitle</h1>
 * 数据包输出标题（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v1.9-a5 删除. 请使用 {@link com.minecraft.moonlake.api.packet.wrapper.PacketPlayOutTitle}
 */
@Deprecated
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

    /**
     * 数据包输出标题类构造函数
     *
     * @deprecated 已过时，请使用 {@link #PacketPlayOutTitle(String)}
     */
    @Deprecated
    public PacketPlayOutTitle() {

        this("");
    }

    /**
     * 数据包输出标题类构造函数
     *
     * @param title 标题
     */
    public PacketPlayOutTitle(String title) {

        this(title, null);
    }

    /**
     * 数据包输出标题类构造函数
     *
     * @param title 标题
     * @param subTitle 子标题
     */
    public PacketPlayOutTitle(String title, String subTitle) {

        this(title, subTitle, -1, -1, -1);
    }

    /**
     * 数据包输出标题类构造函数
     *
     * @param title 标题
     * @param fadeIn 淡入时间
     * @param stay 停留时间
     * @param fadeOut 淡出时间
     */
    public PacketPlayOutTitle(String title, int fadeIn, int stay, int fadeOut) {

        this(title, null, fadeIn, stay, fadeOut);
    }

    /**
     * 数据包输出标题类构造函数
     *
     * @param title 标题
     * @param subTitle 子标题
     * @param fadeIn 淡入时间
     * @param stay 停留时间
     * @param fadeOut 淡出时间
     */
    public PacketPlayOutTitle(String title, String subTitle, int fadeIn, int stay, int fadeOut) {

        this.title = new SimpleStringProperty(title);
        this.subTitle = new SimpleStringProperty(subTitle);
        this.fadeIn = new SimpleIntegerProperty(fadeIn);
        this.stay = new SimpleIntegerProperty(stay);
        this.fadeOut = new SimpleIntegerProperty(fadeOut);
    }

    /**
     * 获取数据包输出标题的主标题
     *
     * @return 主标题
     */
    public StringProperty getTitle() {

        return title;
    }

    /**
     * 获取数据包输出标题的子标题
     *
     * @return 子标题
     */
    public StringProperty getSubTitle() {

        return subTitle;
    }

    /**
     * 获取数据包输出标题的淡入时间
     *
     * @return 淡入时间
     */
    public IntegerProperty getFadeIn() {

        return fadeIn;
    }

    /**
     * 获取数据包输出标题的停留时间
     *
     * @return 停留时间
     */
    public IntegerProperty getStay() {

        return stay;
    }

    /**
     * 获取数据包输出标题的淡出时间
     *
     * @return 淡出时间
     */
    public IntegerProperty getFadeOut() {

        return fadeOut;
    }

    @Override
    public void send(Player... players) throws PacketException {

        if(super.fireEvent(this, players)) return;

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
