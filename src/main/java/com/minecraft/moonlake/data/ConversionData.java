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
 
 
package com.minecraft.moonlake.data;

/**
 * <h1>ConversionData</h1>
 * 数据转换接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v2.0 删除.
 */
@Deprecated
public interface ConversionData {

    /**
     * 将数据转换到字符串
     *
     * @return 字符串
     */
    String asString();

    /**
     * 将数据转换到整数
     *
     * @return 整数
     */
    int asInt();

    /**
     * 将数据转换到双精度浮点数
     *
     * @return 双精度浮点数
     */
    double asDouble();

    /**
     * 将数据转换到单精度浮点数
     *
     * @return 单精度浮点数
     */
    float asFloat();

    /**
     * 将数据转换到长整数
     *
     * @return 长整数
     */
    long asLong();

    /**
     * 将数据转换到短整数
     *
     * @return 短整数
     */
    short asShort();

    /**
     * 将数据转换到字符
     *
     * @return 字符
     */
    char asChar();

    /**
     * 将数据转换到布尔值
     *
     * @return 布尔值
     */
    boolean asBoolean();

    /**
     * 将数据转换到字节
     *
     * @return 字节
     */
    byte asByte();
}
