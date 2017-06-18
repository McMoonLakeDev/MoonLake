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
 
 
package com.minecraft.moonlake.api.fancy;

import com.minecraft.moonlake.api.chat.ChatColor;

/**
 * <h1>FancyMessageStyle</h1>
 * 花式消息样式
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link ChatColor}
 */
@Deprecated
public enum FancyMessageStyle {

    /**
     * 花式消息样式类型: 模糊
     */
    OBFUSCATED("Obfuscated"),
    /**
     * 花式消息样式类型: 加粗
     */
    BOLD("Bold"),
    /**
     * 花式消息样式类型: 删除线
     */
    STRIKETHROUGH("Strikethrough"),
    /**
     * 花式消息样式类型: 下划线
     */
    UNDERLINED("Underlined"),
    /**
     * 花式消息样式类型: 斜体
     */
    ITALIC("Italic"),
    ;

    private final String type;

    /**
     * 花式消息样式构造函数
     *
     * @param type 类型名
     */
    FancyMessageStyle(String type) {

        this.type = type;
    }

    /**
     * 获取花式消息样式的类型
     *
     * @return 类型名
     */
    public String getType() {

        return type;
    }
}
