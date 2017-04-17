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
 
 
package com.minecraft.moonlake.api.nbt;

import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.nbt.exception.NBTException;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import com.minecraft.moonlake.validate.Validate;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.minecraft.moonlake.api.utility.NBTTagReflection.*;

/**
 * <h1>NBTReflectSpigotRaw</h1>
 * NBT Spigot 反射源实现类
 *
 * @version 1.1
 * @author Month_Light
 */
final class NBTReflectSpigotRaw extends NBTReflect {

    /**
     * NBT Spigot 反射源实现类构造函数
     */
    public NBTReflectSpigotRaw() {
    }

    private Object createTag0(ConstructorAccessor target, Object... args) throws NBTException {

        try {

            return target.invoke(args);
        }
        catch (Exception e) {

            throw new NBTException("The nbt create tag exception.", e);
        }
    }

    @Override
    public Object createTagByte(Byte value) throws NBTException {

        return createTag0(getNBTTagByteConstructor(), (byte) (value == null ? 0 : value));
    }

    @Override
    public Object createTagShort(Short value) throws NBTException {

        return createTag0(getNBTTagShortConstructor(), (short) (value == null ? 0 : value));
    }

    @Override
    public Object createTagInt(Integer value) throws NBTException {

        return createTag0(getNBTTagIntConstructor(), (int) (value == null ? 0 : value));
    }

    @Override
    public Object createTagLong(Long value) throws NBTException {

        return createTag0(getNBTTagLongConstructor(), (long) (value == null ? 0L : value));
    }

    @Override
    public Object createTagFloat(Float value) throws NBTException {

        return createTag0(getNBTTagFloatConstructor(), (float) (value == null ? 0f : value));
    }

    @Override
    public Object createTagDouble(Double value) throws NBTException {

        return createTag0(getNBTTagDoubleConstructor(), (double) (value == null ? 0d : value));
    }

    @Override
    public Object createTagString(String value) throws NBTException {

        Validate.notNull(value, "The nbt tag string data value object is null.");

        return createTag0(getNBTTagStringConstructor(), value);
    }

    @Override
    public Object createTagByteArray(byte[] value) throws NBTException {

        Validate.notNull(value, "The nbt tag byte[] data value object is null.");

        return createTag0(getNBTTagByteArrayConstructor(), new Object[] { value });
    }

    @Override
    public Object createTagIntArray(int[] value) throws NBTException {

        Validate.notNull(value, "The nbt tag int[] data value object is null.");

        return createTag0(getNBTTagIntArrayConstructor(), new Object[] { value });
    }

    @Override
    public Object getValue(Object tag) throws NBTException {

        if(tag == null) {

            return null;
        }
        if(!isNBTTag(tag)) {

            throw new NBTException("The nbt tag object not is NBTBase instance.");
        }
        try {

            switch (getTagType(tag)) {

                case 1:
                    return getNBTTagByteDataField().get(tag);
                case 2:
                    return getNBTTagShortDataField().get(tag);
                case 3:
                    return getNBTTagIntDataField().get(tag);
                case 4:
                    return getNBTTagLongDataField().get(tag);
                case 5:
                    return getNBTTagFloatDataField().get(tag);
                case 6:
                    return getNBTTagDoubleDataField().get(tag);
                case 7:
                    return getNBTTagByteArrayDataField().get(tag);
                case 8:
                    return getNBTTagStringDataField().get(tag);
                case 9:
                    return fromNBTList(tag);
                case 10:
                    return fromNBTCompound(tag);
                case 11:
                    return getNBTTagIntArrayDataField().get(tag);
                default:
                    throw new NBTException("The nbt tag object is unknow class: " + tag.getClass());
            }
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag object get value exception.", e);
        }
    }

    @Override
    protected void setRawValue(Object tag, Object value) throws NBTException {

        Validate.notNull(tag, "The nbt tag object is null.");

        if(!isNBTTag(tag)) {

            throw new NBTException("The nbt tag object not is NBTBase instance.");
        }
        try {

            switch (getTagType(tag)) {

                case 1:
                    getNBTTagByteDataField().set(tag, value); break;
                case 2:
                    getNBTTagShortDataField().set(tag, value); break;
                case 3:
                    getNBTTagIntDataField().set(tag, value); break;
                case 4:
                    getNBTTagLongDataField().set(tag, value); break;
                case 5:
                    getNBTTagFloatDataField().set(tag, value); break;
                case 6:
                    getNBTTagDoubleDataField().set(tag, value); break;
                case 7:
                    getNBTTagByteArrayDataField().set(tag, value); break;
                case 8:
                    getNBTTagStringDataField().set(tag, value); break;
                case 9:
                    NBTList list = fromNBTList(tag);
                    list.clear();
                    list.addAll((Collection) value);
                    break;
                case 10:
                    NBTCompound compound = fromNBTCompound(tag);
                    compound.clear();
                    compound.putAll((Map) value);
                    break;
                case 11:
                    getNBTTagIntArrayDataField().get(tag); break;
                default:
                    throw new NBTException("The nbt tag object is unknow class: " + tag.getClass());
            }
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag object set value exception.", e);
        }
    }

    @Override
    public byte getTagType(Object tag) throws NBTException {

        Validate.notNull(tag, "The nbt tag object is null.");

        if(!isNBTTag(tag)) {

            throw new NBTException("The nbt tag object not is NBTBase instance.");
        }
        byte type = 0;

        try {

            type = (byte) getNBTBaseGetTypeIdMethod().invoke(tag);
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag get type exception.", e);
        }
        return type;
    }

    @Override
    public Object createTagOfType(byte type) throws NBTException {

        try {

            return getNBTBaseCreateTagMethod().invoke(null, type);
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag create exception.", e);
        }
    }

    @Override
    public Object cloneTag(Object tag) throws NBTException {

        Validate.notNull(tag, "The nbt tag object is null.");

        try {

            return getNBTBaseCloneMethod().invoke(tag);
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag clone exception.", e);
        }
    }

    @Override
    public Object createTagCompound() throws NBTException {

        try {

            return getNBTTagCompoundConstructor().invoke();
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag compound create exception.", e);
        }
    }

    @Override
    public Object createTagList() throws NBTException {

        try {

            return getNBTTagListConstructor().invoke();
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag list create exception.", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> getHandleMap(Object nbtTagCompound) throws NBTException {

        Validate.notNull(nbtTagCompound, "The nbt tag compound object is null.");

        try {

            return (Map) getNBTTagCompoundDataField().get(nbtTagCompound);
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag compound get map field exception.", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Object> getHandleList(Object nbtTagList) throws NBTException {

        Validate.notNull(nbtTagList, "The nbt tag list object is null.");

        try {

            return (List) getNBTTagListDataField().get(nbtTagList);
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag list get list field exception.", e);
        }
    }

    @Override
    public byte getNBTTagListType(Object nbtTagList) throws NBTException {

        Validate.notNull(nbtTagList, "The nbt tag list object is null.");

        try {

            return (byte) getNBTTagListTypeField().get(nbtTagList);
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag list get type exception.", e);
        }
    }

    @Override
    public void setNBTTagListType(Object nbtTagList, byte type) throws NBTException {

        Validate.notNull(nbtTagList, "The nbt tag list object is null.");

        try {

            getNBTTagListTypeField().set(nbtTagList, type);
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag list set type exception.", e);
        }
    }

    @Override
    public boolean isNBTTag(Object tag) {

        return tag != null && MinecraftReflection.getNBTBaseClass().isInstance(tag);
    }

    @Override
    public void readInputToTag(DataInput input, Object tag) throws IOException {

        Validate.notNull(input, "The data input object is null.");
        Validate.notNull(tag, "The nbt tag object is null.");

        try {

            long readLimit = 4611686018427387903L;
            getNBTBaseLoadMethod().invoke(tag, input, 0, getNBTReadLimiterConstructor().invoke(readLimit));
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag read input to tag exception.", e);
        }
    }

    @Override
    public void writeTagDataToOutput(DataOutput output, Object tag) throws IOException {

        Validate.notNull(output, "The data output object is null.");
        Validate.notNull(tag, "The nbt tag object is null.");

        try {

            getNBTBaseWriteMethod().invoke(tag, output);
        }
        catch (Exception e) {

            throw new NBTException("The nbt tag write tag to output exception.", e);
        }
    }

    @Override
    @Deprecated
    public void setTagName(Object tag, String name) throws NBTException {


    }

    @Override
    @Deprecated
    public String getTagName(Object tag) throws NBTException {

        return "";
    }
}
