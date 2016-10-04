package com.minecraft.moonlake.nms.anvil;

import com.minecraft.moonlake.nms.exception.NMSException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * Created by MoonLake on 2016/10/3.
 */
public final class AnvilWindow {

    private final Plugin plugin;
    private Listener listenerClick;
    private Listener listenerClose;
    private Listener listenerMove;
    private boolean allowMove;
    private boolean isInitialized;
    private AnvilWindowEventHandle<AnvilWindowClickEvent> clickEventHandle;
    private AnvilWindowEventHandle<AnvilWindowCloseEvent> closeEventHandle;
    protected Inventory anvilInventory;
    protected Object anvilInventoryHandle;

    public AnvilWindow(Plugin plugin) {

        this.plugin = plugin;
    }

    public Plugin getPlugin() {
        // get plugin
        return plugin;
    }

    private void initialize() {
        // the initialize anvil inventory
        if(!isInitialized) {

            isInitialized = true;
        }
    }

    public boolean isAllowMove() {
        // get anvil window item stack weather allow move
        return allowMove;
    }

    public void setAllowMove(boolean allowMove) {
        // set anvil window item stack weather allow move
        this.allowMove = allowMove;
        this.setAllowMove0(allowMove);
    }

    private void setAllowMove0(boolean flag) {

        if(flag && listenerMove == null) {
            // register anvil move listener
            listenerMove = new Listener() {

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

                    player.updateInventory();
                }
            };
            Bukkit.getServer().getPluginManager().registerEvents(listenerMove, plugin);
        }
        else if(!flag && listenerMove != null) {
            // unregister anvil move listener
            HandlerList.unregisterAll(listenerMove);
            listenerMove = null;
        }
    }

    public void onClick(AnvilWindowEventHandle<AnvilWindowClickEvent> clickEvent) {
        // set anvil click event handle
        if(clickEvent == null) {
            // unregister listener click
            HandlerList.unregisterAll(listenerClick);
            listenerClick = null;
            clickEventHandle = null;
        }
        else {
            // register listener click
            clickEventHandle = clickEvent;
            listenerClick = new Listener() {

                @EventHandler
                public void onAnvilClick(InventoryClickEvent event) {

                    if(event.getClickedInventory() == null) return;
                    if(!event.getClickedInventory().equals(anvilInventory)) return;
                    if(!(event.getWhoClicked() instanceof Player)) return;

                    Player player = (Player) event.getWhoClicked();
                    ItemStack clickItemStack = event.getCurrentItem();
                    AnvilWindowSlot clickSlot = AnvilWindowSlot.fromRawSlot(event.getRawSlot());

                    if(clickSlot == null) return;
                    AnvilWindowClickEvent awce = new AnvilWindowClickEvent(player, AnvilWindow.this, clickSlot, clickItemStack);
                    clickEventHandle.onExecute(awce);
                }
            };
            Bukkit.getServer().getPluginManager().registerEvents(listenerClick, plugin);
        }
    }

    public void onClose(AnvilWindowEventHandle<AnvilWindowCloseEvent> closeEvent) {
        // set anvil close event handle
        if(closeEvent == null) {
            // unregister listener close
            HandlerList.unregisterAll(listenerClose);
            listenerClose = null;
            closeEventHandle = null;
        }
        else {
            // register listener close
            closeEventHandle = closeEvent;
            listenerClose = new Listener() {

                @EventHandler
                public void onAnvilClose(InventoryCloseEvent event) {

                    if(event.getInventory() == null) return;
                    if(!event.getInventory().equals(anvilInventory)) return;
                    if(!(event.getPlayer() instanceof Player)) return;

                    Player player = (Player) event.getPlayer();

                    AnvilWindowCloseEvent awce = new AnvilWindowCloseEvent(player, AnvilWindow.this);
                    closeEventHandle.onExecute(awce);
                }
            };
            Bukkit.getServer().getPluginManager().registerEvents(listenerClose, plugin);
        }
    }

    public void openAnvil(Player player) throws NMSException {
        // open anvil window to player
        AnvilWindowReflect.get().openAnvil(player, this);
        initialize();
    }

    public void setItem(AnvilWindowSlot slot, ItemStack itemStack) {
        // set itemStack to anvil inventory
        if(!isInitialized) {
            // not open anvil
        }
        anvilInventory.setItem(slot.getSlot(), itemStack);
    }

    public ItemStack getItem(AnvilWindowSlot slot) {
        // get itemStack from anvil inventory;
        if(!isInitialized) {
            // not open anvil
        }
        return anvilInventory.getItem(slot.getSlot());
    }

    public void dispose() {
        // dispose close anvil window
        if(listenerMove != null) {
            HandlerList.unregisterAll(listenerMove);
            listenerMove = null;
        }
        if(listenerClick != null) {
            HandlerList.unregisterAll(listenerClick);
            listenerClick = null;
            clickEventHandle = null;
        }
        if(listenerClose != null) {
            HandlerList.unregisterAll(listenerClose);
            listenerClose = null;
            closeEventHandle = null;
        }
    }
}
