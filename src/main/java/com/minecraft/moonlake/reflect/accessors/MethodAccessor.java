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

import java.lang.reflect.Method;

/**
 * <h1>MethodAccessor</h1>
 * 函数访问器
 *
 * @version 1.0
 * @author Month_Light
 */
public interface MethodAccessor {

    /**
     * 调用此函数并返回函数返回值
     *
     * @param instance 实例
     * @param params 参数
     * @return 返回值
     */
    Object invoke(Object instance, Object... params);

    /**
     * 获取此函数访问器的源函数对象
     *
     * @return 源函数对象
     */
    Method getMethod();
}
