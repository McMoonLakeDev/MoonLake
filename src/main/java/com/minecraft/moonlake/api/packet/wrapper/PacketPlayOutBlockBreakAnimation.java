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
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.property.IntegerProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

/**
 * <h1>PacketPlayOutBlockBreakAnimation</h1>
 * 数据包输出方块破坏动画（详细doc待补充...）
 *
 * @version 2.0
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketPlayOutBukkit
 */
public class PacketPlayOutBlockBreakAnimation extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTBLOCKBREAKANIMATION;
    private static volatile ConstructorAccessor<?> packetPlayOutBlockBreakAnimationVoidConstructor;
    private static volatile ConstructorAccessor<?> packetPlayOutBlockBreakAnimationConstructor;

    static {

        CLASS_PACKETPLAYOUTBLOCKBREAKANIMATION = MinecraftReflection.getMinecraftClass("PacketPlayOutBlockBreakAnimation");
        Class<?> blockPositionClass = MinecraftReflection.getMinecraftBlockPositionClass();
        packetPlayOutBlockBreakAnimationVoidConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTBLOCKBREAKANIMATION);
        packetPlayOutBlockBreakAnimationConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTBLOCKBREAKANIMATION, int.class, blockPositionClass, int.class);
    }

    private IntegerProperty entityId;
    private BlockPosition.BlockPositionProperty blockPosition;
    private IntegerProperty value;

    /**
     * 数据包输出方块破坏动画构造函数
     */
    public PacketPlayOutBlockBreakAnimation() {

        this(0, BlockPosition.ZERO, 0);
    }

    /**
     * 数据包输出方块破坏动画构造函数
     *
     * @param player 玩家
     * @param blockPosition 方块位置
     * @param value 方块破坏值 (0-9)
     */
    public PacketPlayOutBlockBreakAnimation(Player player, BlockPosition blockPosition, int value) {

        this(player.getEntityId(), blockPosition, value);
    }

    /**
     * 数据包输出方块破坏动画构造函数
     *
     * @param player 月色之湖玩家
     * @param blockPosition 方块位置
     * @param value 方块破坏值 (0-9)
     */
    public PacketPlayOutBlockBreakAnimation(MoonLakePlayer player, BlockPosition blockPosition, int value) {

        this(player.getBukkitPlayer().getEntityId(), blockPosition, value);
    }

    /**
     * 数据包输出方块破坏动画构造函数
     *
     * @param entityId 实体 Id
     * @param blockPosition 方块位置
     * @param value 方块破坏值 (0-9)
     */
    public PacketPlayOutBlockBreakAnimation(int entityId, BlockPosition blockPosition, int value) {

        this.entityId = new SimpleIntegerProperty(entityId);
        this.blockPosition = new BlockPosition.BlockPositionProperty(blockPosition);
        this.value = new SimpleIntegerProperty(value);
    }

    /**
     * 获取此数据包输出方块破坏动画的实体 Id 属性
     *
     * @return 实体 Id 属性
     */
    public IntegerProperty entityIdProperty() {

        return entityId;
    }

    /**
     * 获取此数据包输出方块破坏动画的方块位置属性
     *
     * @return 方块位置属性
     */
    public BlockPosition.BlockPositionProperty blockPositionProperty() {

        return blockPosition;
    }

    /**
     * 获取此数据包输出方块破坏动画的方块破坏值属性
     *
     * @return 方块破坏值属性
     */
    public IntegerProperty valueProperty() {

        return value;
    }

    @Override
    public Class<?> getPacketClass() {

        return CLASS_PACKETPLAYOUTBLOCKBREAKANIMATION;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        // 触发事件判断如果为 true 则阻止发送
        if(super.fireEvent(this, players))
            return true;

        BlockPosition blockPosition = blockPositionProperty().get();
        Validate.notNull(blockPosition, "The block position object is null.");

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

        BlockPosition blockPosition = blockPositionProperty().get();
        Validate.notNull(blockPosition, "The block position object is null.");

        try {
            // 先用调用 NMS 的 PacketPlayOutBlockBreakAnimation 构造函数, 参数 int, BlockPosition, int
            // 进行反射实例发送
            return packetPlayOutBlockBreakAnimationConstructor.invoke(entityId.get(), blockPosition.asNMS(), value.get());

        } catch (Exception e) {
            printException(e);
            // 如果异常了说明 NMS 的 PacketPlayOutBlockBreakAnimation 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 判断字段数量等于 3 个的话就是有此方式
                // 这字段分别对应 int, BlockPosition, int 属性
                Object packet = packetPlayOutBlockBreakAnimationVoidConstructor.invoke();
                Object[] values = { entityId.get(), blockPosition.asNMS(), value.get() };
                return setFieldAccessibleAndValueGet(3, CLASS_PACKETPLAYOUTBLOCKBREAKANIMATION, packet, values);

            } catch (Exception e1) {
                printException(e1);
            }
        }
        return null;
    }
}
