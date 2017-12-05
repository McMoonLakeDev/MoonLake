/*
 * Copyright (C) 2016-Present The MoonLake (mcmoonlake@hotmail.com)
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

package com.mcmoonlake.api.chat

import com.mcmoonlake.api.Valuable
import java.util.regex.Pattern

/**
 * * ChatColor (聊天颜色)
 *
 * @see [Valuable]
 * @author Bukkit, lgou2w
 * @since 2.0
 */
enum class ChatColor(
        /**
         * * Enum color code.
         * * 枚举颜色代码.
         */
        val code: Char,
        /**
         * * Whether this chat color is a format character.
         * * 此聊天颜色是否为格式符.
         */
        val isFormat: Boolean = false) : Valuable<Char> {

    /** enums */

    /**
     * * Black
     * * 黑色
     */
    BLACK('0'),
    /**
     * * Dark Blue
     * * 深蓝色
     */
    DARK_BLUE('1'),
    /**
     * * Dark Green
     * * 深绿色
     */
    DARK_GREEN('2'),
    /**
     * * Dark Aqua
     * * 深青色
     */
    DARK_AQUA('3'),
    /**
     * * Dark Red
     * * 深红色
     */
    DARK_RED('4'),
    /**
     * * Dark Purple
     * * 深紫色
     */
    DARK_PURPLE('5'),
    /**
     * * Gold
     * * 金色
     */
    GOLD('6'),
    /**
     * * Gray
     * * 灰色
     */
    GRAY('7'),
    /**
     * * Dark Gray
     * * 深灰色
     */
    DARK_GRAY('8'),
    /**
     * * Blue
     * * 蓝色
     */
    BLUE('9'),
    /**
     * * Green
     * * 绿色
     */
    GREEN('a'),
    /**
     * * Aqua
     * * 青色
     */
    AQUA('b'),
    /**
     * * Red
     * * 红色
     */
    RED('c'),
    /**
     * * Light Purple
     * * 浅紫色
     */
    LIGHT_PURPLE('d'),
    /**
     * * Yellow
     * * 黄色
     */
    YELLOW('e'),
    /**
     * * White
     * * 白色
     */
    WHITE('f'),

    /**
     * * Format: Obfuscated
     * * 格式符: 随机字符
     */
    OBFUSCATED('k', true),
    /**
     * * Format: Bold
     * * 格式符: 粗体
     */
    BOLD('l', true),
    /**
     * * Format: Strikethrough
     * * 格式符: 删除线
     */
    STRIKETHROUGH('m', true),
    /**
     * * Format: Underline
     * * 格式符: 下划线
     */
    UNDERLINE('n', true),
    /**
     * * Format: Italic
     * * 格式符: 斜体
     */
    ITALIC('o', true),
    /**
     * * Special: Reset
     * * 特殊符: 重置
     */
    RESET('r'),
    ;

    override fun value(): Char
            = code

    override fun toString(): String
            = "$CHAR_COLOR$code"

    companion object {

        /**
         * @see [org.bukkit.ChatColor.COLOR_CHAR]
         */
        @JvmStatic
        private val CHAR_COLOR = '§'

        /**
         * @see [org.bukkit.ChatColor.STRIP_COLOR_PATTERN]
         */
        @JvmStatic
        private val STRIP_COLOR = Pattern.compile("(?i)$CHAR_COLOR[0-9A-FK-OR]")

        /**
         * @see [org.bukkit.ChatColor.stripColor]
         */
        @JvmStatic
        @JvmName("stripColor")
        fun stripColor(input: String): String
                = STRIP_COLOR.matcher(input).replaceAll("")

        /**
         * @see [org.bukkit.ChatColor.translateAlternateColorCodes]
         */
        @JvmStatic
        @JvmName("translateAlternateColorCodes")
        fun translateAlternateColorCodes(altColorChar: Char, textToTranslate: String): String {
            val chars = textToTranslate.toCharArray()
            for(i in 0 until chars.size - 1) {
                if(chars[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(chars[i + 1]) > -1) {
                    chars[i] = CHAR_COLOR
                    chars[i + 1] = Character.toLowerCase(chars[i + 1])
                }
            }
            return String(chars)
        }
    }
}
