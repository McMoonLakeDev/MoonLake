package com.minecraft.moonlake.api.nbt;

import com.minecraft.moonlake.nbt.exception.NBTException;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/9/23.
 */
public interface NBTLibrary {

    NBTCompound read(ItemStack itemStack) throws NBTException;

    void write(ItemStack itemStack, NBTCompound nbt) throws NBTException;

    NBTCompound read(Entity entity) throws NBTException;

    void write(Entity entity, NBTCompound nbt) throws NBTException;

    Entity spawnEntity(NBTCompound nbt, World world) throws NBTException;
}
