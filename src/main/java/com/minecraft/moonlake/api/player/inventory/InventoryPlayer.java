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
 
 
package com.minecraft.moonlake.api.player.inventory;

import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;

/**
 * <h1>InventoryPlayer</h1>
 * 玩家物品栏接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface InventoryPlayer extends InventoryHolder {

    /**
     * 获取此玩家的物品栏背包
     *
     * @return 物品栏
     */
    PlayerInventory getInventory();

    /**
     * 获取此玩家的末影箱物品栏
     *
     * @return 末影箱物品栏
     */
    Inventory getEnderChest();

    /**
     * 更新此玩家的物品栏背包
     */
    void updateInventory();

    /**
     * 将此玩家正在打开的物品栏关闭
     */
    void closeInventory();

    /**
     * 获取此玩家的手中物品 (注: 如果 Bukkit 版本为 1.9 或更高不建议使用此函数)
     *
     * @return 手中物品
     */
    ItemStack getItemInHand();

    /**
     * 获取此玩家的主手中物品
     *
     * @return 主手中物品
     * @throws IllegalBukkitVersionException 如果 Bukkit 服务器版本低于 1.9 则抛出异常
     */
    ItemStack getItemInMainHand();

    /**
     * 获取此玩家的副手中物品
     *
     * @return 副手中物品
     * @throws IllegalBukkitVersionException 如果 Bukkit 服务器版本低于 1.9 则抛出异常
     */
    ItemStack getItemInOffHand();

    /**
     * 获取此玩家的鼠标中物品
     *
     * @return 鼠标中物品
     */
    ItemStack getItemOnCursor();

    /**
     * 设置此玩家的手中物品 (注: 如果 Bukkit 版本为 1.9 或更高不建议使用此函数)
     *
     * @param itemStack 物品栈
     */
    void setItemInHand(ItemStack itemStack);

    /**
     * 设置此玩家的主手中物品
     *
     * @param itemStack 物品栈
     * @throws IllegalBukkitVersionException 如果 Bukkit 服务器版本低于 1.9 则抛出异常
     */
    void setItemInMainHand(ItemStack itemStack);

    /**
     * 设置此玩家的副手中物品
     *
     * @param itemStack 物品栈
     * @throws IllegalBukkitVersionException 如果 Bukkit 服务器版本低于 1.9 则抛出异常
     */
    void setItemInOffHand(ItemStack itemStack);

    /**
     * 给此玩家打开指定物品栏对象
     *
     * @param inventory 物品栏对象
     */
    void openInventory(Inventory inventory);

    /**
     * 获取此玩家打开的上面物品栏对象
     *
     * @return 上面物品栏对象
     */
    Inventory getTopInventory();

    /**
     * 给玩家背包给予指定物品栈
     *
     * @param itemStacks 物品栈
     * @return 未成功添加到玩家背包的物品栈集合
     */
    Map<Integer, ItemStack> addItemStack(ItemStack... itemStacks);

    /**
     * 给玩家背包清除指定物品栈
     *
     * @param itemStacks 物品栈
     * @return 未成功清除到玩家背包的物品栈集合
     */
    Map<Integer, ItemStack> removeItemStack(ItemStack... itemStacks);
}
