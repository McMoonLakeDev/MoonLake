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


package com.minecraft.moonlake.api.anvil;

import com.minecraft.moonlake.validate.Validate;
import org.bukkit.plugin.Plugin;

/**
 * <h1>AnvilWindowFactory</h1>
 * 铁砧窗口工厂类
 *
 * @version 1.0
 * @author Month_Light
 */
public class AnvilWindowFactory {

    private AnvilWindowFactory() {
    }

    /**
     * 获取铁砧窗口 AnvilWindow 实例对象
     *
     * @param plugin 插件
     * @return AnvilWindow
     * @throws IllegalArgumentException 如果插件对象为 {@code null} 则抛出异常
     */
    public static AnvilWindow newAnvilWindow(Plugin plugin) {

        Validate.notNull(plugin, "The plugin object is null.");

        return new AnvilWindowExpression(plugin);
    }
}
