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

package com.minecraft.moonlake.api.chat;

class ChatComponentRaw extends ChatComponentBase {

    private final String raw;

    ChatComponentRaw(String raw) {
        this.raw = raw;
    }

    String getRaw() {
        return raw;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof ChatComponentRaw) {
            ChatComponentRaw other = (ChatComponentRaw) obj;
            return super.equals(obj) && raw != null ? raw.equals(other.raw) : other.raw == null;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (raw != null ? raw.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChatComponentRaw{" +
                "raw='" + raw + '\'' +
                ", style=" + getStyle() +
                ", extras=" + getExtras() +
                '}';
    }
}
