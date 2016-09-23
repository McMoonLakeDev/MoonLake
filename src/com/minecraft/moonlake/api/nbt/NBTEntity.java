package com.minecraft.moonlake.api.nbt;

import com.minecraft.moonlake.nbt.exception.NBTException;
import org.bukkit.World;
import org.bukkit.entity.Entity;

/**
 * Created by MoonLake on 2016/9/23.
 */
public interface NBTEntity {

    Object getHandleEntity(Entity entity) throws NBTException;

    void readEntity(Entity entity, Object nbtTagCompound) throws NBTException;

    void writeEntity(Entity entity, Object nbtTagCompound) throws NBTException;

    Entity spawnEntity(Object nbtTagCompound, World world) throws NBTException;
}
