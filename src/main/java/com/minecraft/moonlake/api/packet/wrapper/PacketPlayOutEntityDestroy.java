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
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutEntityDestroy</h1>
 * 数据包输出实体破坏（详细doc待补充...）
 *
 * @version 2.0
 * @author Month_Light
 */
public class PacketPlayOutEntityDestroy extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTENTITYDESTROY;

    static {

        try {

            CLASS_PACKETPLAYOUTENTITYDESTROY = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutEntityDestroy");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The net.minecraft.server packet play out entity destroy reflect raw initialize exception.", e);
        }
    }

    private ObjectProperty<int[]> entityIds;

    public PacketPlayOutEntityDestroy() {

        this(new int[0]);
    }

    public PacketPlayOutEntityDestroy(Entity... entities) {

        int index = 0;
        int[] entityIds = new int[entities.length];

        for(Entity entity : entities)
            entityIds[index++] = entity.getEntityId();

        this.entityIds = new SimpleObjectProperty<>(entityIds);
    }

    public PacketPlayOutEntityDestroy(int... entityIds) {

        this.entityIds = new SimpleObjectProperty<>(entityIds);
    }

    public ObjectProperty<int[]> entityIdsProperty() {

        return entityIds;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        // 触发事件判断如果为 true 则阻止发送
        if(super.fireEvent(this, players))
            return true;

        int[] entityIds = entityIdsProperty().get();
        Validate.notNull(entityIds, "The entity id object is null.");

        try {
            // 先用调用 NMS 的 PacketPlayOutEntityDestroy 构造函数, 参数 int[]
            // 进行反射实例发送
            sendPacket(players, instantiateObject(CLASS_PACKETPLAYOUTENTITYDESTROY, entityIds));
            return true;

        } catch (Exception e) {
            // 如果异常了说明 NMS 的 PacketPlayOutEntityDestroy 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 判断字段数量等于 1 个的话就是有此方式
                // 这个字段对应 int[] 属性
                Object packet = instantiateObject(CLASS_PACKETPLAYOUTENTITYDESTROY);
                Object[] values = { entityIds };
                setFieldAccessibleAndValueSend(players, 1, CLASS_PACKETPLAYOUTENTITYDESTROY, packet, values);
                return true;

            } catch (Exception e1) {
            }
        }
        // 否则前面的方式均不支持则返回 false 并抛出不支持运算异常
        return false;
    }
}
