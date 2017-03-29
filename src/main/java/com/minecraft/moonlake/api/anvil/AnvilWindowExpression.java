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

import com.minecraft.moonlake.api.event.MoonLakeListener;
import com.minecraft.moonlake.api.nms.exception.NMSException;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.event.EventHelper;
import com.minecraft.moonlake.manager.PlayerManager;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * <h1>AnvilWindowExpression</h1>
 * 铁砧窗口接口实现类
 *
 * @version 1.1
 * @author Month_Light
 */
class AnvilWindowExpression implements AnvilWindow {

    private final Plugin plugin;
    private MoonLakeListener listenerClick;
    private MoonLakeListener listenerClose;
    private MoonLakeListener listenerMove;
    private boolean allowMove;
    private boolean isInitialized;
    private AnvilWindowEventHandler<AnvilWindowInputEvent> inputEventHandle;
    private AnvilWindowEventHandler<AnvilWindowClickEvent> clickEventHandle;
    private AnvilWindowEventHandler<AnvilWindowCloseEvent> closeEventHandle;
    Inventory anvilInventory;
    Object anvilInventoryHandle;

    /**
     * 铁砧窗口类构造函数
     *
     * @param plugin 插件
     */
    public AnvilWindowExpression(Plugin plugin) {

        this.plugin = plugin;
        this.setAllowMove(true);
    }


    @Override
    public Plugin getPlugin() {

        return plugin;
    }

    /**
     * 初始化此铁砧窗口
     */
    private void initialize() {

        if(!isInitialized) {

            isInitialized = true;
        }
    }

    @Override
    public boolean isInitialized() {

        return isInitialized;
    }

    @Override
    public boolean isAllowMove() {

        return allowMove;
    }

    @Override
    public void setAllowMove(boolean allowMove) {

        this.allowMove = allowMove;
        this.setAllowMove0(allowMove);
    }

    /**
     * 设置此铁砧窗口是否允许移动物品
     *
     * @param flag 是否允许移动物品
     */
    private void setAllowMove0(boolean flag) {

        if(!flag && listenerMove == null) {

            listenerMove = new MoonLakeListener() {

                @EventHandler
                public void onAnvilMove(InventoryClickEvent event) {

                    if(event.getClickedInventory() == null) return;
                    if(event.getClickedInventory().getType() != InventoryType.PLAYER) return;
                    if(!(event.getWhoClicked() instanceof Player)) return;

                    Player player = (Player) event.getWhoClicked();

                    if(player.getOpenInventory() == null) return;
                    if(player.getOpenInventory().getTopInventory() == null) return;
                    if(!player.getOpenInventory().getTopInventory().equals(anvilInventory)) return;

                    event.setResult(Event.Result.DENY);
                    event.setCancelled(true);

                    PlayerManager.updateInventorySafe(getPlugin(), player);
                }
            };
            registerListener(listenerMove);
        }
        else if(flag && listenerMove != null) {

            disposeMove();
        }
    }

    @Override
    public void setInput(AnvilWindowEventHandler<AnvilWindowInputEvent> inputEvent) {

        if(inputEvent == null) {

            disposeInput();
        }
        else {

            inputEventHandle = inputEvent;
        }
    }

    /**
     * 获取此铁砧窗口的输入事件处理器
     *
     * @return 输入事件处理器
     */
    AnvilWindowEventHandler<AnvilWindowInputEvent> getInputEventHandle() {

        return inputEventHandle;
    }

    @Override
    public void setClick(AnvilWindowEventHandler<AnvilWindowClickEvent> clickEvent) {

        if(clickEvent == null) {

            disposeClick();
        }
        else {

            clickEventHandle = clickEvent;
            listenerClick = new MoonLakeListener() {

                @EventHandler
                public void onAnvilClick(InventoryClickEvent event) {

                    if(event.getClickedInventory() == null) return;
                    if(!event.getClickedInventory().equals(anvilInventory)) return;
                    if(!(event.getWhoClicked() instanceof Player)) return;

                    Player player = (Player) event.getWhoClicked();
                    ItemStack clickItemStack = event.getCurrentItem();
                    AnvilWindowSlot clickSlot = AnvilWindowSlot.fromRawSlot(event.getRawSlot());

                    if(clickSlot == null) return;
                    AnvilWindowClickEvent awce = new AnvilWindowClickEvent(player, AnvilWindowExpression.this, clickSlot, clickItemStack);
                    clickEventHandle.execute(awce);

                    if(awce.isCancelled()) {

                        event.setCancelled(true);
                        PlayerManager.updateInventorySafe(getPlugin(), player);
                    }
                }
            };
            registerListener(listenerClick);
        }
    }

    @Override
    public void setClose(AnvilWindowEventHandler<AnvilWindowCloseEvent> closeEvent) {

        if(closeEvent == null) {

            disposeClose();
        }
        else {

            closeEventHandle = closeEvent;
            listenerClose = new MoonLakeListener() {

                @EventHandler
                public void onAnvilClose(InventoryCloseEvent event) {

                    if(event.getInventory() == null) return;
                    if(!event.getInventory().equals(anvilInventory)) return;
                    if(!(event.getPlayer() instanceof Player)) return;

                    Player player = (Player) event.getPlayer();

                    AnvilWindowCloseEvent awce = new AnvilWindowCloseEvent(player, AnvilWindowExpression.this);
                    closeEventHandle.execute(awce);
                }
            };
            registerListener(listenerClose);
        }
    }

    @Override
    public void openAnvil(Player player) throws NMSException {

        Validate.notNull(player, "The player object is null.");

        AnvilWindowReflect.get().openAnvil(player, this);
        initialize();
    }

    @Override
    public void openAnvil(MoonLakePlayer moonLakePlayer) throws NMSException {

        Validate.notNull(moonLakePlayer, "The moonlake player object is null.");

        openAnvil(moonLakePlayer.getBukkitPlayer());
    }

    @Override
    public void setItem(AnvilWindowSlot slot, ItemStack itemStack) throws NMSException {

        Validate.notNull(slot, "The anvil window slot object is null.");
        Validate.notNull(itemStack, "The itemstack object is null.");

        if(!isInitialized) {

            throw new NMSException("The anvil window not initialize.");
        }
        anvilInventory.setItem(slot.getSlot(), itemStack);
    }

    @Override
    public ItemStack getItem(AnvilWindowSlot slot) {

        Validate.notNull(slot, "The anvil window slot object is null.");

        if(!isInitialized) {

            throw new NMSException("The anvil window not initialize.");
        }
        return anvilInventory.getItem(slot.getSlot());
    }

    /**
     * 注册此铁砧窗口的事件监听器
     *
     * @param listener 监听器
     */
    private void registerListener(MoonLakeListener listener) {

        if(listener != null) {

            EventHelper.registerEvent(listener, plugin);
        }
    }

    @Override
    public void dispose() {

        disposeMove();
        disposeInput();
        disposeClick();
        disposeClose();
    }

    /**
     * 释放此铁砧窗口的移动事件监听器
     */
    private void disposeMove() {

        disposeListener(listenerMove);
        listenerMove = null;
    }

    /**
     * 释放此铁砧窗口的输入事件处理器
     */
    private void disposeInput() {

        inputEventHandle = null;
    }

    /**
     * 释放此铁砧窗口的点击事件监听器
     */
    private void disposeClick() {

        disposeListener(listenerClick);
        listenerClick = null;
        clickEventHandle = null;
    }

    /**
     * 释放此铁砧窗口的关闭事件监听器
     */
    private void disposeClose() {

        disposeListener(listenerClose);
        listenerClose = null;
        closeEventHandle = null;
    }

    /**
     * 释放此铁砧窗口指定事件监听器
     *
     * @param listener 监听器
     */
    private void disposeListener(MoonLakeListener listener) {

        if(listener != null) {
            EventHelper.unregisterAll(listener);
        }
    }
}
