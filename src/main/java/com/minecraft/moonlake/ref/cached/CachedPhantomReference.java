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
 * <h1>CachedPhantomReference</h1>
 * 缓存器虚引用类
 *
 * @param <K> 键
 * @param <V> 值
 *
 * @version 1.0.1
 * @author Month_Light
 * @see CachedPhantomRef
 * @see CachedReferenceBase
 */
public abstract class CachedPhantomReference<K, V> extends CachedReferenceBase<K, V, CachedPhantomRef<K, V>> {

    /**
     * 缓存器虚引用类构造函数
     */
    public CachedPhantomReference() {
        super();
    }

    @Override
    protected abstract V produceValue(K key);

    @Override
    protected CachedPhantomRef<K, V> produceRef(K key, V value, CachedReferenceQueue<V> queue) {
        return new CachedPhantomRef<>(key, value, queue);
    }
}
