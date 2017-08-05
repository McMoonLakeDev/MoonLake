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
 
 
package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.api.chat.ChatComponent;
import com.minecraft.moonlake.api.fancy.FancyMessage;
import com.minecraft.moonlake.api.packet.wrapper.PacketPlayOutChat;
import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
import com.mojang.authlib.GameProfile;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import java.util.Date;
import java.util.UUID;

/**
 * <h1>BasePlayer</h1>
 * 玩家基础接口（详细doc待补充...）
 *
 * @version 1.0.1
 * @author Month_Light
 */
public interface BasePlayer {

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
     * 获取此玩家的实体 Id
     *
     * @return 实体 Id
     */
    int getEntityId();

    /**
     * 获取此玩家的游戏简介
     *
     * @return 游戏简介
     */
    GameProfile getProfile();

    /**
     * 获取此玩家的本地语言
     *
     * @return 本地语言
     */
    String getLanguage();

    /**
     * 获取此玩家的显示名称
     *
     * @return 显示名称
     */
    String getDisplayName();

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
    String getListName();

    /**
     * 设置此玩家的 TAB 列表名称
     *
     * @param listName 列表名称
     * @throws IllegalArgumentException 如果名称对象已经被占用抛出异常
     * @throws IllegalArgumentException 如果名称对象的长度小于 0 或大于 64 抛出异常
     */
    void setListName(String listName);

    /**
     * 获取此玩家的当前所在位置
     *
     * @return 位置
     */
    Location getLocation();

    /**
     * 获取此玩家是否之前玩过服务器
     *
     * @return true 之前玩过 else 第一次玩服务器
     */
    boolean hasBeforePlayed();

    /**
     * 获取此玩家的所在世界对象
     *
     * @return 世界
     */
    World getWorld();

    /**
     * 获取此玩家的当前位置 X 坐标
     *
     * @return X 坐标
     */
    int getX();

    /**
     * 获取此玩家的当前位置 Y 坐标
     *
     * @return Y 坐标
     */
    int getY();

    /**
     * 获取此玩家的当前位置 Z 坐标
     *
     * @return Z 坐标
     */
    int getZ();

    /**
     * 获取此玩家的当前位置 X 坐标
     *
     * @return X 坐标
     */
    double getDoubleX();

    /**
     * 获取此玩家的当前位置 Y 坐标
     *
     * @return Y 坐标
     */
    double getDoubleY();

    /**
     * 获取此玩家的当前位置 Z 坐标
     *
     * @return Z 坐标
     */
    double getDoubleZ();

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
     * @throws IllegalBukkitVersionException 如果 Bukkit 服务器版本低于 1.10 则抛出异常
     */
    void stopSound(String sound);

    /**
     * 将指定音效停止播放给此玩家
     *
     * @param sound 音效
     * @throws IllegalArgumentException 如果音效对象为 {@code null} 则抛出异常
     * @throws IllegalBukkitVersionException 如果 Bukkit 服务器版本低于 1.10 则抛出异常
     */
    void stopSound(Sound sound);

    /**
     * 获取玩家当前位置距离目标位置的距离
     *
     * @param target 目标位置
     * @return 距离
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     */
    double distance(Location target);

    /**
     * 清除此玩家的所有药水效果
     */
    void clearPotionEffect();

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
    long getTime();

    /**
     * 重置此玩家的客户端天气
     */
    void resetWeather();

    /**
     * 重置此玩家的客户端时间
     */
    void resetTime();

    /**
     * 获取此玩家眼睛的高度位置
     *
     * @return 高度位置
     */
    double getEyeHeight();

    /**
     * 获取此玩家眼睛的高度位置
     *
     * @param ignoreSneaking 是否无视潜行的影响效果
     * @return 高度位置
     */
    double getEyeHeight(boolean ignoreSneaking);

    /**
     * 获取此玩家的眼部位置
     *
     * @return 眼部位置
     */
    Location getEyeLocation();

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
    double getLastDamage();

    /**
     * 获取此玩家的最后受伤原因
     *
     * @return 受伤原因
     */
    EntityDamageEvent getLastDamageCause();

    /**
     * 设置此玩家的客户端材质包
     *
     * @param url 材质包地址
     * @throws IllegalArgumentException 如果材质包的地址对象为 {@code null} 抛出异常
     */
    void setResourcePack(String url);

    /**
     * 设置此玩家的客户端材质包
     *
     * @param url 材质包地址
     * @param hash 材质包哈希值
     * @throws IllegalArgumentException 如果材质包的地址对象为 {@code null} 抛出异常
     * @throws IllegalArgumentException 如果材质包的哈希值对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果材质包的哈希值长度不为 {@code 20} 则抛出异常
     */
    void setResourcePack(String url, byte[] hash);

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
    void send(String... messages);

    /**
     * 给此玩家发送花式消息
     *
     * @param fancyMessage 花式消息
     * @throws IllegalArgumentException 如果发送的花式消息对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #send(ChatComponent)}
     */
    @Deprecated
    void send(FancyMessage fancyMessage);

    /**
     * 给此玩家发送花式消息
     *
     * @param fancyMessages 花式消息
     * @throws IllegalArgumentException 如果发送的花式消息对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #send(ChatComponent...)}
     */
    @Deprecated
    void send(FancyMessage... fancyMessages);

    /**
     * 给此玩家发送聊天组件消息
     *
     * @param chatComponent 聊天组件
     * @throws IllegalArgumentException 如果发送的聊天组件对象为 {@code null} 则抛出异常
     */
    void send(ChatComponent chatComponent);

    /**
     * 给此玩家发送聊天组件消息
     *
     * @param chatComponents 聊天组件
     * @throws IllegalArgumentException 如果发送的聊天组件对象为 {@code null} 则抛出异常
     */
    void send(ChatComponent... chatComponents);

    /**
     * 给此玩家发送聊天组件消息
     *
     * @param chatComponent 聊天组件
     * @param mode 聊天模式
     * @throws IllegalArgumentException 如果发送的聊天组件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果聊天模式对象为 {@code null} 则抛出异常
     */
    void send(ChatComponent chatComponent, PacketPlayOutChat.Mode mode);

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
    boolean canSee(Player target);

    /**
     * 获取此玩家是否能看到指定目标玩家
     *
     * @param target 目标玩家
     * @return true 则可以看到 else 看不到
     * @throws IllegalArgumentException 如果目标玩家对象为 {@code null} 则抛出异常
     */
    boolean canSee(MoonLakePlayer target);

    /**
     * 获取此玩家是否能看到指定目标玩家
     *
     * @param target 目标玩家名
     * @return true 则可以看到 else 看不到
     * @throws IllegalArgumentException 如果目标玩家名对象为 {@code null} 则抛出异常
     */
    boolean canSee(String target);
}
