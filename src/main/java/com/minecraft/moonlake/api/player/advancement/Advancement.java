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


package com.minecraft.moonlake.api.player.advancement;

import java.util.Collection;

/**
 * <h1>Advancement</h1>
 * Bukkit 1.12+ 玩家成就接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface Advancement {

    /**
     * 获取此成就的成就键对象
     *
     * @return 成就键
     */
    AdvancementKey getKey();

    /**
     * 获取此成就的准则集合
     *
     * @return 准则集合
     */
    Collection<String> getCriteria();
}
