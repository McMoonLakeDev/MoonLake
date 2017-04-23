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

import com.minecraft.moonlake.api.player.ability.ItemCooldown;
import com.minecraft.moonlake.exception.PlayerNotOnlineException;
import com.minecraft.moonlake.manager.PlayerManager;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * <h1>ItemCooldownExpression</h1>
 * 玩家物品栈冷却能力接口实现类
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v2.0 删除.
 */
@Deprecated
class ItemCooldownExpression implements ItemCooldown { // TODO 2.0

    /**
     * 玩家物品栈冷却能力接口实现类构造函数
     *
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
    public ItemCooldownExpression() {
    }

    @Override
    public void setItemCooldown(String player, Material material, int tick) {

        Validate.notNull(player, "The player string object is null.");
        Validate.notNull(material, "The material object is null.");

        Player target = PlayerLibraryFactorys.player().fromName(player);

        if(target == null || !target.isOnline()) {

            throw new PlayerNotOnlineException(player);
        }
        PlayerManager.setItemCoolDown(target, material, tick);
    }

    @Override
    public boolean hasItemCooldown(String player, Material material) {

        Validate.notNull(player, "The player string object is null.");
        Validate.notNull(material, "The material object is null.");

        Player target = PlayerLibraryFactorys.player().fromName(player);

        if(target == null || !target.isOnline()) {

            throw new PlayerNotOnlineException(player);
        }
        return PlayerManager.hasItemCoolDown(target, material);
    }
}
