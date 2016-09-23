package com.minecraft.moonlake.api.nbt;

import com.minecraft.moonlake.nbt.exception.NBTException;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/9/23.
 */
class NBTExpression implements NBTLibrary {

    public NBTExpression() {

    }

    @Override
    public NBTCompound read(ItemStack itemStack) throws NBTException {

        Object tag = NBTFactory.item().getTag(itemStack);
        return NBTCompound.fromNBTCopy(tag);
    }

    @Override
    public void write(ItemStack itemStack, NBTCompound nbt) throws NBTException {

        NBTFactory.item().setTag(itemStack, nbt.getHandleCopy());
    }

    @Override
    public NBTCompound read(Entity entity) throws NBTException {

        NBTCompound nbtCompound = new NBTCompound();
        NBTFactory.entity().readEntity(entity, nbtCompound);
        return nbtCompound;
    }

    @Override
    public void write(Entity entity, NBTCompound nbt) throws NBTException {

        NBTFactory.entity().writeEntity(entity, nbt);
    }

    @Override
    public Entity spawnEntity(NBTCompound nbt, World world) throws NBTException {

        return nbt == null ? null : NBTFactory.entity().spawnEntity(nbt.getHandle(), world);
    }
}
