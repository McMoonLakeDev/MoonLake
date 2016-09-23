package com.minecraft.moonlake.api.nbt;

import java.util.*;

/**
 * Created by MoonLake on 2016/9/22.
 */
public interface NBTList extends List<Object> {

    List<Object> getHandleList();

    Object getHandle();

    Object getHandleCopy();

    byte getType();

    void setType(byte type);

    <T extends List<Object>> T toList(T list);

    <T extends Collection<Object>> T toCollection(T collection);

    ArrayList<Object> toArrayList();

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
