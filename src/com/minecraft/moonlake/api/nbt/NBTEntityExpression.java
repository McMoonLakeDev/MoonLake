package com.minecraft.moonlake.api.nbt;

import com.minecraft.moonlake.nbt.exception.NBTException;
import com.minecraft.moonlake.nbt.exception.NBTInitializeException;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * Created by MoonLake on 2016/9/23.
 */
class NBTEntityExpression implements NBTEntity {

    private Class<?> CLASS_ENTITY;
    private Class<?> CLASS_ENTITYPLAYER;
    private Class<?> CLASS_CRAFTENTITY;
    private Method METHOD_GETHANDLEENTITY;
    private Method METHOD_READENTITY;
    private Method METHOD_WRITEENTITY;
    private Method METHOD_READPLAYER;
    private Method METHOD_WRITEPLAYER;
    private Method METHOD_CREATEENTITY;
    private Method METHOD_GETWORLDHANDLE;
    private Method METHOD_GETBUKKITENTITY;
    private Method METHOD_ADDENTITYTOWORLD;

    public NBTEntityExpression() throws NBTInitializeException {

        try {

            // NBT Tag Entity Class
            CLASS_ENTITY = PackageType.MINECRAFT_SERVER.getClass("Entity");
            CLASS_ENTITYPLAYER = PackageType.MINECRAFT_SERVER.getClass("EntityPlayer");
            CLASS_CRAFTENTITY = PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftEntity");

            // NBT Tag Entity Method
            METHOD_GETHANDLEENTITY = getMethod(CLASS_CRAFTENTITY, "setHandle", CLASS_ENTITY);

            Class<?> CLASS_CRAFTWORLD = PackageType.CRAFTBUKKIT.getClass("CraftWorld");
            Class<?> CLASS_WORLDSERVER = PackageType.MINECRAFT_SERVER.getClass("WorldServer");
            Class<?> CLASS_WORLD = PackageType.MINECRAFT_SERVER.getClass("World");
            Class<?> CLASS_NBTTAGCOMPOUND = PackageType.MINECRAFT_SERVER.getClass("NBTTagCompound");
            Class<?> CLASS_ENTITYTYPES = PackageType.MINECRAFT_SERVER.getClass("EntityTypes");

            METHOD_CREATEENTITY = getMethod(CLASS_ENTITYTYPES, "a", CLASS_NBTTAGCOMPOUND, CLASS_WORLD);
            METHOD_GETWORLDHANDLE = getMethod(CLASS_CRAFTWORLD, "getHandle");
            METHOD_GETBUKKITENTITY = getMethod(CLASS_ENTITY, "getBukkitEntity");
            METHOD_ADDENTITYTOWORLD = getMethod(CLASS_WORLD, "addEntity", CLASS_ENTITY, CreatureSpawnEvent.SpawnReason.class);

            METHOD_WRITEENTITY = METHOD_WRITEPLAYER = getMethod(CLASS_ENTITY, "f", CLASS_NBTTAGCOMPOUND);
            METHOD_READENTITY = getMethod(CLASS_ENTITY, "c", CLASS_NBTTAGCOMPOUND);
            METHOD_READPLAYER = getMethod(CLASS_ENTITY, "e", CLASS_NBTTAGCOMPOUND);
        }
        catch(Exception e) {

            throw new NBTInitializeException("The nbt entity reflect raw initialize exception.", e);
        }
    }


    @Override
    public Object getHandleEntity(Entity entity) throws NBTException {

        try {

            return METHOD_GETHANDLEENTITY.invoke(entity);
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

            if(CLASS_ENTITYPLAYER.isInstance(nmsEntity)) {

                METHOD_READPLAYER.invoke(nmsEntity, nbtTagCompound);
            }
            else {

                METHOD_READENTITY.invoke(nmsEntity, nbtTagCompound);
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

                METHOD_WRITEPLAYER.invoke(nmsEntity, nbtTagCompound);
            }
            else {

                METHOD_WRITEENTITY.invoke(nmsEntity, nbtTagCompound);
            }
        }
        catch (Exception e) {

            throw new NBTException("The write entity nbt tag exception.");
        }
    }

    @Override
    public Entity spawnEntity(Object nbtTagCompound, World world) throws NBTException {

        Validate.notNull(world, "The target world object is null.");

        try {

            nbtTagCompound = NBTReflect.getHandle().cloneTag(nbtTagCompound);
            Object nmsWorld = METHOD_GETWORLDHANDLE.invoke(world);
            Object nmsEntity = METHOD_CREATEENTITY.invoke(null, nbtTagCompound, nmsWorld);

            if(nmsEntity == null) {

                return null;
            }
            METHOD_ADDENTITYTOWORLD.invoke(nmsWorld, nmsEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
            Entity entity = (Entity) METHOD_GETBUKKITENTITY.invoke(nmsEntity);
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
                Object nmsRiding = METHOD_CREATEENTITY.invoke(null, nbtTagCompound, nmsWorld);

                if(nmsRiding == null) {

                    break;
                }
                METHOD_ADDENTITYTOWORLD.invoke(nmsWorld, nmsRiding);
                Entity riding = (Entity) METHOD_GETBUKKITENTITY.invoke(nmsRiding);
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
