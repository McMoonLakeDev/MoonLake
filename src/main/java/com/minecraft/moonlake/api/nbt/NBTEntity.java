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
import org.bukkit.World;
import org.bukkit.entity.Entity;

/**
 * <h1>NBTEntity</h1>
 * NBT 实体支持库（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
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
     * @deprecated 已过时, 将于 v1.9-a6 删除.
     */
    @Deprecated
    Entity spawnEntity(Object nbtTagCompound, World world) throws NBTException;
}
