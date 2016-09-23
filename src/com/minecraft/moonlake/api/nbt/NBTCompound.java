package com.minecraft.moonlake.api.nbt;

import com.minecraft.moonlake.nbt.utils.NBTReflect;

import java.util.*;

/**
 * Created by MoonLake on 2016/9/21.
 */
public class NBTCompound implements Map<String, Object> {

    private final Map<String, Object> handleMap;
    private final Object handle;

    public NBTCompound() {

        this(NBTReflect.getHandle().createTagCompound());
    }

    public NBTCompound(Object tag) {

        assert NBTReflect.getHandle().getTagType(tag) == 10;

        this.handle = tag;
        this.handleMap = NBTReflect.getHandle().getHandleMap(tag);
    }

    public NBTCompound(Map map) {

        this(NBTReflect.getHandle().createTagCompound());

        Iterator iterator = map.keySet().iterator();

        while(iterator.hasNext()) {

            Object key = iterator.next();
            put(key.toString(), map.get(key));
        }
    }

    public static NBTCompound fromNBT(Object tag) {

        return tag == null ? null : new NBTCompound(tag);
    }

    public static NBTCompound fromNBTCopy(Object tag) {

        return tag == null ? null : fromNBT(NBTReflect.getHandle().cloneTag(tag));
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

                map.put(key, NBTList.fromNBT(nbtTag).toArrayList());
            }
            else if(type == 10) {

                map.put(key, fromNBT(nbtTag).toHashMap());
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

        Object value = get(key);

        return    value instanceof Float ? (Float) value != 0f
                : value instanceof Double ? (Double) value != 0d
                : value instanceof Number ? ((Number) value).longValue() != 0L
                : value instanceof String ? ((String) value).isEmpty()
                : value instanceof int[] ? ((int[]) value).length != 0
                : value instanceof byte[] ? ((byte[]) value).length != 0
                : value instanceof Collection ? !((Collection) value).isEmpty()
                : value instanceof Map ? !((Map) value).isEmpty() : false;
    }

    public byte getByte(String key) {

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
            return 0;
        }
    }

    public short getShort(String key) {

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
            return 0;
        }
    }

    public int getInt(String key) {

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
            return 0;
        }
    }

    public long getLong(String key) {

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
            return 0L;
        }
    }

    public float getFloat(String key) {

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
            return 0f;
        }
    }

    public double getDouble(String key) {

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
            return 0d;
        }
    }

    public String getString(String key) {

        Object value = get(key);
        return value == null ? "" : value.toString();
    }

    public int[] getIntArray(String key) {

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
        else {

            return new int[0];
        }
    }

    public byte[] getByteArray(String key) {

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
        else {

            return new byte[0];
        }
    }

    public NBTCompound getCompound(String key) {

        Object value = get(key);
        return value instanceof NBTCompound ? (NBTCompound) value : null;
    }

    public NBTList getList(String key) {

        Object value = get(key);
        return value instanceof NBTList ? (NBTList) value : null;
    }

    public NBTCompound compound(String key) {

        Object value = get(key);

        if(value instanceof NBTCompound) {

            return (NBTCompound) value;
        }
        else {

            NBTCompound compound = new NBTCompound();
            put0(key, compound.getHandle());
            return compound;
        }
    }

    public NBTList list(String key) {

        Object value = get(key);

        if(value instanceof NBTList) {

            return (NBTList) value;
        }
        else {

            NBTList list = new NBTList();
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

        return new NBTCompound(NBTReflect.getHandle().cloneTag(handle));
    }

    @Override
    public boolean equals(Object obj) {

        return obj instanceof NBTCompound && handle.equals(((NBTCompound) obj).handle);
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

        return new NBTCompound.NBTValues(handleMap.values());
    }

    @Override
    public NBTCompound.NBTEntrySet entrySet() {

        return new NBTCompound.NBTEntrySet(handleMap.entrySet());
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

                    if(obj instanceof NBTCompound && value instanceof Map) {

                        ((NBTCompound) obj).merge((Map) value);
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

        NBTCompound.NBTEntrySet.NBTIterator iterator = entrySet().iterator();

        if(!iterator.hasNext()) {

            return "{}";
        }
        else {

            StringBuilder stringBuilder = new StringBuilder().append('{');

            while(true) {

                NBTCompound.NBTEntrySet.NBTIterator.NBTEntry nbtEntry = iterator.next();
                Object obj = nbtEntry.getValue();

                stringBuilder.append(nbtEntry.getKey()).append('=');

                if(obj instanceof byte[]) {

                    stringBuilder.append("int[").append(((byte[]) obj).length).append(']');
                }
                else if(obj instanceof int[]) {

                    stringBuilder.append("byte[").append(((int[]) obj).length).append(']');
                }
                else {

                    stringBuilder.append(obj);
                }
                if(!iterator.hasNext()) {

                    stringBuilder.append('}').toString();
                }
                stringBuilder.append(", ");
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

            return new NBTCompound.NBTEntrySet.NBTIterator(entries.iterator());
        }

        @Override
        public int size() {

            return entries.size();
        }

        public class NBTIterator implements Iterator<Entry<String, Object>> {

            private Iterator<Entry<String, Object>> iterator;

            private NBTIterator(Iterator<Entry<String, Object>> iterator) {

                this.iterator = iterator();
            }

            @Override
            public boolean hasNext() {

                return iterator.hasNext();
            }

            @Override
            public NBTCompound.NBTEntrySet.NBTIterator.NBTEntry next() {

                return new NBTCompound.NBTEntrySet.NBTIterator.NBTEntry(iterator.next());
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

            return new NBTCompound.NBTValues.NBTValuesIterator(handle.iterator());
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
