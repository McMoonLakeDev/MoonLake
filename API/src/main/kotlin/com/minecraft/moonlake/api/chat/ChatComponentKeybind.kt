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

package com.minecraft.moonlake.api.chat

open class ChatComponentKeybind(private var keybind: String) : ChatComponentAbstract() {

    fun getKeybind(): String
            = keybind

    fun setKeybind(keybind: String): ChatComponentKeybind
            { this.keybind = keybind; return this; }

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is ChatComponentKeybind)
            return super.equals(other) && keybind == other.keybind
        return false
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + keybind.hashCode()
        return result
    }

    override fun toString(): String {
        return "ChatComponentKeybind(keybind='$keybind', style=$style, extras=$extras)"
    }
}
