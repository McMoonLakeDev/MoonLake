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

import java.lang.ref.PhantomReference;

/**
 * <h1>CachedPhantomRef</h1>
 * 缓存器虚引用数据类
 *
 * @param <K> 键
 * @param <V> 值
 *
 * @version 1.0.1
 * @author Month_Light
 * @see CachedRef
 * @see PhantomReference
 */
public class CachedPhantomRef<K, V> extends PhantomReference<V> implements CachedRef<K, V> {

    private K key;

    /**
     * 缓存器虚引用数据类构造函数
     *
     * @param key 键
     * @param referent 引用对象
     * @param queue 缓存器引用队列
     */
    public CachedPhantomRef(K key, V referent, CachedReferenceQueue<? super V> queue) {
        super(referent, queue);
        this.key = key;
    }

    @Override
    public PhantomReference<V> reference() {
        return this;
    }

    @Override
    public K key() {
        return key;
    }
}
