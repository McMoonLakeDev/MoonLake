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

import com.minecraft.moonlake.api.nbt.NBTReflect;

/**
 * Created by MoonLake on 2016/9/21.
 */
public class NBTTagByte extends NBTTagNumberic<Byte> {

    public NBTTagByte() {

        this((byte) 0);
    }

    public NBTTagByte(byte handle) {

        super(handle);
    }

    public NBTTagByte(boolean ignored, Object tag) {

        super(tag);

        if(NBTReflect.getHandle().getTagType(tag) != 1) {

            throw new IllegalArgumentException("The nbt tag not is nbt tag byte object.");
        }
    }

    @Override
    public void setNumber(Number number) {

        set(number.byteValue());
    }

    @Override
    public byte getTypeId() {

        return 1;
    }

    @Override
    public Byte get() {

        return (Byte) NBTReflect.getHandle().getValue(handle);
    }

    @Override
    public int hashCode() {

        return handle.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof NBTBase) {

            obj = ((NBTBase) obj).getHandle();
        }
        return handle.equals(obj);
    }
}
