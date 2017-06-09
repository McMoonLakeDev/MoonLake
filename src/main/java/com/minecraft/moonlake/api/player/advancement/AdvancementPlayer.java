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

import com.minecraft.moonlake.exception.IllegalBukkitVersionException;

import javax.annotation.Nullable;

/**
 * <h1>AdvancementPlayer</h1>
 * Bukkit 1.12+ 成就玩家接口（详细doc待补充...）
 *
 * <p style='color: red;'>注: 此接口的内容只兼容 Bukkit 1.12+ 版本服务端, 其他版本调用将会抛出 {@link IllegalBukkitVersionException} 异常</p>
 *
 * @version 1.0.1
 * @author Month_Light
 */
public interface AdvancementPlayer {

    /**
     * 获取此玩家指定成就对象的成就进度
     *
     * @param advancement 成就对象
     * @return 成就进度 | 不存在的成就则返回 {@code null}
     * @throws IllegalArgumentException 如果成就对象为 {@code null} 则抛出异常
     * @throws IllegalBukkitVersionException 如果 Bukkit 服务器版本低于 1.12 则抛出异常
     */
    @Nullable
    AdvancementProgress getAdvancementProgress(Advancement advancement);

    /**
     * 获取此玩家指定成就键的成就进度
     *
     * @param key 成就键
     * @return 成就进度 | 不存在的成就则返回 {@code null}
     * @throws IllegalArgumentException 如果成就键对象为 {@code null} 则抛出异常
     * @throws IllegalBukkitVersionException 如果 Bukkit 服务器版本低于 1.12 则抛出异常
     */
    @Nullable
    AdvancementProgress getAdvancementProgress(AdvancementKey key);
}
