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

import com.minecraft.moonlake.exception.CannotDependException;

import javax.annotation.Nullable;

/**
 * <h1>WorldEditPlayer</h1>
 * 创世神玩家接口 # 依赖 <a href="https://github.com/sk89q/worldedit" target="_blank">WorldEdit</a> 插件
 *
 * @version 1.0
 * @author Month_Light
 */
public interface WorldEditPlayer {

    /**
     * 获取此玩家的创世神选择区域
     *
     * @return 创世神选择区域
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    @Nullable
    WorldEditSelection getWorldEditSelection() throws CannotDependException;
}
