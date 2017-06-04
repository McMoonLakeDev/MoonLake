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

/**
 * <h1>CachedReference</h1>
 * 缓存器引用接口
 *
 * @param <K> 键
 * @param <V> 值
 * @param <R> 缓存器引用数据
 *
 * @version 1.0
 * @author Month_Light
 * @see Cached
 * @see CachedRef
 */
public interface CachedReference<K, V, R extends CachedRef<K, V>> extends Cached {

    /**
     * 获取当前缓存器内指定键的缓存数据对象, 如果没有存在则进行创建
     *
     * @param key 键
     * @return 缓存数据对象
     * @see CachedReferenceBase#produceValue(Object)
     */
    V getCache(K key);
}
