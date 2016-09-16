package com.minecraft.moonlake.target;

import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MoonLake on 2016/6/1.
 */
public final class TargetHelper {

    private TargetHelper() {

    }

    /**
     * 获取玩家的目标实体集合
     *
     * @param source 源玩家
     * @param range 范围
     * @return 目标实体集合 没有则返回 null
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     */
    public static List<LivingEntity> getLivingTargets(MoonLakePlayer source, double range) {

        Validate.notNull(source, "The moonlake player source object is null.");

        return getLivingTargets(source.getBukkitPlayer(), range);
    }

    /**
     * 获取玩家的目标实体集合
     *
     * @param source 源玩家
     * @param range 范围
     * @param tolerance 限度
     * @return 目标实体集合 没有则返回 null
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     */
    public static List<LivingEntity> getLivingTargets(MoonLakePlayer source, double range, double tolerance) {

        Validate.notNull(source, "The moonlake player source object is null.");

        return getLivingTargets(source.getBukkitPlayer(), range, tolerance);
    }

    /**
     * 获取实体的目标实体集合
     *
     * @param source 源实体
     * @param range 范围
     * @return 目标实体集合
     * @throws IllegalArgumentException 如果源实体对象为 {@code null} 则抛出异常
     */
    public static List<LivingEntity> getLivingTargets(LivingEntity source, double range) {

        return getLivingTargets(source, range, 4.0D);
    }

    /**
     * 获取实体的目标实体集合
     *
     * @param source 源实体
     * @param range 范围
     * @param tolerance 限度
     * @return 目标实体集合
     * @throws IllegalArgumentException 如果源实体对象为 {@code null} 则抛出异常
     */
    private static List<LivingEntity> getLivingTargets(LivingEntity source, double range, double tolerance) {

        Validate.notNull(source, "The entity source object is null.");

        List<Entity> entityList = source.getNearbyEntities(range, range, range);
        List<LivingEntity> targets = new ArrayList<>();

        Vector facing = source.getLocation().getDirection();
        double fLengthSq = facing.lengthSquared();

        entityList.stream().filter(entity -> isInFront(source, entity) && entity instanceof LivingEntity).forEach(entity -> {

            Vector relative = entity.getLocation().subtract(source.getLocation()).toVector();
            double dot = relative.dot(facing);
            double rLengthSq = relative.lengthSquared();
            double cosSquared = dot * dot / (rLengthSq * fLengthSq);
            double sinSquared = 1.0D - cosSquared;
            double dSquared = rLengthSq * sinSquared;

            if (dSquared < tolerance) {

                targets.add((LivingEntity) entity);
            }
        });
        return targets;
    }

    /**
     * 获取玩家的目标实体
     *
     * @param source 源玩家
     * @param range 范围
     * @return 目标实体 没有则返回 null
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     */
    public static LivingEntity getLivingTarget(MoonLakePlayer source, double range) {

        Validate.notNull(source, "The moonlake player source object is null.");

        return getLivingTarget(source.getBukkitPlayer(), range);
    }

    /**
     * 获取玩家的目标实体
     *
     * @param source 源玩家
     * @param range 范围
     * @param tolerance 限度
     * @return 目标实体 没有则返回 null
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     */
    public static LivingEntity getLivingTarget(MoonLakePlayer source, double range, double tolerance) {

        Validate.notNull(source, "The moonlake player source object is null.");

        return getLivingTarget(source.getBukkitPlayer(), range, tolerance);
    }

    /**
     * 获取实体的目标实体
     *
     * @param source 源实体
     * @param range 范围
     * @return 目标实体 没有则返回 null
     * @throws IllegalArgumentException 如果源实体对象为 {@code null} 则抛出异常
     */
    public static LivingEntity getLivingTarget(LivingEntity source, double range) {

        return getLivingTarget(source, range, 4.0D);
    }

    /**
     * 获取实体的目标实体
     *
     * @param source 源实体
     * @param range 范围
     * @param tolerance 限度
     * @return 目标实体 没有则返回 null
     * @throws IllegalArgumentException 如果源实体对象为 {@code null} 则抛出异常
     */
    private static LivingEntity getLivingTarget(LivingEntity source, double range, double tolerance) {

        Validate.notNull(source, "The entity source object is null.");

        List<LivingEntity> targets = getLivingTargets(source, range, tolerance);
        if(targets != null && targets.size() > 0) {

            LivingEntity target = targets.get(0);
            double minDistance = target.getLocation().distanceSquared(source.getLocation());

            for(LivingEntity entity : targets) {

                double distance = entity.getLocation().distanceSquared(source.getLocation());
                if(distance < minDistance) {

                    minDistance = distance;
                    target = entity;
                }
            }
            return target;
        }
        return null;
    }

    /**
     * 判断目标实体是否在实体的前面
     *
     * @param entity 源实体
     * @param target 目标实体
     * @return true 目标实体在实体前面 else 不在
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static boolean isInFront(Entity entity, Entity target) {

        Validate.notNull(entity, "The entity object is null.");
        Validate.notNull(target, "The entity target object is null.");

        Vector facing = entity.getLocation().getDirection();
        Vector relative = target.getLocation().subtract(entity.getLocation()).toVector();

        return facing.dot(relative) >= 0.0D;
    }

    /**
     * 判断目标实体是否在实体的前面
     *
     * @param entity 源实体
     * @param target 目标实体
     * @param angle 角度
     * @return true 目标实体在实体前面 else 不在
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static boolean isInFront(Entity entity, Entity target, double angle) {

        Validate.notNull(entity, "The entity object is null.");
        Validate.notNull(target, "The entity target object is null.");

        if(angle <= 0.0D) return false;
        if(angle >= 360.0D) return true;

        double dotTarget = Math.cos(angle);

        Vector facing = entity.getLocation().getDirection();
        Vector relative = target.getLocation().subtract(entity.getLocation()).toVector().normalize();

        return facing.dot(relative) >= dotTarget;
    }

    /**
     * 判断目标实体是否在实体的后面
     *
     * @param entity 源实体
     * @param target 目标实体
     * @return true 目标实体在实体后面 else 不在
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static boolean isBehind(Entity entity, Entity target) {

        return !isInFront(entity, target);
    }

    /**
     * 判断目标实体是否在实体的后面
     *
     * @param entity 源实体
     * @param target 目标实体
     * @param angle 角度
     * @return true 目标实体在实体后面 else 不在
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    public static boolean isBehind(Entity entity, Entity target, double angle) {

        Validate.notNull(entity, "The entity object is null.");
        Validate.notNull(target, "The entity target object is null.");

        if(angle <= 0.0D) return false;
        if(angle >= 360.0D) return true;

        double dotTarget = Math.cos(angle);

        Vector facing = entity.getLocation().getDirection();
        Vector relative = entity.getLocation().subtract(target.getLocation()).toVector().normalize();

        return facing.dot(relative) >= dotTarget;
    }
}
