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

import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.api.utility.MinecraftVersion;
import com.minecraft.moonlake.builder.SingleParamBuilder;
import com.minecraft.moonlake.nbt.exception.NBTException;
import com.minecraft.moonlake.nbt.exception.NBTInitializeException;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.MethodAccessor;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>NBTBlockExpression</h1>
 * NBT 方块接口实现类
 *
 * @version 1.1
 * @author Month_Light
 */
class NBTBlockExpression implements NBTBlock {

    private volatile MethodAccessor tileEntityGetUpdatePacketMethod;
    private volatile MethodAccessor tileEntitySaveMethod;
    private volatile MethodAccessor tileEntityWriteMethod;

    /**
     * NBT 方块接口实现类构造函数
     *
     * @throws NBTInitializeException 如果初始化错误则抛出异常
     */
    public NBTBlockExpression() throws NBTInitializeException {

        Class<?> tileEntityClass = MinecraftReflection.getMinecraftTileEntityClass();
        Class<?> nbtTagCompoundClass = MinecraftReflection.getNBTTagCompoundClass();

        try {

            tileEntityGetUpdatePacketMethod = Accessors.getMethodAccessor(tileEntityClass, "getUpdatePacket");
            tileEntitySaveMethod = Accessors.getMethodAccessorBuilderMCVer(new SingleParamBuilder<MethodAccessor, MinecraftVersion>() {
                @Override
                public MethodAccessor build(MinecraftVersion param) {
                    if(!param.isOrLater(MinecraftVersion.V1_9))
                        return Accessors.getMethodAccessor(tileEntityClass, "b", nbtTagCompoundClass);
                    return Accessors.getMethodAccessor(tileEntityClass, "save", nbtTagCompoundClass);
                }
            });
            tileEntityWriteMethod = Accessors.getMethodAccessor(tileEntityClass, "a", nbtTagCompoundClass);
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

                tileEntitySaveMethod.invoke(tileEntity, nbtTagCompound);
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

                tileEntityWriteMethod.invoke(tileEntity, nbtTagCompound);
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

                Object packet = tileEntityGetUpdatePacketMethod.invoke(tileEntity);

                if(packet == null) return;

                List<Player> target = new ArrayList<>();
                int maxDistance = Bukkit.getServer().getViewDistance() * 32;

                for(final Player player : block.getWorld().getPlayers()) {

                    if(player.getLocation().distance(block.getLocation()) < maxDistance) {

                        target.add(player);
                    }
                }
                if(target.size() > 0) {

                    MinecraftReflection.sendPacket(target.toArray(new Player[target.size()]), packet);
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

            return MinecraftReflection.getTileEntity(block.getLocation());
        }
        catch (Exception e) {

            throw new NBTException("The get tile entity exception.", e);
        }
    }
}
