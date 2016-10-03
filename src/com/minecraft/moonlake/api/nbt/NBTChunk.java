package com.minecraft.moonlake.api.nbt;

import com.minecraft.moonlake.nbt.exception.NBTException;
import org.bukkit.Chunk;

/**
 * Created by MoonLake on 2016/10/2.
 */
public interface NBTChunk {

    void readChunk(Chunk chunk, Object nbtTagCompound) throws NBTException;

    void writeChunk(Chunk chunk, Object nbtTagCompound) throws NBTException;
}
