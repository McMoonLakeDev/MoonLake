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
 * <h1>AnvilWindow</h1>
 * 铁砧窗口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public final class AnvilWindow {

    private final Plugin plugin;
    private Listener listenerClick;
    private Listener listenerClose;
    private Listener listenerMove;
    private boolean allowMove;
    private boolean isInitialized;
    private AnvilWindowEventHandler<AnvilWindowClickEvent> clickEventHandle;
    private AnvilWindowEventHandler<AnvilWindowCloseEvent> closeEventHandle;
    protected Inventory anvilInventory;
    protected Object anvilInventoryHandle;

    /**
     * 铁砧窗口类构造函数
     *
     * @param plugin JavaPlugin
     */
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
            registerListener(listenerMove);
        }
        else if(!flag && listenerMove != null) {
            // unregister anvil move listener
            disposeMove();
        }
    }

    public void onClick(AnvilWindowEventHandler<AnvilWindowClickEvent> clickEvent) {
        // set anvil click event handle
        if(clickEvent == null) {
            // unregister listener click
            disposeClick();
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
            registerListener(listenerClick);
        }
    }

    public void onClose(AnvilWindowEventHandler<AnvilWindowCloseEvent> closeEvent) {
        // set anvil close event handle
        if(closeEvent == null) {
            // unregister listener close
            disposeClose();
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
            registerListener(listenerClose);
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

    private void registerListener(Listener listener) {

        if(listener != null) {

            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

    public void dispose() {
        // dispose close anvil window
        disposeMove();
        disposeClick();
        disposeClose();
    }

    private void disposeMove() {

        disposeListener(listenerMove);
        listenerMove = null;
    }

    private void disposeClick() {

        disposeListener(listenerClick);
        listenerClick = null;
        clickEventHandle = null;
    }

    private void disposeClose() {

        disposeListener(listenerClose);
        listenerClose = null;
        closeEventHandle = null;
    }

    private void disposeListener(Listener listener) {

        if(listener != null) {
            HandlerList.unregisterAll(listener);
        }
    }
}
