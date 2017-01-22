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

    public PacketPlayOutBlockChange() {

        this(Bukkit.getServer().getWorlds().get(0), BlockPosition.ZERO);
    }

    public PacketPlayOutBlockChange(Block block) {

        this(block.getWorld(), new BlockPosition(block.getX(), block.getY(), block.getZ()));
    }

    public PacketPlayOutBlockChange(World world, BlockPosition blockPosition) {

        this.world = new SimpleObjectProperty<>(world);
        this.blockPosition = new SimpleObjectProperty<>(blockPosition);
    }

    public ObjectProperty<World> getWorld() {

        return world;
    }

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
