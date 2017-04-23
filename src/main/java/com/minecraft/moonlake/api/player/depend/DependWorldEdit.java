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


package com.minecraft.moonlake.api.player.depend;

import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.exception.CannotDependException;
import org.bukkit.entity.Player;

/**
 * <h1>DependWorldEdit</h1>
 * 依赖创世神插件接口 # 依赖 <a href="https://github.com/sk89q/worldedit" target="_blank">WorldEdit</a> 插件
 *
 * @version 1.0
 * @author Month_Light
 * @see Depend
 */
public interface DependWorldEdit extends Depend {

    /**
     * 获取指定玩家的 WorldEdit 选择区域包装对象
     *
     * @param player 玩家
     * @return WorldEditSelection
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     */
    WorldEditSelection getSelection(Player player) throws CannotDependException;

    /**
     * 获取指定玩家的 WorldEdit 选择区域包装对象
     *
     * @param moonLakePlayer 月色之湖玩家
     * @return WorldEditSelection
     * @throws CannotDependException 如果前置插件无法依赖则抛出异常
     */
    WorldEditSelection getSelection(MoonLakePlayer moonLakePlayer) throws CannotDependException;
}
