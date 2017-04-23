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
 
 
package com.minecraft.moonlake.api.player.ability;

import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
import com.minecraft.moonlake.exception.PlayerNotOnlineException;
import com.minecraft.moonlake.manager.PlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * <h1>ItemCooldown</h1>
 * 玩家物品栈冷却能力接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v2.0 删除.
 */
@Deprecated
public interface ItemCooldown { // TODO 2.0

    /**
     * 给指定玩家设置物品栈冷却时间
     *
     * @param player 玩家名
     * @param material 物品栈类型
     * @param tick 时间 Tick (1s = 20tick)
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     * @throws IllegalBukkitVersionException 如果服务器 Bukkit 版本不支持则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link PlayerManager#setItemCoolDown(Player, Material, int)}
     */
    @Deprecated
    void setItemCooldown(String player, Material material, int tick);

    /**
     * 获取指定玩家物品栈类型是否拥有冷却时间
     *
     * @param player 玩家名
     * @param material 物品栈类型
     * @return true 则物品栈类型拥有冷却时间
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     * @throws IllegalBukkitVersionException 如果服务器 Bukkit 版本不支持则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link PlayerManager#hasItemCoolDown(Player, Material)}
     */
    @Deprecated
    boolean hasItemCooldown(String player, Material material);
}
