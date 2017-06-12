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

/**
 * <h1>NBTTagDatable</h1>
 * NBT 标签泛型数据
 *
 * @param <T> 值类型
 * @version 1.0
 * @author Month_Ligth
 * @see NBTBase
 */
public abstract class NBTTagDatable<T> extends NBTBase {

    /**
     * Type Value
     */
    protected T value;

    /**
     * NBT 标签泛型数据构造函数
     *
     * @param name 特殊名
     * @param value 值
     */
    public NBTTagDatable(String name, T value) {
        super(name);
        this.value = value;
    }

    /**
     * 获取此 NBT 标签泛型数据的值
     *
     * @return 值
     */
    @Override
    public T getValue() {
        return value;
    }

    /**
     * 设置此 NBT 标签泛型数据的值
     *
     * @param value 新值
     */
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if(super.equals(obj)) {
            NBTTagDatable other = (NBTTagDatable) obj;
            return value.equals(other.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
