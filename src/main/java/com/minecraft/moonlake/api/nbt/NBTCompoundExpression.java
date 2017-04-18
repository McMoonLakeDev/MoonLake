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

import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * <h1>NBTCompoundExpression</h1>
 * NBT 复合接口实现类
 *
 * @version 1.0
 * @author Month_Light
 */
class NBTCompoundExpression implements NBTCompound {

    private final Map<String, Object> handleMap;
    private final Object handle;

    /**
     * NBT 复合接口实现类构造函数
     */
    public NBTCompoundExpression() {

        this(NBTReflect.getHandle().createTagCompound());
    }

    /**
     * NBT 复合接口实现类构造函数
     *
     * @param tag NBT Tag 对象
     */
    public NBTCompoundExpression(Object tag) {

        assert NBTReflect.getHandle().getTagType(tag) == 10;

        this.handle = tag;
        this.handleMap = NBTReflect.getHandle().getHandleMap(tag);
    }

    /**
     * NBT 复合接口实现类构造函数
     *
     * @param map Map 对象
     */
    public NBTCompoundExpression(Map map) {

        this(NBTReflect.getHandle().createTagCompound());

        Iterator iterator = map.keySet().iterator();

        while(iterator.hasNext()) {

            Object key = iterator.next();
            put(key.toString(), map.get(key));
        }
    }

    public <T extends Map<String, Object>> T toMap(T map) {

        map.clear();

        Iterator iterator = handleMap.entrySet().iterator();

        while(iterator.hasNext()) {

            Entry entry = (Entry) iterator.next();
            String key = (String) entry.getKey();
            Object nbtTag = entry.getValue();
            byte type = NBTReflect.getHandle().getTagType(nbtTag);

            if(type == 9) {

                map.put(key, NBTReflect.fromNBTList(nbtTag).toArrayList());
            }
            else if(type == 10) {

                map.put(key, NBTReflect.fromNBTCompound(nbtTag).toHashMap());
            }
            else {

                map.put(key, NBTReflect.getHandle().getValue(nbtTag));
            }
        }
        return map;
    }

    public HashMap<String, Object> toHashMap() {

        return toMap(new HashMap<>());
    }

    public Map<String, Object> getHandleMap() {

        return handleMap;
    }

    public Object getHandle() {

        return handle;
    }

    public Object getHandleCopy() {

        return NBTReflect.getHandle().cloneTag(handle);
    }

    public boolean getBoolean(String key) {

        return getBoolean(key, false);
    }

    @Override
    public boolean getBoolean(String key, boolean def) {

        Object value = get(key);

        return    value instanceof Float ? (Float) value != 0f
                : value instanceof Double ? (Double) value != 0d
                : value instanceof Number ? ((Number) value).longValue() != 0L
                : value instanceof String ? ((String) value).isEmpty()
                : value instanceof int[] ? ((int[]) value).length != 0
                : value instanceof byte[] ? ((byte[]) value).length != 0
                : value instanceof Collection ? !((Collection) value).isEmpty()
                : value instanceof Map ? !((Map) value).isEmpty() : def;
    }

    public byte getByte(String key) {

        return getByte(key, (byte) 0);
    }

    @Override
    public byte getByte(String key, byte def) {

        Object value = get(key);

        if(value instanceof Number) {

            return ((Number) value).byteValue();
        }
        else {

            if(value instanceof String) {

                try {

                    return (byte) Long.parseLong((String) value);
                }
                catch (Exception e) {

                    try {

                        return (byte) Double.parseDouble((String) value);
                    }
                    catch (Exception e1) {

                    }
                }
            }
            return def;
        }
    }

    public short getShort(String key) {

        return getShort(key, (short) 0);
    }

    @Override
    public short getShort(String key, short def) {

        Object value = get(key);

        if(value instanceof Number) {

            return ((Number) value).shortValue();
        }
        else {

            if(value instanceof String) {

                try {

                    return (short) Long.parseLong((String) value);
                }
                catch (Exception e) {

                    try {

                        return (short) Double.parseDouble((String) value);
                    }
                    catch (Exception e1) {

                    }
                }
            }
            return def;
        }
    }

    public int getInt(String key) {

        return getInt(key, 0);
    }

    @Override
    public int getInt(String key, int def) {

        Object value = get(key);

        if(value instanceof Number) {

            return ((Number) value).intValue();
        }
        else {

            if(value instanceof String) {

                try {

                    return (int) Long.parseLong((String) value);
                }
                catch (Exception e) {

                    try {

                        return (int) Double.parseDouble((String) value);
                    }
                    catch (Exception e1) {

                    }
                }
            }
            return def;
        }
    }

    public long getLong(String key) {

        return getLong(key, 0L);
    }

    @Override
    public long getLong(String key, long def) {

        Object value = get(key);

        if(value instanceof Number) {

            return ((Number) value).longValue();
        }
        else {

            if(value instanceof String) {

                try {

                    return Long.parseLong((String) value);
                }
                catch (Exception e) {

                    try {

                        return (long) Double.parseDouble((String) value);
                    }
                    catch (Exception e1) {

                    }
                }
            }
            return def;
        }
    }

    public float getFloat(String key) {

        return getFloat(key, 0f);
    }

    @Override
    public float getFloat(String key, float def) {

        Object value = get(key);

        if(value instanceof Number) {

            return ((Number) value).floatValue();
        }
        else {

            if(value instanceof String) {

                try {

                    return (float) Double.parseDouble((String) value);
                }
                catch (Exception e1) {

                }
            }
            return def;
        }
    }

    public double getDouble(String key) {

        return getDouble(key, 0d);
    }

    @Override
    public double getDouble(String key, double def) {

        Object value = get(key);

        if(value instanceof Number) {

            return ((Number) value).doubleValue();
        }
        else {

            if(value instanceof String) {

                try {

                    return Double.parseDouble((String) value);
                }
                catch (Exception e1) {

                }
            }
            return def;
        }
    }

    public String getString(String key) {

        return getString(key, "");
    }

    @Override
    public String getString(String key, String def) {

        Object value = get(key);
        return value == null ? def : value.toString();
    }

    public int[] getIntArray(String key) {

        return getIntArray(key, new int[0]);
    }

    @Override
    public int[] getIntArray(String key, int[] def) {

        Object value = get(key);

        if(value instanceof int[]) {

            return (int[]) value;
        }
        else if(value instanceof byte[]) {

            byte[] byte0 = (byte[]) value;
            int[] result = new int[byte0.length];

            for(int i = 0; i < result.length; i++) {

                result[i] = (int) byte0[i];
            }
            return result;
        }
        return def;
    }

    public byte[] getByteArray(String key) {

        return getByteArray(key, new byte[0]);
    }

    @Override
    public byte[] getByteArray(String key, byte[] def) {

        Object value = get(key);

        if(value instanceof byte[]) {

            return (byte[]) value;
        }
        else if(value instanceof int[]) {

            int[] int0 = (int[]) value;
            byte[] result = new byte[int0.length];

            for(int i = 0; i < result.length; i++) {

                result[i] = (byte) int0[i];
            }
            return result;
        }
        return def;
    }

    public NBTCompound getCompound(String key) {

        return getCompound(key, null);
    }

    @Override
    public NBTCompound getCompound(String key, NBTCompound def) {

        Object value = get(key);
        return value != null && value instanceof NBTCompoundExpression ? (NBTCompoundExpression) value : def;
    }

    public NBTList getList(String key) {

        return getList(key, null);
    }

    @Override
    public NBTList getList(String key, NBTList def) {

        Object value = get(key);
        return value != null && value instanceof NBTListExpression ? (NBTListExpression) value : null;
    }

    public NBTCompound compound(String key) {

        Object value = get(key);

        if(value instanceof NBTCompoundExpression) {

            return (NBTCompoundExpression) value;
        }
        else {

            NBTCompoundExpression compound = new NBTCompoundExpression();
            put0(key, compound.getHandle());
            return compound;
        }
    }

    public NBTList list(String key) {

        Object value = get(key);

        if(value instanceof NBTListExpression) {

            return (NBTListExpression) value;
        }
        else {

            NBTListExpression list = new NBTListExpression();
            put0(key, list.getHandle());
            return list;
        }
    }

    public Object bind(String key, NBTCompound value) {

        Object value0 = get(key);
        put0(key, value.getHandle());
        return value0;
    }

    public Object bind(String key, NBTList value) {

        Object value0 = get(key);
        put0(key, value.getHandle());
        return value0;
    }

    @Override
    public void write(ItemStack itemStack) {

        Validate.notNull(itemStack, "The itemstack object is null.");

        NBTFactory.get().write(itemStack, this);
    }

    @Override
    public void write(Block block) {

        Validate.notNull(block, "The block object is null.");

        NBTFactory.get().write(block, this);
    }

    @Override
    public void write(Entity entity) {

        Validate.notNull(entity, "The entity object is null.");

        NBTFactory.get().write(entity, this);
    }

    @Override
    public void write(Chunk chunk) {

        Validate.notNull(chunk, "The chunk object is null.");

        NBTFactory.get().write(chunk, this);
    }

    public boolean containsKey(String key, Class type) {

        Object tag = get(key);
        return tag != null && type.isInstance(tag);
    }

    public boolean containsKey(String key, byte type) {

        Object tag = handleMap.get(key);
        return tag != null && NBTReflect.getHandle().getTagType(tag) == type;
    }

    @Override
    public NBTCompound clone() {

        return new NBTCompoundExpression(NBTReflect.getHandle().cloneTag(handle));
    }

    @Override
    public boolean equals(Object obj) {

        return obj instanceof NBTCompoundExpression && handle.equals(((NBTCompoundExpression) obj).handle);
    }

    @Override
    public int size() {

        return handleMap.size();
    }

    @Override
    public boolean isEmpty() {

        return handleMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {

        return handleMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {

        Object tag = NBTReflect.getHandle().createTag(value);
        return handleMap.containsValue(tag);
    }

    @Override
    public Object get(Object key) {

        return NBTReflect.getHandle().getValue(handleMap.get(key));
    }

    @Override
    public Object put(String key, Object value) {

        if(key == null) {

            return null;
        }
        else if(value == null) {

            return remove(key);
        }
        else {

            Object tag = NBTReflect.getHandle().createTag(value);
            Object oldTag = put0(key, tag);
            return NBTReflect.getHandle().getValue(oldTag);
        }
    }

    @SuppressWarnings("deprecation")
    private Object put0(String key, Object tag) {

        NBTReflect.getHandle().setTagName(tag, key);
        return handleMap.put(key, tag);
    }

    @Override
    public Object remove(Object key) {

        Object oldTag = handleMap.remove(key);
        return NBTReflect.getHandle().getValue(oldTag);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {

        if(m != null) {

            Iterator iterator = m.entrySet().iterator();

            while(iterator.hasNext()) {

                Entry entry = (Entry) iterator.next();
                String key = (String) entry.getKey();

                if(key != null) {

                    put(key, entry.getValue());
                }
            }
        }
    }

    @Override
    public void clear() {

        handleMap.clear();
    }

    @Override
    public Set<String> keySet() {

        return handleMap.keySet();
    }

    @Override
    public Collection<Object> values() {

        return new NBTCompoundExpression.NBTValues(handleMap.values());
    }

    @Override
    public NBTCompoundExpression.NBTEntrySet entrySet() {

        return new NBTCompoundExpression.NBTEntrySet(handleMap.entrySet());
    }

    public void merge(Map map) {

        Iterator iterator = map.keySet().iterator();

        while(true) {

            while(true) {

                Object key;

                do {

                    if(!iterator.hasNext()) {

                        return;
                    }
                    key = iterator.next();
                }
                while (key == null);

                if(!containsKey(key)) {

                    put(key.toString(), map.get(key));
                }
                else {

                    Object obj = get(key);
                    Object value = map.get(key);

                    if(obj instanceof NBTCompoundExpression && value instanceof Map) {

                        ((NBTCompoundExpression) obj).merge((Map) value);
                    }
                    else {

                        put(key.toString(), value);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {

        NBTCompoundExpression.NBTEntrySet.NBTIterator iterator = entrySet().iterator();

        if(!iterator.hasNext()) {

            return "{}";
        }
        else {

            StringBuilder stringBuilder = new StringBuilder().append('{');

            while(true) {

                NBTCompoundExpression.NBTEntrySet.NBTIterator.NBTEntry nbtEntry = iterator.next();
                Object obj = nbtEntry.getValue();

                stringBuilder.append(nbtEntry.getKey()).append(':');

                if(obj instanceof byte[]) {

                    stringBuilder.append("int[").append(((byte[]) obj).length).append(']');
                }
                else if(obj instanceof int[]) {

                    stringBuilder.append("byte[").append(((int[]) obj).length).append(']');
                }
                else if(obj instanceof String) {
                    // name:用户名"特殊包含 --> name:"用户名\"特殊包含"
                    stringBuilder.append("\"").append(obj.toString().replace("\"", "\\\"")).append("\"");
                }
                else {

                    stringBuilder.append(obj);
                }
                if(!iterator.hasNext()) {

                    return stringBuilder.append('}').toString();
                }
                stringBuilder.append(",");
            }
        }
    }

    public class NBTEntrySet extends AbstractSet<Entry<String, Object>> {

        private Set<Entry<String, Object>> entries;

        public NBTEntrySet(Set<Entry<String, Object>> entries) {

            this.entries = entries;
        }

        @Override
        public NBTEntrySet.NBTIterator iterator() {

            return new NBTCompoundExpression.NBTEntrySet.NBTIterator(entries.iterator());
        }

        @Override
        public int size() {

            return entries.size();
        }

        public class NBTIterator implements Iterator<Entry<String, Object>> {

            private Iterator<Entry<String, Object>> iterator;

            private NBTIterator(Iterator<Entry<String, Object>> iterator) {

                this.iterator = iterator;
            }

            @Override
            public boolean hasNext() {

                return iterator.hasNext();
            }

            @Override
            public NBTCompoundExpression.NBTEntrySet.NBTIterator.NBTEntry next() {

                return new NBTCompoundExpression.NBTEntrySet.NBTIterator.NBTEntry(iterator.next());
            }

            @Override
            public void remove() {

            }

            public class NBTEntry implements Entry<String, Object> {

                private Entry<String, Object> entry;

                public NBTEntry(Entry<String, Object> entry) {

                    this.entry = entry;
                }

                @Override
                public String getKey() {

                    return entry.getKey();
                }

                @Override
                public Object getValue() {

                    return NBTReflect.getHandle().getValue(entry.getValue());
                }

                @Override
                public Object setValue(Object value) {

                    Object tag;

                    if(value == null) {

                        tag = getValue();
                        NBTIterator.this.remove();
                        return tag;
                    }
                    else {

                        tag = NBTReflect.getHandle().createTag(value);
                        Object oldTag = entry.setValue(tag);
                        return NBTReflect.getHandle().getValue(oldTag);
                    }
                }
            }
        }
    }

    public class NBTValues extends AbstractCollection<Object> {

        Collection<Object> handle;

        private NBTValues(Collection<Object> handle) {

            this.handle = handle;
        }

        @Override
        public Iterator<Object> iterator() {

            return new NBTCompoundExpression.NBTValues.NBTValuesIterator(handle.iterator());
        }

        @Override
        public int size() {

            return handle.size();
        }

        public class NBTValuesIterator implements Iterator<Object> {

            private Iterator<Object> handle;

            private NBTValuesIterator(Iterator<Object> handle) {

                this.handle = handle;
            }

            @Override
            public boolean hasNext() {

                return handle.hasNext();
            }

            @Override
            public Object next() {

                return NBTReflect.getHandle().getValue(handle.next());
            }

            @Override
            public void remove() {

                handle.remove();
            }
        }
    }
}
