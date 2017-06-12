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

/**
 * <h1>NBTTagShort</h1>
 * NBT 标签短整数类型数据
 *
 * @version 1.0
 * @author Month_Light
 * @see NBTTagNumber
 * @see Short
 */
public class NBTTagShort extends NBTTagNumber<Short> {

    /**
     * NBT 标签短整数类型数据构造函数
     */
    public NBTTagShort() {
        this((short) 0);
    }

    /**
     * NBT 标签短整数类型数据构造函数
     *
     * @param value 值
     */
    public NBTTagShort(short value) {
        this("", value);
    }

    /**
     * NBT 标签短整数类型数据构造函数
     *
     * @param name 特殊名
     */
    public NBTTagShort(String name) {
        this(name, (short) 0);
    }

    /**
     * NBT 标签短整数类型数据构造函数
     *
     * @param name 特殊名
     * @param value 值
     */
    public NBTTagShort(String name, Short value) {
        super(name, value);
    }

    /**
     * 设置此 NBT 标签短整数数据的值
     *
     * @param value 新值
     */
    public void set(short value) {
        super.value = value;
    }

    @Override
    public NBTType getType() {
        return NBTType.SHORT;
    }

    @Override
    public void read(DataInput input) throws IOException {
        super.value = input.readShort();
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeShort(value);
    }

    @Override
    public NBTTagShort clone() {
        return new NBTTagShort(getName(), value);
    }
}
