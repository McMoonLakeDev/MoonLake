package com.minecraft.moonlake.api.nbt;

import com.minecraft.moonlake.nbt.exception.NBTException;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

/**
 * <h1>NBTExpression</h1>
 * NBT 支持库接口实现类
 *
 * @version 1.0
 * @author Month_Light
 */
class NBTExpression implements NBTLibrary {

    /**
     * NBT 支持库接口实现类构造函数
     */
    public NBTExpression() {

    }

    @Override
    public NBTCompound read(ItemStack itemStack) throws NBTException {

        Object tag = NBTFactory.getItem().getTag(itemStack);
        return NBTReflect.fromNBTCompoundCopy(tag);
    }

    @Override
    public NBTCompound readSafe(ItemStack itemStack) throws NBTException {

        NBTCompound temp = read(itemStack);

        if(temp == null) {

            temp = NBTFactory.newCompound();
        }
        return temp;
    }

    @Override
    public void write(ItemStack itemStack, NBTCompound nbt) throws NBTException {

        Validate.notNull(nbt, "The nbt tag object is null.");

        NBTFactory.getItem().setTag(itemStack, nbt.getHandleCopy());
    }

    @Override
    public NBTCompound read(Entity entity) throws NBTException {

        NBTCompound nbtCompound = new NBTCompoundExpression();
        NBTFactory.getEntity().readEntity(entity, nbtCompound.getHandle());
        return nbtCompound;
    }

    @Override
    public NBTCompound readSafe(Entity entity) throws NBTException {

        NBTCompound temp = read(entity);

        if(temp == null) {

            temp = NBTFactory.newCompound();
        }
        return temp;
    }

    @Override
    public void write(Entity entity, NBTCompound nbt) throws NBTException {

        NBTFactory.getEntity().writeEntity(entity, nbt.getHandleCopy());
    }

    @Override
    public NBTCompound read(Block block) throws NBTException {

        NBTCompound nbtCompound = new NBTCompoundExpression();
        NBTFactory.getBlock().readBlock(block, nbtCompound.getHandle());
        return nbtCompound;
    }

    @Override
    public NBTCompound readSafe(Block block) throws NBTException {

        NBTCompound temp = read(block);

        if(temp == null) {

            temp = NBTFactory.newCompound();
        }
        return temp;
    }

    @Override
    public void write(Block block, NBTCompound nbt) throws NBTException {

        NBTFactory.getBlock().writeBlock(block, nbt.getHandleCopy());
        NBTFactory.getBlock().update(block);
    }

    @Override
    public NBTCompound read(Chunk chunk) throws NBTException {

        NBTCompound nbtCompound = new NBTCompoundExpression();
        NBTFactory.getChunk().readChunk(chunk, nbtCompound.getHandle());
        return nbtCompound;
    }

    @Override
    public NBTCompound readSafe(Chunk chunk) throws NBTException {

        NBTCompound temp = read(chunk);

        if(temp == null) {

            temp = NBTFactory.newCompound();
        }
        return temp;
    }

    @Override
    public void write(Chunk chunk, NBTCompound nbt) throws NBTException {

        NBTFactory.getChunk().writeChunk(chunk, nbt.getHandleCopy());
    }

    @Override
    public Entity spawnEntity(NBTCompound nbt, World world) throws NBTException {

        Validate.notNull(nbt, "The nbt tag object is null.");

        return NBTFactory.getEntity().spawnEntity(nbt.getHandle(), world);
    }
}
