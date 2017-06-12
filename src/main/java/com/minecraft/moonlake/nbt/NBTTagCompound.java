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


package com.minecraft.moonlake.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;

/**
 * <h1>NBTTagCompound</h1>
 * NBT 复合类型数据
 *
 * @version 1.0
 * @author Month_Light
 * @see NBTTagDatable
 * @see Iterable
 */
public class NBTTagCompound extends NBTTagDatable<Map<String, NBTBase>> implements Iterable<NBTBase> {

    /**
     * NBT 复合类型数据构造函数
     */
    public NBTTagCompound() {
        this(new HashMap<>());
    }

    /**
     * NBT 复合类型数据构造函数
     *
     * @param value 值
     */
    public NBTTagCompound(Map<String, NBTBase> value) {
        this("", value);
    }

    /**
     * NBT 复合类型数据构造函数
     *
     * @param name 特殊名
     */
    public NBTTagCompound(String name) {
        this(name, new HashMap<>());
    }

    /**
     * NBT 复合类型数据构造函数
     *
     * @param name 特殊名
     * @param value 值
     */
    public NBTTagCompound(String name, Map<String, NBTBase> value) {
        super(name, value);
    }

    @Override
    public NBTType getType() {
        return NBTType.COMPOUND;
    }

    /**
     * 设置此 NBT 复合标签的数据值
     *
     * @param value 新值
     */
    @Override
    public void setValue(Map<String, NBTBase> value) {
        super.value = new HashMap<>(value);
    }

    /**
     * 获取此 NBT 复合标签的数据值
     */
    @Override
    public Map<String, NBTBase> getValue() {
        // 创建一个新的哈希表并拷贝值, 而不是返回当前 value 引用
        return new HashMap<>(value);
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的元素值
     *
     * @param name 特殊名
     * @return 对应特殊名的元素值 | null
     */
    public NBTBase get(String name) {
        return value.get(name);
    }

    /**
     * 将指定 NBT 标签添加到此 NBT 复合标签内
     *
     * @param nbt NBT 标签
     * @param <T> 类型
     * @return 之前与特殊名关联的值, 如果没有映射关系则返回 null
     */
    @SuppressWarnings("unchecked")
    public <T extends NBTBase> T put(T nbt) {
        return (T) value.put(nbt.getName(), nbt);
    }

    /**
     * 将此 NBT 复合标签设置指定特殊名和标签值
     *
     * @param name 特殊名
     * @param nbt 标签值
     */
    public void set(String name, NBTBase nbt) {
        this.value.put(name, nbt);
    }

    /**
     * 将此 NBT 复合标签设置指定特殊名和字节类型标签值
     *
     * @param name 特殊名
     * @param value 字节类型标签值
     */
    public void setByte(String name, byte value) {
        this.value.put(name, new NBTTagByte(name, value));
    }

    /**
     * 将此 NBT 复合标签设置指定特殊名和字节类型标签值
     *
     * @param name 特殊名
     * @param value 字节类型标签值
     */
    public void setByte(String name, int value) {
        setByte(name, (byte) value);
    }

    /**
     * 将此 NBT 复合标签设置指定特殊名和短整数类型标签值
     *
     * @param name 特殊名
     * @param value 短整数类型标签值
     */
    public void setShort(String name, short value) {
        this.value.put(name, new NBTTagShort(name, value));
    }

    /**
     * 将此 NBT 复合标签设置指定特殊名和短整数类型标签值
     *
     * @param name 特殊名
     * @param value 短整数类型标签值
     */
    public void setShort(String name, int value) {
        setShort(name, (short) value);
    }

    /**
     * 将此 NBT 复合标签设置指定特殊名和整数类型标签值
     *
     * @param name 特殊名
     * @param value 整数类型标签值
     */
    public void setInteger(String name, int value) {
        this.value.put(name, new NBTTagInteger(name, value));
    }

    /**
     * 将此 NBT 复合标签设置指定特殊名和长整数类型标签值
     *
     * @param name 特殊名
     * @param value 长整数类型标签值
     */
    public void setLong(String name, long value) {
        this.value.put(name, new NBTTagLong(name, value));
    }

    /**
     * 将此 NBT 复合标签设置指定特殊名和单精度浮点数类型标签值
     *
     * @param name 特殊名
     * @param value 单精度浮点数类型标签值
     */
    public void setFloat(String name, float value) {
        this.value.put(name, new NBTTagFloat(name, value));
    }

    /**
     * 将此 NBT 复合标签设置指定特殊名和双精度浮点数类型标签值
     *
     * @param name 特殊名
     * @param value 双精度浮点数类型标签值
     */
    public void setDouble(String name, double value) {
        this.value.put(name, new NBTTagDouble(name, value));
    }

    /**
     * 将此 NBT 复合标签设置指定特殊名和字节数组类型标签值
     *
     * @param name 特殊名
     * @param value 字节数组类型标签值
     */
    public void setByteArray(String name, byte[] value) {
        this.value.put(name, new NBTTagByteArray(name, value));
    }

    /**
     * 将此 NBT 复合标签设置指定特殊名和整数数组类型标签值
     *
     * @param name 特殊名
     * @param value 整数数组类型标签值
     */
    public void setIntegerArray(String name, int[] value) {
        this.value.put(name, new NBTTagIntegerArray(name, value));
    }

    /**
     * 将此 NBT 复合标签设置指定特殊名和字符串类型标签值
     *
     * @param name 特殊名
     * @param value 字符串类型标签值
     */
    public void setString(String name, String value) {
        this.value.put(name, new NBTTagString(name, value));
    }

    /**
     * 将此 NBT 复合标签设置指定特殊名和布尔类型标签值
     *
     * @param name 特殊名
     * @param value 布尔类型标签值
     * @see #setByte(String, byte)
     */
    public void setBoolean(String name, boolean value) {
        setByte(name, value ? (byte) 1 : 0);
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的数字类型元素值
     *
     * @param name 特殊名
     * @return 数字类型元素值 | null
     */
    protected Number getNumber(String name) {
        NBTBase nbt = get(name);
        if(nbt != null && nbt instanceof NBTTagNumber)
            return (Number) nbt.getValue();
        return null;
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的字节类型元素值
     *
     * @param name 特殊名
     * @return 字节类型元素值 | 0
     */
    public byte getByte(String name) {
        return getByte(name, 0);
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的字节类型元素值
     *
     * @param name 特殊名
     * @param def 如果此特殊名没有关联的值则返回的默认值
     * @return 字节类型元素值 | def
     */
    public byte getByte(String name, int def) {
        return getByte(name, (byte) def);
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的字节类型元素值
     *
     * @param name 特殊名
     * @param def 如果此特殊名没有关联的值则返回的默认值
     * @return 字节类型元素值 | def
     */
    public byte getByte(String name, byte def) {
        Number value = getNumber(name);
        return value != null ? value.byteValue() : def;
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的短整数类型元素值
     *
     * @param name 特殊名
     * @return 短整数类型元素值 | 0
     */
    public short getShort(String name) {
        return getShort(name, 0);
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的短整数类型元素值
     *
     * @param name 特殊名
     * @param def 如果此特殊名没有关联的值则返回的默认值
     * @return 短整数类型元素值 | def
     */
    public short getShort(String name, int def) {
        return getShort(name, (byte) def);
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的短整数类型元素值
     *
     * @param name 特殊名
     * @param def 如果此特殊名没有关联的值则返回的默认值
     * @return 短整数类型元素值 | def
     */
    public short getShort(String name, short def) {
        Number value = getNumber(name);
        return value != null ? value.shortValue() : def;
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的整数类型元素值
     *
     * @param name 特殊名
     * @return 整数类型元素值 | 0
     */
    public int getInteger(String name) {
        return getInteger(name, 0);
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的整数类型元素值
     *
     * @param name 特殊名
     * @param def 如果此特殊名没有关联的值则返回的默认值
     * @return 整数类型元素值 | def
     */
    public int getInteger(String name, int def) {
        Number value = getNumber(name);
        return value != null ? value.intValue() : def;
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的长整数类型元素值
     *
     * @param name 特殊名
     * @return 长整数类型元素值 | 0
     */
    public long getLong(String name) {
        return getLong(name, 0);
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的长整数类型元素值
     *
     * @param name 特殊名
     * @param def 如果此特殊名没有关联的值则返回的默认值
     * @return 长整数类型元素值 | def
     */
    public long getLong(String name, long def) {
        Number value = getNumber(name);
        return value != null ? value.longValue() : def;
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的单精度浮点数类型元素值
     *
     * @param name 特殊名
     * @return 单精度浮点数类型元素值 | 0
     */
    public float getFloat(String name) {
        return getFloat(name, 0);
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的单精度浮点数类型元素值
     *
     * @param name 特殊名
     * @param def 如果此特殊名没有关联的值则返回的默认值
     * @return 单精度浮点数类型元素值 | def
     */
    public float getFloat(String name, float def) {
        Number value = getNumber(name);
        return value != null ? value.floatValue() : def;
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的双精度浮点数类型元素值
     *
     * @param name 特殊名
     * @return 双精度浮点数类型元素值 | 0
     */
    public double getDouble(String name) {
        return getDouble(name, 0);
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的双精度浮点数类型元素值
     *
     * @param name 特殊名
     * @param def 如果此特殊名没有关联的值则返回的默认值
     * @return 双精度浮点数类型元素值 | def
     */
    public double getDouble(String name, double def) {
        Number value = getNumber(name);
        return value != null ? value.doubleValue() : def;
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的字节数组类型元素值
     *
     * @param name 特殊名
     * @return 字节数组类型元素值 | null
     */
    public byte[] getByteArray(String name) {
        return getByteArray(name, null);
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的字节数组类型元素值
     *
     * @param name 特殊名
     * @param def 如果此特殊名没有关联的值则返回的默认值
     * @return 字节数组类型元素值 | def
     */
    public byte[] getByteArray(String name, byte[] def) {
        NBTBase nbt = get(name);
        return nbt != null && nbt instanceof NBTTagByteArray ? ((NBTTagByteArray) nbt).get() : def;
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的整数数组类型元素值
     *
     * @param name 特殊名
     * @return 整数数组类型元素值 | null
     */
    public int[] getIntegerArray(String name) {
        return getIntegerArray(name, null);
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的整数数组类型元素值
     *
     * @param name 特殊名
     * @param def 如果此特殊名没有关联的值则返回的默认值
     * @return 整数数组类型元素值 | def
     */
    public int[] getIntegerArray(String name, int[] def) {
        NBTBase nbt = get(name);
        return nbt != null && nbt instanceof NBTTagIntegerArray ? ((NBTTagIntegerArray) nbt).get() : def;
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的列表类型元素值
     *
     * @param name 特殊名
     * @return 列表类型元素值 | null
     */
    public NBTTagList<?> getList(String name) {
        return getList(name, null);
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的列表类型元素值
     *
     * @param name 特殊名
     * @param def 如果此特殊名没有关联的值则返回的默认值
     * @return 列表类型元素值 | def
     */
    public NBTTagList<?> getList(String name, NBTTagList<?> def) {
        NBTBase nbt = get(name);
        if(nbt != null && nbt instanceof NBTTagList)
            return (NBTTagList<?>) nbt;
        return def;
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的复合类型元素值
     *
     * @param name 特殊名
     * @return 复合类型元素值 | null
     */
    public NBTTagCompound getCompound(String name) {
        return getCompound(name, null);
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的复合类型元素值
     *
     * @param name 特殊名
     * @param def 如果此特殊名没有关联的值则返回的默认值
     * @return 复合类型元素值 | def
     */
    public NBTTagCompound getCompound(String name, NBTTagCompound def) {
        NBTBase nbt = get(name);
        if(nbt != null && nbt instanceof NBTTagCompound)
            return ((NBTTagCompound) nbt);
        return def;
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的字符串类型元素值
     *
     * @param name 特殊名
     * @return 字符串类型元素值 | null
     */
    public String getString(String name) {
        return getString(name, null);
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的字符串类型元素值
     *
     * @param name 特殊名
     * @param def 如果此特殊名没有关联的值则返回的默认值
     * @return 字符串类型元素值 | def
     */
    public String getString(String name, String def) {
        NBTBase nbt = get(name);
        return nbt != null && nbt instanceof NBTTagString ? ((NBTTagString) nbt).getValue() : def;
    }

    /**
     * 获取此 NBT 复合标签指定特殊名的布尔类型元素值
     *
     * @param name 特殊名
     * @return 布尔类型元素值 | false
     * @see #getByte(String)
     */
    public boolean getBoolean(String name) {
        return getByte(name) != 0;
    }

    /**
     * 将指定特殊名从此 NBT 复合标签内删除
     *
     * @param name 特殊名
     * @return 之前与特殊名关联的值, 如果没有映射关系则返回 null
     */
    public NBTBase remove(String name) {
        return value.remove(name);
    }

    /**
     * 获取此 NBT 复合标签的元素是否为空
     *
     * @return 元素是否为空
     */
    public boolean isEmpty() {
        return value.isEmpty();
    }

    /**
     * 获取此 NBT 复合标签是否拥有指定特殊名的元素值
     *
     * @param name 特殊名
     * @return true 则拥有, 否则 false
     */
    public boolean contains(String name) {
        return value.containsKey(name);
    }

    /**
     * 获取此 NBT 复合标签的元素数量大小
     *
     * @return 数量大小
     */
    public int size() {
        return value.size();
    }

    /**
     * 移除此 NBT 复合标签的所有元素标签
     */
    public void clear() {
        this.value.clear();
    }

    /**
     * 获取此 NBT 复合标签的特殊名集合
     *
     * @return 特殊名集合
     */
    public Set<String> keySet() {
        return value.keySet();
    }

    /**
     * 获取此 NBT 复合标签的元素值集合
     *
     * @return 元素值集合
     */
    public Collection<NBTBase> values() {
        return value.values();
    }

    @Override
    public void read(DataInput input) throws IOException {
        List<NBTBase> valueList = new ArrayList<>();
        try {
            NBTBase nbt;
            while((nbt = NBTUtil.read(input)) != null)
                valueList.add(nbt);
        } catch (Exception e) {
            throw new IOException("结束 NBTTagEnd 标签不存在.");
        }
        for(NBTBase nbt : valueList)
            put(nbt);
    }

    @Override
    public void write(DataOutput output) throws IOException {
        for(NBTBase nbt : values())
            NBTUtil.write(output, nbt);
        output.writeByte(0);
    }

    @Override
    public NBTTagCompound clone() {
        Map<String, NBTBase> value = new HashMap<>();
        for(Map.Entry<String, NBTBase> entry : super.value.entrySet())
            value.put(entry.getKey(), entry.getValue());
        return new NBTTagCompound(getName(), value);
    }

    @Override
    public Iterator<NBTBase> iterator() {
        return values().iterator();
    }

    @Override
    public boolean equals(Object obj) {
        if(super.equals(obj)) {
            NBTTagCompound other = (NBTTagCompound) obj;
            return value.entrySet().equals(other.value.entrySet());
        }
        return false;
    }

    @Override
    public String toString() {
        return toString(false);
    }

    /**
     * 将此 NBT 复合标签转换为字符串
     *
     * @param json 是否 Json 格式
     * @return 字符串
     */
    public String toString(boolean json) {
        StringBuilder builder = new StringBuilder("{");
        for(Iterator<String> iterator = value.keySet().iterator(); iterator.hasNext();) {
            String key = iterator.next();
            if(builder.length() != 1)
                builder.append(",");
            if(json)
                builder.append("\"").append(key).append("\"");
            else
                builder.append(key);
            builder.append(":");

            NBTBase nbt = value.get(key);
            if(nbt instanceof NBTTagCompound)
                builder.append(((NBTTagCompound) nbt).toString(json));
            else if(nbt instanceof NBTTagList)
                builder.append(((NBTTagList) nbt).toString(json));
            else
                builder.append(nbt);
        }
        return builder.append("}").toString();
    }
}
