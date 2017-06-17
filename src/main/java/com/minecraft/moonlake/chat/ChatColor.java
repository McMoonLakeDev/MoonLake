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


package com.minecraft.moonlake.chat;

/**
 * <h1>ChatColor</h1>
 * 聊天组件颜色
 *
 * @version 1.0
 * @author Month_Light
 */
public enum ChatColor {

    /**
     * <div style='background-color: rgb(0, 0, 0)'><span style='color: white'>\u00260 黑色</span></div>
     */
    BLACK('0'),
    /**
     * <div style='background-color: rgb(0, 0, 170)'><span style='color: white'>\u00261 深蓝</span></div>
     */
    DARK_BLUE('1'),
    /**
     * <div style='background-color: rgb(0, 170, 0)'><span style='color: white'>\u00262 深绿</span></div>
     */
    DARK_GREEN('2'),
    /**
     * <div style='background-color: rgb(0, 170, 170)'><span style='color: white'>\u00263 深青</span></div>
     */
    DARK_AQUA('3'),
    /**
     * <div style='background-color: rgb(170, 0, 0)'><span style='color: white'>\u00264 深红</span></div>
     */
    DARK_RED('4'),
    /**
     * <div style='background-color: rgb(170, 0, 170)'><span style='color: white'>\u00265 深紫</span></div>
     */
    DARK_PURPLE('5'),
    /**
     * <div style='background-color: rgb(255, 170, 0)'><span style='color: white'>\u00266 橙色</span></div>
     */
    GOLD('6'),
    /**
     * <div style='background-color: rgb(85, 85, 85)'><span style='color: white'>\u00267 灰色</span></div>
     */
    GRAY('7'),
    /**
     * <div style='background-color: rgb(170, 170, 170)'><span style='color: white'>\u00268 深灰</span></div>
     */
    DARK_GRAY('8'),
    /**
     * <div style='background-color: rgb(85, 85, 255)'><span style='color: white'>\u00269 蓝色</span></div>
     */
    BLUE('9'),
    /**
     * <div style='background-color: rgb(85, 255, 85)'><span style='color: white'>\u0026a 绿色</span></div>
     */
    GREEN('a'),
    /**
     * <div style='background-color: rgb(85, 255, 255)'><span style='color: white'>\u0026b 青色</span></div>
     */
    AQUA('b'),
    /**
     * <div style='background-color: rgb(255, 85, 85)'><span style='color: white'>\u0026c 红色</span></div>
     */
    RED('c'),
    /**
     * <div style='background-color: rgb(255, 85, 255)'><span style='color: white'>\u0026d 紫色</span></div>
     */
    LIGHT_PURPLE('d'),
    /**
     * <div style='background-color: rgb(255, 255, 85)'><span style='color: black'>\u0026e 黄色</span></div>
     */
    YELLOW('e'),
    /**
     * <div style='background-color: rgb(255, 255, 255)'><span style='color: black'>\u0026f 白色</span></div>
     */
    WHITE('f'),
    /**
     * <div style='background-color: rgb(255, 255, 255)'><span style='color: black'>\u0026k 随机字符</span></div>
     */
    OBFUSCATED('k', true),
    /**
     * <div style='background-color: rgb(255, 255, 255)'><span style='color: black'><b>\u0026l 文字加粗</b></span></div>
     */
    BOLD('l', true),
    /**
     * <div style='background-color: rgb(255, 255, 255)'><span style='color: black'><s>\u0026m 文字删除线</s></span></div>
     */
    STRIKETHROUGH('m', true),
    /**
     * <div style='background-color: rgb(255, 255, 255)'><span style='color: black'><u>\u0026n 文字下划线</u></span></div>
     */
    UNDERLINE('n', true),
    /**
     * <div style='background-color: rgb(255, 255, 255)'><span style='color: black'><i>\u0026o 文字斜体</i></span></div>
     */
    ITALIC('o', true),
    /**
     * <div style='background-color: rgb(255, 255, 255)'><span style='color: black'>\u0026r 重置</span></div>
     */
    RESET('r'),
    ;

    private final char code;
    private final boolean format;

    /**
     * 聊天组件颜色构造函数
     *
     * @param code 颜色码
     */
    ChatColor(char code) {
        this(code, false);
    }

    /**
     * 聊天组件颜色构造函数
     *
     * @param code 颜色码
     * @param format 是否文字格式符
     */
    ChatColor(char code, boolean format) {
        this.code = code;
        this.format = format;
    }

    /**
     * 获取此聊天组件颜色的颜色码
     *
     * @return 颜色嘛
     */
    public char getCode() {
        return code;
    }

    /**
     * 获取此聊天组件颜色是否为文字格式符
     *
     * @return 是否文字格式符
     */
    public boolean isFormat() {
        return format;
    }

    /**
     * 从名称返回聊天组件颜色
     *
     * @param name 名称
     * @return ChatColor
     */
    public static ChatColor fromName(String name) {
        return ChatColor.valueOf(name.toUpperCase());
    }
}
