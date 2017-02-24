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


package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.nms.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.nms.packet.wrapped.BlockPosition;
import com.minecraft.moonlake.property.IntegerProperty;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import org.bukkit.entity.Player;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutBlockBreakAnimation</h1>
 * 数据包输出方块破坏动画（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v1.9-a5 删除. 请使用 {@link com.minecraft.moonlake.api.packet.wrapper.PacketPlayOutBlockBreakAnimation}
 */
@Deprecated
public class PacketPlayOutBlockBreakAnimation extends PacketAbstract<PacketPlayOutBlockBreakAnimation> {

    private final static Class<?> CLASS_PACKETPLAYOUTBLOCKBREAKANIMATION;

    static {

        try {

            CLASS_PACKETPLAYOUTBLOCKBREAKANIMATION = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutBlockBreakAnimation");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out block break animation reflect raw initialize exception.", e);
        }
    }

    private IntegerProperty entityId;
    private ObjectProperty<BlockPosition> blockPosition;
    private IntegerProperty value;

    /**
     * 数据包输出方块破坏动画类构造函数
     *
     * @param entityId 实体 Id
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     * @param value 破坏程度 (0 - 9)
     */
    public PacketPlayOutBlockBreakAnimation(int entityId, int x, int y, int z, int value) {

        this(entityId, new BlockPosition(x, y, z), value);
    }

    /**
     * 数据包输出方块破坏动画类构造函数
     *
     * @param player 玩家
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     * @param value 破坏程度 (0 - 9)
     */
    public PacketPlayOutBlockBreakAnimation(Player player, int x, int y, int z, int value) {

        this(player, new BlockPosition(x, y, z), value);
    }

    /**
     * 数据包输出方块破坏动画类构造函数
     *
     * @param entityId 实体 Id
     * @param blockPosition 方块位置对象
     * @param value 破坏程度 (0 - 9)
     */
    public PacketPlayOutBlockBreakAnimation(int entityId, BlockPosition blockPosition, int value) {

        this.entityId = new SimpleIntegerProperty(entityId);
        this.blockPosition = new SimpleObjectProperty<>(blockPosition);
        this.value = new SimpleIntegerProperty(value);
    }

    /**
     * 数据包输出方块破坏动画类构造函数
     *
     * @param player 玩家
     * @param blockPosition 方块位置对象
     * @param value 破坏程度 (0 - 9)
     */
    public PacketPlayOutBlockBreakAnimation(Player player, BlockPosition blockPosition, int value) {

        this(player.getEntityId(), blockPosition, value);
    }

    /**
     * 获取此数据包输出方块破坏动画的实体 Id
     *
     * @return 实体 Id
     */
    public IntegerProperty getEntityId() {

        return entityId;
    }

    /**
     * 获取此数据包输出方块破坏动画的方块位置
     *
     * @return 方块位置
     */
    public ObjectProperty<BlockPosition> getBlockPosition() {

        return blockPosition;
    }

    /**
     * 获取此数据包输出方块破坏动画的破坏程度值
     *
     * @return 破坏程度值 (0 - 9)
     */
    public IntegerProperty getValue() {

        return value;
    }

    @Override
    public void send(Player... players) throws PacketException {

        if(super.fireEvent(this, players)) return;

        try {

            Object packet = instantiateObject(CLASS_PACKETPLAYOUTBLOCKBREAKANIMATION, blockPosition.get().asNMS(), value.get());

            PacketReflect.get().send(players, packet);
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out block break animation send exception.", e);
        }
    }
}
