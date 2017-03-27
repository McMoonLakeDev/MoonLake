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


package com.minecraft.moonlake.api.anvil;

import com.minecraft.moonlake.api.nms.exception.NMSException;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * <h1>AnvilWindow</h1>
 * 铁砧窗口接口（详细doc待补充...）
 *
 * @version 1.1
 * @author Month_Light
 */
public interface AnvilWindow {

    /**
     * 获取此铁砧窗口的插件对象
     *
     * @return Plugin
     */
    Plugin getPlugin();

    /**
     * 获取此铁砧窗口是否初始化完毕
     *
     * @return 是否初始化完毕
     */
    boolean isInitialized();

    /**
     * 获取此铁砧窗口是否允许移动物品
     *
     * @return 是否允许移动物品
     */
    boolean isAllowMove();

    /**
     * 设置此铁砧窗口是否允许移动物品
     *
     * @param allowMove 是否允许移动物品
     */
    void setAllowMove(boolean allowMove);

    /**
     * 设置此铁砧窗口的输入事件监听器
     *
     * @param inputEvent 输入事件
     */
    void setInput(AnvilWindowEventHandler<AnvilWindowInputEvent> inputEvent);

    /**
     * 设置此铁砧窗口的点击事件监听器
     *
     * @param clickEvent 点击事件
     */
    void setClick(AnvilWindowEventHandler<AnvilWindowClickEvent> clickEvent);

    /**
     * 设置此铁砧窗口的关闭事件监听器
     *
     * @param closeEvent 关闭事件
     */
    void setClose(AnvilWindowEventHandler<AnvilWindowCloseEvent> closeEvent);

    /**
     * 将此铁砧窗口打开给指定玩家
     *
     * @param player 玩家
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     * @throws NMSException 如果打开错误则抛出异常
     */
    void openAnvil(Player player) throws NMSException;

    /**
     * 将此铁砧窗口打开给指定玩家
     *
     * @param moonLakePlayer 月色之湖玩家
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     * @throws NMSException 如果打开错误则抛出异常
     */
    void openAnvil(MoonLakePlayer moonLakePlayer) throws NMSException;

    /**
     * 设置此铁砧窗口指定槽位的物品栈
     *
     * @param slot 槽位
     * @param itemStack 物品栈
     * @throws IllegalArgumentException 如果铁砧窗口槽位对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     * @throws NMSException 如果没有初始化则抛出异常
     */
    void setItem(AnvilWindowSlot slot, ItemStack itemStack) throws NMSException;

    /**
     * 获取此铁砧窗口指定槽位的物品栈
     *
     * @param slot 槽位
     * @return 物品栈
     * @throws IllegalArgumentException 如果铁砧窗口槽位对象为 {@code null} 则抛出异常
     * @throws NMSException 如果没有初始化则抛出异常
     */
    ItemStack getItem(AnvilWindowSlot slot) throws NMSException;

    /**
     * 释放此铁砧窗口对象
     */
    void dispose();
}
