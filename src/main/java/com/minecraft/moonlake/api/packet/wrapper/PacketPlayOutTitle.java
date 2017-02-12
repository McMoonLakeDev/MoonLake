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

import com.minecraft.moonlake.api.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.property.IntegerProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import com.minecraft.moonlake.property.SimpleStringProperty;
import com.minecraft.moonlake.property.StringProperty;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutTitle</h1>
 * 数据包输出标题（详细doc待补充...）
 *
 * @version 2.0
 * @author Month_Light
 */
public class PacketPlayOutTitle extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTTITLE;
    private final static Class<?> CLASS_ENUMTITLEACTION;
    private final static Class<?> CLASS_CHATSERIALIZER;
    private final static Method METHOD_CHARSERIALIZER_A;
    private final static Method METHOD_ENUMTITLEACTION_A;

    static {

        try {

            CLASS_PACKETPLAYOUTTITLE = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutTitle");
            CLASS_ENUMTITLEACTION = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutTitle$EnumTitleAction");
            CLASS_CHATSERIALIZER =  PackageType.MINECRAFT_SERVER.getClass(getServerVersion().equals("v1_8_R1") ? "ChatSerializer" : "IChatBaseComponent$ChatSerializer");
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

    public PacketPlayOutTitle() {

        this(null);
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

    public StringProperty titleProperty() {

        return title;
    }

    public StringProperty subTitleProperty() {

        return subTitle;
    }

    public IntegerProperty fadeInProperty() {

        return fadeIn;
    }

    public IntegerProperty stayProperty() {

        return stay;
    }

    public IntegerProperty fadeOutProperty() {

        return fadeOut;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        String title = titleProperty().get();
        String subTitle = subTitleProperty().get();
        Validate.notNull(title, "The title object is null.");

        try {
            // 先用调用 NMS 的 PacketPlayOutTitle 构造函数,
            // 参数 EnumTitleAction, IChatBaseComponent
            // 参数 int, int, int
            // 进行反射实例发送
            Object nmsTitle = METHOD_CHARSERIALIZER_A.invoke(null, "{\"text\":\"" + title + "\"}");
            Object nmsSubTitle = subTitle != null ? METHOD_CHARSERIALIZER_A.invoke(null, "{\"text\":\"" + subTitle + "\"}") : null;
            Object packet0 = instantiateObject(CLASS_PACKETPLAYOUTTITLE, fadeIn.get(), stay.get(), fadeOut.get()); // TIMES Packet
            Object packet1 = instantiateObject(CLASS_PACKETPLAYOUTTITLE, METHOD_ENUMTITLEACTION_A.invoke(null, "TITLE"), nmsTitle); // Title Packet
            Object packet2 = nmsSubTitle != null ?  instantiateObject(CLASS_PACKETPLAYOUTTITLE, METHOD_ENUMTITLEACTION_A.invoke(null, "SUBTITLE"), nmsSubTitle) : null; // SubTitle Packet
            sendPacket(players, packet0); // 发送标题的时间数据包
            sendPacket(players, packet1); // 发送标题的主标题数据包
            if(packet2 != null)
                sendPacket(players, packet2); // 发送标题的副标题数据包
            return true;

        } catch (Exception e) {
            // 如果异常了说明 NMS 的 PacketPlayOutTitle 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 判断字段数量等于 5 个的话就是有此方式
                // 这两个字段分别对应 EnumTitleAction, IChatBaseComponent, int, int, int 的 5 个属性
                Object packet0 = instantiateObject(CLASS_PACKETPLAYOUTTITLE); // 先设置并发送 TIMES 的标题数据包
                Object[] values0 = {  METHOD_ENUMTITLEACTION_A.invoke(null, "TIMES"), fadeIn.get(), stay.get(), fadeOut.get() };
                setFieldAccessibleAndValueSend(players, 2, 5, CLASS_PACKETPLAYOUTTITLE, packet0, values0);

                Object nmsTitle = METHOD_CHARSERIALIZER_A.invoke(null, "{\"text\":\"" + title + "\"}");
                Object packet1 = instantiateObject(CLASS_PACKETPLAYOUTTITLE); // 再设置并发送 TITLE 的标题数据包
                Object[] values1 = { METHOD_ENUMTITLEACTION_A.invoke(null, "TITLE"), nmsTitle };
                setFieldAccessibleAndValueSend(players, 2, CLASS_PACKETPLAYOUTTITLE, packet1, values1);

                Object nmsSubTitle = subTitle != null ? METHOD_CHARSERIALIZER_A.invoke(null, "{\"text\":\"" + subTitle + "\"}") : null;
                if(nmsSubTitle != null) {
                    // 如果副标题不为 null 则进行设置并发送
                    Object packet2 = instantiateObject(CLASS_PACKETPLAYOUTTITLE); // 最后设置并发送 SUBTITLE 的标题数据包
                    Object[] values2 = { METHOD_ENUMTITLEACTION_A.invoke(null, "SUBTITLE"), nmsSubTitle };
                    setFieldAccessibleAndValueSend(players, 2, CLASS_PACKETPLAYOUTTITLE, packet2, values2);
                }
                return true;

            } catch (Exception e1) {
            }
        }
        // 否则前面的方式均不支持则返回 false 并抛出不支持运算异常
        return false;
    }
}
