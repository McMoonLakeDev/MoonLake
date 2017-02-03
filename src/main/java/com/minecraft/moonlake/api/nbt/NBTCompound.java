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
 
 
package com.minecraft.moonlake.api.nbt;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <h1>NBTCompound</h1>
 * NBT 复合接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface NBTCompound extends Map<String, Object> {

    /**
     * 将此 NBT 复合转换到 HashMap 对象
     *
     * @return HashMap
     */
    HashMap<String, Object> toHashMap();

    /**
     * 获取此 NBT 复合的句柄对象
     *
     * @return NBT 句柄
     */
    Object getHandle();

    /**
     * 获取此 NBT 复合的句柄数据对象
     *
     * @return NBT 句柄数据
     */
    Map<String, Object> getHandleMap();

    /**
     * 将此 NBT 复合转换到指定类型的 Map 对象
     *
     * @param map Map 对象
     * @param <T> Map
     * @return Map
     */
    <T extends Map<String, Object>> T toMap(T map);

    /**
     * 获取此 NBT 复合的句柄对象的拷贝
     *
     * @return NBT 句柄
     */
    Object getHandleCopy();

    /**
     * 获取此 NBT 复合指定键的 {@code boolean} 值
     *
     * @param key 键
     * @return boolean
     */
    boolean getBoolean(String key);

    /**
     * 获取此 NBT 复合指定键的 {@code boolean} 值
     *
     * @param key 键
     * @param def 默认值
     * @return boolean
     */
    boolean getBoolean(String key, boolean def);

    /**
     * 获取此 NBT 复合指定键的 {@code byte} 值
     *
     * @param key 键
     * @return byte
     */
    byte getByte(String key);

    /**
     * 获取此 NBT 复合指定键的 {@code byte} 值
     *
     * @param key 键
     * @param def 默认值
     * @return byte
     */
    byte getByte(String key, byte def);

    /**
     * 获取此 NBT 复合指定键的 {@code short} 值
     *
     * @param key 键
     * @return short
     */
    short getShort(String key);

    /**
     * 获取此 NBT 复合指定键的 {@code short} 值
     *
     * @param key 键
     * @param def 默认值
     * @return short
     */
    short getShort(String key, short def);

    /**
     * 获取此 NBT 复合指定键的 {@code int} 值
     *
     * @param key 键
     * @return int
     */
    int getInt(String key);

    /**
     * 获取此 NBT 复合指定键的 {@code int} 值
     *
     * @param key 键
     * @param def 默认值
     * @return int
     */
    int getInt(String key, int def);

    /**
     * 获取此 NBT 复合指定键的 {@code long} 值
     *
     * @param key 键
     * @return long
     */
    long getLong(String key);

    /**
     * 获取此 NBT 复合指定键的 {@code long} 值
     *
     * @param key 键
     * @param def 默认值
     * @return long
     */
    long getLong(String key, long def);

    /**
     * 获取此 NBT 复合指定键的 {@code float} 值
     *
     * @param key 键
     * @return float
     */
    float getFloat(String key);

    /**
     * 获取此 NBT 复合指定键的 {@code float} 值
     *
     * @param key 键
     * @param def 默认值
     * @return float
     */
    float getFloat(String key, float def);

    /**
     * 获取此 NBT 复合指定键的 {@code double} 值
     *
     * @param key 键
     * @return double
     */
    double getDouble(String key);

    /**
     * 获取此 NBT 复合指定键的 {@code double} 值
     *
     * @param key 键
     * @param def 默认值
     * @return double
     */
    double getDouble(String key, double def);

    /**
     * 获取此 NBT 复合指定键的 {@code String} 值
     *
     * @param key 键
     * @return String
     */
    String getString(String key);

    /**
     * 获取此 NBT 复合指定键的 {@code String} 值
     *
     * @param key 键
     * @param def 默认值
     * @return String
     */
    String getString(String key, String def);

    /**
     * 获取此 NBT 复合指定键的 {@code byte[]} 值
     *
     * @param key 键
     * @return byte[]
     */
    byte[] getByteArray(String key);

    /**
     * 获取此 NBT 复合指定键的 {@code byte[]} 值
     *
     * @param key 键
     * @param def 默认值
     * @return byte[]
     */
    byte[] getByteArray(String key, byte[] def);

    /**
     * 获取此 NBT 复合指定键的 {@code int[]} 值
     *
     * @param key 键
     * @return int[]
     */
    int[] getIntArray(String key);


    /**
     * 获取此 NBT 复合指定键的 {@code int[]} 值
     *
     * @param key 键
     * @param def 默认值
     * @return int[]
     */
    int[] getIntArray(String key, int[] def);

    /**
     * 获取此 NBT 复合指定键的 {@code NBTCompound} 值
     *
     * @param key 键
     * @return NBTCompound
     */
    NBTCompound getCompound(String key);

    /**
     * 获取此 NBT 复合指定键的 {@code NBTCompound} 值
     *
     * @param key 键
     * @param def 默认值
     * @return NBTCompound
     */
    NBTCompound getCompound(String key, NBTCompound def);

    /**
     * 获取此 NBT 复合指定键的 {@code NBTList} 值
     *
     * @param key 键
     * @return NBTList
     */
    NBTList getList(String key);

    /**
     * 获取此 NBT 复合指定键的 {@code NBTList} 值
     *
     * @param key 键
     * @param def 默认值
     * @return NBTList
     */
    NBTList getList(String key, NBTList def);

    NBTCompound compound(String key);

    NBTList list(String key);

    Object bind(String key, NBTCompound value);

    Object bind(String key, NBTList value);

    /**
     * 将此 NBT 复合对象写入到指定物品栈
     *
     * @param itemStack 物品栈
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    void write(ItemStack itemStack);

    /**
     * 将此 NBT 复合对象写入到指定方块
     *
     * @param block 方块
     * @throws IllegalArgumentException 如果方块对象为 {@code null} 则抛出异常
     */
    void write(Block block);

    /**
     * 将此 NBT 复合对象写入到指定实体
     *
     * @param entity 实体
     * @throws IllegalArgumentException 如果实体对象为 {@code null} 则抛出异常
     */
    void write(Entity entity);

    /**
     * 将此 NBT 复合对象写入到指定区块
     *
     * @param chunk 区块
     * @throws IllegalArgumentException 如果区块对象为 {@code null} 则抛出异常
     */
    void write(Chunk chunk);

    /**
     * 获取此 NBT 复合是否包含指定键的值类型
     *
     * @param key 键
     * @param type 值类型
     * @return true 则包含
     */
    boolean containsKey(String key, Class type);

    /**
     * 获取此 NBT 复合是否包含指定键的值类型
     *
     * @param key 键
     * @param type NBT 类型
     * @return true 则包含
     */
    boolean containsKey(String key, byte type);

    /**
     * 克隆此 NBT 复合对象
     *
     * @return NBTCompound
     */
    NBTCompound clone();

    @Override
    int size();

    @Override
    boolean isEmpty();

    @Override
    boolean containsKey(Object key);

    @Override
    boolean containsValue(Object value);

    @Override
    Object get(Object key);

    @Override
    Object put(String key, Object value);

    @Override
    Object remove(Object key);

    @Override
    void putAll(Map<? extends String, ?> m);

    @Override
    void clear();

    @Override
    Set<String> keySet();

    @Override
    Collection<Object> values();

    @Override
    Set<Entry<String, Object>> entrySet();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
