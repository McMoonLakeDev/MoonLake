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
