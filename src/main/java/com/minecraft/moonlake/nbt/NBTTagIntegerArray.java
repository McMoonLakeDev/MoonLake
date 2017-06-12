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
import java.util.Arrays;

/**
 * <h1>NBTTagIntegerArray</h1>
 * NBT 标签整数数组类型数据
 *
 * @version 1.0
 * @author Month_Light
 * @see NBTTagDatable
 * @see Integer
 */
public class NBTTagIntegerArray extends NBTTagDatable<Integer[]> {

    /**
     * NBT 标签整数数组类型数据构造函数
     */
    public NBTTagIntegerArray() {
        this(new Integer[0]);
    }

    /**
     * NBT 标签整数数组类型数据构造函数
     *
     * @param value 基础整数数组类型值
     */
    public NBTTagIntegerArray(int[] value) {
        this("", value);
    }

    /**
     * NBT 标签整数数组类型数据构造函数
     *
     * @param value 包装整数数组类型值
     */
    public NBTTagIntegerArray(Integer[] value) {
        this("", value);
    }

    /**
     * NBT 标签整数数组类型数据构造函数
     *
     * @param name 特殊名
     */
    public NBTTagIntegerArray(String name) {
        this(name, new Integer[0]);
    }

    /**
     * NBT 标签整数数组类型数据构造函数
     *
     * @param name 特殊名
     * @param value 基础整数数组类型值
     */
    public NBTTagIntegerArray(String name, int[] value) {
        super(name, null); // 先传一个 null 值之后再重新设置新值
        set(value);
    }

    /**
     * NBT 标签整数数组类型数据构造函数
     *
     * @param name 特殊名
     * @param value 包装整数数组类型值
     */
    public NBTTagIntegerArray(String name, Integer[] value) {
        super(name, value);
    }

    @Override
    public NBTType getType() {
        return NBTType.INTEGER_ARRAY;
    }

    /**
     * 设置此 NBT 标签整数数组数据的值
     *
     * @param value 新值
     */
    public void set(int[] value) {
        Integer[] value0 = new Integer[value.length];
        for(int i = 0; i < value0.length; i++)
            value0[i] = value[i];
        super.value = value0;
    }

    /**
     * 获取此 NBT 标签整数数组的值
     *
     * @return 基础整数数组类型值
     */
    public int[] get() {
        int[] value0 = new int[value.length];
        for(int i = 0; i < value0.length; i++)
            value0[i] = value[i];
        return value0;
    }

    /**
     * 获取此 NBT 标签整数数组的值
     *
     * @return 包装整数数组类型值
     */
    @Override
    public Integer[] getValue() {
        return super.getValue();
    }

    @Override
    public void read(DataInput input) throws IOException {
        int[] value = new int[input.readInt()];
        for(int i = 0; i < value.length; i++)
            value[i] = input.readInt();
        set(value);
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(value.length);
        for(int i = 0; i < value.length; i++)
            output.writeInt(value[i]);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && Arrays.equals(value, ((NBTTagIntegerArray) obj).value);
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(value);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for(int i = 0; i < value.length; i++)
            builder.append(value[i]).append(",");
        return builder.append("]").toString();
    }

    @Override
    public NBTTagIntegerArray clone() {
        return new NBTTagIntegerArray(getName(), value);
    }
}
