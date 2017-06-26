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
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

/**
 * <h1>PacketPlayOutEntityDestroy</h1>
 * 数据包输出实体破坏（详细doc待补充...）
 *
 * @version 2.0
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketPlayOutBukkit
 */
public class PacketPlayOutEntityDestroy extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTENTITYDESTROY;
    private static volatile ConstructorAccessor<?> packetPlayOutEntityDestroyVoidConstructor;
    private static volatile ConstructorAccessor<?> packetPlayOutEntityDestroyConstructor;

    static {

        CLASS_PACKETPLAYOUTENTITYDESTROY = MinecraftReflection.getMinecraftClass("PacketPlayOutEntityDestroy");
        packetPlayOutEntityDestroyVoidConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTENTITYDESTROY);
        packetPlayOutEntityDestroyConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTENTITYDESTROY, int[].class);
    }

    private ObjectProperty<int[]> entityIds;

    /**
     * 数据包输出实体破坏构造函数
     */
    public PacketPlayOutEntityDestroy() {

        this(new int[0]);
    }

    /**
     * 数据包输出实体破坏构造函数
     *
     * @param entities 实体
     */
    public PacketPlayOutEntityDestroy(Entity... entities) {

        int index = 0;
        int[] entityIds = new int[entities.length];

        for(Entity entity : entities)
            entityIds[index++] = entity.getEntityId();

        this.entityIds = new SimpleObjectProperty<>(entityIds);
    }

    /**
     * 数据包输出实体破坏构造函数
     *
     * @param entityIds 实体 Id
     */
    public PacketPlayOutEntityDestroy(int... entityIds) {

        this.entityIds = new SimpleObjectProperty<>(entityIds);
    }

    /**
     * 获取此数据包输出实体破坏的实体 Id 属性
     *
     * @return 实体 Id 属性
     */
    public ObjectProperty<int[]> entityIdsProperty() {

        return entityIds;
    }

    @Override
    public Class<?> getPacketClass() {

        return CLASS_PACKETPLAYOUTENTITYDESTROY;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        // 触发事件判断如果为 true 则阻止发送
        if(super.fireEvent(this, players))
            return true;

        int[] entityIds = entityIdsProperty().get();
        Validate.notNull(entityIds, "The entity id object is null.");

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

        int[] entityIds = entityIdsProperty().get();
        Validate.notNull(entityIds, "The entity id object is null.");

        try {
            // 先用调用 NMS 的 PacketPlayOutEntityDestroy 构造函数, 参数 int[]
            // 进行反射实例发送
            return packetPlayOutEntityDestroyConstructor.invoke(entityIds);

        } catch (Exception e) {
            printException(e);
            // 如果异常了说明 NMS 的 PacketPlayOutEntityDestroy 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 判断字段数量等于 1 个的话就是有此方式
                // 这个字段对应 int[] 属性
                Object packet = packetPlayOutEntityDestroyVoidConstructor.invoke();
                Object[] values = { entityIds };
                return setFieldAccessibleAndValueGet(1, CLASS_PACKETPLAYOUTENTITYDESTROY, packet, values);

            } catch (Exception e1) {
                printException(e1);
            }
        }
        return null;
    }
}
