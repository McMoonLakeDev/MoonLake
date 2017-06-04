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

import com.minecraft.moonlake.nbt.exception.NBTException;
import com.minecraft.moonlake.api.nbt.NBTReflect;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by MoonLake on 2016/9/21.
 */
public abstract class NBTBase {

    protected final Object handle;

    public NBTBase(Object handle) {

        this.handle = handle;
    }

    public final void raad(DataInput input) throws IOException {

        NBTReflect.getHandle().readInputToTag(input, handle);
    }

    public final void write(DataOutput output) throws IOException {

        NBTReflect.getHandle().writeTagDataToOutput(output, handle);
    }

    public final byte[] toBytes() throws NBTException {

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(buffer);

        try {

            write(out);
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag to bytes exception.", e);
        }
        return buffer.toByteArray();
    }

    public final void fromBytes(byte[] source) throws NBTException {

        ByteArrayInputStream buffer = new ByteArrayInputStream(source);
        DataInputStream in = new DataInputStream(buffer);

        try {

            raad(in);
        }
        catch (Exception e) {

            throw new NBTException("The read input to nbt tag exception.", e);
        }
    }

    public Object getHandle() {

        return handle;
    }

    @Deprecated
    @SuppressWarnings("deprecation")
    public String getName() {

        return NBTReflect.getHandle().getTagName(handle);
    }

    @Deprecated
    @SuppressWarnings("deprecation")
    public void setName(String name) {

        NBTReflect.getHandle().setTagName(handle, name);
    }

    NBTBase getDefault() {

        return getDefault(getTypeId());
    }

    public static NBTBase wrap(Object handle) {

        if(handle == null) {

            return null;
        }
        byte type = NBTReflect.getHandle().getTagType(handle);

        switch (type) {

            case 1:
                return new NBTTagByte(true, handle);
            case 2:
                return new NBTTagShort(true, handle);
            case 3:
                return new NBTTagInt(true, handle);
            case 4:
                return new NBTTagLong(true, handle);
            case 5:
                return new NBTTagFloat(true, handle);
            case 6:
                return new NBTTagDouble(true, handle);
            case 7:
                return new NBTTagByteArray(true, handle);
            case 8:
                return new NBTTagString(true, handle);
            case 9:
                return new NBTTagList(true, handle);
            case 10:
                return new NBTTagCompound(true, handle);
            case 11:
                return new NBTTagIntArray(true, handle);
            default:
                return null;
        }
    }

    public abstract byte getTypeId();

    public final NBTType getType() {

        return NBTType.fromByte(getTypeId());
    }

    public static NBTBase getDefault(byte type) {

        switch (type) {

            case 1:
                return new NBTTagByte();
            case 2:
                return new NBTTagShort();
            case 3:
                return new NBTTagInt();
            case 4:
                return new NBTTagLong();
            case 5:
                return new NBTTagFloat();
            case 6:
                return new NBTTagDouble();
            case 7:
                return new NBTTagByteArray();
            case 8:
                return new NBTTagShort();
            case 9:
                return new NBTTagList();
            case 10:
                return new NBTTagCompound();
            case 11:
                return new NBTTagIntArray();
            default:
                return null;
        }
    }

    public Object cloneHandle() {

        return cloneHandle(handle);
    }

    public static Object cloneHandle(Object handle) {

        return NBTReflect.getHandle().cloneTag(handle);
    }

    public NBTBase clone() {

        return wrap(NBTReflect.getHandle().cloneTag(handle));
    }

    public static NBTBase getByValue(Object obj) {

        if(obj == null) {

            return null;
        }
        else if(obj instanceof NBTBase) {

            return (NBTBase) obj;
        }
        else if(NBTReflect.getHandle().isNBTTag(obj)) {

            return wrap(obj);
        }
        else if(obj instanceof Byte) {

            return new NBTTagByte((byte) obj);
        }
        else if(obj instanceof Short) {

            return new NBTTagShort((short) obj);
        }
        else if(obj instanceof Integer) {

            return new NBTTagInt((int) obj);
        }
        else if(obj instanceof Long) {

            return new NBTTagLong((long) obj);
        }
        else if(obj instanceof Float) {

            return new NBTTagFloat((float) obj);
        }
        else if(obj instanceof Double) {

            return new NBTTagDouble((double) obj);
        }
        else if(obj instanceof byte[]) {

            return new NBTTagByteArray((byte[]) obj);
        }
        else if(obj instanceof int[]) {

            return new NBTTagIntArray((int[]) obj);
        }
        else {

            Iterator iterator;

            if(obj instanceof Map) {

                NBTTagCompound compound = new NBTTagCompound();
                iterator = ((Map) obj).entrySet().iterator();

                while(iterator.hasNext()) {

                    Map.Entry entry = (Map.Entry) iterator.next();
                    compound.putToHandle(entry.toString(), getByValue(entry.getValue()));
                }
                return compound;
            }
            else {

                if(obj instanceof Object[]) {

                    obj = Arrays.asList((Object[]) obj);
                }
                if(obj instanceof List) {

                    NBTTagList list = new NBTTagList();
                    iterator = ((List) obj).iterator();

                    while(iterator.hasNext()) {

                        Object value = iterator.next();
                        list.addB(getByValue(value));
                    }
                    return list;
                }
            }
            throw new IllegalArgumentException("The object is unknow type: " + obj.getClass().getSimpleName());
        }
    }

    public abstract int hashCode();

    public abstract String toString();
}
