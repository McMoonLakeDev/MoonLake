package com.minecraft.moonlake.nms.anvil;

import org.bukkit.entity.Player;

/**
 * Created by MoonLake on 2016/10/3.
 */
public class AnvilWindowCloseEvent extends AnvilWindowAbstractEvent {

    public AnvilWindowCloseEvent(Player player, AnvilWindow anvilWindow) {

        super(player, anvilWindow);
    }
}
