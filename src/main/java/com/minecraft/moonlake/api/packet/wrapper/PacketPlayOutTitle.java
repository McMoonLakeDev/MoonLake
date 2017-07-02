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

import com.minecraft.moonlake.api.packet.Packet;
import com.minecraft.moonlake.api.packet.PacketPlayOut;
import com.minecraft.moonlake.api.packet.PacketPlayOutBukkit;
import com.minecraft.moonlake.api.utility.MinecraftBukkitVersion;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.builder.SingleParamBuilder;
import com.minecraft.moonlake.property.IntegerProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import com.minecraft.moonlake.property.SimpleStringProperty;
import com.minecraft.moonlake.property.StringProperty;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h1>PacketPlayOutTitle</h1>
 * 数据包输出标题（详细doc待补充...）
 *
 * @version 2.1
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketPlayOutBukkit
 */
public class PacketPlayOutTitle extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTTITLE;
    private final static Class<?> CLASS_ENUMTITLEACTION;
    private static volatile ConstructorAccessor<?> packetPlayOutTitleVoidConstructor;
    private static volatile ConstructorAccessor<?> packetPlayOutTitleActionConstructor;
    private static volatile ConstructorAccessor<?> packetPlayOutTitleTimesConstructor;

    /**
     * <h1>PacketPlayOutTitleSpecial</h1>
     * 数据包输出标题特殊
     *
     * @version 1.0
     * @author Month_Light
     * @see PacketPlayOutTitle
     */
    private static class PacketPlayOutTitleSpecial extends PacketPlayOutTitle {

        private String enumTitleActionName;

        /**
         * 数据包输出标题特殊构造函数
         *
         * @param enumTitleActionName 枚举标题交互名称
         */
        private PacketPlayOutTitleSpecial(String enumTitleActionName) {

            super(null, null, -1, -1, -1);

            this.enumTitleActionName = enumTitleActionName;
        }

        @Override
        public boolean isSpecial() {

            return true;
        }

        @Override
        protected boolean sendPacket(Player... players) throws Exception {

            // 触发事件判断如果为 true 则阻止发送
            if(super.fireEvent(this, players))
                return true;

            try {
                List packets = (List) packet();
                MinecraftReflection.sendPacket(players, packets.get(0));
                return true;

            } catch (Exception e) {
                printException(e);
            }
            // 否则前面的方式均不支持则返回 false 并抛出不支持运算异常
            throw new UnsupportedOperationException();
        }

        @Nullable
        @Override
        public Object packet() {
            try {
                return Arrays.asList(new Object[] { packetPlayOutTitleActionConstructor.invoke(getEnumTitleAction(enumTitleActionName), null) });
            } catch (Exception e) {
                printException(e);
            }
            return null;
        }
    }

    /**
     * 特殊: 数据包输出标题清除
     */
    public final static PacketPlayOutTitle CLEAR = new PacketPlayOutTitleSpecial("CLEAR");

    /**
     * 特殊: 数据包输出标题重置
     */
    public final static PacketPlayOutTitle RESET = new PacketPlayOutTitleSpecial("RESET");

    static {

        CLASS_PACKETPLAYOUTTITLE = MinecraftReflection.getMinecraftClass("PacketPlayOutTitle");
        CLASS_ENUMTITLEACTION = MinecraftReflection.getMinecraftClassBuilder(new SingleParamBuilder<Class<?>, MinecraftBukkitVersion>() {
            @Override
            public Class<?> build(MinecraftBukkitVersion param) {
                if(param.equals(MinecraftBukkitVersion.V1_8_R1))
                    return MinecraftReflection.getMinecraftClass("EnumTitleAction");
                return MinecraftReflection.getMinecraftClass("PacketPlayOutTitle$EnumTitleAction");
            }
        });
        Class<?> iChatBaseComponentClass = MinecraftReflection.getIChatBaseComponentClass();
        packetPlayOutTitleVoidConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTTITLE);
        packetPlayOutTitleActionConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTTITLE, CLASS_ENUMTITLEACTION, iChatBaseComponentClass);
        packetPlayOutTitleTimesConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTTITLE, int.class, int.class, int.class);
    }

    private StringProperty title;
    private StringProperty subTitle;
    private IntegerProperty fadeIn;
    private IntegerProperty stay;
    private IntegerProperty fadeOut;

    /**
     * 数据包输出标题构造函数
     */
    public PacketPlayOutTitle() {

        this(null);
    }

    /**
     * 数据包输出标题构造函数
     *
     * @param title 主标题
     */
    public PacketPlayOutTitle(String title) {

        this(title, null);
    }

    /**
     * 数据包输出标题构造函数
     *
     * @param title 主标题
     * @param subTitle 副标题
     */
    public PacketPlayOutTitle(String title, String subTitle) {

        this(title, subTitle, -1, -1, -1);
    }

    /**
     * 数据包输出标题构造函数
     *
     * @param title 主标题
     * @param fadeIn 淡入时间
     * @param stay 停留时间
     * @param fadeOut 淡出时间
     */
    public PacketPlayOutTitle(String title, int fadeIn, int stay, int fadeOut) {

        this(title, null, fadeIn, stay, fadeOut);
    }

    /**
     * 数据包输出标题构造函数
     *
     * @param title 主标题
     * @param subTitle 副标题
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
     * 获取此数据包输出标题的主标题属性
     *
     * @return 主标题属性
     */
    public StringProperty titleProperty() {

        return title;
    }

    /**
     * 获取此数据包输出标题的副标题属性
     *
     * @return 副标题属性
     */
    public StringProperty subTitleProperty() {

        return subTitle;
    }

    /**
     * 获取此数据包输出标题的淡入时间属性
     *
     * @return 淡入时间属性
     */
    public IntegerProperty fadeInProperty() {

        return fadeIn;
    }

    /**
     * 获取此数据包输出标题的停留时间属性
     *
     * @return 停留时间属性
     */
    public IntegerProperty stayProperty() {

        return stay;
    }

    /**
     * 获取此数据包输出标题的淡出时间属性
     *
     * @return 淡出时间属性
     */
    public IntegerProperty fadeOutProperty() {

        return fadeOut;
    }

    /**
     * 获取此数据包输出标题是否为特殊的
     *
     * @return 是否特殊
     * @see #CLEAR
     * @see #RESET
     */
    public boolean isSpecial() {

        return false;
    }

    @Override
    public Class<?> getPacketClass() {

        return CLASS_PACKETPLAYOUTTITLE;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        // 触发事件判断如果为 true 则阻止发送
        if(super.fireEvent(this, players))
            return true;

        String title = titleProperty().get();
        Validate.notNull(title, "The title object is null.");

        try {
            List packets = (List) packet();
            for(Object packet : packets)
                MinecraftReflection.sendPacket(players, packet);
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

        String title = titleProperty().get();
        String subTitle = subTitleProperty().get();
        Validate.notNull(title, "The title object is null.");

        List<Object> packets = new ArrayList<>();

        try {
            // 先用调用 NMS 的 PacketPlayOutTitle 构造函数,
            // 参数 EnumTitleAction, IChatBaseComponent
            // 参数 int, int, int
            // 进行反射实例发送
            Object nmsTitle = MinecraftReflection.getIChatBaseComponentFromString(title);
            Object nmsSubTitle = subTitle != null ? MinecraftReflection.getIChatBaseComponentFromString(subTitle) : null;
            packets.add(packetPlayOutTitleTimesConstructor.invoke(fadeIn.get(), stay.get(), fadeOut.get())); // TIMES Packet
            packets.add(packetPlayOutTitleActionConstructor.invoke(getEnumTitleAction("TITLE"), nmsTitle)); // Title Packet
            Object packet2 = nmsSubTitle != null ?  packetPlayOutTitleActionConstructor.invoke(getEnumTitleAction("SUBTITLE"), nmsSubTitle) : null; // SubTitle Packet
            if(packet2 != null)
                packets.add(packet2);
            return packets;

        } catch (Exception e) {
            printException(e);
            // 如果异常了说明 NMS 的 PacketPlayOutTitle 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                packets.clear();
                // 判断字段数量等于 5 个的话就是有此方式
                // 这两个字段分别对应 EnumTitleAction, IChatBaseComponent, int, int, int 的 5 个属性
                Object packet0 = packetPlayOutTitleVoidConstructor.invoke(); // 先设置并发送 TIMES 的标题数据包
                Object[] values0 = { getEnumTitleAction("TIMES"), fadeIn.get(), stay.get(), fadeOut.get() };
                packets.add(setFieldAccessibleAndValueGet(2, 5, CLASS_PACKETPLAYOUTTITLE, packet0, values0));

                Object nmsTitle = MinecraftReflection.getIChatBaseComponentFromString(title);
                Object packet1 = packetPlayOutTitleVoidConstructor.invoke(); // 再设置并发送 TITLE 的标题数据包
                Object[] values1 = { getEnumTitleAction("TITLE"), nmsTitle };
                packets.add(setFieldAccessibleAndValueGet(2, CLASS_PACKETPLAYOUTTITLE, packet1, values1));

                Object nmsSubTitle = subTitle != null ? MinecraftReflection.getIChatBaseComponentFromString(subTitle) : null;
                if(nmsSubTitle != null) {
                    // 如果副标题不为 null 则进行设置并发送
                    Object packet2 = packetPlayOutTitleVoidConstructor.invoke(); // 最后设置并发送 SUBTITLE 的标题数据包
                    Object[] values2 = { getEnumTitleAction("SUBTITLE"), nmsSubTitle };
                    packets.add(setFieldAccessibleAndValueGet(2, CLASS_PACKETPLAYOUTTITLE, packet2, values2));
                }
                return packets;

            } catch (Exception e1) {
                printException(e1);
            }
        }
        return null;
    }

    private static Object getEnumTitleAction(String name) {
        return MinecraftReflection.enumOfNameAny(CLASS_ENUMTITLEACTION, name);
    }
}
