/*
 * Copyright (C) 2017 The MoonLake Authors
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

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.player.depend.DependPluginPlayerAbstract;
import com.minecraft.moonlake.api.player.depend.DependWorldEdit;
import com.minecraft.moonlake.api.player.depend.WorldEditSelection;
import com.minecraft.moonlake.exception.CannotDependException;
import com.minecraft.moonlake.exception.CannotDependVersionException;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * <h1>DependWorldEditPlayer</h1>
 * 依赖创世神插件玩家实现类 # 依赖 <a href="https://github.com/sk89q/worldedit" target="_blank">WorldEdit</a> 插件
 *
 * @version 1.0
 * @author Month_Light
 */
class DependWorldEditPlayer extends DependPluginPlayerAbstract<WorldEditPlugin> implements DependWorldEdit {

    public DependWorldEditPlayer() throws CannotDependException, CannotDependVersionException {

        super((WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit"));

        if(getOwn() == null) {

            throw new CannotDependException("The cannot depend 'WorldEdit' plugin exception.");
        }
        // 检查 WorldEdit 插件版本, 本依赖最低需要 6.0 版本
        String version = getPluginVersion();

        if(version.compareTo("6.0") < 0) {
            // 服务端加载的 WorldEdit 插件版本小于 6.0 则抛出异常
            throw new CannotDependVersionException("The depend 'WorldEdit' plugin, but version less than 6.0 exception.");
        }

        MoonLakeAPI.getLogger().info("Success hook 'WorldEdit' plugin, 'WorldEditPlayer' interface be use.");
    }

    /**
     * 获取指定玩家的 WorldEdit 选择区域对象
     *
     * @param player 玩家
     * @return Selection
     */
    protected Selection getSelection0(Player player) {

        return getOwn().getSelection(player);
    }

    @Override
    public WorldEditSelection getSelection(Player player) {

        Selection selection = getSelection0(player);
        return selection != null ? new DependWorldEditPlayerSelectionExpression(selection) : null;
    }

    @Override
    public WorldEditSelection getSelection(MoonLakePlayer player) {

        return getSelection(player.getBukkitPlayer());
    }
}
