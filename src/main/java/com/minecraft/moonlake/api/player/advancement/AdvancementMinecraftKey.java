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

import com.minecraft.moonlake.validate.Validate;
import org.bukkit.NamespacedKey;

/**
 * <h1>AdvancementMinecraftKey</h1>
 * Bukkit 1.12+ 玩家成就 Minecraft 键类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @see AdvancementKey
 */
public final class AdvancementMinecraftKey implements AdvancementKey {

    private NamespacedKey namespacedKey;

    /**
     * Bukkit 1.12+ 玩家成就 Minecraft 键类构造函数
     *
     * @param key 键名称
     * @throws IllegalArgumentException 如果键名称对象为 {@code null} 则抛出异常
     */
    public AdvancementMinecraftKey(String key) {
        this.namespacedKey = NamespacedKey.minecraft(Validate.checkNotNull(key));
    }

    @Override
    public String getNamespace() {
        return namespacedKey.getNamespace();
    }

    @Override
    public String getKey() {
        return namespacedKey.getKey();
    }
}
