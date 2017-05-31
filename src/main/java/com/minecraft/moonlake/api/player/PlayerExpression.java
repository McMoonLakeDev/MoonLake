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
 
 
package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.exception.PlayerNotOnlineException;
import com.minecraft.moonlake.manager.PlayerManager;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * <h1>PlayerExpression</h1>
 * 玩家支持库抽象实现类
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v2.0 删除.
 */
@Deprecated
abstract class PlayerExpression implements PlayerLibrary { // TODO 2.0

    /**
     * 玩家支持库抽象实现类构造函数
     */
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

    @Override
    public MoonLakePlayer fromNames(String name) {

        Player target = fromName(name);

        if(target == null || !target.isOnline()) {

            throw new PlayerNotOnlineException(name);
        }
        //return new SimpleMoonLakePlayer(target);
        return PlayerManager.getCache(target);
    }

    @Override
    public MoonLakePlayer fromUUIDs(UUID uuid) {

        Player target = fromUUID(uuid);

        if(target == null || !target.isOnline()) {

            throw new PlayerNotOnlineException(uuid.toString());
        }
        //return new SimpleMoonLakePlayer(target);
        return PlayerManager.getCache(target);
    }

    @Override
    public MoonLakePlayer fromUUIDs(String uuid) {

        Validate.notNull(uuid, "The player uuid object is null.");

        return fromUUIDs(UUID.fromString(uuid));
    }
}
