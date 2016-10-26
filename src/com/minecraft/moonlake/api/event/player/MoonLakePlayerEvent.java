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


package com.minecraft.moonlake.api.event.player;

import com.minecraft.moonlake.api.event.MoonLakeEvent;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.manager.PlayerManager;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

public abstract class MoonLakePlayerEvent extends MoonLakeEvent {

    private final MoonLakePlayer moonLakePlayer;

    @Deprecated
    public MoonLakePlayerEvent(Player player) {

        this(PlayerManager.adapter(player));
    }

    public MoonLakePlayerEvent(MoonLakePlayer moonLakePlayer) {

        Validate.notNull(moonLakePlayer, "The moonlake player object is null.");

        this.moonLakePlayer = moonLakePlayer;
    }

    public final MoonLakePlayer getPlayer() {

        return moonLakePlayer;
    }

    public final Player getBukkitPlayer() {

        return moonLakePlayer.getBukkitPlayer();
    }

    public final String getName() {

        return moonLakePlayer.getName();
    }
}
