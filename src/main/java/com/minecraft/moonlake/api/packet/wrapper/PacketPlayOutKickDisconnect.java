/*
 * Copyright (C) 2017 The MoonLake Authors
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

import com.minecraft.moonlake.api.chat.ChatComponent;
import com.minecraft.moonlake.api.chat.ChatSerializer;
import com.minecraft.moonlake.api.packet.Packet;
import com.minecraft.moonlake.api.packet.PacketPlayOut;
import com.minecraft.moonlake.api.packet.PacketPlayOutBukkit;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

/**
 * <h1>PacketPlayOutKickDisconnect</h1>
 * 数据包输出踢出断开连接（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketPlayOutBukkit
 */
public class PacketPlayOutKickDisconnect extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTKICKDISCONNECT;
    private static volatile ConstructorAccessor<?> packetPlayOutKickDisconnectVoidConstructor;
    private static volatile ConstructorAccessor<?> packetPlayOutKickDisconnectConstructor;

    static {
        CLASS_PACKETPLAYOUTKICKDISCONNECT = MinecraftReflection.getMinecraftClass("PacketPlayOutKickDisconnect");
        Class<?> iChatBaseComponentClass = MinecraftReflection.getIChatBaseComponentClass();
        packetPlayOutKickDisconnectVoidConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTKICKDISCONNECT);
        packetPlayOutKickDisconnectConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTKICKDISCONNECT, iChatBaseComponentClass);
    }

    private ObjectProperty<ChatComponent> reason;

    /**
     * 数据包输出踢出断开连接构造函数
     */
    public PacketPlayOutKickDisconnect() {
        this(null);
    }

    /**
     * 数据包输出踢出断开连接构造函数
     *
     * @param reason 原因
     */
    public PacketPlayOutKickDisconnect(ChatComponent reason) {
        this.reason = new SimpleObjectProperty<>(reason);
    }

    /**
     * 获取此数据包输出踢出断开连接的原因属性
     *
     * @return 原因属性
     */
    public ObjectProperty<ChatComponent> reasonProperty() {
        return reason;
    }

    @Override
    public Class<?> getPacketClass() {

        return CLASS_PACKETPLAYOUTKICKDISCONNECT;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        // 触发事件判断如果为 true 则阻止发送
        if(super.fireEvent(this, players))
            return true;

        ChatComponent chatComponent = reasonProperty().get();
        Validate.notNull(chatComponent, "The reason object is null.");

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

        ChatComponent chatComponent = reasonProperty().get();
        Validate.notNull(chatComponent, "The reason object is null.");

        try {
            // 先用调用 NMS 的 PacketPlayOutKickDisconnect 构造函数, 参数 IChatBaseComponent
            // 进行反射实例发送
            Object icbc = ChatSerializer.iCBCFromChatComponent(chatComponent);
            return packetPlayOutKickDisconnectConstructor.invoke(icbc);
        } catch (Exception e) {
            printException(e);
            // 如果异常了说明 NMS 的 PacketPlayOutKickDisconnect 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 判断字段数量等于 1 个的话就是有此方式
                // 这一个字段对应 IChatBaseComponent 的 1 个属性
                Object packet = packetPlayOutKickDisconnectVoidConstructor.invoke();
                Object[] values = { ChatSerializer.iCBCFromChatComponent(chatComponent) };
                return setFieldAccessibleAndValueGet(1, CLASS_PACKETPLAYOUTKICKDISCONNECT, packet, values);

            } catch (Exception e1) {
                printException(e1);
            }
        }
        return null;
    }
}
