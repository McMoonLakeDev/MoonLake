package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.exception.player.PlayerNotOnlineException;
import com.minecraft.moonlake.manager.PlayerManager;
import com.minecraft.moonlake.util.Util;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by MoonLake on 2016/7/16.
 */
public class AbstractPlayer implements MoonLakePlayer {

    private final String name;
    private final Player player;

    public AbstractPlayer(String name) {

        this.name = name;
        this.player = PlayerManager.fromName(name);
    }

    /**
     * 获取此玩家的 Bukkit 玩家对象
     *
     * @return Bukkit 玩家对象
     */
    @Override
    public Player getBukkitPlayer() {

        return player;
    }

    /**
     * 获取此玩家的名称
     *
     * @return 名称
     */
    @Override
    public String getName() {

        return name;
    }

    /**
     * 获取此玩家的 UUID
     *
     * @return UUID
     */
    @Override
    public UUID getUniqueId() {

        return player.getUniqueId();
    }

    /**
     * 获取此玩家的显示名称
     *
     * @return 显示名称
     */
    @Override
    public String getDisplayName() {

        return player.getDisplayName();
    }

    /**
     * 设置此玩家的显示名称
     *
     * @param displayName 显示名称
     */
    @Override
    public void setDisplayName(String displayName) {

        player.setDisplayName(displayName);
    }

    /**
     * 获取此玩家的 TAB 列表名称
     *
     * @return 列表名称
     */
    @Override
    public String getListName() {

        return player.getPlayerListName();
    }

    /**
     * 设置此玩家的 TAB 列表名称
     *
     * @param listName 列表名称
     * @throws IllegalArgumentException 如果名称已经被占用抛出异常
     * @throws IllegalArgumentException 如果名称的长度小于 0 或大于 64 抛出异常
     */
    @Override
    public void setListName(String listName) {

        player.setPlayerListName(listName);
    }

    /**
     * 将此玩家强制发送聊天文本
     *
     * @param msg 文本消息
     */
    @Override
    public void chat(String msg) {

        player.chat(msg);
    }

    /**
     * 给此玩家发送消息
     *
     * @param msg 消息
     */
    @Override
    public void send(String msg) {

        player.sendMessage(Util.color(msg));
    }

    /**
     * 给此玩家发送消息
     *
     * @param msg 消息
     */
    @Override
    public void send(String[] msg) {

        player.sendMessage(Util.color(msg));
    }

    /**
     * 给此玩家发送基础消息
     *
     * @param bc 基础消息
     */
    @Override
    public void send(BaseComponent bc) {

        player.spigot().sendMessage(bc);
    }

    /**
     * 给此玩家发送基础消息
     *
     * @param bcs 基础消息
     */
    @Override
    public void send(BaseComponent... bcs) {

        player.spigot().sendMessage(bcs);
    }

    /**
     * 获取此玩家的当前血量
     *
     * @return 血量
     */
    @Override
    public double getHealth() {

        return player.getHealth();
    }

    /**
     * 获取此玩家的最大生命
     *
     * @return 最大血量
     */
    @Override
    public double getMaxHealth() {

        return player.getMaxHealth();
    }

    /**
     * 给予此玩家指定数量的血量
     *
     * @param amount 数量
     */
    @Override
    public void giveHealth(double amount) {

        if(getHealth() + amount >= getMaxHealth()) {

            setHealth(getMaxHealth());
            return;
        }
        setHealth(getHealth() + amount);
    }

    /**
     * 减少此玩家指定数量的血量
     *
     * @param amount 数量
     */
    @Override
    public void takeHealth(double amount) {

        if(getHealth() - amount <= 0d) {

            setHealth(0d);
            return;
        }
        setHealth(getHealth() - amount);
    }

    /**
     * 获取此玩家的当前经验
     *
     * @return 经验
     */
    @Override
    public float getXp() {

        return player.getExp();
    }

    /**
     * 获取此玩家的当前等级
     *
     * @return 等级
     */
    @Override
    public int getLevel() {

        return player.getLevel();
    }

    /**
     * 设置此玩家的当前血量
     *
     * @param health 血量
     * @throws IllegalArgumentException 如果血量小于 0 或者大于 maxHealth 则抛出异常
     */
    @Override
    public void setHealth(double health) {

        player.setHealth(health);
    }

    /**
     * 设置此玩家的最大血量
     *
     * @param maxHealth 最大血量
     */
    @Override
    public void setMaxHealth(double maxHealth) {

        player.setMaxHealth(maxHealth);
    }

    /**
     * 给予此玩家指定经验
     *
     * @param xp 经验
     */
    @Override
    public void giveXp(float xp) {

        setXp(getXp() + xp);
    }

    /**
     * 设置此玩家的经验值
     *
     * @param xp 经验值
     */
    @Override
    public void setXp(float xp) {

        player.setExp(xp);
    }

    /**
     * 设置此玩家的当前等级
     *
     * @param level 等级
     */
    @Override
    public void setLevel(int level) {

        player.setLevel(level);
    }

    /**
     * 获取此玩家的飞行速度
     *
     * @return 飞行速度
     */
    @Override
    public float getFlySpeed() {

        return player.getFlySpeed();
    }

    /**
     * 获取此玩家的饱食度
     *
     * @return 饱食度
     */
    @Override
    public int getFoodLevel() {

        return player.getFoodLevel();
    }

    /**
     * 设置此玩家的飞行速度
     *
     * @param flySpeed 飞行速度
     * @throws IllegalArgumentException 如果飞行速度小于 -1 或大于 1 抛出异常
     */
    @Override
    public void setFlySpeed(float flySpeed) {

        player.setFlySpeed(flySpeed);
    }

    /**
     * 设置此玩家的饱食度
     *
     * @param foodLevel 饱食度
     */
    @Override
    public void setFoodLevel(int foodLevel) {

        player.setFoodLevel(foodLevel);
    }

    /**
     * 获取此玩家的计分板对象
     *
     * @return 计分板对象
     */
    @Override
    public Scoreboard getScoreboard() {

        return player.getScoreboard();
    }

    /**
     * 设置此玩家的计分板对象
     *
     * @param scoreboard 计分板对象
     */
    @Override
    public void setScoreboard(Scoreboard scoreboard) {

        player.setScoreboard(scoreboard);
    }

    /**
     * 获取此玩家在观察者模式的追踪目标实体
     *
     * @return 目标实体 没有则返回 null
     */
    @Override
    public Entity getSpectatorTarget() {

        return player.getSpectatorTarget();
    }

    /**
     * 设置此玩家在观察者模式的追踪目标实体对象
     *
     * @param entity 实体对象
     */
    @Override
    public void setSpectatorTarget(Entity entity) {

        player.setSpectatorTarget(entity);
    }

    /**
     * 将此玩家强制受到伤害
     *
     * @param damage  伤害
     */
    @Override
    public void damage(double damage) {

        player.damage(damage);
    }

    /**
     * 将此玩家强制受到伤害
     *
     * @param damage  伤害
     * @param damager 伤害者
     */
    @Override
    public void damage(double damage, LivingEntity damager) {

        player.damage(damage, damager);
    }

    /**
     * 获取此玩家的当前所在位置
     *
     * @return 位置
     */
    @Override
    public Location getLocation() {

        return player.getLocation();
    }

    /**
     * 获取此玩家的物品栏背包
     *
     * @return 物品栏
     */
    @Override
    public PlayerInventory getInventory() {

        return player.getInventory();
    }

    /**
     * 获取此玩家是否之前玩过服务器
     *
     * @return true 之前玩过 else 第一次玩服务器
     */
    @Override
    public boolean hasBeforePlayed() {

        return player.hasPlayedBefore();
    }

    /**
     * 获取此玩家是否在地面
     *
     * @return 是否在地面
     */
    @Override
    public boolean isOnGround() {

        return player.isOnGround();
    }

    /**
     * 获取此玩家是否飞行模式中
     *
     * @return true 则为飞行模式 else 没有
     */
    @Override
    public boolean isFly() {

        return player.isFlying();
    }

    /**
     * 获取此玩家是否允许飞行
     *
     * @return true 则允许飞行 else 不允许
     */
    @Override
    public boolean isAllowFly() {

        return player.getAllowFlight();
    }

    /**
     * 设置此玩家是否允许飞行
     *
     * @param flag 是否允许
     */
    @Override
    public void setAllowFly(boolean flag) {

        player.setAllowFlight(flag);
    }

    /**
     * 获取此玩家的所在世界对象
     *
     * @return 世界
     */
    @Override
    public World getWorld() {

        return player.getWorld();
    }

    /**
     * 更新此玩家的物品栏背包
     */
    @Override
    public void updateInventory() {

        player.updateInventory();
    }

    /**
     * 将此玩家正在打开的物品栏关闭
     */
    @Override
    public void closeInventory() {

        player.closeInventory();
    }

    /**
     * 获取此玩家的行走速度
     *
     * @return 行走速度
     */
    @Override
    public float getWalkSpeed() {

        return player.getWalkSpeed();
    }

    /**
     * 设置此玩家的行走速度
     *
     * @param speed 行走速度
     * @throws IllegalArgumentException 如果行走速度小于 -1 或大于 1 抛出异常
     */
    @Override
    public void setWalkSpeed(float speed) {

        player.setWalkSpeed(speed);
    }

    /**
     * 获取此玩家的当前位置 X 坐标
     *
     * @return X 坐标
     */
    @Override
    public int getX() {

        return getLocation().getBlockX();
    }

    /**
     * 获取此玩家的当前位置 Y 坐标
     *
     * @return Y 坐标
     */
    @Override
    public int getY() {

        return getLocation().getBlockY();
    }

    /**
     * 获取此玩家的当前位置 Z 坐标
     *
     * @return Z 坐标
     */
    @Override
    public int getZ() {

        return getLocation().getBlockZ();
    }

    /**
     * 获取此玩家的当前位置 X 坐标
     *
     * @return X 坐标
     */
    @Override
    public double getDoubleX() {

        return getLocation().getX();
    }

    /**
     * 获取此玩家的当前位置 Y 坐标
     *
     * @return Y 坐标
     */
    @Override
    public double getDoubleY() {

        return getLocation().getY();
    }

    /**
     * 获取此玩家的当前位置 Z 坐标
     *
     * @return Z 坐标
     */
    @Override
    public double getDoubleZ() {

        return getLocation().getZ();
    }

    /**
     * 将此玩家传送到指定实体身边
     *
     * @param entity 实体
     */
    @Override
    public void teleport(Entity entity) {

        player.teleport(entity);
    }

    /**
     * 将此玩家传送到指定位置
     *
     * @param location 位置
     */
    @Override
    public void teleport(Location location) {

        player.teleport(location);
    }

    /**
     * 将此玩家传送到玩家当前世界的指定 xyz 坐标
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     */
    @Override
    public void teleport(int x, int y, int z) {

        teleport((double)x, (double)y, (double)z);
    }

    /**
     * 将此玩家传送到玩家当前世界的指定 xyz 坐标
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     */
    @Override
    public void teleport(double x, double y, double z) {

        player.teleport(new Location(getWorld(), x, y, z));
    }

    /**
     * 将此玩家传送到指定世界的指定 xyz 坐标
     *
     * @param world 指定世界
     * @param x     X 坐标
     * @param y     Y 坐标
     * @param z     Z 坐标
     */
    @Override
    public void teleport(World world, int x, int y, int z) {

        teleport(world, (double)x, (double)y, (double)z);
    }

    /**
     * 将此玩家传送到指定世界的指定 xyz 坐标
     *
     * @param world 指定世界
     * @param x     X 坐标
     * @param y     Y 坐标
     * @param z     Z 坐标
     */
    @Override
    public void teleport(World world, double x, double y, double z) {

        player.teleport(new Location(world, x, y, z));
    }

    /**
     * 获取此玩家准星的目标方块
     *
     * @param distance 距离
     * @return 准星的方块 没有则返回 null
     */
    @Override
    public Block getTargetBlock(int distance) {

        return getTargetBlock(null, distance);
    }

    /**
     * 给此玩家在当前位置播放音效
     *
     * @param sound  音效名
     * @param volume 音量
     * @param pitch  音调
     */
    @Override
    public void playSound(String sound, float volume, float pitch) {

        player.playSound(getLocation(), sound, volume, pitch);
    }

    /**
     * 给此玩家在当前位置播放音效
     *
     * @param sound  音效
     * @param volume 音量
     * @param pitch  音调
     */
    @Override
    public void playSound(Sound sound, float volume, float pitch) {

        player.playSound(getLocation(), sound, volume, pitch);
    }

    /**
     * 给此玩家在当前位置播放音符
     *
     * @param instrument 仪器
     * @param note       音符
     */
    @Override
    public void playNote(Instrument instrument, Note note) {

        player.playNote(getLocation(), instrument, note);
    }

    /**
     * 将指定音效停止播放给此玩家
     *
     * @param sound 音效名
     */
    @Override
    public void stopSound(String sound) {

        player.stopSound(sound);
    }

    /**
     * 将指定音效停止播放给此玩家
     *
     * @param sound 音效
     */
    @Override
    public void stopSound(Sound sound) {

        player.stopSound(sound);
    }

    /**
     * 获取玩家当前位置距离目标位置的距离
     *
     * @param target 目标位置
     * @return 距离
     */
    @Override
    public double distance(Location target) {

        return getLocation().distance(target);
    }

    /**
     * 获取此玩家的主手中物品
     *
     * @return 主手中物品
     */
    @Override
    public ItemStack getItemInMainHand() {

        return getInventory().getItemInMainHand();
    }

    /**
     * 获取此玩家的副手中物品
     *
     * @return 副手中物品
     */
    @Override
    public ItemStack getItemInOffHand() {

        return getInventory().getItemInOffHand();
    }

    /**
     * 获取此玩家的鼠标中物品
     *
     * @return 鼠标中物品
     */
    @Override
    public ItemStack getItemOnCursor() {

        return player.getItemOnCursor();
    }

    /**
     * 设置此玩家的主手中物品
     *
     * @param item 物品栈
     */
    @Override
    public void setItemInMainHand(ItemStack item) {

        getInventory().setItemInMainHand(item);
    }

    /**
     * 设置此玩家的副手中物品
     *
     * @param item 物品栈
     */
    @Override
    public void setItemInOffHand(ItemStack item) {

        getInventory().setItemInOffHand(item);
    }

    /**
     * 给此玩家打开指定物品栏对象
     *
     * @param inv 物品栏对象
     */
    @Override
    public void openInventory(Inventory inv) {

        player.openInventory(inv);
    }

    /**
     * 获取此玩家打开的上面物品栏对象
     *
     * @return 上面物品栏对象 没有则返回 null
     */
    @Override
    public Inventory getTopInventory() {

        return player.getOpenInventory() != null ? player.getOpenInventory().getTopInventory() : null;
    }

    /**
     * 将此玩家添加药水效果
     *
     * @param type  药水效果类型
     * @param level 等级
     * @param time  时间 (Tick)
     */
    @Override
    public void addPotionEffect(PotionEffectType type, int level, int time) {

        player.addPotionEffect(new PotionEffect(type, time, level - 1));
    }

    /**
     * 将此玩家添加药水效果
     *
     * @param type    药水效果类型
     * @param level   等级
     * @param time    时间 (Tick)
     * @param ambient 是否产生更多的药水半透明粒子
     */
    @Override
    public void addPotionEffect(PotionEffectType type, int level, int time, boolean ambient) {

        player.addPotionEffect(new PotionEffect(type, time, level - 1, ambient));
    }

    /**
     * 将此玩家添加药水效果
     *
     * @param type      药水效果类型
     * @param level     等级
     * @param time      时间 (Tick)
     * @param ambient   是否产生更多的药水半透明粒子
     * @param particles 是否拥有粒子效果
     */
    @Override
    public void addPotionEffect(PotionEffectType type, int level, int time, boolean ambient, boolean particles) {

        player.addPotionEffect(new PotionEffect(type, time, level - 1, ambient, particles));
    }

    /**
     * 将此玩家添加药水效果
     *
     * @param type      药水效果类型
     * @param level     等级
     * @param time      时间 (Tick)
     * @param ambient   是否产生更多的药水半透明粒子
     * @param particles 是否拥有粒子效果
     * @param color     药水粒子的颜色
     */
    @Override
    public void addPotionEffect(PotionEffectType type, int level, int time, boolean ambient, boolean particles, Color color) {

        player.addPotionEffect(new PotionEffect(type, time, level - 1, ambient, particles, color));
    }

    /**
     * 获取此玩家是否拥有药水效果
     *
     * @param type 药水效果类型
     * @return true 则拥有此效果类型 else 没有
     */
    @Override
    public boolean hasPotionEffect(PotionEffectType type) {

        return player.hasPotionEffect(type);
    }

    /**
     * 清除此玩家的指定药水效果
     *
     * @param type 药水效果类型
     */
    @Override
    public void removePotionEffect(PotionEffectType type) {

        player.removePotionEffect(type);
    }

    /**
     * 给玩家背包给予指定物品栈
     *
     * @param items 物品栈
     * @return 未成功添加到玩家背包的物品栈集合
     */
    @Override
    public Map<Integer, ItemStack> addItemStack(ItemStack... items) {

        return getInventory().addItem(items);
    }

    /**
     * 给玩家背包清除指定物品栈
     *
     * @param items 物品栈
     * @return 未成功清除到玩家背包的物品栈集合
     */
    @Override
    public Map<Integer, ItemStack> removeItemStack(ItemStack... items) {

        return getInventory().removeItem(items);
    }

    /**
     * 获取此玩家是否拥有指定权限
     *
     * @param permission 权限
     * @return true 拥有此权限 else 没有
     */
    @Override
    public boolean hasPermission(String permission) {

        return player.hasPermission(permission);
    }

    /**
     * 将此玩家进行踢出服务器
     */
    @Override
    public void onKick() {

        onKick("无");
    }

    /**
     * 将此玩家进行踢出服务器
     *
     * @param message 踢出的消息
     */
    @Override
    public void onKick(String message) {

        player.kickPlayer("你被服务器踢出,原因: " + message);
    }

    /**
     * 将此玩家进行封禁名字
     */
    @Override
    public void onBanName() {

        onBanName(null);
    }

    /**
     * 将此玩家进行封禁名字
     *
     * @param cause 原因
     */
    @Override
    public void onBanName(String cause) {

        onBanName(cause, null);
    }

    /**
     * 将此玩家进行封禁名称
     *
     * @param cause 原因
     * @param time  封禁的时间
     */
    @Override
    public void onBanName(String cause, Date time) {

        Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(getName(), cause, time, null);
    }

    /**
     * 将此玩家进行封禁 IP
     */
    @Override
    public void onBanIp() {

        onBanIp(null);
    }

    /**
     * 将此玩家进行封禁 IP
     *
     * @param cause 原因
     */
    @Override
    public void onBanIp(String cause) {

        onBanIp(cause, null);
    }

    /**
     * 将此玩家进行封禁 IP
     *
     * @param cause 原因
     * @param time  封禁的时间
     */
    @Override
    public void onBanIp(String cause, Date time) {

        Bukkit.getServer().getBanList(BanList.Type.IP).addBan(getName(), cause, time, null);
    }

    /**
     * 将此玩家进行解封
     */
    @Override
    public void onUnBan() {

        for(BanList.Type type : BanList.Type.values()) {

            if(Bukkit.getServer().getBanList(type).isBanned(getName())) {

                Bukkit.getServer().getBanList(type).pardon(getName());
            }
        }
    }

    /**
     * 获取此玩家是否潜行中
     *
     * @return true 则潜行 else 没有
     */
    @Override
    public boolean isShift() {

        return player.isSneaking();
    }

    /**
     * 获取此玩家是否冲刺中
     *
     * @return true 则冲刺中 else 没有
     */
    @Override
    public boolean isSprinting() {

        return player.isSprinting();
    }

    /**
     * 获取此玩家是否是管理员
     *
     * @return true 则是管理员 else 不是
     */
    @Override
    public boolean isOp() {

        return player.isOp();
    }

    /**
     * 设置此玩家的矢量
     *
     * @param vector 矢量
     */
    @Override
    public void setVelocity(Vector vector) {

        player.setVelocity(vector);
    }

    /**
     * 设置此玩家的无敌时间 (Tick)
     *
     * @param ticks 时间
     */
    @Override
    public void setNoDamageTicks(int ticks) {

        player.setNoDamageTicks(ticks);
    }

    /**
     * 将此玩家从交通工具上下来
     */
    @Override
    public void onEject() {

        player.eject();
    }

    /**
     * 在玩家当前位置创建爆炸
     *
     * @param power 强度
     */
    @Override
    public void createExplosion(float power) {

        createExplosion(power, true, true);
    }

    /**
     * 在玩家当前位置创建爆炸
     *
     * @param power   强度
     * @param setFire 是否着火
     */
    @Override
    public void createExplosion(float power, boolean setFire) {

        createExplosion(power, setFire, true);
    }

    /**
     * 在玩家当前位置创建爆炸
     *
     * @param power      强度
     * @param setFire    是否着火
     * @param breakBlock 是否破坏方块
     */
    @Override
    public void createExplosion(float power, boolean setFire, boolean breakBlock) {

        getWorld().createExplosion(getX(), getY(), getZ(), power, setFire, breakBlock);
    }

    /**
     * 将此玩家强制执行命令
     *
     * @param command 命令
     */
    @Override
    public boolean performCommand(String command) {

        return player.performCommand(command);
    }

    /**
     * 将指定目标玩家进行隐藏
     *
     * @param target 目标玩家名
     */
    @Override
    public void onHide(String target) {

        Player targetPlayer = PlayerManager.fromName(target);

        if(targetPlayer != null) {

            player.hidePlayer(targetPlayer);
        }
    }

    /**
     * 将指定目标玩家进行显示
     *
     * @param target 目标玩家名
     */
    @Override
    public void onShow(String target) {

        Player targetPlayer = PlayerManager.fromName(target);

        if(targetPlayer != null) {

            player.showPlayer(targetPlayer);
        }
    }

    /**
     * 获取此玩家是否能看到指定目标玩家
     *
     * @param target 目标玩家名
     * @return true 则可以看到 else 看不到
     */
    @Override
    public boolean canSee(String target) {

        Player targetPlayer = PlayerManager.fromName(target);

        return targetPlayer != null && player.canSee(targetPlayer);
    }

    /**
     * 设置此玩家的客户端时间
     *
     * @param time 时间
     */
    @Override
    public void setTime(long time) {

        setTime(time, true);
    }

    /**
     * 设置此玩家的客户端时间
     *
     * @param time     时间
     * @param relative 是否相对的
     */
    @Override
    public void setTime(long time, boolean relative) {

        player.setPlayerTime(time, relative);
    }

    /**
     * 设置此玩家的客户端天气
     *
     * @param weather 天气类型
     */
    @Override
    public void setWeather(WeatherType weather) {

        player.setPlayerWeather(weather);
    }

    /**
     * 获取此玩家的客户端天气
     *
     * @return 天气类型
     */
    @Override
    public WeatherType getWeather() {

        return player.getPlayerWeather();
    }

    /**
     * 获取此玩家的客户端时间
     *
     * @return 时间
     */
    @Override
    public long getTime() {

        return player.getPlayerTime();
    }

    /**
     * 重置此玩家的客户端天气
     */
    @Override
    public void resetWeather() {

        player.resetPlayerWeather();
    }

    /**
     * 重置此玩家的客户端时间
     */
    @Override
    public void resetTime() {

        player.resetPlayerTime();
    }

    /**
     * 获取此玩家的游戏模式
     *
     * @return 游戏模式
     */
    @Override
    public GameMode getGameMode() {

        return player.getGameMode();
    }

    /**
     * 设置此玩家的游戏模式
     *
     * @param mode 模式
     */
    @Override
    public void setGameMode(int mode) {

        GameMode gameMode = GameMode.getByValue(mode);

        if(gameMode != null) {

            setGameMode(gameMode);
        }
    }

    /**
     * 设置此玩家的游戏模式
     *
     * @param mode 模式
     */
    @Override
    public void setGameMode(GameMode mode) {

        player.setGameMode(mode);
    }

    /**
     * 重置此玩家的血量
     */
    @Override
    public void resetMaxHealth() {

        player.resetMaxHealth();
    }

    /**
     * 获取此玩家是否滑翔状态
     *
     * @return 是否滑翔
     */
    @Override
    public boolean isGliding() {

        return player.isGliding();
    }

    /**
     * 设置此玩家是否滑翔状态
     *
     * @param gliding 是否滑翔
     */
    @Override
    public void setGliding(boolean gliding) {

        player.setGliding(gliding);
    }

    /**
     * 清除此玩家的所有药水效果
     */
    @Override
    public void clearPotionEffect() {

        if(player != null && !player.getActivePotionEffects().isEmpty()) {

            for(PotionEffect potionEffect : player.getActivePotionEffects()) {

                if(potionEffect != null && player.hasPotionEffect(potionEffect.getType())) {

                    player.removePotionEffect(potionEffect.getType());
                }
            }
        }
    }

    /**
     * 获取此玩家的眼部位置
     *
     * @return 眼部位置
     */
    @Override
    public Location getEyeLocation() {

        return player.getEyeLocation();
    }

    /**
     * 获取此玩家的摔落距离
     *
     * @return 摔落距离
     */
    @Override
    public float getFallDistance() {

        return player.getFallDistance();
    }

    /**
     * 设置此玩家的摔落距离
     *
     * @param fallDistance 摔落距离
     */
    @Override
    public void setFallDistance(float fallDistance) {

        player.setFallDistance(fallDistance);
    }

    /**
     * 获取此玩家的方向矢量对象
     *
     * @return 方向矢量
     */
    @Override
    public Vector getDirection() {

        return getLocation().getDirection();
    }

    /**
     * 获取此玩家的最后受伤伤害
     *
     * @return 受伤伤害
     */
    @Override
    public double getLastDamage() {

        return player.getLastDamage();
    }

    /**
     * 获取此玩家的最后受伤原因
     *
     * @return 受伤原因
     */
    public EntityDamageEvent getLastDamageCause() {

        return player.getLastDamageCause();
    }

    /**
     * 获取此玩家准星的目标方块
     *
     * @param transparent 无视的方块类型
     * @param maxDistance 最大距离
     * @return 目标方块
     */
    @Override
    public Block getTargetBlock(Set<Material> transparent, int maxDistance) {

        return player.getTargetBlock(transparent, maxDistance);
    }

    /**
     * 设置此玩家是否可以拾取物品
     *
     * @param pickup 是否可以拾取
     */
    @Override
    public void setCanPickupItems(boolean pickup) {

        player.setCanPickupItems(pickup);
    }

    /**
     * 获取此玩家是否可以拾取物品
     *
     * @return 是否可以拾取
     */
    @Override
    public boolean isCanPickupItems() {

        return player.getCanPickupItems();
    }

    /**
     * 设置此玩家是否发光
     *
     * @param flag 是否发光
     */
    @Override
    public void setGlowing(boolean flag) {

        player.setGlowing(flag);
    }

    /**
     * 获取此实体是否发光
     *
     * @return 是否发光
     */
    @Override
    public boolean isGlowing() {

        return player.isGlowing();
    }

    /**
     * 设置此玩家是否坚不可摧 (无敌)
     *
     * @param flag 是否坚不可摧
     */
    @Override
    public void setInvulnerable(boolean flag) {

        player.setInvulnerable(flag);
    }

    /**
     * 获取此玩家是否坚不可摧 (无敌)
     *
     * @return 是否坚不可摧
     */
    @Override
    public boolean isInvulnerable() {

        return player.isInvulnerable();
    }

    /**
     * 获取此玩家是否沉默
     *
     * @return 是否沉默
     */
    @Override
    public boolean isSilent() {

        return player.isSilent();
    }

    /**
     * 设置此玩家是否沉默
     *
     * @param flag 是否沉默
     */
    @Override
    public void setSilent(boolean flag) {

        player.setSilent(flag);
    }

    /**
     * 获取此玩家是否拥有重力
     *
     * @return 是否拥有重力
     */
    @Override
    public boolean hasGravity() {

        return player.hasGravity();
    }

    /**
     * 设置此玩家是否拥有重力
     *
     * @param gravity 是否拥有重力
     */
    @Override
    public void setGravity(boolean gravity) {

        player.setGravity(gravity);
    }

    /**
     * 设置此玩家的客户端材质包
     *
     * @param url 材质包地址
     * @throws IllegalArgumentException 如果材质包的地址是 null 抛出异常
     * @throws IllegalArgumentException 如果材质包的地址长度大于 40 抛出异常
     */
    @Override
    public void setResourcePack(String url) {

        player.setResourcePack(url);
    }

    /**
     * 在此玩家发射弹丸源实体
     *
     * @param projectile 弹丸子类
     * @return 弹丸源
     */
    @Override
    public <T extends Projectile> T launcherProjectile(Class<? extends T> projectile) {

        return player.launchProjectile(projectile);
    }

    /**
     * 在此玩家发射弹丸源实体
     *
     * @param projectile 弹丸子类
     * @param vector     矢量
     * @return 弹丸源
     */
    @Override
    public <T extends Projectile> T launcherProjectile(Class<? extends T> projectile, Vector vector) {

        return player.launchProjectile(projectile, vector);
    }

    /**
     * 获取此玩家的网络套接字地址
     *
     * @return 网络套接字地址
     */
    @Override
    public InetSocketAddress getAddress() {

        return player.getAddress();
    }

    /**
     * 获取此玩家的网络套接字地址 IP
     *
     * @return 网络套接字地址 IP 如果未解析则返回 127.0.0.1
     */
    @Override
    public String getIp() {

        InetAddress address = getAddress().getAddress();

        return address != null ? address.getHostAddress() : "127.0.0.1";
    }

    /**
     * 获取此玩家的网络套接字地址端口号
     *
     * @return 网络套接字地址端口号
     */
    @Override
    public int getPort() {

        return getAddress().getPort();
    }

    /**
     * 比较两个对象
     *
     * @param target 目标对象
     * @return true 则对象相同 else 不同
     */
    @Override
    public int compareTo(MoonLakePlayer target) {

        return getName().compareTo(target.getName());
    }

    /**
     * 判断此对象是否和另个对象完全符合
     *
     * @param object 对象
     * @return 是否符合、一致、匹配
     */
    @Override
    public boolean equals(Object object) {

        if(object != null) {

            if(object instanceof MoonLakePlayer) {

                MoonLakePlayer target = (MoonLakePlayer)object;

                return getName().equals(target.getName());
            }
        }
        return false;
    }

    /**
     * 获取此玩家的网络 Ping 值
     *
     * @return Ping 值
     */
    @Override
    public int getPing() {

        return PlayerManager.getLibrary().getPing(getName());
    }

    /**
     * 给玩家发送标题数据包
     *
     * @param title 标题
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    @Override
    public void sendTitlePacket(String title) {

        PlayerManager.getLibrary().sendTitlePacket(getName(), Util.color(title));
    }

    /**
     * 给玩家发送标题数据包
     *
     * @param title    标题
     * @param subTitle 子标题
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    @Override
    public void sendTitlePacket(String title, String subTitle) {

        PlayerManager.getLibrary().sendTitlePacket(getName(), Util.color(title), Util.color(subTitle));
    }

    /**
     * 给玩家发送标题数据包
     *
     * @param title  标题
     * @param drTime 淡入时间
     * @param plTime 停留时间
     * @param dcTime 淡出时间
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    @Override
    public void sendTitlePacket(String title, int drTime, int plTime, int dcTime) {

        PlayerManager.getLibrary().sendTitlePacket(getName(), Util.color(title), drTime, plTime, dcTime);
    }

    /**
     * 给玩家发送标题数据包
     *
     * @param title    标题
     * @param subTitle 子标题
     * @param drTime   淡入时间
     * @param plTime   停留时间
     * @param dcTime   淡出时间
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    @Override
    public void sendTitlePacket(String title, String subTitle, int drTime, int plTime, int dcTime) {

        PlayerManager.getLibrary().sendTitlePacket(getName(), Util.color(title), Util.color(subTitle), drTime, plTime, dcTime);
    }

    /**
     * 给玩家发送中心聊天数据包
     *
     * @param player  玩家名
     * @param message 消息
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    @Override
    public void sendMainChatPacket(String message) {

        PlayerManager.getLibrary().sendMainChatPacket(getName(), Util.color(message));
    }

    /**
     * 给玩家发送Tab列表数据包
     *
     * @param header 头文本
     * @param footer 脚文本
     * @throws PlayerNotOnlineException 玩家不在线则抛出异常
     */
    @Override
    public void sendTabListPacket(String header, String footer) {

        PlayerManager.getLibrary().sendTabListPacket(getName(), Util.color(header), Util.color(footer));
    }

    /**
     * 给玩家发送物品冷却时间数据包
     *
     * @param type 物品类型
     * @param tick 冷却的时间
     */
    @Override
    public void sendItemCooldownPacket(Material type, int tick) {

        PlayerManager.getLibrary().sendItemCooldownPacket(getName(), type, tick);
    }

    /**
     * 获取玩家某个物品是否还有冷却时间
     *
     * @param type 物品类型
     * @return true 还有冷却时间 else 无冷却时间
     */
    @Override
    public boolean hasItemCooldown(Material type) {

        return PlayerManager.getLibrary().hasItemCooldown(getName(), type);
    }
}
