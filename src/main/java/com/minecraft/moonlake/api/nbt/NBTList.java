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

import java.util.*;

/**
 * <h1>NBTList</h1>
 * NBT 列表接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface NBTList extends List<Object> {

    /**
     * 获取此 NBT 列表的句柄数据对象
     *
     * @return NBT 句柄数据
     */
    List<Object> getHandleList();

    /**
     * 获取此 NBT 列表的句柄对象
     *
     * @return NBT 句柄
     */
    Object getHandle();

    /**
     * 获取此 NBT 列表的句柄对象的拷贝
     *
     * @return NBT 句柄
     */
    Object getHandleCopy();

    /**
     * 获取此 NBT 列表的 NBT 类型
     *
     * @return NBT 类型
     */
    byte getType();

    /**
     * 设置此 NBT 列表的 NBT 类型
     *
     * @param type NBT 类型
     */
    void setType(byte type);

    /**
     * 将此 NBT 列表转换到 List 对象
     *
     * @param list List
     * @param <T> List
     * @return List
     */
    <T extends List<Object>> T toList(T list);

    /**
     * 将此 NBT 列表转换到 Collection 对象
     *
     * @param collection Collection
     * @param <T> Collection
     * @return Collection
     */
    <T extends Collection<Object>> T toCollection(T collection);

    /**
     * 将此 NBT 列表转换到 ArrayList 对象
     *
     * @return ArrayList
     */
    ArrayList<Object> toArrayList();

    /**
     * 克隆此 NBT 列表对象
     *
     * @return NBTList
     */
    NBTList clone();

    @Override
    int size();

    @Override
    boolean isEmpty();

    @Override
    boolean contains(Object o);

    @Override
    Iterator<Object> iterator();

    @Override
    Object[] toArray();

    @Override
    <T> T[] toArray(T[] a);

    @Override
    boolean add(Object o);

    @Override
    boolean remove(Object o);

    @Override
    boolean containsAll(Collection<?> c);

    @Override
    boolean addAll(Collection<?> c);

    @Override
    boolean addAll(int index, Collection<?> c);

    @Override
    boolean removeAll(Collection<?> c);

    @Override
    boolean retainAll(Collection<?> c);

    @Override
    void clear();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();

    @Override
    Object get(int index);

    @Override
    Object set(int index, Object element);

    @Override
    void add(int index, Object element);

    @Override
    Object remove(int index);

    @Override
    int indexOf(Object o);

    @Override
    int lastIndexOf(Object o);

    @Override
    ListIterator<Object> listIterator();

    @Override
    ListIterator<Object> listIterator(int index);

    @Override
    List<Object> subList(int fromIndex, int toIndex);
}
