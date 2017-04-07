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

import com.minecraft.moonlake.api.chat.ChatSerializer;
import com.minecraft.moonlake.api.packet.Packet;
import com.minecraft.moonlake.api.packet.PacketPlayOut;
import com.minecraft.moonlake.api.packet.PacketPlayOutBukkit;
import com.minecraft.moonlake.api.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.property.SimpleStringProperty;
import com.minecraft.moonlake.property.StringProperty;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

import static com.minecraft.moonlake.reflect.Reflect.PackageType;
import static com.minecraft.moonlake.reflect.Reflect.instantiateObject;

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

    static {

        try {

            CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutPlayerListHeaderFooter");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The net.minecraft.server packet play out player list header footer reflect raw initialize exception.", e);
        }
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
        String footer = footerProperty().get();
        Validate.notNull(header, "The header object is null.");

        try {
            // 先用调用 NMS 的 PacketPlayOutPlayerListHeaderFooter 构造函数, 参数 IChatBaseComponent
            // 进行反射实例发送
            Object nmsHeader = ChatSerializer.fromJson("{\"text\":\"" + header + "\"}");
            Object nmsFooter = footer != null ? ChatSerializer.fromJson("{\"text\":\"" + footer + "\"}") : null;
            Object packet = instantiateObject(CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER, nmsHeader);
            if(nmsFooter != null)
                setFieldAccessibleAndValueSend(players, 1, 2, CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER, packet, nmsFooter);
            else
                sendPacket(players, packet);
            return true;

        } catch (Exception e) {
            // 如果异常了说明 NMS 的 PacketPlayOutPlayerListHeaderFooter 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 判断字段数量大于等于 2 个的话就是有此方式
                // 这两个字段分别对应 IChatBaseComponent, IChatBaseComponent 的 2 个属性
                Object nmsHeader = ChatSerializer.fromJson("{\"text\":\"" + header + "\"}");
                Object nmsFooter = footer != null ? ChatSerializer.fromJson("{\"text\":\"" + footer + "\"}") : null;
                Object packet = instantiateObject(CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER);
                if(nmsFooter != null)
                    setFieldAccessibleAndValueSend(players, 2, CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER, packet, nmsHeader, nmsFooter);
                else
                    setFieldAccessibleAndValueSend(players, 1, CLASS_PACKETPLAYOUTPLAYERLISTHEADERFOOTER, packet, nmsHeader);
                return true;

            } catch (Exception e1) {
            }
        }
        // 否则前面的方式均不支持则返回 false 并抛出不支持运算异常
        return false;
    }
}
