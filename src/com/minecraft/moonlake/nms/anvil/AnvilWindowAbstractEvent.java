package com.minecraft.moonlake.nms.anvil;

import org.bukkit.entity.Player;

/**
 * Created by MoonLake on 2016/10/3.
 */
public abstract class AnvilWindowAbstractEvent implements AnvilWindowEvent {

    private final Player player;
    private final AnvilWindow anvilWindow;

    public AnvilWindowAbstractEvent(Player player, AnvilWindow anvilWindow) {

        this.player = player;
        this.anvilWindow = anvilWindow;
    }

    public Player getPlayer() {

        return player;
    }

    public AnvilWindow getAnvilWindow() {

        return anvilWindow;
    }
}
