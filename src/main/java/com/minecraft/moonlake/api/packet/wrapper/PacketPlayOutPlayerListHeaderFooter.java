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
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.property.SimpleStringProperty;
import com.minecraft.moonlake.property.StringProperty;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

/**
 * <h1>PacketPlayOutPlayerListHeaderFooter</h1>
 * 数据包输出玩家列表头脚文本（详细doc待补充...）
 *
 * @version 2.0
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketPlayOutBukkit
 */
public class PacketPlayOutPlayerListHeaderFooter extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER;
    private static volatile ConstructorAccessor<?> packetPlayOutPlayerListHeaderFooterVoidConstructor;
    private static volatile ConstructorAccessor<?> packetPlayOutPlayerListHeaderFooterConstructor;

    static {

        CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER = MinecraftReflection.getMinecraftClass("PacketPlayOutPlayerListHeaderFooter");
        Class<?> iChatBaseComponentClass = MinecraftReflection.getIChatBaseComponentClass();
        packetPlayOutPlayerListHeaderFooterVoidConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER);
        packetPlayOutPlayerListHeaderFooterConstructor = Accessors.getConstructorAccessorOrNull(CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER, iChatBaseComponentClass);
    }

    private StringProperty header;
    private StringProperty footer;

    /**
     * 数据包输出玩家列表头脚文本构造函数
     */
    public PacketPlayOutPlayerListHeaderFooter() {

        this(null, null);
    }

    /**
     * 数据包输出玩家列表头脚文本构造函数
     *
     * @param header 头文本
     */
    public PacketPlayOutPlayerListHeaderFooter(String header) {

        this(header, null);
    }

    /**
     * 数据包输出玩家列表头脚文本构造函数
     *
     * @param header 头文本
     * @param footer 脚文本
     */
    public PacketPlayOutPlayerListHeaderFooter(String header, String footer) {

        this.header = new SimpleStringProperty(header);
        this.footer = new SimpleStringProperty(footer);
    }

    /**
     * 获取此数据包输出玩家列表头脚文本的头文本属性
     *
     * @return 头文本属性
     */
    public StringProperty headerProperty() {

        return header;
    }

    /**
     * 获取此数据包输出玩家列表头脚文本的脚文本属性
     *
     * @return 脚文本属性
     */
    public StringProperty footerProperty() {

        return footer;
    }

    @Override
    public Class<?> getPacketClass() {

        return CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        // 触发事件判断如果为 true 则阻止发送
        if(super.fireEvent(this, players))
            return true;

        String header = headerProperty().get();
        Validate.notNull(header, "The header object is null.");

        try {
            MinecraftReflection.sendPacket(players, packet());
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

        String header = headerProperty().get();
        String footer = footerProperty().get();
        Validate.notNull(header, "The header object is null.");

        if(packetPlayOutPlayerListHeaderFooterConstructor == null)
            // 貌似 mc1.12 版本去除了一个参数的构造函数, 所以判断如果这个构造为 null 则直接修改字段发送
            return getVoid(header, footer);

        try {
            // 先用调用 NMS 的 PacketPlayOutPlayerListHeaderFooter 构造函数, 参数 IChatBaseComponent
            // 进行反射实例发送
            Object nmsHeader = MinecraftReflection.getIChatBaseComponentFromString(header);
            Object nmsFooter = footer != null ? MinecraftReflection.getIChatBaseComponentFromString(footer) : null;
            Object packet = packetPlayOutPlayerListHeaderFooterConstructor.invoke(nmsHeader);
            if(nmsFooter != null)
                return setFieldAccessibleAndValueGet(1, 2, CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER, packet, nmsFooter);
            else
                return packet;

        } catch (Exception e) {
            printException(e);
            // 如果异常了说明 NMS 的 PacketPlayOutPlayerListHeaderFooter 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            return getVoid(header, footer);
        }
    }

    private Object getVoid(String header, String footer) {
        try {
            // 判断字段数量大于等于 2 个的话就是有此方式
            // 这两个字段分别对应 IChatBaseComponent, IChatBaseComponent 的 2 个属性
            Object nmsHeader = MinecraftReflection.getIChatBaseComponentFromString(header);
            Object nmsFooter = footer != null ? MinecraftReflection.getIChatBaseComponentFromString(footer) : null;
            Object packet = packetPlayOutPlayerListHeaderFooterVoidConstructor.invoke();
            if(nmsFooter != null)
                return setFieldAccessibleAndValueGet(2, CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER, packet, nmsHeader, nmsFooter);
            else
                return setFieldAccessibleAndValueGet(1, CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER, packet, nmsHeader);

        } catch (Exception e1) {
            printException(e1);
        }
        return null;
    }
}
