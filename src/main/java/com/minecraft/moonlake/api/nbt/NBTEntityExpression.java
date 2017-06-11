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

import com.minecraft.moonlake.api.utility.MinecraftBukkitVersion;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.builder.SingleParamBuilder;
import com.minecraft.moonlake.nbt.exception.NBTException;
import com.minecraft.moonlake.nbt.exception.NBTInitializeException;
import com.minecraft.moonlake.reflect.FuzzyReflect;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.MethodAccessor;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.List;
import java.util.Map;

/**
 * <h1>NBTEntityExpression</h1>
 * NBT 实体接口实现类
 *
 * @version 1.1
 * @author Month_Light
 */
class NBTEntityExpression implements NBTEntity {

    private volatile MethodAccessor entityReadMethod;
    private volatile MethodAccessor entityWriteMethod;
    private volatile MethodAccessor entityPlayerReadMethod;
    private volatile MethodAccessor entityPlayerWriteMethod;
    private volatile MethodAccessor entityTypesCreateEntityMethod;

    /**
     * NBT 实体接口实现类构造函数
     *
     * @throws NBTInitializeException 如果初始化错误则抛出异常
     */
    public NBTEntityExpression() throws NBTInitializeException {

        Class<?> worldClass = MinecraftReflection.getMinecraftWorldClass();
        Class<?> entityClass = MinecraftReflection.getMinecraftEntityClass();
        Class<?> entityTypesClass = MinecraftReflection.getMinecraftEntityTypesClass();
        Class<?> nbtTagCompoundClass = MinecraftReflection.getNBTTagCompoundClass();

        try {
            // 这个 EntityTypes 类可以用模糊反射, 但是 Entity 类这几个函数都是同样参数同样返回值, 暂时不用
            entityTypesCreateEntityMethod = Accessors.getMethodAccessor(FuzzyReflect.fromClass(entityTypesClass, true).getMethodByParameters("a", entityClass, new Class[] { nbtTagCompoundClass, worldClass }));
            entityReadMethod = Accessors.getMethodAccessor(entityClass, "c", nbtTagCompoundClass);
            entityPlayerReadMethod = Accessors.getMethodAccessorBuilderBukkitVer(new SingleParamBuilder<MethodAccessor, MinecraftBukkitVersion>() {
                @Override
                public MethodAccessor build(MinecraftBukkitVersion param) {
                    if(!param.isOrLater(MinecraftBukkitVersion.V1_9_R2))
                        return Accessors.getMethodAccessor(entityClass, "e", nbtTagCompoundClass);
                    return Accessors.getMethodAccessor(FuzzyReflect.fromClass(entityClass).getMethodByParameters("save", nbtTagCompoundClass, new Class[] { nbtTagCompoundClass }));
                }
            });
            entityWriteMethod = entityPlayerWriteMethod = Accessors.getMethodAccessor(entityClass, "f", nbtTagCompoundClass);
        }
        catch(Exception e) {

            throw new NBTInitializeException("The nbt entity reflect raw initialize exception.", e);
        }
    }


    @Override
    public Object getHandleEntity(Entity entity) throws NBTException {

        Validate.notNull(entity, "The entity object is null.");

        try {

            return MinecraftReflection.getEntity(entity);
        }
        catch (Exception e) {

            throw new NBTException("The get entity handle exception.", e);
        }
    }

    @Override
    public void readEntity(Entity entity, Object nbtTagCompound) throws NBTException {

        Validate.notNull(entity, "The entity object is null.");
        Validate.notNull(nbtTagCompound, "The nbt tag object is null.");

        Object nmsEntity = getHandleEntity(entity);

        try {

            if(MinecraftReflection.getMinecraftEntityPlayerClass().isInstance(nmsEntity)) {

                entityPlayerReadMethod.invoke(nmsEntity, nbtTagCompound);
            }
            else {

                entityReadMethod.invoke(nmsEntity, nbtTagCompound);
            }
        }
        catch (Exception e) {

            throw new NBTException("The read entity nbt tag exception.");
        }
    }

    @Override
    public void writeEntity(Entity entity, Object nbtTagCompound) throws NBTException {

        Validate.notNull(entity, "The entity object is null.");
        Validate.notNull(nbtTagCompound, "The nbt tag object is null.");

        Object nmsEntity = getHandleEntity(entity);

        try {

            if(entity.getType() == EntityType.PLAYER) {

                entityPlayerWriteMethod.invoke(nmsEntity, nbtTagCompound);
            }
            else {

                entityWriteMethod.invoke(nmsEntity, nbtTagCompound);
            }
        }
        catch (Exception e) {

            throw new NBTException("The write entity nbt tag exception.");
        }
    }

    @Override
    public Entity spawnEntity(Object nbtTagCompound, World world) throws NBTException {

        Validate.notNull(nbtTagCompound, "The nbt tag compound object is null.");
        Validate.notNull(world, "The target world object is null.");

        try {

            nbtTagCompound = NBTReflect.getHandle().cloneTag(nbtTagCompound);
            Object nmsWorld = MinecraftReflection.getWorldServer(world);
            Object nmsEntity = entityTypesCreateEntityMethod.invoke(null, nbtTagCompound, nmsWorld);

            if(nmsEntity == null) {

                return null;
            }
            MinecraftReflection.addEntity(nmsWorld, nmsEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
            Entity entity = MinecraftReflection.getBukkitEntity(nmsEntity);
            Location location = entity.getLocation();
            Object tagPos = NBTReflect.getHandle().createTagList();
            NBTReflect.getHandle().setNBTTagListType(tagPos, (byte) 6);
            List<Object> handleList = NBTReflect.getHandle().getHandleList(tagPos);
            handleList.add(NBTReflect.getHandle().createTagDouble(location.getX()));
            handleList.add(NBTReflect.getHandle().createTagDouble(location.getY()));
            handleList.add(NBTReflect.getHandle().createTagDouble(location.getZ()));
            Entity currentEntity = entity;

            while (true) {

                Map<String, Object> handleMap = NBTReflect.getHandle().getHandleMap(nbtTagCompound);
                nbtTagCompound = handleMap.get("Riding");

                if(nbtTagCompound == null) {

                    break;
                }
                Map<String, Object> ridingMap = NBTReflect.getHandle().getHandleMap(nbtTagCompound);
                ridingMap.put("Pos", NBTReflect.getHandle().cloneTag(tagPos));
                Object nmsRiding = entityTypesCreateEntityMethod.invoke(null, nbtTagCompound, nmsWorld);

                if(nmsRiding == null) {

                    break;
                }
                MinecraftReflection.addEntity(nmsWorld, nmsRiding);
                Entity riding = MinecraftReflection.getBukkitEntity(nmsRiding);
                riding.setPassenger(currentEntity);
                currentEntity = riding;
            }
            return entity;
        }
        catch (Exception e) {

            throw new NBTException("The spawn nbt tag entity exception.", e);
        }
    }
}
