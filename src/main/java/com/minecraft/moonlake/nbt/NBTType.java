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
 
 
package com.minecraft.moonlake.nbt;

/**
 * Created by MoonLake on 2016/9/21.
 */
public enum NBTType {

    END((byte) 0, "end"),
    BYTE((byte) 1, "byte"),
    SHORT((byte) 2, "short"),
    INT((byte) 3, "int"),
    LONG((byte) 4, "long"),
    FLOAT((byte) 5, "float"),
    DOUBLE((byte) 6, "double"),
    BYTEARRAY((byte) 7, "byte[]"),
    STRING((byte) 8, "string"),
    LIST((byte) 9, "list"),
    COMPOUND((byte) 10, "compound"),
    INTARRAY((byte) 11, "int[]")
    ;

    private final byte type;
    private final String name;

    NBTType(byte type, String name) {

        this.type = type;
        this.name = name;
    }

    public byte getType() {

        return type;
    }

    public String getName() {

        return name;
    }

    public NBTBase getDefault() {

        return NBTBase.getDefault(getType());
    }

    public static NBTType fromByte(byte type) {

        for(final NBTType nbtType : values()) {

            if(nbtType.getType() == type) {

                return nbtType;
            }
        }
        return END;
    }

    public static NBTType fromBase(NBTBase nbtBase) {

        if(nbtBase == null) {

            return END;
        }
        return fromByte(nbtBase.getTypeId());
    }

    public static NBTType fromString(String name) {

        if (name == null || name.isEmpty()) {

            return END;
        }
        String nameLowerCase = name.toLowerCase();

        for (final NBTType nbtType : values()) {

            if (nameLowerCase.equalsIgnoreCase(nbtType.name) || nbtType.getName().toLowerCase().startsWith(nameLowerCase)) {

                return nbtType;
            }
        }
        return END;
    }
}
