package com.minecraft.moonlake.api.player.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;

/**
 * Created by MoonLake on 2016/10/1.
 */
public interface InventoryPlayer extends InventoryHolder {

    /**
     * 获取此玩家的物品栏背包
     *
     * @return 物品栏
     */
    PlayerInventory getInventory();

    /**
     * 更新此玩家的物品栏背包
     */
    void updateInventory();

    /**
     * 将此玩家正在打开的物品栏关闭
     */
    void closeInventory();

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
     * @param itemStack 物品栈
     */
    void setItemInMainHand(ItemStack itemStack);

    /**
     * 设置此玩家的副手中物品
     *
     * @param itemStack 物品栈
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
    @Deprecated
    Map<Integer, ItemStack> addItemStack(ItemStack... itemStacks);

    /**
     * 给玩家背包清除指定物品栈
     *
     * @param itemStacks 物品栈
     * @return 未成功清除到玩家背包的物品栈集合
     */
    @Deprecated
    Map<Integer, ItemStack> removeItemStack(ItemStack... itemStacks);
}
