package com.minecraft.moonlake.nms.anvil;

import org.bukkit.entity.Player;

/**
 * Created by MoonLake on 2016/10/3.
 */
public interface AnvilWindowEvent {

    Player getPlayer();

    AnvilWindow getAnvilWindow();
}
