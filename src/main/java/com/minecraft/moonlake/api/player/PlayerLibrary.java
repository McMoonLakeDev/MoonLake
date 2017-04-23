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

import com.minecraft.moonlake.api.player.ability.AbilityLibrary;
import com.minecraft.moonlake.exception.PlayerNotOnlineException;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * <h1>PlayerLibrary</h1>
 * 玩家支持库接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @see AbilityLibrary
 * @see NMSPlayerLibrary
 * @deprecated 已过时, 将于 v2.0 删除.
 */
@Deprecated
public interface PlayerLibrary extends NMSPlayerLibrary, AbilityLibrary { // TODO 2.0

    /**
     * 获取 Player 对象从名称
     *
     * @param name 玩家名
     * @return Player
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
    Player fromName(String name);

    /**
     * 获取 Player 对象从 UUID
     *
     * @param uuid UUID
     * @return Player
     * @throws IllegalArgumentException 如果 {@code UUID} 对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
    Player fromUUID(UUID uuid);

    /**
     * 获取 Player 对象从 UUID 字符串
     *
     * @param uuid UUID 字符串
     * @return Player
     * @see PlayerLibrary#fromUUID(UUID)
     * @throws IllegalArgumentException 如果 {@code UUID} 字符串对象为 {@code null} 则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
    Player fromUUID(String uuid);

    /**
     * 获取 MoonLakePlayer 对象从名称
     *
     * @param name 玩家名
     * @return MoonLakePlayer
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家没有在线则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
    MoonLakePlayer fromNames(String name);

    /**
     * 获取 MoonLakePlayer 对象从 UUID
     *
     * @param uuid UUID
     * @return MoonLakePlayer
     * @throws IllegalArgumentException 如果 {@code UUID} 对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家没有在线则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
    MoonLakePlayer fromUUIDs(UUID uuid);

    /**
     * 获取 MoonLakePlayer 对象从 UUID 字符串
     *
     * @param uuid UUID 字符串
     * @return MoonLakePlayer
     * @see PlayerLibrary#fromUUIDs(UUID)
     * @throws IllegalArgumentException 如果 {@code UUID} 字符串对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家没有在线则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
    MoonLakePlayer fromUUIDs(String uuid);
}
