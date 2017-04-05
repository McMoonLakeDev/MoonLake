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
 
 
package com.minecraft.moonlake.api.nbt;

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.utility.MinecraftVersion;
import com.minecraft.moonlake.nbt.exception.NBTException;
import com.minecraft.moonlake.nbt.exception.NBTInitializeException;
import com.minecraft.moonlake.nms.packet.PacketFactory;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>NBTBlockExpression</h1>
 * NBT 方块接口实现类
 *
 * @version 1.0.1
 * @author Month_Light
 */
class NBTBlockExpression implements NBTBlock {

    private Class<?> CLASS_WORLD;
    private Class<?> CLASS_CRAFTWORLD;
    private Class<?> CLASS_TILEENTITY;
    private Class<?> CLASS_BLOCKPOSITION;
    private Class<?> CLASS_NBTTAGCOMPOUND;
    private Method METHOD_GETHANDLE;
    private Method METHOD_GETTILEENTITY;
    private Method METHOD_GETUPDATEPACKET;
    private Method METHOD_READ;
    private Method METHOD_WRITE;

    /**
     * NBT 方块接口实现类构造函数
     *
     * @throws NBTInitializeException 如果初始化错误则抛出异常
     */
    public NBTBlockExpression() throws NBTInitializeException {

        try {

            CLASS_WORLD = PackageType.MINECRAFT_SERVER.getClass("World");
            CLASS_CRAFTWORLD = PackageType.CRAFTBUKKIT.getClass("CraftWorld");
            CLASS_TILEENTITY = PackageType.MINECRAFT_SERVER.getClass("TileEntity");
            CLASS_BLOCKPOSITION = PackageType.MINECRAFT_SERVER.getClass("BlockPosition");
            CLASS_NBTTAGCOMPOUND = PackageType.MINECRAFT_SERVER.getClass("NBTTagCompound");

            METHOD_GETHANDLE = getMethod(CLASS_CRAFTWORLD, "getHandle");
            METHOD_GETTILEENTITY = getMethod(CLASS_WORLD, "getTileEntity", CLASS_BLOCKPOSITION);
            METHOD_GETUPDATEPACKET = getMethod(CLASS_TILEENTITY, "getUpdatePacket");
            METHOD_READ = getMethod(CLASS_TILEENTITY, !MoonLakeAPI.currentMCVersion().isOrLater(MinecraftVersion.V1_9) ? "b" : "save", CLASS_NBTTAGCOMPOUND);
            METHOD_WRITE = getMethod(CLASS_TILEENTITY, "a", CLASS_NBTTAGCOMPOUND);
        }
        catch (Exception e) {

            throw new NBTInitializeException("The nbt block reflect raw initialize exception.", e);
        }
    }

    @Override
    public void readBlock(Block block, Object nbtTagCompound) throws NBTException {

        Validate.notNull(block, "The block object is null.");
        Validate.notNull(nbtTagCompound, "The nbt tag object is null.");

        try {

            Object tileEntity = getTileEntity(block);

            if(tileEntity != null) {

                METHOD_READ.invoke(tileEntity, nbtTagCompound);
            }
        }
        catch (Exception e) {

            throw new NBTException("The read block nbt tag exception.", e);
        }
    }

    @Override
    public void writeBlock(Block block, Object nbtTagCompound) throws NBTException {

        Validate.notNull(block, "The block object is null.");
        Validate.notNull(nbtTagCompound, "The nbt tag object is null.");

        try {

            Object tileEntity = getTileEntity(block);

            if(tileEntity != null) {

                METHOD_WRITE.invoke(tileEntity, nbtTagCompound);
            }
        }
        catch (Exception e) {

            throw new NBTException("The write block nbt tag exception.", e);
        }
    }

    @Override
    public void update(Block block) throws NBTException {

        if(block == null) return;

        try {

            Object tileEntity = getTileEntity(block);

            if(tileEntity != null) {

                Object packet = METHOD_GETUPDATEPACKET.invoke(tileEntity);

                if(packet == null) return;

                List<Player> target = new ArrayList<>();
                int maxDistance = Bukkit.getServer().getViewDistance() * 32;

                for(final Player player : block.getWorld().getPlayers()) {

                    if(player.getLocation().distance(block.getLocation()) < maxDistance) {

                        target.add(player);
                    }
                }
                if(target.size() > 0) {

                    PacketFactory.get().sendPacket(target.toArray(new Player[target.size()]), packet);
                }
            }
        }
        catch (Exception e) {

            throw new NBTException("The update block nbt tag exception.", e);
        }
    }

    @Override
    public Object getTileEntity(Block block) throws NBTException {

        Validate.notNull(block, "The block object is null.");

        try {

            Object nmsWorld = METHOD_GETHANDLE.invoke(block.getWorld());
            Object nmsBlockPosition = instantiateObject(CLASS_BLOCKPOSITION, block.getX(), block.getY(), block.getZ());

            return METHOD_GETTILEENTITY.invoke(nmsWorld, nmsBlockPosition);
        }
        catch (Exception e) {

            throw new NBTException("The get tile entity exception.", e);
        }
    }
}
