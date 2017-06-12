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
 * <h1>NBTBase</h1>
 * NBT 标签
 *
 * @version 1.0
 * @author Month_Light
 */
public abstract class NBTBase implements Cloneable {

    private final String name;

    /**
     * NBT 标签构造函数
     *
     * @param name 特殊名
     */
    public NBTBase(String name) {
        this.name = name;
    }

    /**
     * 获取此 NBT 标签特殊名
     *
     * @return 特殊名
     */
    public final String getName() {
        return name;
    }

    /**
     * 获取此 NBT 标签的值
     *
     * @return 值
     */
    public abstract Object getValue();

    /**
     * 获取此 NBT 标签类型
     *
     * @return 类型
     */
    public abstract NBTType getType();

    /**
     * 读取指定数据输入流
     *
     * @param input 数据输入流
     * @throws IOException 如果 IO 错误则抛出异常
     */
    public abstract void read(DataInput input) throws IOException;

    /**
     * 写入指定数据输出流
     *
     * @param output 数据输出流
     * @throws IOException 如果 IO 错误则抛出异常
     */
    public abstract void write(DataOutput output) throws IOException;

    /**
     * 获取此 NBT 标签类型 Id
     *
     * @return 类型 Id
     */
    public int getTypeId() {
        return getType().getTypeId();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof NBTBase) {
            NBTBase other = (NBTBase) obj;
            return name.equals(other.name) && getTypeId() == other.getTypeId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getTypeId();
    }

    @Override
    public abstract String toString();

    @Override
    public abstract NBTBase clone();
}
