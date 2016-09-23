package com.minecraft.moonlake.api.nbt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by MoonLake on 2016/9/21.
 */
public interface NBTCompound extends Map<String, Object> {

    HashMap<String, Object> toHashMap();

    Object getHandle();

    Map<String, Object> getHandleMap();

    <T extends Map<String, Object>> T toMap(T map);

    Object getHandleCopy();

    boolean getBoolean(String key);

    byte getByte(String key);

    short getShort(String key);

    int getInt(String key);

    long getLong(String key);

    float getFloat(String key);

    double getDouble(String key);

    String getString(String key);

    byte[] getByteArray(String key);

    int[] getIntArray(String key);

    NBTCompound getCompound(String key);

    NBTListExpression getList(String key);

    NBTCompound compound(String key);

    NBTList list(String key);

    Object bind(String key, NBTCompound value);

    Object bind(String key, NBTList value);

    boolean containsKey(String key, Class type);

    boolean containsKey(String key, byte type);

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
