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


package com.minecraft.moonlake.ref.cached;

import com.minecraft.moonlake.ref.MoonLakeRef;

import java.lang.ref.Reference;

/**
 * <h1>CachedRef</h1>
 * 缓存器引用数据接口
 *
 * @param <K> 键
 * @param <V> 值
 *
 * @version 1.0
 * @author Month_Light
 * @see MoonLakeRef
 */
public interface CachedRef<K, V> extends MoonLakeRef {

    /**
     * 获取此缓存器引用数据的引用对象
     *
     * @return 引用对象
     */
    Reference<V> reference();

    /**
     * 获取此缓存器引用数据的键对象
     *
     * @return 键
     */
    K key();
}
