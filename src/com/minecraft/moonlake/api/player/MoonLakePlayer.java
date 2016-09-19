package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.api.fancy.FancyMessage;
import com.minecraft.moonlake.property.*;
import com.mojang.authlib.GameProfile;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * <hr />
 * <div>
 *     <h1>Minecraft MoonLake Player Interface</h1>
 *     <p>By Month_Light Ver: 1.7</p>
 * </div>
 * <hr />
 * <div>
 *     <h1>如果派生子类应该这样写继承关系:</h1>
 *     <p>public class SonPlayer extends {@link AbstractPlayer} { }</p>
 *     <hr />
 *     <h2>如果使用接口来实现继承关系:</h2>
 *     <p>public interface SonPlayer extends MoonLakePlayer { }</p>
 *     <h2>那么你的实现类就应该这样来继承关系:</h2>
 *     <p>public class SonPlayerWrapped extends {@link AbstractPlayer} implements SonPlayer { }</p>
 * </div>
 * <hr />
 *
 * @see AbstractPlayer
 * @see NMSPlayer
 * @see InternetPlayer
 * @see SkinmePlayer
 */
public interface MoonLakePlayer extends NMSPlayer, InternetPlayer, SkinmePlayer, InventoryHolder, Comparable<MoonLakePlayer> {

    /**
     * 获取此玩家的 Bukkit 玩家对象
     *
     * @return Bukkit 玩家对象
     */
    Player getBukkitPlayer();

    /**
     * 获取此玩家的名称
     *
     * @return 名称
     */
    ReadOnlyStringProperty getName();

    /**
     * 获取此玩家的 UUID
     *
     * @return UUID
     */
    UUID getUniqueId();

    /**
     * 获取此玩家的游戏简介
     *
     * @return 游戏简介
     */
    GameProfile getProfile();

    /**
     * 获取此玩家的显示名称
     *
     * @return 显示名称
     */
    ReadOnlyStringProperty getDisplayName();

    /**
     * 设置此玩家的显示名称
     *
     * @param displayName 显示名称
     */
    void setDisplayName(String displayName);

    /**
     * 获取此玩家的 TAB 列表名称
     *
     * @return 列表名称
     */
    ReadOnlyStringProperty getListName();

    /**
     * 设置此玩家的 TAB 列表名称
     *
     * @param listName 列表名称
     * @throws IllegalArgumentException 如果名称对象已经被占用抛出异常
     * @throws IllegalArgumentException 如果名称对象的长度小于 0 或大于 64 抛出异常
     */
    void setListName(String listName);

    /**
     * 将此玩家强制发送聊天文本
     *
     * @param message 文本消息
     * @throws IllegalArgumentException 如果发送的消息对象为 {@code null} 则抛出异常
     */
    void chat(String message);

    /**
     * 给此玩家发送消息
     *
     * @param message 消息
     * @throws IllegalArgumentException 如果发送的消息对象为 {@code null} 则抛出异常
     */
    void send(String message);

    /**
     * 给此玩家发送消息
     *
     * @param messages 消息
     * @throws IllegalArgumentException 如果发送的消息对象为 {@code null} 则抛出异常
     */
    void send(String[] messages);

    /**
     * 给此玩家发送花式消息
     *
     * @param fancyMessage 花式消息
     * @throws IllegalArgumentException 如果发送的花式消息对象为 {@code null} 则抛出异常
     */
    void send(FancyMessage fancyMessage);

    /**
     * 给此玩家发送花式消息
     *
     * @param fancyMessage 花式消息
     * @throws IllegalArgumentException 如果发送的花式消息对象为 {@code null} 则抛出异常
     */
    void send(FancyMessage... fancyMessages);

    /**
     * 获取此玩家的当前血量
     *
     * @return 血量
     */
    ReadOnlyDoubleProperty getHealth();

    /**
     * 获取此玩家的最大生命
     *
     * @return 最大血量
     */
    ReadOnlyDoubleProperty getMaxHealth();

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
     * 获取此玩家的当前经验
     *
     * @return 经验
     */
    ReadOnlyFloatProperty getXp();

    /**
     * 获取此玩家的当前等级
     *
     * @return 等级
     */
    ReadOnlyIntegerProperty getLevel();

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
    ReadOnlyFloatProperty getFlySpeed();

    /**
     * 获取此玩家的饱食度
     *
     * @return 饱食度
     */
    ReadOnlyIntegerProperty getFoodLevel();

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
     * 获取此玩家的当前所在位置
     *
     * @return 位置
     */
    Location getLocation();

    /**
     * 获取此玩家的物品栏背包
     *
     * @return 物品栏
     */
    PlayerInventory getInventory();

    /**
     * 获取此玩家是否之前玩过服务器
     *
     * @return true 之前玩过 else 第一次玩服务器
     */
    ReadOnlyBooleanProperty hasBeforePlayed();

    /**
     * 获取此玩家是否在地面
     *
     * @return 是否在地面
     */
    ReadOnlyBooleanProperty isOnGround();

    /**
     * 获取此玩家是否飞行模式中
     *
     * @return true 则为飞行模式 else 没有
     */
    ReadOnlyBooleanProperty isFly();

    /**
     * 获取此玩家是否允许飞行
     *
     * @return true 则允许飞行 else 不允许
     */
    ReadOnlyBooleanProperty isAllowFly();

    /**
     * 设置此玩家是否允许飞行
     *
     * @param flag 是否允许
     */
    void setAllowFly(boolean flag);

    /**
     * 获取此玩家的所在世界对象
     *
     * @return 世界
     */
    World getWorld();

    /**
     * 更新此玩家的物品栏背包
     */
    void updateInventory();

    /**
     * 将此玩家正在打开的物品栏关闭
     */
    void closeInventory();

    /**
     * 获取此玩家的行走速度
     *
     * @return 行走速度
     */
    ReadOnlyFloatProperty getWalkSpeed();

    /**
     * 设置此玩家的行走速度
     *
     * @param speed 行走速度
     * @throws IllegalArgumentException 如果行走速度小于 -1 或大于 1 抛出异常
     */
    void setWalkSpeed(float speed);

    /**
     * 获取此玩家的当前位置 X 坐标
     *
     * @return X 坐标
     */
    ReadOnlyIntegerProperty getX();

    /**
     * 获取此玩家的当前位置 Y 坐标
     *
     * @return Y 坐标
     */
    ReadOnlyIntegerProperty getY();

    /**
     * 获取此玩家的当前位置 Z 坐标
     *
     * @return Z 坐标
     */
    ReadOnlyIntegerProperty getZ();

    /**
     * 获取此玩家的当前位置 X 坐标
     *
     * @return X 坐标
     */
    ReadOnlyDoubleProperty getDoubleX();

    /**
     * 获取此玩家的当前位置 Y 坐标
     *
     * @return Y 坐标
     */
    ReadOnlyDoubleProperty getDoubleY();

    /**
     * 获取此玩家的当前位置 Z 坐标
     *
     * @return Z 坐标
     */
    ReadOnlyDoubleProperty getDoubleZ();

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
     * 获取此玩家准星的目标方块
     *
     * @param distance 距离
     * @return 准星的方块 没有则返回 null
     */
    Block getTargetBlock(int distance);

    /**
     * 给此玩家在当前位置播放音效
     *
     * @param sound  音效名
     * @param volume 音量
     * @param pitch  音调
     * @throws IllegalArgumentException 如果音效名对象为 {@code null} 则抛出异常
     */
    void playSound(String sound, float volume, float pitch);

    /**
     * 给此玩家在当前位置播放音效
     *
     * @param sound  音效
     * @param volume 音量
     * @param pitch  音调
     * @throws IllegalArgumentException 如果音效对象为 {@code null} 则抛出异常
     */
    void playSound(Sound sound, float volume, float pitch);

    /**
     * 给此玩家在当前位置播放音符
     *
     * @param instrument 仪器
     * @param note 音符
     * @throws IllegalArgumentException 如果仪器对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果音符对象为 {@code null} 则抛出异常
     */
    void playNote(Instrument instrument, Note note);

    /**
     * 将指定音效停止播放给此玩家
     *
     * @param sound 音效名
     * @throws IllegalArgumentException 如果音效名对象为 {@code null} 则抛出异常
     */
    void stopSound(String sound);

    /**
     * 将指定音效停止播放给此玩家
     *
     * @param sound 音效
     * @throws IllegalArgumentException 如果音效对象为 {@code null} 则抛出异常
     */
    void stopSound(Sound sound);

    /**
     * 获取玩家当前位置距离目标位置的距离
     *
     * @param target 目标位置
     * @return 距离
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     */
    ReadOnlyDoubleProperty distance(Location target);

    /**
     * 获取此玩家的主手中物品
     *
     * @return 主手中物品
     */
    ItemStack getItemInMainHand();

    /**
     * 获取此玩家的副手中物品
     *
     * @return 副手中物品
     */
    ItemStack getItemInOffHand();

    /**
     * 获取此玩家的鼠标中物品
     *
     * @return 鼠标中物品
     */
    ItemStack getItemOnCursor();

    /**
     * 设置此玩家的主手中物品
     *
     * @param item 物品栈
     */
    void setItemInMainHand(ItemStack item);

    /**
     * 设置此玩家的副手中物品
     *
     * @param item 物品栈
     */
    void setItemInOffHand(ItemStack item);

    /**
     * 给此玩家打开指定物品栏对象
     *
     * @param inv 物品栏对象
     */
    void openInventory(Inventory inv);

    /**
     * 获取此玩家打开的上面物品栏对象
     *
     * @return 上面物品栏对象
     */
    Inventory getTopInventory();

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
    ReadOnlyBooleanProperty hasPotionEffect(PotionEffectType type);

    /**
     * 清除此玩家的指定药水效果
     *
     * @param type 药水效果类型
     * @throws IllegalArgumentException 如果药水效果类型对象为 {@code null} 则抛出异常
     */
    void removePotionEffect(PotionEffectType type);

    /**
     * 给玩家背包给予指定物品栈
     *
     * @param items 物品栈
     * @return 未成功添加到玩家背包的物品栈集合
     */
    @Deprecated
    Map<Integer, ItemStack> addItemStack(ItemStack... items);

    /**
     * 给玩家背包清除指定物品栈
     *
     * @param items 物品栈
     * @return 未成功清除到玩家背包的物品栈集合
     */
    @Deprecated
    Map<Integer, ItemStack> removeItemStack(ItemStack... items);

    /**
     * 获取此玩家是否拥有指定权限
     *
     * @param permission 权限
     * @return true 拥有此权限 else 没有
     * @throws IllegalArgumentException 如果权限对象为 {@code null} 则抛出异常
     */
    ReadOnlyBooleanProperty hasPermission(String permission);

    /**
     * 将此玩家进行踢出服务器
     */
    void onKick();

    /**
     * 将此玩家进行踢出服务器
     *
     * @param message 踢出的消息
     * @throws IllegalArgumentException 如果消息对象为 {@code null} 则抛出异常
     */
    void onKick(String message);

    /**
     * 将此玩家进行封禁名称
     */
    void onBanName();

    /**
     * 将此玩家进行封禁名称
     *
     * @param cause 原因
     */
    void onBanName(String cause);

    /**
     * 将此玩家进行封禁名称
     *
     * @param cause 原因
     * @param time 封禁的时间
     */
    void onBanName(String cause, Date time);

    /**
     * 将此玩家进行封禁 IP
     */
    void onBanIp();

    /**
     * 将此玩家进行封禁 IP
     *
     * @param cause 原因
     */
    void onBanIp(String cause);

    /**
     * 将此玩家进行封禁 IP
     *
     * @param cause 原因
     * @param time 封禁的时间
     */
    void onBanIp(String cause, Date time);

    /**
     * 将此玩家进行解封
     */
    void onUnBan();

    /**
     * 获取此玩家是否潜行中
     *
     * @return true 则潜行 else 没有
     */
    ReadOnlyBooleanProperty isShift();

    /**
     * 获取此玩家是否冲刺中
     *
     * @return true 则冲刺中 else 没有
     */
    ReadOnlyBooleanProperty isSprinting();

    /**
     * 获取此玩家是否是管理员
     *
     * @return true 则是管理员 else 不是
     */
    ReadOnlyBooleanProperty isOp();

    /**
     * 设置此玩家的矢量
     *
     * @param vector 矢量
     * @throws IllegalArgumentException 如果矢量对象为 {@code null} 则抛出异常
     */
    void setVelocity(Vector vector);

    /**
     * 设置此玩家的无敌时间 (Tick)
     *
     * @param ticks 时间
     */
    void setNoDamageTicks(int ticks);

    /**
     * 将此玩家从交通工具上下来
     */
    void onEject();

    /**
     * 在玩家当前位置创建爆炸
     *
     * @param power 强度
     */
    void createExplosion(float power);

    /**
     * 在玩家当前位置创建爆炸
     *
     * @param power 强度
     * @param setFire 是否着火
     */
    void createExplosion(float power, boolean setFire);

    /**
     * 在玩家当前位置创建爆炸
     *
     * @param power 强度
     * @param setFire 是否着火
     * @param breakBlock 是否破坏方块
     */
    void createExplosion(float power, boolean setFire, boolean breakBlock);

    /**
     * 将此玩家强制执行命令
     *
     * @param command 命令
     * @return true 则执行成功 else 没有
     * @throws IllegalArgumentException 如果命令对象为 {@code null} 则抛出异常
     */
    ReadOnlyBooleanProperty performCommand(String command);

    /**
     * 将指定目标玩家进行隐藏
     *
     * @param target 目标玩家
     * @throws IllegalArgumentException 如果目标玩家对象为 {@code null} 则抛出异常
     */
    void onHide(Player target);

    /**
     * 将指定目标玩家进行隐藏
     *
     * @param target 目标玩家
     * @throws IllegalArgumentException 如果目标玩家对象为 {@code null} 则抛出异常
     */
    void onHide(MoonLakePlayer target);

    /**
     * 将指定目标玩家进行隐藏
     *
     * @param target 目标玩家名
     * @throws IllegalArgumentException 如果目标玩家名对象为 {@code null} 则抛出异常
     */
    void onHide(String target);

    /**
     * 将指定目标玩家进行显示
     *
     * @param target 目标玩家
     * @throws IllegalArgumentException 如果目标玩家对象为 {@code null} 则抛出异常
     */
    void onShow(Player target);

    /**
     * 将指定目标玩家进行显示
     *
     * @param target 目标玩家
     * @throws IllegalArgumentException 如果目标玩家对象为 {@code null} 则抛出异常
     */
    void onShow(MoonLakePlayer target);

    /**
     * 将指定目标玩家进行显示
     *
     * @param target 目标玩家名
     * @throws IllegalArgumentException 如果目标玩家名对象为 {@code null} 则抛出异常
     */
    void onShow(String target);

    /**
     * 获取此玩家是否能看到指定目标玩家
     *
     * @param target 目标玩家
     * @return true 则可以看到 else 看不到
     * @throws IllegalArgumentException 如果目标玩家对象为 {@code null} 则抛出异常
     */
    ReadOnlyBooleanProperty canSee(Player target);

    /**
     * 获取此玩家是否能看到指定目标玩家
     *
     * @param target 目标玩家
     * @return true 则可以看到 else 看不到
     * @throws IllegalArgumentException 如果目标玩家对象为 {@code null} 则抛出异常
     */
    ReadOnlyBooleanProperty canSee(MoonLakePlayer target);

    /**
     * 获取此玩家是否能看到指定目标玩家
     *
     * @param target 目标玩家名
     * @return true 则可以看到 else 看不到
     * @throws IllegalArgumentException 如果目标玩家名对象为 {@code null} 则抛出异常
     */
    ReadOnlyBooleanProperty canSee(String target);

    /**
     * 设置此玩家的客户端时间
     *
     * @param time 时间
     */
    void setTime(long time);

    /**
     * 设置此玩家的客户端时间
     *
     * @param time 时间
     * @param relative 是否相对的
     */
    void setTime(long time, boolean relative);

    /**
     * 设置此玩家的客户端天气
     *
     * @param weather 天气类型
     * @throws IllegalArgumentException 如果天气类型对象为 {@code null} 则抛出异常
     */
    void setWeather(WeatherType weather);

    /**
     * 获取此玩家的客户端天气
     *
     * @return 天气类型
     */
    WeatherType getWeather();

    /**
     * 获取此玩家的客户端时间
     *
     * @return 时间
     */
    ReadOnlyLongProperty getTime();

    /**
     * 重置此玩家的客户端天气
     */
    void resetWeather();

    /**
     * 重置此玩家的客户端时间
     */
    void resetTime();

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
     * 重置此玩家的最大血量
     */
    void resetMaxHealth();

    /**
     * 获取此玩家是否滑翔状态
     *
     * @return 是否滑翔
     */
    ReadOnlyBooleanProperty isGliding();

    /**
     * 设置此玩家是否滑翔状态
     *
     * @param gliding 是否滑翔
     */
    void setGliding(boolean gliding);

    /**
     * 清除此玩家的所有药水效果
     */
    void clearPotionEffect();

    /**
     * 获取此玩家的眼部位置
     *
     * @return 眼部位置
     */
    Location getEyeLocation();

    /**
     * 获取此玩家的摔落距离
     *
     * @return 摔落距离
     */
    ReadOnlyFloatProperty getFallDistance();

    /**
     * 设置此玩家的摔落距离
     *
     * @param fallDistance 摔落距离
     */
    void setFallDistance(float fallDistance);

    /**
     * 获取此玩家的方向矢量对象
     *
     * @return 方向矢量
     */
    Vector getDirection();

    /**
     * 获取此玩家的最后受伤伤害
     *
     * @return 受伤伤害
     */
    ReadOnlyDoubleProperty getLastDamage();

    /**
     * 获取此玩家的最后受伤原因
     *
     * @return 受伤原因
     */
    EntityDamageEvent getLastDamageCause();

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
    ReadOnlyBooleanProperty isCanPickupItems();

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
    ReadOnlyBooleanProperty isGlowing();

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
    ReadOnlyBooleanProperty isInvulnerable();

    /**
     * 获取此玩家是否沉默
     *
     * @return 是否沉默
     */
    ReadOnlyBooleanProperty isSilent();

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
    ReadOnlyBooleanProperty hasGravity();

    /**
     * 设置此玩家是否拥有重力
     *
     * @param gravity 是否拥有重力
     */
    void setGravity(boolean gravity);

    /**
     * 设置此玩家的客户端材质包
     *
     * @param url 材质包地址
     * @throws IllegalArgumentException 如果材质包的地址对象为 {@code null} 抛出异常
     * @throws IllegalArgumentException 如果材质包的地址对象长度大于 40 抛出异常
     */
    void setResourcePack(String url);

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
     * 比较两个对象
     *
     * @param target 目标对象
     * @return true 则对象相同 else 不同
     */
    @Override
    int compareTo(MoonLakePlayer target);

    /**
     * 判断此对象是否和另个对象完全符合
     *
     * @param object 对象
     * @return 是否符合、一致、匹配
     */
    boolean equals(Object object);
}
