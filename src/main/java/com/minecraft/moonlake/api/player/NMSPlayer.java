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

import com.minecraft.moonlake.api.packet.PacketPlayOutBukkit;
import com.minecraft.moonlake.api.packet.exception.PacketException;
import com.minecraft.moonlake.api.packet.wrapper.PacketPlayOutAnimation;
import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
import org.bukkit.Material;

/**
 * <h1>NMSPlayer</h1>
 * 玩家 NMS 接口（详细doc待补充...）
 *
 * @version 1.0.1
 * @author Month_Light
 */
public interface NMSPlayer {

    /**
     * 获取此玩家的网络 Ping 值
     *
     * @return Ping 值
     */
    int getPing();

    /**
     * 给此玩家发送数据包
     *
     * @param packet 数据包
     * @throws IllegalArgumentException 如果数据包对象为 {@code null} 则抛出异常
     */
    void sendPacket(PacketPlayOutBukkit packet);

    /**
     * 给此玩家发送标题数据包
     *
     * @param title 主标题
     * @throws IllegalArgumentException 如果主标题对象为 {@code null} 则抛出异常
     */
    void sendTitlePacket(String title);

    /**
     * 给此玩家发送标题数据包
     *
     * @param title 主标题
     * @param subTitle 子标题
     * @throws IllegalArgumentException 如果主标题对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果子标题对象为 {@code null} 则抛出异常
     */
    void sendTitlePacket(String title, String subTitle);

    /**
     * 给此玩家发送标题数据包
     *
     * @param title 主标题
     * @param fadeIn 淡入时间
     * @param stay 停留时间
     * @param fadeOut 淡出时间
     * @throws IllegalArgumentException 如果主标题对象为 {@code null} 则抛出异常
     */
    void sendTitlePacket(String title, int fadeIn, int stay, int fadeOut);

    /**
     * 给此玩家发送标题数据包
     *
     * @param title 主标题
     * @param subTitle 子标题
     * @param fadeIn 淡入时间
     * @param stay 停留时间
     * @param fadeOut 淡出时间
     * @throws IllegalArgumentException 如果主标题对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果子标题对象为 {@code null} 则抛出异常
     */
    void sendTitlePacket(String title, String subTitle, int fadeIn, int stay, int fadeOut);

    /**
     * 给此玩家发送主聊天消息 (经验条上面)
     *
     * @param message 消息
     * @throws IllegalArgumentException 如果消息对象为 {@code null} 则抛出异常
     */
    void sendMainChatPacket(String message);

    /**
     * 给此玩家发送 TAB 列表数据包
     *
     * @param header 头内容
     * @throws IllegalArgumentException 如果头内容对象为 {@code null} 则抛出异常
     */
    void sendTabListPacket(String header);

    /**
     * 给此玩家发送 TAB 列表数据包
     *
     * @param header 头内容
     * @param footer 脚内容
     * @throws IllegalArgumentException 如果头内容对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果脚内容对象为 {@code null} 则抛出异常
     */
    void sendTabListPacket(String header, String footer);

    /**
     * 给此玩家设置物品栈冷却时间
     *
     * @param material 物品栈类型
     * @param tick 时间 Tick (1s = 20tick)
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     * @throws IllegalBukkitVersionException 如果服务器 Bukkit 版本不支持则抛出异常
     */
    void setItemCooldown(Material material, int tick);

    /**
     * 获取此玩家物品栈类型是否拥有冷却时间
     *
     * @param material 物品栈类型
     * @return true 则物品栈类型拥有冷却时间
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     * @throws IllegalBukkitVersionException 如果服务器 Bukkit 版本不支持则抛出异常
     */
    boolean hasItemCooldown(Material material);

    /**
     * 获取此玩家物品栈类型的冷却时间
     *
     * @param material 物品栈类型
     * @return 冷却时间
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     * @throws IllegalBukkitVersionException 如果服务器 Bukkit 版本不支持则抛出异常
     */
    int getItemCooldown(Material material);

    /**
     * 将此玩家播放受伤动画效果
     *
     * @throws PacketException 如果发送时错误则抛出异常
     * @see PacketPlayOutAnimation
     * @see PacketPlayOutAnimation.Type#HURT_EFFECT
     */
    void playHurtAnimation() throws PacketException;
}
