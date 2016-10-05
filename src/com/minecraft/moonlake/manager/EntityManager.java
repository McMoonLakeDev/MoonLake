package com.minecraft.moonlake.manager;

import com.google.common.collect.Sets;
import com.minecraft.moonlake.api.nbt.NBTCompound;
import com.minecraft.moonlake.api.nbt.NBTFactory;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.reflect.Reflect;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.spigotmc.SpigotConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by MoonLake on 2016/7/17.
 */
public class EntityManager extends MoonLakeManager {

    private EntityManager() {

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

            Class<?> CraftEntity = Reflect.PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftEntity");
            Class<?> EntityInsentient = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityInsentient");

            Method getHandle = Reflect.getMethod(CraftEntity, "getHandle");
            Object NMSEntity = getHandle.invoke(entity);

            if(EntityInsentient.isInstance(NMSEntity)) {

                Class<?> PathfinderGoalSelector = Reflect.PackageType.MINECRAFT_SERVER.getClass("PathfinderGoalSelector");

                Field goalSelector = Reflect.getField(EntityInsentient, true, "goalSelector");
                Field goalSelectorB = Reflect.getField(PathfinderGoalSelector, true, "b");
                Field goalSelectorC = Reflect.getField(PathfinderGoalSelector, true, "c");
                goalSelectorB.set(goalSelector.get(NMSEntity), Sets.newLinkedHashSet());
                goalSelectorC.set(goalSelector.get(NMSEntity), Sets.newLinkedHashSet());

                Field targetSelector = Reflect.getField(EntityInsentient, true, "targetSelector");
                Field targetSelectorB = Reflect.getField(PathfinderGoalSelector, true, "b");
                Field targetSelectorC = Reflect.getField(PathfinderGoalSelector, true, "c");
                targetSelectorB.set(targetSelector.get(NMSEntity), Sets.newLinkedHashSet());
                targetSelectorC.set(targetSelector.get(NMSEntity), Sets.newLinkedHashSet());
            }
        }
        catch (Exception e) {

            getMain().getMLogger().warn("清除实体 UUID 为 '" + entity.getUniqueId().toString() + "' 的路径发现者 AI 时异常: " + e.getMessage());
        }
    }

    /**
     * 设置实体 NBT 标签指定键的值
     *
     * @param entity 实体
     * @param key 键
     * @param value 值
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @deprecated 已过期, 详情查看新版 {@link com.minecraft.moonlake.api.nbt.NBTLibrary}
     */
    @Deprecated
    public static void setTagValue(Entity entity, String key, Object value) {

        Validate.notNull(entity, "The entity object is null.");

        NBTCompound nbtCompound = NBTFactory.get().readSafe(entity);
        nbtCompound.put(key, value);

        NBTFactory.get().write(entity, nbtCompound);
    }

    /**
     * 设置实体的 NoAI 属性
     *
     * @param entity 实体
     * @param flag 是否无 AI
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setNoAI(Entity entity, boolean flag) {

        try {

            setTagValue(entity, "NoAI", (byte) (flag ? 1 : 0));
        }
        catch (Exception e) {

            getMain().getMLogger().warn("设置实体 UUID 为 '" + entity.getUniqueId().toString() + "' 的 NoAI 属性时异常: " + e.getMessage());
        }
    }

    /**
     * 设置实体的沉默属性
     *
     * @param entity 实体
     * @param flag 是否沉默
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setSilent(Entity entity, boolean flag) {

        try {

            setTagValue(entity, "Silent", (byte) (flag ? 1 : 0));
        }
        catch (Exception e) {

            getMain().getMLogger().warn("设置实体 UUID 为 '" + entity.getUniqueId().toString() + " ' 的 Silent 属性时异常: " + e.getMessage());
        }
    }

    /**
     * 设置实体的坚不可摧属性
     *
     * @param entity 实体
     * @param flag 是否坚不可摧
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static void setInvulnerable(Entity entity, boolean flag) {

        try {

            setTagValue(entity, "Invulnerable", (byte) (flag ? 1 : 0));
        }
        catch (Exception e) {

            getMain().getMLogger().warn("设置实体 UUID 为 '" + entity.getUniqueId().toString() + " ' 的 Invulnerable 属性时异常: " + e.getMessage());
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

        return getEntityInRadius(location, radius, new HashSet<Class<? extends LivingEntity>>());
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

        for(int i = 0; i < entityList.size(); i++) {

            LivingEntity entity = entityList.get(i);

            if(entity instanceof Player && ((Player)entity).getName().equals(owner.getName())) {

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
     * @param owner 无视主人
     * @param ignoreEntity 无视的实体
     * @return 实体集合
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果主人对象为 {@code null} 则抛出异常
     */
    public static List<LivingEntity> getEntityInRadius(Location location, double radius, Player owner, Set<Class<? extends LivingEntity>> ignoreEntity) {

        Validate.notNull(owner, "The owner object is null.");

        List<LivingEntity> entityList = getEntityInRadius(location, radius, ignoreEntity);

        for(int i = 0; i < entityList.size(); i++) {

            LivingEntity entity = entityList.get(i);

            if(entity instanceof Player && ((Player)entity).getName().equals(owner.getName())) {

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
     * @param owner 无视主人
     * @return 实体集合
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果主人对象为 {@code null} 则抛出异常
     */
    public static List<LivingEntity> getEntityInRadius(Location location, double radius, MoonLakePlayer owner) {

        Validate.notNull(owner, "The owner object is null.");

        List<LivingEntity> entityList = getEntityInRadius(location, radius);

        for(int i = 0; i < entityList.size(); i++) {

            LivingEntity entity = entityList.get(i);

            if(entity instanceof Player && ((Player)entity).getName().equals(owner.getName())) {

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

        for(int i = 0; i < entityList.size(); i++) {

            LivingEntity entity = entityList.get(i);

            if(entity instanceof Player && ((Player)entity).getName().equals(owner.getName())) {

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
     * 给予指定实体真实的伤害
     *
     * @param source 源实体
     * @param damager 攻击者实体
     * @param damage 真实伤害
     * @throws IllegalArgumentException 如果源实体对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果攻击者实体对象为 {@code null} 则抛出异常
     */
    public static void realDamage(LivingEntity source, MoonLakePlayer damager, double damage) {

        realDamage(source, damager.getBukkitPlayer(), damage);
    }

    /**
     * 给予指定实体真实的伤害
     *
     * @param source 源实体
     * @param damager 攻击者实体
     * @param damage 真实伤害
     * @throws IllegalArgumentException 如果源实体对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果攻击者实体对象为 {@code null} 则抛出异常
     */
    @SuppressWarnings("deprecation")
    public static void realDamage(LivingEntity source, LivingEntity damager, double damage) {

        Validate.notNull(source, "The entity source object is null.");
        Validate.notNull(damager, "The entity damager object is null.");

        if(!source.isDead() && !damager.isDead() && damage > 0d) {

            EntityDamageByEntityEvent edbee = new EntityDamageByEntityEvent(damager, source, EntityDamageEvent.DamageCause.CUSTOM, damage);

            source.damage(0d, damager);
            source.setLastDamageCause(edbee);

            Bukkit.getServer().getPluginManager().callEvent(edbee);

            if(source.getHealth() <= damage) {

                source.setHealth(0d);
                return;
            }
            source.setHealth(source.getHealth() - damage);
        }
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
     * @return 实体的移动速度值 异常返回 -1
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
     * @return 实体的击退抗性值 异常返回 -1
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
     * @return 实体的攻击伤害值 异常返回 -1
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

            ((LivingEntity)entity).setHealth(((LivingEntity)entity).getMaxHealth());
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
     * @return 实体的追踪范围 异常返回 -1
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static double getFollowRange(Entity entity) {

        return getAttribute(entity, AttributeType.FOLLOW_RANGE);
    }

    /**
     * 设置指定实体的指定属性类型的值
     *
     * @param entity 实体
     * @param type 属性类型
     * @param value 值
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果属性类型对象为 {@code null} 则抛出异常
     */
    public static void setAttribute(Entity entity, AttributeType type, double value) {

        Validate.notNull(entity, "The entity object is null.");
        Validate.notNull(type, "The attribute type object is null.");

        try {

            Class<?> IAttribute = Reflect.PackageType.MINECRAFT_SERVER.getClass("IAttribute");
            Class<?> EntityLiving = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityLiving");
            Class<?> AttributeInstance = Reflect.PackageType.MINECRAFT_SERVER.getClass("AttributeInstance");
            Class<?> GenericAttributes = Reflect.PackageType.MINECRAFT_SERVER.getClass("GenericAttributes");
            Class<?> CraftLivingEntity = Reflect.PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftLivingEntity");

            Method getHandle = Reflect.getMethod(CraftLivingEntity, "getHandle");
            Object NMSEntity = getHandle.invoke(entity);

            Method getAttributeInstance = Reflect.getMethod(EntityLiving, "getAttributeInstance", IAttribute);
            Field FIELD = Reflect.getField(GenericAttributes, true, type.getField());

            Method setValue = Reflect.getMethod(AttributeInstance, "setValue", Double.class);
            setValue.invoke(getAttributeInstance.invoke(NMSEntity, FIELD.get(null)), valueCheck(type, value));
        }
        catch (Exception e) {

            getMain().getMLogger().warn("设置实体 UUID 为 '" + entity.getUniqueId().toString() + "' 的 " + type.getType() + " 时异常: " + e.getMessage());
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
     */
    public static double getAttribute(Entity entity, AttributeType type) {

        Validate.notNull(entity, "The entity object is null.");
        Validate.notNull(type, "The attribute type object is null.");

        try {

            Class<?> IAttribute = Reflect.PackageType.MINECRAFT_SERVER.getClass("IAttribute");
            Class<?> EntityLiving = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityLiving");
            Class<?> AttributeInstance = Reflect.PackageType.MINECRAFT_SERVER.getClass("AttributeInstance");
            Class<?> GenericAttributes = Reflect.PackageType.MINECRAFT_SERVER.getClass("GenericAttributes");
            Class<?> CraftLivingEntity = Reflect.PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftLivingEntity");

            Method getHandle = Reflect.getMethod(CraftLivingEntity, "getHandle");
            Object NMSEntity = getHandle.invoke(entity);

            Method getAttributeInstance = Reflect.getMethod(EntityLiving, "getAttributeInstance", IAttribute);
            Field FIELD = Reflect.getField(GenericAttributes, true, type.getField());

            Method getValue = Reflect.getMethod(AttributeInstance, "getValue");
            return (Double) getValue.invoke(getAttributeInstance.invoke(NMSEntity, FIELD.get(null)));
        }
        catch (Exception e) {

            getMain().getMLogger().warn("获取实体 UUID 为 '" + entity.getUniqueId().toString() + "' 的 " + type.getType() + " 时异常: " + e.getMessage());
        }
        return -1d;
    }

    /**
     * 检测设置的属性值是否符合
     *
     * @param type 类型
     * @param value 值
     * @return 属性符合的值
     * @throws IllegalArgumentException 如果属性类型对象为 {@code null} 则抛出异常
     */
    protected final static double valueCheck(AttributeType type, double value) {

        Validate.notNull(type, "The attribute type object is null.");

        double min = Math.max(type.getMin(), value);

        return min == type.getMin() ? min : Math.min(min, type.getMax());
    }

    public enum AttributeType {

        /**
         * 实体属性类型: 生命上限 (def: 20.0, range: 0.0 - SpigotConfig.maxHealth)
         *
         * @see org.spigotmc.SpigotConfig#maxHealth
         * @see "spigot.yml >> settings.attribute.maxHealth.max (def: 2048.0)"
         */
        MAX_HEALTH("MaxHealth", "maxHealth", 20.0d, 0.0d, SpigotConfig.maxHealth),
        /**
         * 实体属性类型: 追踪范围 (def: 32.0, range: 0.0 - 2048.0)
         */
        FOLLOW_RANGE("FollowRange", "FOLLOW_RANGE", 32.0d, 0.0d, 2048.0d),
        /**
         * 实体属性类型: 抗击退 (def: 0.0, range: 0.0 - 1.0)
         */
        KNOCK_BACK_RESISTANCE("KnockBackResistance", "c", 0.0d, 0.0d, 1.0d),
        /**
         * 实体属性类型: 移动速度 (def: 0.699999988079071, range: 0.0 - SpigotConfig.movementSpeed)
         *
         * @see org.spigotmc.SpigotConfig#movementSpeed
         * @see "spigot.yml >> settings.attribute.movementSpeed.max (def: 2048.0)"
         */
        MOVEMENT_SPEED("MovementSpeed", "MOVEMENT_SPEED", 0.699999988079071d, 0.0d, SpigotConfig.movementSpeed),
        /**
         * 实体属性类型: 攻击伤害 (def: 2.0, range: 0.0 - SpigotConfig.attackDamage)
         *
         * @see org.spigotmc.SpigotConfig#attackDamage
         * @see "spigot.yml >> settings.attribute.attackDamage.max (def: 2048.0)"
         */
        ATTACK_DAMAGE("AttackDamage", "ATTACK_DAMAGE", 2.0d, 0.0d, SpigotConfig.attackDamage),
        /**
         * 实体属性类型: 攻击速度 (def: 4.0, range: 0.0 - 1024.0)
         */
        ATTACK_SPEED("AttackSpeed", "f", 4.0d, 0.0d, 1024.0d),
        /**
         * 实体属性类型: 护甲 (def: 0.0, range: 0.0 - 30.0)
         */
        ARMOR("Armor", "g", 0.0d, 0.0d, 30.0d),
        /**
         * 实体属性类型: 护甲韧性 (def: 0.0, range: 0.0 - 20.0)
         */
        ARMOR_TOUGHNESS("ArmorToughness", "h", 0.0d, 0.0d, 20.0d),
        /**
         * 实体属性类型: 幸运 (def: 0.0, range: -1024.0 - 1024.0)
         */
        LUCK("Luck", "i", 0.0d, -1024.0d, 1024.0d),
        ;

        private String type;
        private String field;
        private double def;
        private double min;
        private double max;

        AttributeType(String type, String field, double def, double min, double max) {

            this.type = type;
            this.field = field;
            this.def = def;
            this.min = min;
            this.max = max;
        }

        public String getType() {

            return type;
        }

        public String getField() {

            return field;
        }

        public double getDef() {

            return def;
        }

        public double getMin() {

            return min;
        }

        public double getMax() {

            return max;
        }
    }
}
