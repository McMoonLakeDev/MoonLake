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
import org.bukkit.Chunk;

/**
 * <h1>NBTChunk</h1>
 * NBT 区块支持库（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface NBTChunk {

    /**
     * 读取指定区块的 NBT 数据
     *
     * @param chunk 区块
     * @param nbtTagCompound NBT 复合对象
     * @throws NBTException 如果读取区块时错误则抛出异常
     * @throws IllegalArgumentException 如果区块对象为 {@code null} 则抛出异常
     */
    void readChunk(Chunk chunk, Object nbtTagCompound) throws NBTException;

    /**
     * 将指定 NBT 数据写入到区块
     *
     * @param chunk 区块
     * @param nbtTagCompound NBT 复合对象
     * @throws NBTException 如果写入区块时错误则抛出异常
     * @throws IllegalArgumentException 如果区块对象为 {@code null} 则抛出异常
     */
    void writeChunk(Chunk chunk, Object nbtTagCompound) throws NBTException;
}
