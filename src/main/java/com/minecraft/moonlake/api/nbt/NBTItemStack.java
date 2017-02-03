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
import org.bukkit.inventory.ItemStack;

/**
 * <h1>NBTItemStack</h1>
 * NBT 物品栈支持库（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface NBTItemStack {

    /**
     * 将指定物品栈创建为 NMS 物品栈对象
     *
     * @param itemStack 物品栈
     * @return NMS ItemStack
     * @throws NBTException 如果创建错误则抛出异常
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    Object createNMSItemStack(ItemStack itemStack) throws NBTException;

    /**
     * 将指定 NMS 物品栈对象创建为物品栈对象
     *
     * @param nmsItemStack NMS 物品栈
     * @return ItemStack
     * @throws NBTException 如果创建错误则抛出异常
     * @throws IllegalArgumentException 如果 NMS 物品栈对象为 {@code null} 则抛出异常
     */
    ItemStack createCraftItemStack(Object nmsItemStack) throws NBTException;

    /**
     * 将指定物品栈对象创建为物品栈对象
     *
     * @param itemStack 物品栈
     * @return ItemStack
     * @throws NBTException 如果创建错误则抛出异常
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    ItemStack createCraftItemStack(ItemStack itemStack) throws NBTException;

    /**
     * 将指定物品栈设置 NBT 数据
     *
     * @param itemStack 物品栈
     * @param nbtTagCompound NBT 数据
     * @throws NBTException 如果设置错误则抛出异常
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    void setTag(ItemStack itemStack, Object nbtTagCompound) throws NBTException;

    /**
     * 获取指定物品栈的 NBT 数据
     *
     * @param itemStack 物品栈
     * @return NBT 对象
     * @throws NBTException 如果获取错误则抛出异常
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    Object getTag(ItemStack itemStack) throws NBTException;
}
