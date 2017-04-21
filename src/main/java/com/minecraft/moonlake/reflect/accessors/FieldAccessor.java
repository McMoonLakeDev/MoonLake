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


package com.minecraft.moonlake.reflect.accessors;

import java.lang.reflect.Field;

/**
 * <h1>FieldAccessor</h1>
 * 字段访问器接口
 *
 * @version 1.0
 * @author Month_Light
 */
public interface FieldAccessor {

    /**
     * 获取此字段访问器的字段值
     *
     * @param instance 实例
     * @return 字段值
     */
    Object get(Object instance);

    /**
     * 设置此字段访问器的字段值
     *
     * @param instance 实例
     * @param value 值
     */
    void set(Object instance, Object value);

    /**
     * 获取此字段访问器的源字段对象
     *
     * @return 源字段对象
     */
    Field getField();
}
