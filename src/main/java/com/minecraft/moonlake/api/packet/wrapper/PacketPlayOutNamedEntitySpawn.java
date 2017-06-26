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

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.packet.Packet;
import com.minecraft.moonlake.api.packet.PacketPlayOut;
import com.minecraft.moonlake.api.packet.PacketPlayOutBukkit;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.api.utility.MinecraftVersion;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import com.minecraft.moonlake.reflect.accessors.MethodAccessor;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

/**
 * <h1>PacketPlayOutNamedEntitySpawn</h1>
 * 数据包输出名称实体生成（详细doc待补充...）
 *
 * @version 2.0.1
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketPlayOutBukkit
 */
public class PacketPlayOutNamedEntitySpawn extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTNAMEDENTITYSPAWN;
    private static volatile ConstructorAccessor<?> packetPlayOutNamedEntitySpawnVoidConstructor;
    private static volatile ConstructorAccessor<?> packetPlayOutNamedEntitySpawnConstructor;
    private static volatile MethodAccessor entityGetDataWatcherMethod;

    static {

        CLASS_PACKETPLAYOUTNAMEDENTITYSPAWN = MinecraftReflection.getMinecraftClass("PacketPlayOutNamedEntitySpawn");
        Class<?> entityClass = MinecraftReflection.getMinecraftEntityClass();
        Class<?> entityHumanClass = MinecraftReflection.getMinecraftEntityHumanClass();
        packetPlayOutNamedEntitySpawnVoidConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTNAMEDENTITYSPAWN);
        packetPlayOutNamedEntitySpawnConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTNAMEDENTITYSPAWN, entityHumanClass);
        entityGetDataWatcherMethod = Accessors.getMethodAccessor(entityClass, "getDataWatcher");
    }

    private ObjectProperty<Player> entity;

    /**
     * 数据包输出名称实体生成构造函数
     */
    public PacketPlayOutNamedEntitySpawn() {

        this(null);
    }

    /**
     * 数据包输出名称实体生成构造函数
     *
     * @param entity 实体玩家
     */
    public PacketPlayOutNamedEntitySpawn(Player entity) {

        this.entity = new SimpleObjectProperty<>(entity);
    }

    /**
     * 获取此数据包输出名称实体生成的实体玩家属性
     *
     * @return 实体玩家属性
     */
    public ObjectProperty<Player> entityProperty() {

        return entity;
    }

    @Override
    public Class<?> getPacketClass() {

        return CLASS_PACKETPLAYOUTNAMEDENTITYSPAWN;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        // 触发事件判断如果为 true 则阻止发送
        if(super.fireEvent(this, players))
            return true;

        Player player = entityProperty().get();
        Validate.notNull(player, "The player object is null.");

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
    @SuppressWarnings("deprecation")
    public Object packet() {

        Player player = entityProperty().get();
        Validate.notNull(player, "The player object is null.");

        try {
            // 先用调用 NMS 的 PacketPlayOutNamedEntitySpawn 构造函数, 参数 EntityHuman
            // 进行反射实例发送
            Object entityPlayer = MinecraftReflection.getEntityPlayer(player);
            return packetPlayOutNamedEntitySpawnConstructor.invoke(entityPlayer);

        } catch (Exception e) {
            printException(e);
            // 如果异常了说明 NMS 的 PacketPlayOutNamedEntitySpawn 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 貌似 1.8 版本总共有 10 个字段 int, UUID, int, int, int, byte, byte, int, DataWatcher, List
                // 而 1.9 以及更高的版本有 9 个字段  int, UUID, double, double, double, byte, byte, DataWatcher, List
                // 其中这些字段中最后一个 List 可以为 null 的
                Location location = player.getLocation();
                Object packet = packetPlayOutNamedEntitySpawnVoidConstructor.invoke();
                Object dataWatcher = entityGetDataWatcherMethod.invoke(MinecraftReflection.getEntityPlayer(player));

                if(!MoonLakeAPI.currentMCVersion().isOrLater(MinecraftVersion.V1_9)) {
                    Object[] values = {
                            player.getEntityId(),
                            player.getUniqueId(),
                            (int) Math.floor(location.getX() * 32d),
                            (int) Math.floor(location.getY() * 32d),
                            (int) Math.floor(location.getZ() * 32d),
                            (byte) (int) (location.getYaw() * 256f / 320f),
                            (byte) (int) (location.getPitch() * 256f / 320f),
                            player.getItemInHand().getTypeId(), // @SuppressWarnings("deprecation")
                            dataWatcher
                    };
                    return setFieldAccessibleAndValueGet(9, CLASS_PACKETPLAYOUTNAMEDENTITYSPAWN, packet, values);

                } else {
                    Object[] values = {
                            player.getEntityId(),
                            player.getUniqueId(),
                            location.getX(),
                            location.getY(),
                            location.getZ(),
                            (byte) (int) (location.getYaw() * 256f / 320f),
                            (byte) (int) (location.getPitch() * 256f / 320f),
                            dataWatcher
                    };
                    return setFieldAccessibleAndValueGet(8, CLASS_PACKETPLAYOUTNAMEDENTITYSPAWN, packet, values);
                }
            } catch (Exception e1) {
                printException(e1);
            }
        }
        return null;
    }
}
