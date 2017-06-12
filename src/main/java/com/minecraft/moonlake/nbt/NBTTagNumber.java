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
 * <h1>NBTTagNumber</h1>
 * NBT 标签数字类型数据
 *
 * @param <T> 数字类型
 * @version 1.0
 * @author Month_Light
 * @see NBTTagDatable
 * @see Number
 */
public abstract class NBTTagNumber<T extends Number> extends NBTTagDatable<T> {

    /**
     * NBT 标签数字类型数据构造寒山寺
     *
     * @param name 特殊名
     * @param value 数字值
     */
    public NBTTagNumber(String name, T value) {
        super(name, value);
    }

    /**
     * 获取此 NBT 标签数字值的 {@code int} 类型值
     *
     * @return {@code int} 类型值
     * @see Number#intValue()
     */
    public int intValue() {
        return getValue().intValue();
    }

    /**
     * 获取此 NBT 标签数字值的 {@code long} 类型值
     *
     * @return {@code long} 类型值
     * @see Number#longValue()
     */
    public long longValue() {
        return getValue().longValue();
    }

    /**
     * 获取此 NBT 标签数字值的 {@code float} 类型值
     *
     * @return {@code float} 类型值
     * @see Number#floatValue()
     */
    public float floatValue() {
        return getValue().floatValue();
    }

    /**
     * 获取此 NBT 标签数字值的 {@code double} 类型值
     *
     * @return {@code double} 类型值
     * @see Number#doubleValue()
     */
    public double doubleValue() {
        return getValue().doubleValue();
    }

    /**
     * 获取此 NBT 标签数字值的 {@code byte} 类型值
     *
     * @return {@code byte} 类型值
     * @see Number#byteValue()
     */
    public byte byteValue() {
        return getValue().byteValue();
    }

    /**
     * 获取此 NBT 标签数字值的 {@code short} 类型值
     *
     * @return {@code short} 类型值
     * @see Number#shortValue()
     */
    public short shortValue() {
        return getValue().shortValue();
    }
}
