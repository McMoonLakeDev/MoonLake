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
 
 
package com.minecraft.moonlake.manager;

import com.google.common.collect.Sets;
import com.minecraft.moonlake.api.entity.AttributeType;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.executor.Consumer;
import com.minecraft.moonlake.reflect.Reflect;
import com.minecraft.moonlake.util.StringUtil;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>EntityManager</h1>
 * 实体管理实现类
 *
 * @version 1.0
 * @author Month_Light
 */
public class EntityManager extends MoonLakeManager {

    private final static Class<?> CLASS_WORLD;
    private final static Class<?> CLASS_CRAFTWORLD;
    private final static Class<?> CLASS_CRAFTENTITY;
    private final static Class<?> CLASS_ENTITYINSENTIENT;
    private final static Class<?> CLASS_PATHFINDERGOALSELECTOR;
    private final static Class<?> CLASS_UNSAFELIST;
    private final static Class<?> CLASS_IATTRIBUTE;
    private final static Class<?> CLASS_ENTITY;
    private final static Class<?> CLASS_ENTITYITEM;
    private final static Class<?> CLASS_ENTITYLIVING;
    private final static Class<?> CLASS_ATTRIBUTEINSTANCE;
    private final static Class<?> CLASS_GENERICATTRIBUTES;
    private final static Method METHOD_GETENTITYHANDLE;
    private final static Method METHOD_GETWORLDHANDLE;
    private final static Method METHOD_GETATTRIBUTEINSTANCE;
    private final static Method METHOD_GETVALUE;
    private final static Method METHOD_SETVALUE;
    private final static Method METHOD_SETCUSTOMNAME;
    private final static Method METHOD_SETCUSTOMNAMEVISIBLE;
    private final static Method METHOD_ADDENTITY;
    private final static Method METHOD_GETBUKKITENTITY;
    private final static Field FIELD_GOALSELECTOR;
    private final static Field FIELD_TARGETSELECTOR;
    private final static Field FIELD_PATHFINDERGOALSELECTOR_B;
    private final static Field FIELD_PATHFINDERGOALSELECTOR_C;

    static {

        try {

            CLASS_WORLD = PackageType.MINECRAFT_SERVER.getClass("World");
            CLASS_CRAFTWORLD = PackageType.CRAFTBUKKIT.getClass("CraftWorld");
            CLASS_CRAFTENTITY = PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftEntity");
            CLASS_ENTITYINSENTIENT = PackageType.MINECRAFT_SERVER.getClass("EntityInsentient");
            CLASS_PATHFINDERGOALSELECTOR = PackageType.MINECRAFT_SERVER.getClass("PathfinderGoalSelector");
            CLASS_UNSAFELIST = PackageType.CRAFTBUKKIT_UTIL.getClass("UnsafeList");
            CLASS_IATTRIBUTE = PackageType.MINECRAFT_SERVER.getClass("IAttribute");
            CLASS_ENTITY = PackageType.MINECRAFT_SERVER.getClass("Entity");
            CLASS_ENTITYITEM = PackageType.MINECRAFT_SERVER.getClass("EntityItem");
            CLASS_ENTITYLIVING = PackageType.MINECRAFT_SERVER.getClass("EntityLiving");
            CLASS_ATTRIBUTEINSTANCE = PackageType.MINECRAFT_SERVER.getClass("AttributeInstance");
            CLASS_GENERICATTRIBUTES = PackageType.MINECRAFT_SERVER.getClass("GenericAttributes");
            METHOD_GETENTITYHANDLE = getMethod(CLASS_CRAFTENTITY, "getHandle");
            METHOD_GETWORLDHANDLE = getMethod(CLASS_CRAFTWORLD, "getHandle");
            METHOD_GETATTRIBUTEINSTANCE = getMethod(CLASS_ENTITYLIVING, "getAttributeInstance", CLASS_IATTRIBUTE);
            METHOD_GETVALUE = getMethod(CLASS_ATTRIBUTEINSTANCE, "getValue");
            METHOD_SETVALUE = getMethod(CLASS_ATTRIBUTEINSTANCE, "setValue", double.class);
            METHOD_SETCUSTOMNAME = getMethod(CLASS_ENTITYITEM, "setCustomName", String.class);
            METHOD_SETCUSTOMNAMEVISIBLE = getMethod(CLASS_ENTITYITEM, "setCustomNameVisible", boolean.class);
            METHOD_ADDENTITY = getMethod(CLASS_WORLD, "addEntity", CLASS_ENTITY, CreatureSpawnEvent.SpawnReason.class);
            METHOD_GETBUKKITENTITY = getMethod(CLASS_ENTITY, "getBukkitEntity");
            FIELD_GOALSELECTOR = getField(CLASS_ENTITYINSENTIENT, true, "goalSelector");
            FIELD_TARGETSELECTOR = getField(CLASS_ENTITYINSENTIENT, true, "targetSelector");
            FIELD_PATHFINDERGOALSELECTOR_B = getField(CLASS_PATHFINDERGOALSELECTOR, true, "b");
            FIELD_PATHFINDERGOALSELECTOR_C = getField(CLASS_PATHFINDERGOALSELECTOR, true, "c");
        }
        catch (Exception e) {

            throw new IllegalBukkitVersionException("The entity manager reflect raw exception.", e);
        }
    }

    /**
     * 实体管理实现类构造函数
     */
    private EntityManager() {

    }

    /**
     * 将 Bukkit 实体对象转换到 NMS 实体对象
     *
     * @param entity Bukkit 实体
     * @return NMS 实体
     * @throws IllegalArgumentException 如果 Bukkit 实体对象为 {@code null} 则抛出异常
     */
    public static Object asNMSEntity(Entity entity) {

        Validate.notNull(entity, "The entity object is null.");

        try {

            return METHOD_GETENTITYHANDLE.invoke(entity);
        }
        catch (Exception e) {

            throw new MoonLakeException("The get entity nms entity exception.", e);
        }
    }

    /**
     * 获取 NMS 实体的 Bukkit 实体对象
     *
     * @param nmsEntity NMS 实体
     * @return Bukkit 实体
     * @throws IllegalArgumentException 如果 NMS 实体对象为 {@code null} 则抛出异常
     */
    public static Entity getBukkitEntity(Object nmsEntity) {

        Validate.notNull(nmsEntity, "The nms entity object is null.");

        try {

            return (Entity) METHOD_GETBUKKITENTITY.invoke(nmsEntity);
        }
        catch (Exception e) {

            throw new MoonLakeException("The get nms entity bukkit entity exception.", e);
        }
    }

    /**
     * 将指定 NMS 世界添加指定 NMS 实体对象
     *
     * @param nmsWorld NMS 世界
     * @param nmsEntity NMS 实体
     * @return 是否成功
     * @throws IllegalArgumentException 如果 NMS 世界对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果 NMS 实体对象为 {@code null} 则抛出异常
     */
    public static boolean addEntity(Object nmsWorld, Object nmsEntity) {

        return addEntity(nmsWorld, nmsEntity, null);
    }

    /**
     * 将指定 NMS 世界添加指定 NMS 实体对象
     *
     * @param nmsWorld NMS 世界
     * @param nmsEntity NMS 实体
     * @param reason 实体生成原因
     * @return 是否成功
     * @throws IllegalArgumentException 如果 NMS 世界对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果 NMS 实体对象为 {@code null} 则抛出异常
     */
    public static boolean addEntity(Object nmsWorld, Object nmsEntity, CreatureSpawnEvent.SpawnReason reason) {

        Validate.notNull(nmsWorld, "The nms world object is null.");
        Validate.notNull(nmsEntity, "The nms entity object is null.");

        try {

            if(reason == null) reason = CreatureSpawnEvent.SpawnReason.DEFAULT;

            return (boolean) METHOD_ADDENTITY.invoke(nmsWorld, nmsEntity, reason);
        }
        catch (Exception e) {

            throw new MoonLakeException("The add entity exception.", e);
        }
    }

    /**
     * 在指定位置掉落物品栈实体
     *
     * @param location 位置
     * @param itemStack 物品栈
     * @return 物品栈实体对象
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    public static Item dropItem(Location location, ItemStack itemStack) {

        return dropItem(location, itemStack, null);
    }

    /**
     * 在指定位置掉落物品栈实体
     *
     * @param location 位置
     * @param itemStack 物品栈
     * @param customName 自定义名称
     * @return 物品栈实体对象
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    public static Item dropItem(Location location, ItemStack itemStack, String customName) {

        return dropItem(location, itemStack, customName, false);
    }

    /**
     * 在指定位置掉落物品栈实体
     *
     * @param location 位置
     * @param itemStack 物品栈
     * @param customName 自定义名称
     * @param customNameVisible 自定义名称是否可见
     * @return 物品栈实体对象
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    public static Item dropItem(Location location, ItemStack itemStack, String customName, boolean customNameVisible) {

        Validate.notNull(location, "The location object is null.");
        Validate.notNull(itemStack, "The itemstack object is null.");

        try {

            Object nmsItemStack = ItemManager.asNMSCopy(itemStack);
            Object nmsWorld = METHOD_GETWORLDHANDLE.invoke(location.getWorld());
            Object nmsEntityItem = instantiateObject(CLASS_ENTITYITEM, nmsWorld, location.getX(), location.getY(), location.getZ(), nmsItemStack);

            if(customName != null) {

                METHOD_SETCUSTOMNAME.invoke(nmsEntityItem, StringUtil.toColor(customName));
            }
            METHOD_SETCUSTOMNAMEVISIBLE.invoke(nmsEntityItem, customNameVisible);

            addEntity(nmsWorld, nmsEntityItem, CreatureSpawnEvent.SpawnReason.CUSTOM);

            return (Item) getBukkitEntity(nmsEntityItem);
        }
        catch (Exception e) {

            throw new MoonLakeException("The drop itemstack entity exception.", e);
        }
    }

    /**
     * 清除实体的路径发现者 AI
     *
     * @param entity 实体
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void removePathFinders(Entity entity) {

        Validate.notNull(entity, "The entity object is null.");

        try {

            Object nmsEntity = METHOD_GETENTITYHANDLE.invoke(entity);

            if(CLASS_ENTITYINSENTIENT.isInstance(nmsEntity)) {

                Object nmsGoalSelector = FIELD_GOALSELECTOR.get(nmsEntity);
                Object nmsTargetSelector = FIELD_TARGETSELECTOR.get(nmsEntity);

                if(getServerVersionNumber() <= 8) {

                    FIELD_PATHFINDERGOALSELECTOR_B.set(nmsGoalSelector, instantiateObject(CLASS_UNSAFELIST));
                    FIELD_PATHFINDERGOALSELECTOR_C.set(nmsGoalSelector, instantiateObject(CLASS_UNSAFELIST));
                    FIELD_PATHFINDERGOALSELECTOR_B.set(nmsTargetSelector, instantiateObject(CLASS_UNSAFELIST));
                    FIELD_PATHFINDERGOALSELECTOR_C.set(nmsTargetSelector, instantiateObject(CLASS_UNSAFELIST));
                }
                else {

                    FIELD_PATHFINDERGOALSELECTOR_B.set(nmsGoalSelector, Sets.newLinkedHashSet());
                    FIELD_PATHFINDERGOALSELECTOR_C.set(nmsGoalSelector, Sets.newLinkedHashSet());
                    FIELD_PATHFINDERGOALSELECTOR_B.set(nmsTargetSelector, Sets.newLinkedHashSet());
                    FIELD_PATHFINDERGOALSELECTOR_C.set(nmsTargetSelector, Sets.newLinkedHashSet());
                }
            }
        }
        catch (Exception e) {

            throw new MoonLakeException("The remove entity path finders ai exception.", e);
        }
    }

    /**
     * 获取指定位置的半径内的实体
     *
     * @param location 位置
     * @param radius 半径
     * @return 实体集合
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     */
    public static List<LivingEntity> getEntityInRadius(Location location, double radius) {

        return getEntityInRadius(location, radius, new HashSet<>());
    }

    /**
     * 获取指定位置的半径内的实体
     *
     * @param location 位置
     * @param radius 半径
     * @param owner 无视主人
     * @return 实体集合
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果主人对象为 {@code null} 则抛出异常
     */
    public static List<LivingEntity> getEntityInRadius(Location location, double radius, Player owner) {

        Validate.notNull(owner, "The owner object is null.");

        List<LivingEntity> entityList = getEntityInRadius(location, radius);

        return getEntityInRadius0(entityList, owner);
    }

    /**
     * 获取指定位置的半径内的实体
     *
     * @param location 位置
     * @param radius 半径
     * @param owner 无视主人
     * @param ignoreEntity 无视的实体
     * @return 实体集合
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果主人对象为 {@code null} 则抛出异常
     */
    public static List<LivingEntity> getEntityInRadius(Location location, double radius, Player owner, Set<Class<? extends LivingEntity>> ignoreEntity) {

        Validate.notNull(owner, "The owner object is null.");

        List<LivingEntity> entityList = getEntityInRadius(location, radius, ignoreEntity);

        return getEntityInRadius0(entityList, owner);
    }

    /**
     * 获取指定位置的半径内的实体
     *
     * @param location 位置
     * @param radius 半径
     * @param owner 无视主人
     * @return 实体集合
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果主人对象为 {@code null} 则抛出异常
     */
    public static List<LivingEntity> getEntityInRadius(Location location, double radius, MoonLakePlayer owner) {

        Validate.notNull(owner, "The owner object is null.");

        List<LivingEntity> entityList = getEntityInRadius(location, radius);

        return getEntityInRadius0(entityList, owner.getBukkitPlayer());
    }

    /**
     * 获取指定位置的半径内的实体
     *
     * @param location 位置
     * @param radius 半径
     * @param owner 无视主人
     * @param ignoreEntity 无视的实体
     * @return 实体集合
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果主人对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果忽略实体对象为 {@code null} 则抛出异常
     */
    public static List<LivingEntity> getEntityInRadius(Location location, double radius, MoonLakePlayer owner, Set<Class<? extends LivingEntity>> ignoreEntity) {

        Validate.notNull(owner, "The owner object is null.");

        List<LivingEntity> entityList = getEntityInRadius(location, radius, ignoreEntity);

        return getEntityInRadius0(entityList, owner.getBukkitPlayer());
    }

    private static List<LivingEntity> getEntityInRadius0(List<LivingEntity> entityList, Player owner) {

        for(int i = 0; i < entityList.size(); i++) {

            LivingEntity entity = entityList.get(i);

            if(entity instanceof Player && ((Player) entity).equals(owner)) {

                entityList.remove(entity);

                i--;
            }
        }
        return entityList;
    }

    /**
     * 获取指定位置的半径内的实体
     *
     * @param location 位置
     * @param radius 半径
     * @param ignoreEntity 无视的实体
     * @return 实体集合
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果忽略实体对象为 {@code null} 则抛出异常
     */
    public static List<LivingEntity> getEntityInRadius(Location location, double radius, Set<Class<? extends LivingEntity>> ignoreEntity) {

        Validate.notNull(location, "The location object is null.");
        Validate.notNull(ignoreEntity, "The ignoreEntity set object is null.");

        List<LivingEntity> entityList = new ArrayList<>();

        for(Entity entity : location.getWorld().getNearbyEntities(location, radius, radius, radius)) {

            if(entity != null && !entity.isDead() && entity instanceof LivingEntity && !ignoreEntity.contains(entity.getClass())) {

                entityList.add((LivingEntity) entity);
            }
        }
        return entityList;
    }

    /**
     * 设置指定实体的移动速度值
     *
     * @param entity 实体
     * @param value 值
     * @see AttributeType#MOVEMENT_SPEED
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setMovementSpeed(Entity entity, double value) {

        setAttribute(entity, AttributeType.MOVEMENT_SPEED, value);
    }

    /**
     * 获取指定实体的移动速度值
     *
     * @param entity 实体
     * @return 实体的移动速度值
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static double getMovementSpeed(Entity entity) {

        return getAttribute(entity, AttributeType.MOVEMENT_SPEED);
    }

    /**
     * 设置指定实体的击退抗性值
     *
     * @param entity 实体
     * @param value 值
     * @see AttributeType#KNOCK_BACK_RESISTANCE
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setKnockBackResistance(Entity entity, double value) {

        setAttribute(entity, AttributeType.KNOCK_BACK_RESISTANCE, value);
    }

    /**
     * 获取指定实体的击退抗性值
     *
     * @param entity 实体
     * @return 实体的击退抗性值
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static double getKnockBackResistance(Entity entity) {

        return getAttribute(entity, AttributeType.KNOCK_BACK_RESISTANCE);
    }

    /**
     * 设置指定实体的攻击伤害值
     *
     * @param entity 实体
     * @param value 值
     * @see AttributeType#ATTACK_DAMAGE
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setDamage(Entity entity, double value) {

        setAttribute(entity, AttributeType.ATTACK_DAMAGE, value);
    }

    /**
     * 获取指定实体的攻击伤害值
     *
     * @param entity 实体
     * @return 实体的攻击伤害值
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static double getDamage(Entity entity) {

        return getAttribute(entity, AttributeType.ATTACK_DAMAGE);
    }

    /**
     * 设置指定实体的血量上限值
     *
     * @param entity 实体
     * @param value 值
     * @see AttributeType#MAX_HEALTH
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setMaxHealth(Entity entity, double value) {

        setMaxHealth(entity, value, true);
    }

    /**
     * 设置指定实体的血量上限值
     *
     * @param entity 实体
     * @param value 值
     * @param regain 是否恢复
     * @see AttributeType#MAX_HEALTH
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setMaxHealth(Entity entity, double value, boolean regain) {

        setAttribute(entity, AttributeType.MAX_HEALTH, value);

        if(regain && entity instanceof LivingEntity) {

            ((LivingEntity)entity).setHealth(value);
        }
    }

    /**
     * 设置指定实体的追踪范围
     *
     * @param entity 实体
     * @param value 值
     * @see AttributeType#FOLLOW_RANGE
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setFollowRange(Entity entity, double value) {

        setAttribute(entity, AttributeType.FOLLOW_RANGE, value);
    }

    /**
     * 获取指定实体的追踪范围
     *
     * @param entity 实体
     * @return 实体的追踪范围
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static double getFollowRange(Entity entity) {

        return getAttribute(entity, AttributeType.FOLLOW_RANGE);
    }

    /**
     * 设置指定实体的攻击速度
     *
     * @param entity 实体
     * @param value 值
     * @see AttributeType#FOLLOW_RANGE
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setAttackSpeed(Entity entity, double value) {

        setAttribute(entity, AttributeType.ATTACK_SPEED, value);
    }

    /**
     * 获取指定实体的攻击速度
     *
     * @param entity 实体
     * @return 实体的攻击速度
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static double getAttackSpeed(Entity entity) {

        return getAttribute(entity, AttributeType.ATTACK_SPEED);
    }

    /**
     * 设置指定实体的护甲
     *
     * @param entity 实体
     * @param value 值
     * @see AttributeType#ARMOR
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setArmor(Entity entity, double value) {

        setAttribute(entity, AttributeType.ARMOR, value);
    }

    /**
     * 获取指定实体的护甲
     *
     * @param entity 实体
     * @return 实体的护甲
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static double getArmor(Entity entity) {

        return getAttribute(entity, AttributeType.ARMOR);
    }

    /**
     * 设置指定实体的护甲韧性
     *
     * @param entity 实体
     * @param value 值
     * @see AttributeType#ARMOR_TOUGHNESS
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setArmorToughness(Entity entity, double value) {

        setAttribute(entity, AttributeType.ARMOR_TOUGHNESS, value);
    }

    /**
     * 获取指定实体的护甲韧性
     *
     * @param entity 实体
     * @return 实体的护甲韧性
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static double getArmorToughness(Entity entity) {

        return getAttribute(entity, AttributeType.ARMOR_TOUGHNESS);
    }

    /**
     * 设置指定实体的幸运
     *
     * @param entity 实体
     * @param value 值
     * @see AttributeType#LUCK
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setLuck(Entity entity, double value) {

        setAttribute(entity, AttributeType.LUCK, value);
    }

    /**
     * 获取指定实体的幸运
     *
     * @param entity 实体
     * @return 实体的幸运
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static double getLuck(Entity entity) {

        return getAttribute(entity, AttributeType.LUCK);
    }

    /**
     * 设置指定实体的指定属性类型的值
     *
     * @param entity 实体
     * @param type 属性类型
     * @param value 值
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果属性类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果属性类型的值小于或大于限制则抛出异常
     * @throws IllegalBukkitVersionException 如果属性类型不支持服务器版本则抛出异常
     */
    public static void setAttribute(Entity entity, AttributeType type, double value) {

        Validate.notNull(entity, "The entity object is null.");
        Validate.notNull(type, "The attribute type object is null.");

        type.isSupported();

        try {

            Object nmsEntity = asNMSEntity(entity);

            Field FIELD = Reflect.getField(CLASS_GENERICATTRIBUTES, true, type.getField());

            METHOD_SETVALUE.invoke(METHOD_GETATTRIBUTEINSTANCE.invoke(nmsEntity, FIELD.get(null)), type.getSafeValue(value));
        }
        catch (Exception e) {

            throw new MoonLakeException("The set entity attribute exception.", e);
        }
    }

    /**
     * 获取指定实体的指定属性类型的值
     *
     * @param entity 实体
     * @param type 属性类型
     * @return 属性类型的值 异常返回 -1
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果属性类型对象为 {@code null} 则抛出异常
     * @throws IllegalBukkitVersionException 如果属性类型不支持服务器版本则抛出异常
     */
    public static double getAttribute(Entity entity, AttributeType type) {

        Validate.notNull(entity, "The entity object is null.");
        Validate.notNull(type, "The attribute type object is null.");

        type.isSupported();

        try {

            Object nmsEntity = asNMSEntity(entity);

            Field FIELD = Reflect.getField(CLASS_GENERICATTRIBUTES, true, type.getField());

            return (Double) METHOD_GETVALUE.invoke(METHOD_GETATTRIBUTEINSTANCE.invoke(nmsEntity, FIELD.get(null)));
        }
        catch (Exception e) {

            throw new MoonLakeException("The get entity attribute exception.", e);
        }
    }

    /**
     * 将指定实体类生成到指定位置
     *
     * @param location 位置
     * @param entityClass 实体类
     * @param <T> 实体类
     * @return 实体对象
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果实体类不能生成则抛出异常
     */
    public static <T extends Entity> T spawnEntity(Location location, Class<T> entityClass) {

        return Validate.checkNotNull(location).getWorld().spawn(location, entityClass);
    }

    /**
     * 将指定实体类生成到指定位置
     *
     * @param location 位置
     * @param entityClass 实体类
     * @param consumer 消费者
     * @param <T> 实体类
     * @return 实体对象
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果实体类不能生成则抛出异常
     */
    public static <T extends Entity> T spawnEntity(Location location, Class<T> entityClass, Consumer<T> consumer) {

        T t = spawnEntity(location, entityClass);

        if(consumer != null)
            consumer.accept(t);

        return t;
    }
}
