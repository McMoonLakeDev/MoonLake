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

package com.minecraft.moonlake.api.chat

import com.minecraft.moonlake.api.Valuable
import java.util.regex.Pattern

enum class ChatColor(val code: Char, val isFormat: Boolean = false) : Valuable<Char> {

    /** enums */

    BLACK('0'),
    DARK_BLUE('1'),
    DARK_GREEN('2'),
    DARK_AQUA('3'),
    DARK_RED('4'),
    DARK_PURPLE('5'),
    GOLD('6'),
    GRAY('7'),
    DARK_GRAY('8'),
    BLUE('9'),
    GREEN('a'),
    AQUA('b'),
    RED('c'),
    LIGHT_PURPLE('d'),
    YELLOW('e'),
    WHITE('f'),
    OBFUSCATED('k', true),
    BOLD('l', true),
    STRIKETHROUGH('m', true),
    UNDERLINE('n', true),
    ITALIC('o', true),
    RESET('r'),
    ;

    override fun value(): Char
            = code

    override fun toString(): String {
        return "$CHAR_COLOR$code"
    }

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
