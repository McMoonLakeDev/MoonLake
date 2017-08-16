/*
 * Copyright (C) 2016-2017 The MoonLake (mcmoonlake@hotmail.com)
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

@file:JvmName("MoonLakeAPI")

package com.minecraft.moonlake.api

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.World

/** MoonLake API Extended Function */

fun <T> T?.notNull(message: String = "验证的对象值为 null 时异常."): T {
    if(this == null)
        throw IllegalArgumentException(message)
    return this
}

fun String.toColor(): String
        = ChatColor.translateAlternateColorCodes('\u0026', this)

fun String.toColor(altColorChar: Char): String
        = ChatColor.translateAlternateColorCodes(altColorChar, this)

fun Array<out String>.toColor(): Array<out String>
        = this.clone().toList().map { it -> it.toColor() }.toTypedArray()

fun Array<out String>.toColor(altColorChar: Char): Array<out String>
        = this.clone().toList().map { it -> it.toColor(altColorChar) }.toTypedArray()

fun String.toBukkitWorld(): World?
        = Bukkit.getWorld(this)

