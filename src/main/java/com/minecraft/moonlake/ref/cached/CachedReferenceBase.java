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

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>CachedReferenceBase</h1>
 * 缓存器引用基础抽象类
 *
 * @param <K> 键
 * @param <V> 值
 * @param <R> 缓存器引用数据
 *
 * @version 1.0.1
 * @author Month_Light
 */
public abstract class CachedReferenceBase<K, V, R extends CachedRef<K, V>> implements CachedReference<K, V, R> {

    private Map<K, R> cacheMap;
    private CachedReferenceQueue<V> queue;

    /**
     * 缓存器引用基础抽象类构造函数
     */
    protected CachedReferenceBase() {
        this.cacheMap = new HashMap<>();
        this.queue = new CachedReferenceQueue<>();
    }

    /**
     * 缓存器引用基础抽象类构造函数
     *
     * @param map 缓存 Map
     */
    protected CachedReferenceBase(Map<K, R> map) {
        this.cacheMap = map != null ? map : new HashMap<>();
        this.queue = new CachedReferenceQueue<>();
    }

    @Override
    public V getCache(K key) {
        V value = null;
        if(cacheMap.containsKey(key)) {
            R ref = cacheMap.get(key);
            value = ref.reference().get();
        }
        if(value == null) {
            value = produceValue(key);
            this.addCache(key, value);
        }
        return value;
    }

    @Override
    public void clear() {
        this.removeCache();
        this.cacheMap.clear();
    }

    /**
     * 生产指定键对应的缓存引用数据对象
     *
     * @param key 键
     * @return 值
     */
    protected abstract V produceValue(K key);

    /**
     * 生产指定值的缓存器引用数据
     *
     * @param key 键
     * @param value 值
     * @param queue 队列
     * @return 缓存器引用数据
     */
    protected abstract R produceRef(K key, V value, CachedReferenceQueue<V> queue);

    /**
     * 将当前缓存器中已经被添加到队列内的引用数据进行删除
     */
    @SuppressWarnings("unchecked")
    protected final void removeCache() {
        R ref;
        while ((ref = (R) queue.poll()) != null)
            cacheMap.remove(ref.key()); // gc
    }

    /**
     * 将当前缓存器中指定存在的键进行删除, 然后开始清除队列内的
     *
     * @param key 键
     */
    protected final void removeCache(K key) {
        if(cacheMap.containsKey(key))
            cacheMap.remove(key);
        removeCache();
    }

    /**
     * 将指定键和指定值进行生产缓存器引用数据并添加到缓存器内
     *
     * @param key 键
     * @param value 值
     */
    private void addCache(K key, V value) {
        this.removeCache();
        R ref = produceRef(key, value, queue);
        cacheMap.put(key, ref);
    }
}
