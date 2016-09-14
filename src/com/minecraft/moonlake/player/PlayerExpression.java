package com.minecraft.moonlake.player;

import com.minecraft.moonlake.api.player.PlayerLibrary;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by MoonLake on 2016/9/14.
 */
public abstract class PlayerExpression implements PlayerLibrary {

    public PlayerExpression() {

    }

    @Override
    public Player fromName(String name) {

        Validate.notNull(name, "The player name object is null.");

        return Bukkit.getServer().getPlayer(name);
    }

    @Override
    public Player fromUUID(UUID uuid) {

        Validate.notNull(uuid, "The player uuid object is null.");

        return Bukkit.getServer().getPlayer(uuid);
    }

    @Override
    public Player fromUUID(String uuid) {

        Validate.notNull(uuid, "The player uuid object is null.");

        return fromUUID(UUID.fromString(uuid));
    }
}
