package com.minecraft.moonlake.api.nbt;

import com.minecraft.moonlake.nbt.exception.NBTException;
import com.minecraft.moonlake.nbt.exception.NBTInitializeException;
import org.bukkit.Chunk;

/**
 * Created by MoonLake on 2016/10/2.
 */
public class NBTChunkExpression implements NBTChunk {

    public NBTChunkExpression() throws NBTInitializeException {

    }

    @Override
    public void readTag(Chunk chunk, Object nbtTagCompound) throws NBTException {

    }

    @Override
    public void writeTag(Chunk chunk, Object nbtTagCompound) throws NBTException {

    }
}
