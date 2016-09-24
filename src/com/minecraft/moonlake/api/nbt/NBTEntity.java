package com.minecraft.moonlake.api.nbt;

import com.minecraft.moonlake.nbt.exception.NBTException;
import org.bukkit.World;
import org.bukkit.entity.Entity;

/**
 * Created by MoonLake on 2016/9/23.
 */
public interface NBTEntity {

    /**
     * 获取指定 Entity 对象的 NMS 对象
     *
     * @param entity 实体
     * @return NMS Entity
     * @throws NBTException 如果获取错误则抛出异常
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    Object getHandleEntity(Entity entity) throws NBTException;

    /**
     * 读取指定实体的 NBT 数据
     *
     * @param entity 实体
     * @param nbtTagCompound NBT 复合对象
     * @throws NBTException 如果读取实体时错误则抛出异常
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    void readEntity(Entity entity, Object nbtTagCompound) throws NBTException;

    /**
     * 将指定 NBT 数据写入到实体
     *
     * @param entity 实体
     * @param nbtTagCompound NBT 复合对象
     * @throws NBTException 如果写入实体时错误则抛出异常
     * @throws IllegalArgumentException 如果实体对象或 NBT 复合对象为 {@code null} 则抛出异常
     */
    void writeEntity(Entity entity, Object nbtTagCompound) throws NBTException;

    /**
     * 将指定 NBT 数据生成为实体
     *
     * @param nbtTagCompound NBT 复合对象
     * @param world 目标世界
     * @return Entity
     * @throws NBTException 如果生成实体时错误则抛出异常
     * @throws IllegalArgumentException 如果 NBT 复合对象或目标世界对象为 {@code null} 则抛出异常
     */
    Entity spawnEntity(Object nbtTagCompound, World world) throws NBTException;
}
