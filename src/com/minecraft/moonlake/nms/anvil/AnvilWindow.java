package com.minecraft.moonlake.nms.anvil;

import com.minecraft.moonlake.nms.exception.NMSException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * Created by MoonLake on 2016/10/3.
 */
public class AnvilWindow {

    private Inventory inventory;
    private Listener listenerClick;
    private Listener listenerClose;
    private Listener listenerMove;
    private boolean allowMove;

    public AnvilWindow() {

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

                }
            };
        }
        else if(!flag && listenerMove != null) {
            // unregister anvil move listener
            HandlerList.unregisterAll(listenerMove);
        }
    }

    public void openAnvil(Player player) throws NMSException {
        // open anvil window to player

    }

    public  void onClick(AnvilWindowEventHandle<AnvilWindowClickEvent> event) {
        // set anvil click event handle

    }

    public  void onClose(AnvilWindowEventHandle<AnvilWindowCloseEvent> event) {
        // set anvil close event handle

    }

    public void dispose() {
        // dispose close anvil window

    }
}
