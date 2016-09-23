package com.minecraft.moonlake.api.nbt;

import com.minecraft.moonlake.nbt.exception.NBTException;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/9/23.
 */
public interface NBTItemStack {

    Object createNMSItemStack(ItemStack itemStack) throws NBTException;

    ItemStack createCraftItemStack(Object nmsItemStack) throws NBTException;

    ItemStack createCraftItemStack(ItemStack itemStack) throws NBTException;

    void setTag(ItemStack itemStack, Object nbtTagCompound) throws NBTException;

    Object getTag(ItemStack itemStack) throws NBTException;
}
