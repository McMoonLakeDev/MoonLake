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

import java.lang.reflect.Constructor;

/**
 * <h1>ConstructorAccessor</h1>
 * 构造函数访问器接口
 *
 * @param <T> 类型
 * @version 1.0
 * @author Month_Light
 */
public interface ConstructorAccessor<T> {

    /**
     * 调用此构造函数并返回实例
     *
     * @param params 参数
     * @return 实例
     */
    T invoke(Object... params);

    /**
     * 获取此构造函数访问器的源构造函数对象
     *
     * @return 源构造函数对象
     */
    Constructor<T> getConstructor();
}
