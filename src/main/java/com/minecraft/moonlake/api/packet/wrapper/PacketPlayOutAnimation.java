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

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.packet.Packet;
import com.minecraft.moonlake.api.packet.PacketPlayOut;
import com.minecraft.moonlake.api.packet.PacketPlayOutBukkit;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.api.utility.MinecraftVersion;
import com.minecraft.moonlake.property.IntegerProperty;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * <h1>PacketPlayOutAnimation</h1>
 * 数据包输出动画（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketPlayOutBukkit
 */
public class PacketPlayOutAnimation extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTANIMATION;
    private static volatile ConstructorAccessor<?> packetPlayOutAnimationVoidConstructor;

    static {

        CLASS_PACKETPLAYOUTANIMATION = MinecraftReflection.getMinecraftClass("PacketPlayOutAnimation");
        packetPlayOutAnimationVoidConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTANIMATION);
    }

    private IntegerProperty entityId;
    private ObjectProperty<Type> type;

    /**
     * 数据包输出动画构造函数
     */
    public PacketPlayOutAnimation() {

        this(-1, null);
    }

    /**
     * 数据包输出动画构造函数
     *
     * @param entityId 实体 Id
     * @param type 动画类型
     */
    public PacketPlayOutAnimation(int entityId, Type type) {

        this.entityId = new SimpleIntegerProperty(entityId);
        this.type = new SimpleObjectProperty<>(type);
    }

    /**
     * 数据包输出动画构造函数
     *
     * @param player 玩家
     * @param type 动画类型
     */
    public PacketPlayOutAnimation(Player player, Type type) {

        this(player.getEntityId(), type);
    }

    /**
     * 数据包输出动画构造函数
     *
     * @param player 玩家
     * @param type 动画类型
     */
    public PacketPlayOutAnimation(MoonLakePlayer player, Type type) {

        this(player.getBukkitPlayer(), type);
    }

    /**
     * 获取此数据包输出动画的实体 Id 属性
     *
     * @return 实体 Id 属性
     */
    public IntegerProperty entityIdProperty() {

        return entityId;
    }

    /**
     * 获取此数据包输出动画的动画类型属性
     *
     * @return 动画类型属性
     */
    public ObjectProperty<Type> typeProperty() {

        return type;
    }

    @Override
    public Class<?> getPacketClass() {

        return CLASS_PACKETPLAYOUTANIMATION;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        // 触发事件判断如果为 true 则阻止发送
        if(super.fireEvent(this, players))
            return true;

        Type animationType = typeProperty().get();
        Validate.notNull(animationType, "The animation type object is null.");

        if(animationType == Type.SWING_OFFHAND_ARM)
            if(!MoonLakeAPI.currentMCVersion().isOrLater(MinecraftVersion.V1_9))
                return true; // 因为 1.8 及以下版本不支持副手功能

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

        Type animationType = typeProperty().get();
        Validate.notNull(animationType, "The animation type object is null.");

        if(animationType == Type.SWING_OFFHAND_ARM)
            if(!MoonLakeAPI.currentMCVersion().isOrLater(MinecraftVersion.V1_9))
                return null; // 因为 1.8 及以下版本不支持副手功能

        try {
            // 直接使用反射设置字段的方式来发送
            Object packet = packetPlayOutAnimationVoidConstructor.invoke();
            Object[] values = { entityId.get(), animationType.getId() };
            return setFieldAccessibleAndValueGet(2, CLASS_PACKETPLAYOUTANIMATION, packet, values);
        } catch (Exception e) {
            printException(e);
        }
        return null;
    }

    /**
     * <h1>Type</h1>
     * 动画类型（详细doc待补充...）
     *
     * @version 1.0
     * @author Month_Light
     */
    public enum Type {

        /**
         * 动画类型: 主手臂摇摆
         */
        SWING_MAIN_ARM(0),
        /**
         * 动画类型: 副手臂摇摆 (注: 此项只兼容 1.9+ 服务器版本)
         */
        SWING_OFFHAND_ARM(3),
        /**
         * 动画类型: 受伤效果
         */
        HURT_EFFECT(1),
        /**
         * 动画类型: 离开床
         */
        LEAVE_BED(2),
        /**
         * 动画类型: 暴击效果
         */
        CRITICAL_EFFECT(4),
        /**
         * 动画类型: 魔法暴击效果
         */
        MAGIC_CRITICAL_EFFECT(5),
        ;

        private final int id;
        private final static Map<Integer, Type> ID_MAP;

        static {

            ID_MAP = new HashMap<>();
            for(Type type : values())
                ID_MAP.put(type.getId(), type);
        }

        /**
         * 动画类型类构造函数
         *
         * @param id Id
         */
        Type(int id) {

            this.id = id;
        }

        /**
         * 获取此动画类型的 Id
         *
         * @return Id
         */
        public int getId() {

            return id;
        }

        /**
         * 将指定动画类型从 Id 值获取
         *
         * @param id Id
         * @return {@code Type | null}
         */
        public static Type fromId(int id) {

            return ID_MAP.get(id);
        }
    }
}
