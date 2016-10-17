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
import com.minecraft.moonlake.nbt.exception.NBTInitializeException;
import org.bukkit.Chunk;

/**
 * <h1>NBTChunkExpression</h1>
 * NBT 区块接口实现类
 *
 * @version 1.0
 * @author Month_Light
 */
class NBTChunkExpression implements NBTChunk {

    /**
     * NBT 区块接口实现类构造函数
     *
     * @throws NBTInitializeException 如果初始化错误则抛出异常
     */
    public NBTChunkExpression() throws NBTInitializeException {

    }

    @Override
    public void readChunk(Chunk chunk, Object nbtTagCompound) throws NBTException {

    }

    @Override
    public void writeChunk(Chunk chunk, Object nbtTagCompound) throws NBTException {

    }
}
