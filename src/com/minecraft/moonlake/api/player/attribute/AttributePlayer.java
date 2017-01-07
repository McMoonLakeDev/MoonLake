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


package com.minecraft.moonlake.api.player.attribute;

import com.minecraft.moonlake.api.entity.AttributeType;

/**
 * <h1>AttributePlayer</h1>
 * 特殊属性玩家接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface AttributePlayer {

    /**
     * 获取此玩家指定特殊属性
     *
     * @param type 特殊属性类型
     * @return 特殊属性
     * @throws IllegalArgumentException 如果特殊属性类型对象为 {@code null} 则抛出异常
     * @throws com.minecraft.moonlake.exception.IllegalBukkitVersionException 如果特殊属性类型不支持 Bukkit 版本则抛出异常
     */
    Attribute getAttribute(AttributeType type);
}
