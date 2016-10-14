package com.minecraft.moonlake.api.player.ability;

import com.minecraft.moonlake.api.player.BasePlayer;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import java.util.Set;
import java.util.UUID;

/**
 * <h1>AbilityPlayer</h1>
 * 玩家能力接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @see BasePlayer
 */
public interface AbilityPlayer extends BasePlayer, AnimalTamer {

    /**
     * 获取此玩家的名称
     *
     * @return 名称
     */
    String getName();

    /**
     * 获取此玩家的 UUID
     *
     * @return UUID
     */
    UUID getUniqueId();

    /**
     * 获取此玩家的当前血量
     *
     * @return 血量
     */
    double getHealth();

    /**
     * 获取此玩家的最大生命
     *
     * @return 最大血量
     */
    double getMaxHealth();

    /**
     * 给予此玩家指定数量的血量
     *
     * @param amount 数量
     */
    void giveHealth(double amount);

    /**
     * 减少此玩家指定数量的血量
     *
     * @param amount 数量
     */
    void takeHealth(double amount);

    /**
     * 重置此玩家的最大血量
     */
    void resetMaxHealth();

    /**
     * 获取此玩家的当前经验
     *
     * @return 经验
     */
    float getXp();

    /**
     * 获取此玩家的当前等级
     *
     * @return 等级
     */
    int getLevel();

    /**
     * 设置此玩家的当前血量
     *
     * @param health 血量
     * @throws IllegalArgumentException 如果血量小于 0 或者大于 maxHealth 则抛出异常
     */
    void setHealth(double health);

    /**
     * 设置此玩家的最大血量
     *
     * @param maxHealth 最大血量
     */
    void setMaxHealth(double maxHealth);

    /**
     * 给予此玩家指定经验
     *
     * @param xp 经验
     */
    void giveXp(float xp);

    /**
     * 设置此玩家的经验值
     *
     * @param xp 经验值
     */
    void setXp(float xp);

    /**
     * 设置此玩家的当前等级
     *
     * @param level 等级
     */
    void setLevel(int level);

    /**
     * 获取此玩家的飞行速度
     *
     * @return 飞行速度
     */
    float getFlySpeed();

    /**
     * 获取此玩家的饱食度
     *
     * @return 饱食度
     */
    int getFoodLevel();

    /**
     * 设置此玩家的飞行速度
     *
     * @param flySpeed 飞行速度
     * @throws IllegalArgumentException 如果飞行速度小于 -1 或大于 1 抛出异常
     */
    void setFlySpeed(float flySpeed);

    /**
     * 设置此玩家的饱食度
     *
     * @param foodLevel 饱食度
     */
    void setFoodLevel(int foodLevel);

    /**
     * 获取此玩家是否飞行模式中
     *
     * @return true 则为飞行模式 else 没有
     */
    boolean isFly();

    /**
     * 获取此玩家是否允许飞行
     *
     * @return true 则允许飞行 else 不允许
     */
    boolean isAllowFly();

    /**
     * 设置此玩家是否允许飞行
     *
     * @param flag 是否允许
     */
    void setAllowFly(boolean flag);

    /**
     * 获取此玩家的行走速度
     *
     * @return 行走速度
     */
    float getWalkSpeed();

    /**
     * 设置此玩家的行走速度
     *
     * @param speed 行走速度
     * @throws IllegalArgumentException 如果行走速度小于 -1 或大于 1 抛出异常
     */
    void setWalkSpeed(float speed);

    /**
     * 将此玩家强制受到伤害
     *
     * @param damage  伤害
     */
    void damage(double damage);

    /**
     * 将此玩家强制受到伤害
     *
     * @param damage  伤害
     * @param damager 伤害者
     */
    void damage(double damage, LivingEntity damager);

    /**
     * 将此玩家传送到指定实体身边
     *
     * @param entity 实体
     */
    void teleport(Entity entity);

    /**
     * 将此玩家传送到指定玩家身边
     *
     * @param player 玩家
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     */
    void teleport(MoonLakePlayer player);

    /**
     * 将此玩家传送到指定位置
     *
     * @param location 位置
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     */
    void teleport(Location location);

    /**
     * 将此玩家传送到指定世界的重生点
     *
     * @param world 指定世界
     * @throws IllegalArgumentException 如果世界对象为 {@code null} 则抛出异常
     */
    void teleport(World world);

    /**
     * 将此玩家传送到指定世界的重生点
     *
     * @param world 指定世界名
     * @throws IllegalArgumentException 如果世界字符串对象为 {@code null} 或世界不存在则抛出异常
     */
    void teleport(String world);

    /**
     * 将此玩家传送到玩家当前世界的指定 xyz 坐标
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     */
    void teleport(int x, int y, int z);

    /**
     * 将此玩家传送到玩家当前世界的指定 xyz 坐标
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     */
    void teleport(double x, double y, double z);

    /**
     * 将此玩家传送到指定世界的指定 xyz 坐标
     *
     * @param world 指定世界
     * @param x     X 坐标
     * @param y     Y 坐标
     * @param z     Z 坐标
     * @throws IllegalArgumentException 如果世界对象为 {@code null} 则抛出异常
     */
    void teleport(World world, int x, int y, int z);

    /**
     * 将此玩家传送到指定世界的指定 xyz 坐标
     *
     * @param world 指定世界名
     * @param x     X 坐标
     * @param y     Y 坐标
     * @param z     Z 坐标
     * @throws IllegalArgumentException 如果世界字符串对象为 {@code null} 或世界不存在则抛出异常
     */
    void teleport(String world, int x, int y, int z);

    /**
     * 将此玩家传送到指定世界的指定 xyz 坐标
     *
     * @param world 指定世界
     * @param x     X 坐标
     * @param y     Y 坐标
     * @param z     Z 坐标
     * @throws IllegalArgumentException 如果世界对象为 {@code null} 则抛出异常
     */
    void teleport(World world, double x, double y, double z);

    /**
     * 将此玩家传送到指定世界的指定 xyz 坐标
     *
     * @param world 指定世界名
     * @param x     X 坐标
     * @param y     Y 坐标
     * @param z     Z 坐标
     * @throws IllegalArgumentException 如果世界字符串对象为 {@code null} 或世界不存在则抛出异常
     */
    void teleport(String world, double x, double y, double z);

    /**
     * 获取此玩家是否滑翔状态
     *
     * @return 是否滑翔
     */
    boolean isGliding();

    /**
     * 设置此玩家是否滑翔状态
     *
     * @param gliding 是否滑翔
     */
    void setGliding(boolean gliding);

    /**
     * 设置此玩家是否发光
     *
     * @param flag 是否发光
     */
    void setGlowing(boolean flag);

    /**
     * 获取此实体是否发光
     *
     * @return 是否发光
     */
    boolean isGlowing();

    /**
     * 设置此玩家是否坚不可摧 (无敌)
     *
     * @param flag 是否坚不可摧
     */
    void setInvulnerable(boolean flag);

    /**
     * 获取此玩家是否坚不可摧 (无敌)
     *
     * @return 是否坚不可摧
     */
    boolean isInvulnerable();

    /**
     * 获取此玩家是否沉默
     *
     * @return 是否沉默
     */
    boolean isSilent();

    /**
     * 设置此玩家是否沉默
     *
     * @param flag 是否沉默
     */
    void setSilent(boolean flag);

    /**
     * 获取此玩家是否拥有重力
     *
     * @return 是否拥有重力
     */
    boolean hasGravity();

    /**
     * 设置此玩家是否拥有重力
     *
     * @param gravity 是否拥有重力
     */
    void setGravity(boolean gravity);

    /**
     * 设置此玩家是否可以拾取物品
     *
     * @param pickup 是否可以拾取
     */
    void setCanPickupItems(boolean pickup);

    /**
     * 获取此玩家是否可以拾取物品
     *
     * @return 是否可以拾取
     */
    boolean isCanPickupItems();

    /**
     * 获取此玩家的摔落距离
     *
     * @return 摔落距离
     */
    float getFallDistance();

    /**
     * 设置此玩家的摔落距离
     *
     * @param fallDistance 摔落距离
     */
    void setFallDistance(float fallDistance);

    /**
     * 获取此玩家的游戏模式
     *
     * @return 游戏模式
     */
    GameMode getGameMode();

    /**
     * 设置此玩家的游戏模式
     *
     * @param mode 模式
     */
    void setGameMode(int mode);

    /**
     * 设置此玩家的游戏模式
     *
     * @param mode 模式
     * @throws IllegalArgumentException 如果游戏模式对象为 {@code null} 则抛出异常
     */
    void setGameMode(GameMode mode);

    /**
     * 获取此玩家是否拥有指定权限
     *
     * @param permission 权限
     * @return true 拥有此权限 else 没有
     * @throws IllegalArgumentException 如果权限对象为 {@code null} 则抛出异常
     */
    boolean hasPermission(String permission);

    /**
     * 获取此玩家是否潜行中
     *
     * @return true 则潜行 else 没有
     */
    boolean isShift();

    /**
     * 获取此玩家是否冲刺中
     *
     * @return true 则冲刺中 else 没有
     */
    boolean isSprinting();

    /**
     * 获取此玩家是否是管理员
     *
     * @return true 则是管理员 else 不是
     */
    boolean isOp();

    /**
     * 将此玩家添加药水效果
     *
     * @param type 药水效果类型
     * @param level 等级
     * @param time 时间 (Tick)
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    void addPotionEffect(PotionEffectType type, int level, int time);

    /**
     * 将此玩家添加药水效果
     *
     * @param type 药水效果类型
     * @param level 等级
     * @param time 时间 (Tick)
     * @param ambient 是否产生更多的药水半透明粒子
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    void addPotionEffect(PotionEffectType type, int level, int time, boolean ambient);

    /**
     * 将此玩家添加药水效果
     *
     * @param type 药水效果类型
     * @param level 等级
     * @param time 时间 (Tick)
     * @param ambient 是否产生更多的药水半透明粒子
     * @param particles 是否拥有粒子效果
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    void addPotionEffect(PotionEffectType type, int level, int time, boolean ambient, boolean particles);

    /**
     * 将此玩家添加药水效果
     *
     * @param type  药水效果类型
     * @param level 等级
     * @param time  时间 (Tick)
     * @param ambient 是否产生更多的药水半透明粒子
     * @param particles 是否拥有粒子效果
     * @param color 药水粒子的颜色
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    void addPotionEffect(PotionEffectType type, int level, int time, boolean ambient, boolean particles, Color color);

    /**
     * 获取此玩家是否拥有药水效果
     *
     * @param type 药水效果类型
     * @return true 则拥有此效果类型 else 没有
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    boolean hasPotionEffect(PotionEffectType type);

    /**
     * 清除此玩家的指定药水效果
     *
     * @param type 药水效果类型
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    void removePotionEffect(PotionEffectType type);

    /**
     * 获取此玩家是否在地面
     *
     * @return 是否在地面
     */
    boolean isOnGround();

    /**
     * 获取此玩家在观察者模式的追踪目标实体
     *
     * @return 目标实体 没有则返回 null
     */
    Entity getSpectatorTarget();

    /**
     * 设置此玩家在观察者模式的追踪目标实体对象
     *
     * @param entity 实体对象
     * @throws IllegalArgumentException 如果此玩家的游戏模式不为 {@link GameMode#SPECTATOR} 则抛出异常
     */
    void setSpectatorTarget(Entity entity);

    /**
     * 获取此玩家的计分板对象
     *
     * @return 计分板对象
     */
    Scoreboard getScoreboard();

    /**
     * 设置此玩家的计分板对象
     *
     * @param scoreboard 计分板对象
     * @throws IllegalArgumentException 如果计分板对象为 {@code null} 则抛出异常
     */
    void setScoreboard(Scoreboard scoreboard);

    /**
     * 在此玩家发射弹丸实体
     *
     * @param projectile 弹丸类
     * @param <T> 弹丸类
     * @return 弹丸
     * @throws IllegalArgumentException 如果弹丸类对象为 {@code null} 则抛出异常
     */
    <T extends Projectile> T launcherProjectile(Class<? extends T> projectile);

    /**
     * 在此玩家发射弹丸实体
     *
     * @param projectile 弹丸类
     * @param vector 矢量
     * @param <T> 弹丸类
     * @return 弹丸
     * @throws IllegalArgumentException 如果弹丸类对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果弹丸的矢量对象为 {@code null} 则抛出异常
     */
    <T extends Projectile> T launcherProjectile(Class<? extends T> projectile, Vector vector);

    /**
     * 获取此玩家准星的目标方块
     *
     * @param transparent 无视的方块类型
     * @param maxDistance 最大距离
     * @return 目标方块
     * @throws IllegalArgumentException 如果无视的方块类型集合对象为 {@code null} 则抛出异常
     */
    Block getTargetBlock(Set<Material> transparent, int maxDistance);

    /**
     * 将此玩家强制发送聊天文本
     *
     * @param message 文本消息
     * @throws IllegalArgumentException 如果发送的消息对象为 {@code null} 则抛出异常
     */
    void chat(String message);

    /**
     * 将此玩家强制执行命令
     *
     * @param command 命令
     * @return true 则执行成功 else 没有
     * @throws IllegalArgumentException 如果命令对象为 {@code null} 则抛出异常
     */
    boolean performCommand(String command);
}
