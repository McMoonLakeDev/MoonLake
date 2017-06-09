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

import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.nbt.exception.NBTException;
import com.minecraft.moonlake.nbt.exception.NBTInitializeException;
import com.minecraft.moonlake.reflect.FuzzyReflect;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.FieldAccessor;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * <h1>NBTItemStackExpression</h1>
 * NBT 物品栈接口实现类
 *
 * @version 1.1.1
 * @author Month_Light
 */
class NBTItemStackExpression implements NBTItemStack {

    private volatile FieldAccessor itemStackTagField;
    private volatile FieldAccessor craftItemStackHandleField;

    /**
     * NBT 物品栈接口实现类构造函数
     *
     * @throws NBTInitializeException 如果初始化错误则抛出异常
     */
    public NBTItemStackExpression() throws NBTInitializeException {

        Class<?> itemStackClass = MinecraftReflection.getMinecraftItemStackClass();
        Class<?> craftItemStackClass = MinecraftReflection.getCraftItemStackClass();

        try {
            // 用模糊反射的方式获取里面的指定类型的字段, 而不是通过名称来准确访问
            // 好处是防止以后字段名称有所改动导致无法获取到
            itemStackTagField = Accessors.getFieldAccessor(FuzzyReflect.fromClass(itemStackClass, true).getFieldByType("tag", MinecraftReflection.getNBTTagCompoundClass()));
            craftItemStackHandleField = Accessors.getFieldAccessor(FuzzyReflect.fromClass(craftItemStackClass, true).getFieldByType("handle", itemStackClass));
        }
        catch (Exception e) {

            throw new NBTInitializeException("The nbt itemstack reflect raw initialize exception.", e);
        }
    }

    private Object getTag(Object nmsItemStack) throws NBTException {

        try {

            return itemStackTagField.get(nmsItemStack);
        }
        catch (Exception e) {

            throw new NBTException("The get itemstack nbt tag exception.", e);
        }
    }

    private void setTag(Object nmsItemStack, Object nbtTagCompound) throws NBTException {

        try {

            itemStackTagField.set(nmsItemStack, nbtTagCompound);
        }
        catch (Exception e) {

            throw new NBTException("The set itemstack nbt tag exception.", e);
        }
    }

    @Override
    public Object createNMSItemStack(ItemStack itemStack) throws NBTException {

        Validate.notNull(itemStack, "The itemstack object is null.");

        try {

            return MinecraftReflection.asNMSCopy(itemStack);
        }
        catch (Exception e) {

            throw new NBTException("The create nms itemstack exception.", e);
        }
    }

    @Override
    public ItemStack createCraftItemStack(Object nmsItemStack) throws NBTException {

        Validate.notNull(nmsItemStack, "The nms itemstack object is null.");

        try {

            return MinecraftReflection.asCraftMirror(nmsItemStack);
        }
        catch (Exception e) {

            throw new NBTException("The create craftbukkit itemstack exception.", e);
        }
    }

    private Object getHandle(ItemStack itemStack) throws NBTException {

        try {

            return craftItemStackHandleField.get(itemStack);
        }
        catch (Exception e) {

            throw new NBTException("The get nms itemstack handle exception.", e);
        }
    }

    @Override
    public ItemStack createCraftItemStack(ItemStack itemStack) throws NBTException {

        return createCraftItemStack(createNMSItemStack(itemStack));
    }

    @Override
    public void setTag(ItemStack itemStack, Object nbtTagCompound) throws NBTException {

        Validate.notNull(itemStack, "The itemstack object is null.");

        try {

            if(MinecraftReflection.getCraftItemStackClass().isInstance(itemStack)) {

                setTagCraftBukkit(itemStack, nbtTagCompound);
            }
            else {

                setTagOrigin(itemStack, nbtTagCompound);
            }
        }
        catch (Exception e) {

            throw new NBTException("The set itemstack nbt tag exception.", e);
        }
    }

    @Override
    public Object getTag(ItemStack itemStack) throws NBTException {

        Validate.notNull(itemStack, "The itemstack object is null.");

        try {

            if(MinecraftReflection.getCraftItemStackClass().isInstance(itemStack)) {

                return getTagCraftBukkit(itemStack);
            }
            else {

                return getTagOrigin(itemStack);
            }
        }
        catch (Exception e) {

            throw new NBTException("The get itemstack nbt tag exception.", e);
        }
    }

    private void setTagCraftBukkit(ItemStack itemStack, Object nbtTagCompound) throws NBTException {

        Validate.notNull(nbtTagCompound, "The nbt tag object is null.");

        try {

            Object NMSItemStack = getHandle(itemStack);
            setTag(NMSItemStack, nbtTagCompound);
        }
        catch (Exception e) {

            throw new NBTException("The set itemstack nbt tag exception.", e);
        }
    }

    private Object getTagCraftBukkit(ItemStack itemStack) throws NBTException {

        try {

            Object NMSItemStack = getHandle(itemStack);
            return getTag(NMSItemStack);
        }
        catch (Exception e) {

            throw new NBTException("The get itemstack nbt tag exception.", e);
        }
    }

    private void setTagOrigin(ItemStack itemStack, Object nbtTagCompound) throws NBTException {

        if(nbtTagCompound == null) {

            itemStack.setItemMeta(null);
        }
        else {

            ItemStack copyNMSItemStack = createCraftItemStack(itemStack);

            try {

                setTagCraftBukkit(copyNMSItemStack, nbtTagCompound);
                ItemMeta itemMeta = copyNMSItemStack.getItemMeta();
                itemStack.setItemMeta(itemMeta);
            }
            catch (Exception e) {

                throw new NBTException("The set itemstack nbt tag exception.", e);
            }
        }
    }

    private Object getTagOrigin(ItemStack itemStack) throws NBTException {

        ItemStack copyNMSItemStack = createCraftItemStack(itemStack);

        try {

            ItemMeta itemMeta = itemStack.getItemMeta();
            copyNMSItemStack.setItemMeta(itemMeta);
            return getTagCraftBukkit(copyNMSItemStack);
        }
        catch (Exception e) {

            throw new NBTException("The get itemstack nbt tag exception.", e);
        }
    }
}
