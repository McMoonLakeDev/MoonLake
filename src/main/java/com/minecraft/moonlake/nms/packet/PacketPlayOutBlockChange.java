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


package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.nms.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.nms.packet.wrapped.BlockPosition;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutBlockChange</h1>
 * 数据包输出方块改变类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v1.9-a5 删除. 请使用 {@link com.minecraft.moonlake.api.packet.wrapper.PacketPlayOutBlockChange}
 */
@Deprecated
public class PacketPlayOutBlockChange extends PacketAbstract<PacketPlayOutBlockChange> {

    private final static Class<?> CLASS_PACKETPLAYOUTBLOCKCHANGE;
    private final static Class<?> CLASS_CRAFTWORLD;
    private final static Method METHOD_GETHANDLER;

    static {

        try {

            CLASS_PACKETPLAYOUTBLOCKCHANGE = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutBlockChange");
            CLASS_CRAFTWORLD = PackageType.CRAFTBUKKIT.getClass("CraftWorld");
            METHOD_GETHANDLER = getMethod(CLASS_CRAFTWORLD, "getHandle");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out block change reflect raw initialize exception.", e);
        }
    }

    private ObjectProperty<World> world;
    private ObjectProperty<BlockPosition> blockPosition;

    /**
     * 数据包输出方块改变类构造函数
     *
     * @deprecated 已过时, 请使用 {@link #PacketPlayOutBlockChange(World, BlockPosition)}
     */
    @Deprecated
    public PacketPlayOutBlockChange() {

        this(Bukkit.getServer().getWorlds().get(0), BlockPosition.ZERO);
    }

    /**
     * 数据包输出方块改变类构造函数
     *
     * @param block 方块
     */
    public PacketPlayOutBlockChange(Block block) {

        this(block.getWorld(), new BlockPosition(block.getX(), block.getY(), block.getZ()));
    }

    /**
     * 数据包输出方块改变类构造函数
     *
     * @param world 世界
     * @param blockPosition 方块位置
     */
    public PacketPlayOutBlockChange(World world, BlockPosition blockPosition) {

        this.world = new SimpleObjectProperty<>(world);
        this.blockPosition = new SimpleObjectProperty<>(blockPosition);
    }

    /**
     * 获取此数据包输出方块改变的世界
     *
     * @return 世界
     */
    public ObjectProperty<World> getWorld() {

        return world;
    }

    /**
     * 获取此数据包输出方块改变的方块位置
     *
     * @return 方块位置
     */
    public ObjectProperty<BlockPosition> getBlockPosition() {

        return blockPosition;
    }

    @Override
    public void send(Player... players) throws PacketException {

        if(super.fireEvent(this, players)) return;

        try {

            Object nmsWorld = METHOD_GETHANDLER.invoke(world.get());
            Object packet = instantiateObject(CLASS_PACKETPLAYOUTBLOCKCHANGE, nmsWorld, blockPosition.get().asNMS());

            PacketReflect.get().send(players, packet);
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out block change send exception.", e);
        }
    }
}
