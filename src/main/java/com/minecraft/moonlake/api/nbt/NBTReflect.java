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

import com.minecraft.moonlake.nbt.NBTType;
import com.minecraft.moonlake.nbt.exception.NBTConvertException;
import com.minecraft.moonlake.nbt.exception.NBTException;
import com.minecraft.moonlake.nbt.exception.NBTInitializeException;
import com.minecraft.moonlake.reflect.Reflect;
import com.minecraft.moonlake.validate.Validate;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * <h1>NBTReflect</h1>
 * NBT 抽象反射实现类
 *
 * @version 1.0
 * @author Month_Light
 */
public abstract class NBTReflect {

    private static NBTReflect handle;

    static {

        boolean raw = false;

        try {

            Reflect.getConstructor("NBTTagByte", Reflect.PackageType.MINECRAFT_SERVER, byte.class);
            raw = true;
        }
        catch (Exception e) {

        }
        handle = raw ? new NBTReflectSpigotRaw() : null;
    }

    /**
     * NBT 抽象反射实现类构造函数
     */
    protected NBTReflect() {

    }

    /**
     * 获取 NBT 反射源对象
     *
     * @return NBTReflect
     * @throws NBTException 如果 NBT 反射源对象为 {@code null} 则抛出异常
     */
    public static NBTReflect getHandle() throws NBTException {

        if(handle == null) {

            throw new NBTInitializeException("The nbt tag options library reflect raw exception.");
        }
        return handle;
    }

    /**
     * 将指定 NBT 对象转换到 NBTCompound 实例对象
     *
     * @param tag NBT 对象
     * @return NBTCompound 如果 NBT 对象为 {@code null} 则返回 {@code null}
     */
    public static NBTCompound fromNBTCompound(Object tag) {

        return tag == null ? null : new NBTCompoundExpression(tag);
    }

    /**
     * 将指定 NBT 对象克隆并转换到 NBTCompound 实例对象
     *
     * @param tag Tag 对象
     * @return NBTCompound 如果 NBT 对象为 {@code null} 则返回 {@code null}
     */
    public static NBTCompound fromNBTCompoundCopy(Object tag) {

        return tag == null ? null : fromNBTCompound(getHandle().cloneTag(tag));
    }

    /**
     * 将指定 NBT 对象转换到 NBTList 实例对象
     *
     * @param tag NBT 对象
     * @return NBTList 如果 NBT 对象为 {@code null} 则返回 {@code null}
     */
    public static NBTList fromNBTList(Object tag) {

        return tag == null ? null : new NBTListExpression(tag);
    }

    /**
     * 将指定 NBT 对象克隆并转换到 NBTList 实例对象
     *
     * @param tag NBT 对象
     * @return NBTList 如果 NBT 对象为 {@code null} 则返回 {@code null}
     */
    public static NBTList fromNBTListCopy(Object tag) {

        return tag == null ? null : new NBTListExpression(getHandle().cloneTag(tag));
    }

    /**
     * 创建 NBT 实例对象
     *
     * @param value 值
     * @return NBT 实例对象
     * @throws NBTConvertException 如果未知值类型则抛出异常
     */
    public Object createTag(Object value) throws NBTConvertException {

        if(value == null) {

            return null;
        }
        else if(value instanceof NBTCompoundExpression) {

            return cloneTag(((NBTCompoundExpression) value).getHandle());
        }
        else if(value instanceof NBTListExpression) {

            return cloneTag(((NBTListExpression) value).getHandle());
        }
        else if(value instanceof Collection) {

            return new NBTListExpression(((Collection) value)).getHandle();
        }
        else if(value instanceof Boolean) {

            if((Boolean) value) {

                return createTagByte((byte) 1);
            }
            return (byte) 0;
        }
        else if(value instanceof Byte) {

            return createTagByte((Byte) value);
        }
        else if(value instanceof Short) {

            return createTagShort((Short) value);
        }
        else if(value instanceof Integer) {

            return createTagInt((Integer) value);
        }
        else if(value instanceof Long) {

            return createTagLong((Long) value);
        }
        else if(value instanceof Float) {

            return createTagFloat((Float) value);
        }
        else if(value instanceof Double) {

            return createTagDouble((Double) value);
        }
        else if(value instanceof byte[]) {

            return createTagByteArray((byte[]) value);
        }
        else if(value instanceof String) {

            return createTagString((String) value);
        }
        else if(value instanceof int[]) {

            return createTagIntArray((int[]) value);
        }
        else if(value instanceof Object[]) {

            return new NBTListExpression(Arrays.asList((Object[]) value)).getHandle();
        }
        throw new NBTConvertException("The nbt convert object exception: " + value.getClass().getSimpleName());
    }

    /**
     * 创建 NBT 实例对象
     *
     * @param value 值
     * @param type NBT 类型
     * @return NBT 实例对象
     * @throws NBTConvertException 如果未知值类型则抛出异常
     */
    public Object createTag(Object value, byte type) throws NBTConvertException {

        return createTag(convertValue(value, type));
    }

    /**
     * 创建 NBT 实例对象
     *
     * @param value 值
     * @param type NBT 类型
     * @return NBT 实例对象
     * @throws NBTConvertException 如果未知值类型则抛出异常
     * @throws IllegalArgumentException 如果 NBT 类型对象为 {@code null} 则抛出异常
     */
    public Object createTag(Object value, NBTType type) throws NBTConvertException {

        Validate.notNull(type, "The nbt tag type object is null.");

        return createTag(convertValue(value, (byte) type.getTypeId()));
    }

    /**
     * 将指定 NBT 对象转换到指定类型值
     *
     * @param value NBT 值
     * @param type NBT 类型
     * @return NBT 的数据值 如果 NBT 值为 {@code null} 则返回 {@code null}
     * @throws NBTConvertException 如果未知 NBT 或值类型则抛出异常
     */
    public Object convertValue(Object value, byte type) throws NBTConvertException {

        switch (type) {

            case 0:
                if(value == null) {

                    return null;
                }
            case 1:
                if(value instanceof Number) {

                    return ((Number) value).byteValue();
                }
                if(value instanceof String) {

                    return new Byte(((String) value));
                }
            case 2:
                if(value instanceof Number) {

                    return ((Number) value).shortValue();
                }
                if(value instanceof String) {

                    return new Short(((String) value));
                }
            case 3:
                if(value instanceof Number) {

                    return ((Number) value).intValue();
                }
                if(value instanceof String) {

                    return new Integer(((String) value));
                }
            case 4:
                if(value instanceof Number) {

                    return ((Number) value).longValue();
                }
                if(value instanceof String) {

                    return new Long(((String) value));
                }
            case 5:
                if(value instanceof Number) {

                    return ((Number) value).floatValue();
                }
                if(value instanceof String) {

                    return new Float(((String) value));
                }
            case 6:
                if(value instanceof Number) {

                    return ((Number) value).doubleValue();
                }
                if(value instanceof String) {

                    return new Double(((String) value));
                }
            case 7:
                if(value instanceof byte[]) {

                    return value;
                }
                if(value instanceof int[]) {

                    int[] values = (int[]) value;
                    byte[] temp = new byte[values.length];

                    for(int i = 0; i < temp.length; i++) {

                        temp[i] = (byte) values[i];
                    }
                    return temp;
                }
                if(value instanceof Collection) {

                    Collection values = (Collection) value;
                    byte[] temp = new byte[values.size()];
                    int index = 0;

                    for(final Object value0 : values) {

                        if(!(value0 instanceof Number)) {

                            throw new NBTConvertException("The nbt convert object exception: " + value0.getClass().getSimpleName());
                        }
                        temp[index++] = ((Number) value0).byteValue();
                    }
                    return temp;
                }
                if(value instanceof String) {

                    return ((String) value).getBytes(Charset.forName("utf-8"));
                }
            case 8:
                return value.toString();
            case 9:
                if(value instanceof Collection || value instanceof Object[]) {

                    return value;
                }
                if(value instanceof byte[]) {

                    List<Byte> list = new ArrayList<>(((byte[]) value).length);

                    for(final byte value0 : ((byte[]) value)){

                        list.add(value0);
                    }
                    return list;
                }
                if(value instanceof int[]) {

                    List<Integer> list = new ArrayList<>(((int[]) value).length);

                    for(final int value0 : ((int[]) value)){

                        list.add(value0);
                    }
                    return list;
                }
            case 10:
                if(value instanceof Map) {

                    return value;
                }
            case 11:
                if(value instanceof int[]) {

                    return value;
                }
                if(value instanceof byte[]) {

                    byte[] values = (byte[]) value;
                    int[] temp = new int[values.length];

                    for(int i = 0; i < temp.length; i++) {

                        temp[i] = values[i];
                    }
                    return temp;
                }
                if(value instanceof Collection) {

                    Collection values = (Collection) value;
                    int[] temp = new int[values.size()];
                    int index = 0;

                    for(final Object value0 : values) {

                        if(!(value0 instanceof Number)) {

                            throw new NBTConvertException("The nbt convert object exception: " + value0.getClass().getSimpleName());
                        }
                        temp[index++] = ((Number) value0).intValue();
                    }
                    return temp;
                }
            default:
                throw new NBTConvertException("The nbt convert object exception: " + value.getClass().getSimpleName());
        }
    }

    /**
     * 设置指定 NBT 对象的数据值
     *
     * @param tag NBT 对象
     * @param value 数据值
     * @throws NBTException 如果设置指定 NBT 对象数据值错误则抛出异常
     * @throws IllegalArgumentException 如果 NBT 对象为 {@code null} 则抛出异常
     */
    public void setValue(Object tag, Object value) throws NBTException {

        setRawValue(tag, convertValue(value, getTagType(tag)));
    }

    /**
     * 将指定 NBT 对象写入到输出流
     *
     * @param output 输出流
     * @param tag NBT 对象
     * @throws IOException 如果 I/O 错误则抛出异常
     * @throws IllegalArgumentException 如果输出流对象或 NBT 对象为 {@code null} 则抛出异常
     */
    public void writeTagToOutput(DataOutput output, Object tag) throws IOException {

        Validate.notNull(output, "The data output object is null.");

        output.writeByte(getTagType(tag));
        output.writeUTF(getTagName(tag));
        writeTagDataToOutput(output, tag);
    }

    /**
     * 从指定输入流读取 NBT 对象
     *
     * @param input 输入流
     * @param type NBT 类型
     * @return NBT 对象
     * @throws IOException 如果 I/O 错误则抛出异常
     * @throws IllegalArgumentException 如果输入流对象为 {@code null} 则抛出异常
     */
    public Object readTagOfType(DataInput input, byte type) throws IOException {

        Validate.notNull(input, "The data input object is null.");

        Object tag = createTagOfType(type);
        readInputToTag(input, type);
        return tag;
    }

    /**
     * 从指定输入流读取 NBT 对象
     *
     * @param input 输入流
     * @return NBT 对象
     * @throws IOException 如果 I/O 错误则抛出异常
     */
    public Object readTag(DataInput input) throws IOException {

        byte type = input.readByte();
        input.readUTF();
        return readTagOfType(input, type);
    }

    /**
     * 创建 NBTTagByte 实例对象
     *
     * @param value 值
     * @return NBTTagByte 对象
     * @throws NBTException 如果创建错误则抛出异常
     */
    public abstract Object createTagByte(Byte value) throws NBTException;

    /**
     * 创建 NBTTagShort 实例对象
     *
     * @param value 值
     * @return NBTTagShort 对象
     * @throws NBTException 如果创建错误则抛出异常
     */
    public abstract Object createTagShort(Short value) throws NBTException;

    /**
     * 创建 NBTTagInt 实例对象
     *
     * @param value 值
     * @return NBTTagInt 对象
     * @throws NBTException 如果创建错误则抛出异常
     */
    public abstract Object createTagInt(Integer value) throws NBTException;

    /**
     * 创建 NBTTagLong 实例对象
     *
     * @param value 值
     * @return NBTTagLong 对象
     * @throws NBTException 如果创建错误则抛出异常
     */
    public abstract Object createTagLong(Long value) throws NBTException;

    /**
     * 创建 NBTTagFloat 实例对象
     *
     * @param value 值
     * @return NBTTagFloat 对象
     * @throws NBTException 如果创建错误则抛出异常
     */
    public abstract Object createTagFloat(Float value) throws NBTException;

    /**
     * 创建 NBTTagDouble 实例对象
     *
     * @param value 值
     * @return NBTTagDouble 对象
     * @throws NBTException 如果创建错误则抛出异常
     */
    public abstract Object createTagDouble(Double value) throws NBTException;

    /**
     * 创建 NBTTagString 实例对象
     *
     * @param value 值
     * @return NBTTagString 对象
     * @throws NBTException 如果创建错误则抛出异常
     * @throws IllegalArgumentException 如果数据值对象为 {@code null} 则抛出异常
     */
    public abstract Object createTagString(String value) throws NBTException;

    /**
     * 创建 NBTTagByteArray 实例对象
     *
     * @param value 值
     * @return NBTTagByteArray 对象
     * @throws NBTException 如果创建错误则抛出异常
     * @throws IllegalArgumentException 如果数据值对象为 {@code null} 则抛出异常
     */
    public abstract Object createTagByteArray(byte[] value) throws NBTException;

    /**
     * 创建 NBTTagIntArray 实例对象
     *
     * @param value 值
     * @return NBTTagIntArray 对象
     * @throws NBTException 如果创建错误则抛出异常
     * @throws IllegalArgumentException 如果数据值对象为 {@code null} 则抛出异常
     */
    public abstract Object createTagIntArray(int[] value) throws NBTException;

    /**
     * 获取指定 NBT 对象的数据值
     *
     * @param tag NBT 对象
     * @return 数据值
     * @throws NBTException 如果获取错误则抛出异常
     */
    public abstract Object getValue(Object tag) throws NBTException;

    /**
     * 设置指定 NBT 对象的数据值
     *
     * @param tag NBT 对象
     * @param value 数据值
     * @throws NBTException 如果设置错误则抛出异常
     * @throws IllegalArgumentException 如果 NBT 对象为 {@code null} 则抛出异常
     */
    protected abstract void setRawValue(Object tag, Object value)  throws NBTException;

    /**
     * 获取指定 NBT 对象的 NBT 类型
     *
     * @param tag NBT 对象
     * @return NBT 类型
     * @throws NBTException 如果获取错误则抛出异常
     * @throws IllegalArgumentException 如果 NBT 对象为 {@code null} 则抛出异常
     */
    public abstract byte getTagType(Object tag)  throws NBTException;

    /**
     * 创建 NBT 实例对象从 NBT 类型
     *
     * @param type NBT 类型
     * @return NBT 对象
     * @throws NBTException 如果创建错误则抛出异常
     */
    public abstract Object createTagOfType(byte type) throws NBTException;

    /**
     * 克隆指定 NBT 对象
     *
     * @param tag NBT 对象
     * @return NBT 对象
     * @throws NBTException 如果克隆错误则抛出异常
     * @throws IllegalArgumentException 如果 NBT 对象为 {@code null} 则抛出异常
     */
    public abstract Object cloneTag(Object tag) throws NBTException;

    /**
     * 创建 NBT 复合实例对象
     *
     * @return NBT 复合对象
     * @throws NBTException 如果创建错误则抛出异常
     */
    public abstract Object createTagCompound() throws NBTException;

    /**
     * 创建 NBT 列表实例对象
     *
     * @return NBT 列表对象
     * @throws NBTException 如果创建错误则抛出异常
     */
    public abstract Object createTagList() throws NBTException;

    /**
     * 获取指定 NBT 复合对象的 Map 数据值
     *
     * @param nbtTagCompound NBT 复合对象
     * @return Map 数据值
     * @throws NBTException 如果获取错误则抛出异常
     * @throws IllegalArgumentException 如果 NBT 复合对象为 {@code null} 则抛出异常
     */
    public abstract Map<String, Object> getHandleMap(Object nbtTagCompound) throws NBTException;

    /**
     * 获取指定 NBT 列表对象的 List 数据值
     *
     * @param nbtTagList NBT 列表对象
     * @return List 数据值
     * @throws NBTException 如果获取错误则抛出异常
     * @throws IllegalArgumentException 如果 NBT 列表对象为 {@code null} 则抛出异常
     */
    public abstract List<Object> getHandleList(Object nbtTagList) throws NBTException;

    /**
     * 获取指定 NBT 列表对象的 NBT 类型
     *
     * @param nbtTagList NBT 列表对象
     * @return NBT 类型
     * @throws NBTException 如果获取错误则抛出异常
     * @throws IllegalArgumentException 如果 NBT 列表对象为 {@code null} 则抛出异常
     */
    public abstract byte getNBTTagListType(Object nbtTagList) throws NBTException;

    /**
     * 设置指定 NBT 列表对象的 NBT 类型
     *
     * @param nbtTagList NBT 列表对象
     * @param type NBT 类型
     * @throws NBTException 如果设置错误则抛出异常
     * @throws IllegalArgumentException 如果 NBT 列表对象为 {@code null} 则抛出异常
     */
    public abstract void setNBTTagListType(Object nbtTagList, byte type) throws NBTException;

    /**
     * 获取指定对象是否为 NBT 对象
     *
     * @param tag 对象
     * @return true 则是 NBT 对象
     */
    public abstract boolean isNBTTag(Object tag);

    /**
     * 从指定输入流读取 NBT 对象
     *
     * @param input 输入流
     * @param tag NBT 对象
     * @throws IOException 如果 I/O 错误则抛出异常
     * @throws IllegalArgumentException 如果输入流对象或 NBT 对象为 {@code null} 则抛出异常
     */
    public abstract void readInputToTag(DataInput input, Object tag) throws IOException;

    /**
     * 将指定 NBT 对象写入到输出流
     *
     * @param output 输出流
     * @param tag NBT 对象
     * @throws IOException 如果 I/O 错误则抛出异常
     * @throws IllegalArgumentException 如果输出流对象或 NBT 对象为 {@code null} 则抛出异常
     */
    public abstract void writeTagDataToOutput(DataOutput output, Object tag) throws IOException;

    /**
     * 设置指定 NBT 对象的名称
     *
     * @param tag NBT 对象
     * @param name 名称
     * @throws NBTException 如果设置错误则抛出异常
     * @deprecated not has implement
     */
    @Deprecated
    public abstract void setTagName(Object tag, String name) throws NBTException;

    /**
     * 获取指定 NBT 对象的名称
     *
     * @param tag NBT 对象
     * @return NBT 名称
     * @throws NBTException 如果获取错误则抛出异常
     * @deprecated not has implement
     */
    @Deprecated
    public abstract String getTagName(Object tag) throws NBTException;
}
