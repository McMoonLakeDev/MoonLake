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

import com.minecraft.moonlake.api.entity.AttributeType;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
import com.minecraft.moonlake.executor.Consumer;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import com.minecraft.moonlake.reflect.accessors.MethodAccessor;
import com.minecraft.moonlake.util.StringUtil;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <h1>EntityManager</h1>
 * 实体管理实现类
 *
 * @version 1.1
 * @author Month_Light
 */
public class EntityManager extends MoonLakeManager {

    private static volatile ConstructorAccessor<?> entityItemConstructor;
    private static volatile MethodAccessor entityItemSetCustomNameMethod;
    private static volatile MethodAccessor entityItemSetCustomNameVisibleMethod;

    static {
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
     * @see MinecraftReflection#getEntity(Entity)
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link MinecraftReflection#getEntity(Entity)}
     */
    @Deprecated
    public static Object asNMSEntity(Entity entity) { // TODO 2.0

        return MinecraftReflection.getEntity(entity);
    }

    /**
     * 获取 NMS 实体的 Bukkit 实体对象
     *
     * @param nmsEntity NMS 实体
     * @return Bukkit 实体
     * @throws IllegalArgumentException 如果 NMS 实体对象为 {@code null} 则抛出异常
     */
    public static Entity getBukkitEntity(Object nmsEntity) {

        return MinecraftReflection.getBukkitEntity(nmsEntity);
    }

    /**
     * 将指定 NMS 世界添加指定 NMS 实体对象
     *
     * @param nmsWorld NMS 世界
     * @param nmsEntity NMS 实体
     * @return 是否成功
     * @throws IllegalArgumentException 如果 NMS 世界对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果 NMS 实体对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link MinecraftReflection#addEntity(Object, Object)}
     */
    @Deprecated
    public static boolean addEntity(Object nmsWorld, Object nmsEntity) { // TODO 2.0

        return MinecraftReflection.addEntity(nmsWorld, nmsEntity);
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
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link MinecraftReflection#addEntity(Object, Object, CreatureSpawnEvent.SpawnReason)}
     * @see MinecraftReflection#addEntity(Object, Object, CreatureSpawnEvent.SpawnReason)
     */
    @Deprecated
    public static boolean addEntity(Object nmsWorld, Object nmsEntity, CreatureSpawnEvent.SpawnReason reason) { // TODO 2.0

        return MinecraftReflection.addEntity(nmsWorld, nmsEntity, reason);
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

        if(entityItemConstructor == null) {
            Class<?> worldClass = MinecraftReflection.getMinecraftWorldClass();
            Class<?> itemStackClass = MinecraftReflection.getMinecraftItemStackClass();
            Class<?> entityItemClass = MinecraftReflection.getMinecraftEntityItemClass();
            entityItemConstructor = Accessors.getConstructorAccessor(entityItemClass, worldClass, double.class, double.class, double.class, itemStackClass);
            entityItemSetCustomNameMethod = Accessors.getMethodAccessor(entityItemClass, "setCustomName", String.class);
            entityItemSetCustomNameVisibleMethod = Accessors.getMethodAccessor(entityItemClass, "setCustomNameVisible", boolean.class);
        }
        Object nmsItemStack = MinecraftReflection.asNMSCopy(itemStack);
        Object nmsWorld = MinecraftReflection.getWorldServer(location.getWorld());
        Object nmsEntityItem = entityItemConstructor.invoke(nmsWorld, location.getX(), location.getY(), location.getZ(), nmsItemStack);

        if(!StringUtil.isEmpty(customName))
            entityItemSetCustomNameMethod.invoke(nmsEntityItem, StringUtil.toColor(customName));
        entityItemSetCustomNameVisibleMethod.invoke(nmsEntityItem, customNameVisible);
        MinecraftReflection.addEntity(nmsWorld, nmsEntityItem, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return (Item) getBukkitEntity(nmsEntityItem);
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
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #setAttributeMovementSpeed(LivingEntity, double)}
     */
    @Deprecated
    public static void setMovementSpeed(Entity entity, double value) { // TODO 2.0
        if(!(entity instanceof LivingEntity))
            throw new IllegalStateException("The entity object not is LivingEntity instance.");
        setAttributeMovementSpeed((LivingEntity) entity, value);
    }

    /**
     * 设置指定实体的移动速度值
     *
     * @param livingEntity 实体
     * @param value 值
     * @see AttributeType#MOVEMENT_SPEED
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setAttributeMovementSpeed(LivingEntity livingEntity, double value) {
        setAttributeValue(livingEntity, AttributeType.MOVEMENT_SPEED, value);
    }

    /**
     * 获取指定实体的移动速度值
     *
     * @param entity 实体
     * @return 实体的移动速度值
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #getAttributeMovementSpeed(LivingEntity)}
     */
    @Deprecated
    public static double getMovementSpeed(Entity entity) { // TODO 2.0
        if(!(entity instanceof LivingEntity))
            throw new IllegalStateException("The entity object not is LivingEntity instance.");
        return getAttributeMovementSpeed((LivingEntity) entity);
    }

    /**
     * 获取指定实体的移动速度值
     *
     * @param livingEntity 实体
     * @return 实体的移动速度值
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static double getAttributeMovementSpeed(LivingEntity livingEntity) {
        return getAttributeValue(livingEntity, AttributeType.MOVEMENT_SPEED);
    }

    /**
     * 设置指定实体的击退抗性值
     *
     * @param entity 实体
     * @param value 值
     * @see AttributeType#KNOCK_BACK_RESISTANCE
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #setAttributeKnockBackResistance(LivingEntity, double)}
     */
    @Deprecated
    public static void setKnockBackResistance(Entity entity, double value) { // TODO 2.0
        if(!(entity instanceof LivingEntity))
            throw new IllegalStateException("The entity object not is LivingEntity instance.");
        setAttributeKnockBackResistance((LivingEntity) entity, value);
    }

    /**
     * 设置指定实体的击退抗性值
     *
     * @param livingEntity 实体
     * @param value 值
     * @see AttributeType#KNOCK_BACK_RESISTANCE
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setAttributeKnockBackResistance(LivingEntity livingEntity, double value) {
        setAttributeValue(livingEntity, AttributeType.KNOCK_BACK_RESISTANCE, value);
    }

    /**
     * 获取指定实体的击退抗性值
     *
     * @param entity 实体
     * @return 实体的击退抗性值
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #getAttributeKnockBackResistance(LivingEntity)}
     */
    @Deprecated
    public static double getKnockBackResistance(Entity entity) { // TODO 2.0
        if(!(entity instanceof LivingEntity))
            throw new IllegalStateException("The entity object not is LivingEntity instance.");
        return getAttributeKnockBackResistance((LivingEntity) entity);
    }

    /**
     * 获取指定实体的击退抗性值
     *
     * @param livingEntity 实体
     * @return 实体的击退抗性值
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static double getAttributeKnockBackResistance(LivingEntity livingEntity) {
        return getAttributeValue(livingEntity, AttributeType.KNOCK_BACK_RESISTANCE);
    }

    /**
     * 设置指定实体的攻击伤害值
     *
     * @param entity 实体
     * @param value 值
     * @see AttributeType#ATTACK_DAMAGE
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #setAttributeAttackDamage(LivingEntity, double)}
     */
    @Deprecated
    public static void setDamage(Entity entity, double value) { // TODO 2.0
        if(!(entity instanceof LivingEntity))
            throw new IllegalStateException("The entity object not is LivingEntity instance.");
        setAttributeAttackDamage((LivingEntity) entity, value);
    }

    /**
     * 设置指定实体的攻击伤害值
     *
     * @param livingEntity 实体
     * @param value 值
     * @see AttributeType#ATTACK_DAMAGE
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setAttributeAttackDamage(LivingEntity livingEntity, double value) {
        setAttributeValue(livingEntity, AttributeType.ATTACK_DAMAGE, value);
    }

    /**
     * 获取指定实体的攻击伤害值
     *
     * @param entity 实体
     * @return 实体的攻击伤害值
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #getAttributeAttackDamage(LivingEntity)}
     */
    @Deprecated
    public static double getDamage(Entity entity) { // TODO 2.0
        if(!(entity instanceof LivingEntity))
            throw new IllegalStateException("The entity object not is LivingEntity instance.");
        return getAttributeAttackDamage((LivingEntity) entity);
    }

    /**
     * 获取指定实体的攻击伤害值
     *
     * @param livingEntity 实体
     * @return 实体的攻击伤害值
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static double getAttributeAttackDamage(LivingEntity livingEntity) {
        return getAttributeValue(livingEntity, AttributeType.ATTACK_DAMAGE);
    }

    /**
     * 设置指定实体的血量上限值
     *
     * @param entity 实体
     * @param value 值
     * @see AttributeType#MAX_HEALTH
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #setAttributeMaxHealth(LivingEntity, double)}
     */
    @Deprecated
    public static void setMaxHealth(Entity entity, double value) { // TODO 2.0
        if(!(entity instanceof LivingEntity))
            throw new IllegalStateException("The entity object not is LivingEntity instance.");
        setAttributeMaxHealth((LivingEntity) entity, value);
    }

    /**
     * 设置指定实体的血量上限值
     *
     * @param livingEntity 实体
     * @param value 值
     * @see AttributeType#MAX_HEALTH
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setAttributeMaxHealth(LivingEntity livingEntity, double value) {
        setAttributeMaxHealth(livingEntity, value, true);
    }

    /**
     * 设置指定实体的血量上限值
     *
     * @param entity 实体
     * @param value 值
     * @param regain 是否恢复
     * @see AttributeType#MAX_HEALTH
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #setAttributeMaxHealth(LivingEntity, double, boolean)}
     */
    @Deprecated
    public static void setMaxHealth(Entity entity, double value, boolean regain) { // TODO 2.0
        if(!(entity instanceof LivingEntity))
            throw new IllegalStateException("The entity object not is LivingEntity instance.");
        setAttributeMaxHealth((LivingEntity) entity, value, regain);
    }

    /**
     * 设置指定实体的血量上限值
     *
     * @param livingEntity 实体
     * @param value 值
     * @param regain 是否恢复
     * @see AttributeType#MAX_HEALTH
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setAttributeMaxHealth(LivingEntity livingEntity, double value, boolean regain) {
        setAttributeValue(livingEntity, AttributeType.MAX_HEALTH, value);
        if(regain)
            livingEntity.setHealth(value);
    }

    /**
     * 设置指定实体的追踪范围
     *
     * @param entity 实体
     * @param value 值
     * @see AttributeType#FOLLOW_RANGE
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #setAttributeFollowRange(LivingEntity, double)}
     */
    @Deprecated
    public static void setFollowRange(Entity entity, double value) { // TODO 2.0
        if(!(entity instanceof LivingEntity))
            throw new IllegalStateException("The entity object not is LivingEntity instance.");
        setAttributeFollowRange((LivingEntity) entity, value);
    }

    /**
     * 设置指定实体的追踪范围
     *
     * @param livingEntity 实体
     * @param value 值
     * @see AttributeType#FOLLOW_RANGE
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setAttributeFollowRange(LivingEntity livingEntity, double value) {
        setAttributeValue(livingEntity, AttributeType.FOLLOW_RANGE, value);
    }

    /**
     * 获取指定实体的追踪范围
     *
     * @param entity 实体
     * @return 实体的追踪范围
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #getAttributeFollowRange(LivingEntity)}
     */
    @Deprecated
    public static double getFollowRange(Entity entity) { // TODO 2.0
        if(!(entity instanceof LivingEntity))
            throw new IllegalStateException("The entity object not is LivingEntity instance.");
        return getAttributeFollowRange((LivingEntity) entity);
    }

    /**
     * 获取指定实体的追踪范围
     *
     * @param livingEntity 实体
     * @return 实体的追踪范围
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static double getAttributeFollowRange(LivingEntity livingEntity) {
        return getAttributeValue(livingEntity, AttributeType.FOLLOW_RANGE);
    }

    /**
     * 设置指定实体的攻击速度
     *
     * @param entity 实体
     * @param value 值
     * @see AttributeType#FOLLOW_RANGE
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #setAttributeAttackSpeed(LivingEntity, double)}
     */
    @Deprecated
    public static void setAttackSpeed(Entity entity, double value) { // TODO 2.0
        if(!(entity instanceof LivingEntity))
            throw new IllegalStateException("The entity object not is LivingEntity instance.");
        setAttributeAttackSpeed((LivingEntity) entity, value);
    }

    /**
     * 设置指定实体的攻击速度
     *
     * @param livingEntity 实体
     * @param value 值
     * @see AttributeType#FOLLOW_RANGE
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setAttributeAttackSpeed(LivingEntity livingEntity, double value) {
        setAttributeValue(livingEntity, AttributeType.ATTACK_SPEED, value);
    }

    /**
     * 获取指定实体的攻击速度
     *
     * @param entity 实体
     * @return 实体的攻击速度
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #getAttributeAttackSpeed(LivingEntity)}
     */
    @Deprecated
    public static double getAttackSpeed(Entity entity) { // TODO 2.0
        if(!(entity instanceof LivingEntity))
            throw new IllegalStateException("The entity object not is LivingEntity instance.");
        return getAttributeAttackSpeed((LivingEntity) entity);
    }

    /**
     * 获取指定实体的攻击速度
     *
     * @param livingEntity 实体
     * @return 实体的攻击速度
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static double getAttributeAttackSpeed(LivingEntity livingEntity) {
        return getAttributeValue(livingEntity, AttributeType.ATTACK_SPEED);
    }

    /**
     * 设置指定实体的护甲
     *
     * @param entity 实体
     * @param value 值
     * @see AttributeType#ARMOR
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #setAttributeArmor(LivingEntity, double)}
     */
    @Deprecated
    public static void setArmor(Entity entity, double value) { // TODO 2.0
        if(!(entity instanceof LivingEntity))
            throw new IllegalStateException("The entity object not is LivingEntity instance.");
        setAttributeArmor((LivingEntity) entity, value);
    }

    /**
     * 设置指定实体的护甲
     *
     * @param livingEntity 实体
     * @param value 值
     * @see AttributeType#ARMOR
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setAttributeArmor(LivingEntity livingEntity, double value) {
        setAttributeValue(livingEntity, AttributeType.ARMOR, value);
    }

    /**
     * 获取指定实体的护甲
     *
     * @param entity 实体
     * @return 实体的护甲
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #getAttributeArmor(LivingEntity)}
     */
    @Deprecated
    public static double getArmor(Entity entity) { // TODO 2.0
        if(!(entity instanceof LivingEntity))
            throw new IllegalStateException("The entity object not is LivingEntity instance.");
        return getAttributeArmor((LivingEntity) entity);
    }

    /**
     * 获取指定实体的护甲
     *
     * @param livingEntity 实体
     * @return 实体的护甲
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static double getAttributeArmor(LivingEntity livingEntity) {
        return getAttributeValue(livingEntity, AttributeType.ARMOR);
    }

    /**
     * 设置指定实体的护甲韧性
     *
     * @param entity 实体
     * @param value 值
     * @see AttributeType#ARMOR_TOUGHNESS
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #setAttributeArmorToughness(LivingEntity, double)}
     */
    @Deprecated
    public static void setArmorToughness(Entity entity, double value) { // TODO 2.0
        if(!(entity instanceof LivingEntity))
            throw new IllegalStateException("The entity object not is LivingEntity instance.");
        setAttributeArmorToughness((LivingEntity) entity, value);
    }

    /**
     * 设置指定实体的护甲韧性
     *
     * @param livingEntity 实体
     * @param value 值
     * @see AttributeType#ARMOR_TOUGHNESS
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setAttributeArmorToughness(LivingEntity livingEntity, double value) {
        setAttributeValue(livingEntity, AttributeType.ARMOR_TOUGHNESS, value);
    }

    /**
     * 获取指定实体的护甲韧性
     *
     * @param entity 实体
     * @return 实体的护甲韧性
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #getAttributeArmorToughness(LivingEntity)}
     */
    @Deprecated
    public static double getArmorToughness(Entity entity) { // TODO 2.0
        if(!(entity instanceof LivingEntity))
            throw new IllegalStateException("The entity object not is LivingEntity instance.");
        return getAttributeArmorToughness((LivingEntity) entity);
    }

    /**
     * 获取指定实体的护甲韧性
     *
     * @param livingEntity 实体
     * @return 实体的护甲韧性
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static double getAttributeArmorToughness(LivingEntity livingEntity) {
        return getAttributeValue(livingEntity, AttributeType.ARMOR_TOUGHNESS);
    }

    /**
     * 设置指定实体的幸运
     *
     * @param entity 实体
     * @param value 值
     * @see AttributeType#LUCK
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #setAttributeLuck(LivingEntity, double)}
     */
    @Deprecated
    public static void setLuck(Entity entity, double value) { // TODO 2.0
        if(!(entity instanceof LivingEntity))
            throw new IllegalStateException("The entity object not is LivingEntity instance.");
        setAttributeLuck((LivingEntity) entity, value);
    }

    /**
     * 设置指定实体的幸运
     *
     * @param livingEntity 实体
     * @param value 值
     * @see AttributeType#LUCK
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setAttributeLuck(LivingEntity livingEntity, double value) {
        setAttributeValue(livingEntity, AttributeType.LUCK, value);
    }

    /**
     * 获取指定实体的幸运
     *
     * @param entity 实体
     * @return 实体的幸运
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #getAttributeLuck(LivingEntity)}
     */
    @Deprecated
    public static double getLuck(Entity entity) { // TODO 2.0
        if(!(entity instanceof LivingEntity))
            throw new IllegalStateException("The entity object not is LivingEntity instance.");
        return getAttributeLuck((LivingEntity) entity);
    }

    /**
     * 获取指定实体的幸运
     *
     * @param livingEntity 实体
     * @return 实体的幸运
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static double getAttributeLuck(LivingEntity livingEntity) {
        return getAttributeValue(livingEntity, AttributeType.LUCK);
    }

    /**
     * 设置指定实体的飞行速度
     *
     * @param livingEntity 实体
     * @param value 值
     * @see AttributeType#FLYING_SPEED
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setAttributeFlyingSpeed(LivingEntity livingEntity, double value) {
        setAttributeValue(livingEntity, AttributeType.FLYING_SPEED, value);
    }

    /**
     * 获取指定实体的飞行速度
     *
     * @param livingEntity 实体
     * @return 实体的飞行速度
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static double getAttributeFlyingSpeed(LivingEntity livingEntity) {
        return getAttributeValue(livingEntity, AttributeType.FLYING_SPEED);
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
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #setAttributeValue(LivingEntity, AttributeType, double)}
     */
    @Deprecated
    public static void setAttribute(Entity entity, AttributeType type, double value) { // TODO 2.0

        if(!(entity instanceof LivingEntity))
            throw new IllegalStateException("The entity object not is LivingEntity instance.");
        setAttributeValue((LivingEntity) entity, type, value);
    }

    /**
     * 设置指定实体的指定属性类型的值
     *
     * @param livingEntity 实体
     * @param type 属性类型
     * @param value 值
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果属性类型对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果属性类型的值小于或大于限制则抛出异常
     * @throws IllegalBukkitVersionException 如果属性类型不支持服务器版本则抛出异常
     */
    public static void setAttributeValue(LivingEntity livingEntity, AttributeType type, double value) {
        MinecraftReflection.setAttributeValue(livingEntity, type, value);
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
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #getAttributeValue(LivingEntity, AttributeType)}
     */
    @Deprecated
    public static double getAttribute(Entity entity, AttributeType type) { // TODO 2.0

        if(!(entity instanceof LivingEntity))
            throw new IllegalStateException("The entity object not is LivingEntity instance.");
        return getAttributeValue((LivingEntity) entity, type);
    }

    /**
     * 获取指定实体的指定属性类型的值
     *
     * @param livingEntity 实体
     * @param type 属性类型
     * @return 属性类型的值 异常返回 -1
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果属性类型对象为 {@code null} 则抛出异常
     * @throws IllegalBukkitVersionException 如果属性类型不支持服务器版本则抛出异常
     */
    public static double getAttributeValue(LivingEntity livingEntity, AttributeType type) {
        return MinecraftReflection.getAttributeValue(livingEntity, type);
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
