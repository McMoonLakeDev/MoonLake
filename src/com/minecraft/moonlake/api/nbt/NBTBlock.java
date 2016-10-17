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

import com.minecraft.moonlake.nbt.exception.NBTException;
import org.bukkit.block.Block;

/**
 * <h1>NBTBlock</h1>
 * NBT 方块支持库（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface NBTBlock {

    /**
     * 读取指定方块的 NBT 数据
     *
     * @param block 方块
     * @param nbtTagCompound NBT 复合对象
     * @throws NBTException 如果读取方块时错误则抛出异常
     * @throws IllegalArgumentException 如果方块对象为 {@code null} 则抛出异常
     */
    void readBlock(Block block, Object nbtTagCompound) throws NBTException;

    /**
     * 将指定 NBT 数据写入到方块
     *
     * @param block 方块
     * @param nbtTagCompound NBT 复合对象
     * @throws NBTException 如果写入方块时错误则抛出异常
     * @throws IllegalArgumentException 如果方块对象或 NBT 数据对象为 {@code null} 则抛出异常
     */
    void writeBlock(Block block, Object nbtTagCompound) throws NBTException;

    /**
     * 更新指定方块对象
     *
     * @param block 方块
     * @throws NBTException 如果更新方块时错误则抛出异常
     */
    void update(Block block) throws NBTException;

    /**
     * 获取指定 TileEntity 对象
     *
     * @param block 方块
     * @return TileEntity
     * @throws IllegalArgumentException 如果方块对象为 {@code null} 则抛出异常
     * @throws NBTException 如果获取时错误则抛出异常
     * @throws NBTException 如果方块不是 TileEntity 对象则抛出异常
     */
    Object getTileEntity(Block block) throws NBTException;
}
