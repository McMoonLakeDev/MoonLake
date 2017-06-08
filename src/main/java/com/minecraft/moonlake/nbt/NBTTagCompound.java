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
 
 
package com.minecraft.moonlake.nbt;

import com.minecraft.moonlake.api.nbt.NBTReflect;

import java.util.*;

/**
 * Created by MoonLake on 2016/9/21.
 *
 * @deprecated 已过时, 将于 v1.9-a6 删除.
 */
@Deprecated
public class NBTTagCompound extends NBTBase implements Map<String, NBTBase> {

    public NBTTagCompound() {

        super(NBTReflect.getHandle().createTagCompound());
    }

    public NBTTagCompound(Object handle) {

        super(handle);
    }

    public NBTTagCompound(boolean ignored, Object tag) {

        super(tag);

        if(NBTReflect.getHandle().getTagType(tag) != 10) {

            throw new IllegalArgumentException("The nbt tag not is nbt tag compound object.");
        }
    }

    public Map<String, Object> getHandleMap() {

        return NBTReflect.getHandle().getHandleMap(handle);
    }

    public NBTBase get(String key) {

        Object obj = getHandleMap().get(key);
        return NBTBase.wrap(obj);
    }

    public Byte getByte(String key) {

        NBTBase base = get(key);
        return base instanceof NBTTagByte ? ((NBTTagByte) base).get() : null;
    }

    public Short getShort(String key) {

        NBTBase base = get(key);
        return base instanceof NBTTagShort ? ((NBTTagShort) base).get() : null;
    }

    public Integer getInt(String key) {

        NBTBase base = get(key);
        return base instanceof NBTTagInt ? ((NBTTagInt) base).get() : null;
    }

    public Long getLong(String key) {

        NBTBase base = get(key);
        return base instanceof NBTTagLong ? ((NBTTagLong) base).get() : null;
    }

    public Float getFloat(String key) {

        NBTBase base = get(key);
        return base instanceof NBTTagFloat ? ((NBTTagFloat) base).get() : null;
    }

    public Double getDouble(String key) {

        NBTBase base = get(key);
        return base instanceof NBTTagDouble ? ((NBTTagDouble) base).get() : null;
    }

    public Number getNumber(String key) {

        NBTBase base = get(key);
        return base instanceof NBTTagNumberic ? (Number) ((NBTTagNumberic) base).get() : null;
    }

    public int[] getIntArray(String key) {

        NBTBase base = get(key);
        return base instanceof NBTTagIntArray ? ((NBTTagIntArray) base).get() : null;
    }

    public byte[] getByteArray(String key) {

        NBTBase base = get(key);
        return base instanceof NBTTagByteArray ? ((NBTTagByteArray) base).get() : null;
    }

    public NBTTagList getList(String key) {

        NBTBase base = get(key);
        return base instanceof NBTTagList ? (NBTTagList) base : null;
    }

    public NBTTagCompound getCompound(String key) {

        NBTBase base = get(key);
        return base instanceof NBTTagCompound ? (NBTTagCompound) base : null;
    }

    public boolean getBoolean(String key) {

        Byte byte0 = getByte(key);
        return byte0 != null && byte0 != 0;
    }

    public boolean has(String key) {

        return getHandleMap().containsKey(key);
    }

    @SuppressWarnings("deprecation")
    public NBTTagCompound nextCompound(String key) {

        NBTBase base = get(key);

        if(base instanceof NBTTagCompound) {

            return (NBTTagCompound) base;
        }
        else {

            NBTTagCompound compound = new NBTTagCompound();
            Object tag = compound.getHandle();
            NBTReflect.getHandle().setTagName(tag, key);
            getHandleMap().put(key, tag);
            return compound;
        }
    }

    @SuppressWarnings("deprecation")
    public NBTTagList nextList(String key) {

        NBTBase base = get(key);

        if(base instanceof NBTTagList) {

            return (NBTTagList) base;
        }
        else {

            NBTTagList list = new NBTTagList();
            Object tag = list.getHandle();
            NBTReflect.getHandle().setTagName(tag, key);
            getHandleMap().put(key, tag);
            return list;
        }
    }

    public boolean remove(String key) {

        return getHandleMap().remove(key) != null;
    }

    public void rename(String oldKey, String newKey) {

        Map<String, Object> map = getHandleMap();
        map.put(newKey, map.remove(oldKey));
    }

    @Override
    public byte getTypeId() {

        return 10;
    }

    @Override
    public int size() {

        return getHandleMap().size();
    }

    @Override
    public boolean isEmpty() {

        return getHandleMap().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {

        return getHandleMap().containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {

        return getHandleMap().containsValue(value);
    }

    @Override
    public NBTBase get(Object key) {

        return NBTBase.wrap(getHandleMap().get(key));
    }

    @Override
    @SuppressWarnings("deprecation")
    public NBTBase put(String key, NBTBase value) {

        NBTBase base = NBTBase.wrap(getHandleMap().get(key));
        Object tag = value.clone().handle;
        NBTReflect.getHandle().setTagName(tag, key);
        getHandleMap().put(key, tag);
        return base;
    }

    public NBTBase put(String key, Object value) {

        return put(key, NBTBase.getByValue(value));
    }

    @SuppressWarnings("deprecation")
    public void putToHandle(String key, NBTBase value) {

        Object tag = value.clone().handle;
        NBTReflect.getHandle().setTagName(tag, key);
        getHandleMap().put(key, tag);
    }

    @Override
    public NBTBase remove(Object key) {

        NBTBase base = NBTBase.wrap(getHandleMap().get(key));
        getHandleMap().remove(key);
        return base;
    }

    @Override
    public void putAll(Map<? extends String, ? extends NBTBase> m) {

        Iterator iterator = m.entrySet().iterator();

        while(iterator.hasNext()) {

            Entry entry = (Entry) iterator.next();
            put((String) entry.getKey(), (NBTBase) entry.getValue());
        }
    }

    @Override
    public void clear() {

        getHandleMap().clear();
    }

    @Override
    public Set<String> keySet() {

        return new NBTTagCompound.MapKeySetWrapped(getHandleMap().keySet());
    }

    @Override
    public Collection<NBTBase> values() {

        return new NBTTagCompound.MapValuesWrapped(getHandleMap().values());
    }

    @Override
    public Set<Entry<String, NBTBase>> entrySet() {

        return new NBTTagCompound.EntrySetWrapped(getHandleMap().entrySet());
    }

    @Override
    public int hashCode() {

        return handle.hashCode();
    }

    @Override
    public String toString() {

        Iterator iterator = this.entrySet().iterator();

        if(!iterator.hasNext()) {

            return "{}";
        }
        else {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('{');

            while(true) {

                Entry e = (Entry)iterator.next();
                String key = (String)e.getKey();
                NBTBase value = (NBTBase)e.getValue();
                stringBuilder.append(key);
                stringBuilder.append('=');

                if(value == this) {

                    stringBuilder.append("(this Compound)");
                }
                else if(value instanceof NBTTagString) {

                    String stringValue = ((NBTTagString)value).get();
                    stringValue = stringValue.replace("\\", "\\\\");
                    stringValue = stringValue.replace("\n", "\\n");
                    stringValue = stringValue.replace("\b", "\\b");
                    stringValue = stringValue.replace("\r", "\\r");
                    stringValue = stringValue.replace("\t", "\\t");
                    stringValue = stringValue.replace("\f", "\\f");
                    stringValue = stringValue.replace("\"", "\\\"");
                    stringValue = stringValue.replace("&", "\\&");
                    stringValue = stringValue.replace(String.valueOf('§'), "&");
                    stringBuilder.append("\"").append(stringValue).append("\"");
                }
                else {

                    stringBuilder.append(value);
                }
                if(!iterator.hasNext()) {

                    return stringBuilder.append('}').toString();
                }
                stringBuilder.append(",");
            }
        }
    }

    private static <T> T[] finishToArray(T[] result, Iterator<?> iterator) {

        int index = 0;

        for(int i = result.length; iterator.hasNext(); result[index++] = (T) iterator.next()) {

            int cap = result.length;

            if(index == cap) {

                int newCap = (cap / 2 + 1) * 3;

                if(newCap <= cap) {

                    if(cap == 2147483647) {

                        throw new OutOfMemoryError("Required array size too large.");
                    }
                    newCap = 2147483647;
                }
                result = Arrays.copyOf(result, newCap);
            }
        }
        return index == result.length ? result : Arrays.copyOf(result, index);
    }

    private class EntrySetWrapped extends AbstractSet<Entry<String, NBTBase>> {

        private final Set<Entry<String, Object>> handle;

        public EntrySetWrapped(Set<Entry<String, Object>> handle) {

            this.handle = handle;
        }

        @Override
        public Iterator<Entry<String, NBTBase>> iterator() {

            return new NBTTagCompound.EntrySetWrapped.IteratorWrapped(handle.iterator());
        }

        @Override
        public int size() {

            return handle.size();
        }

        @Override
        public Object[] toArray() {

            Entry[] entries = new Entry[size()];
            Iterator iterator = iterator();

            for(int i = 0; i < entries.length; ++i) {

                if(!iterator.hasNext()) {

                    return Arrays.copyOf(entries, i);
                }
                entries[i] = (Entry) iterator.next();
            }
            return iterator.hasNext() ? NBTTagCompound.finishToArray(entries, iterator) : entries;
        }

        @Override
        public <T> T[] toArray(T[] a) {

            Entry[] entries = new Entry[size()];
            Iterator iterator = iterator();

            for(int i = 0; i < entries.length; ++i) {

                if(!iterator.hasNext()) {

                    return (T[]) Arrays.copyOf(entries, i);
                }
                entries[i] = (Entry) iterator.next();
            }
            return iterator.hasNext() ? NBTTagCompound.finishToArray((T[]) entries, iterator) : (T[]) entries;
        }

        @Override
        public boolean remove(Object o) {

            return handle.remove(NBTBase.getByValue(o).handle);
        }

        @Override
        public boolean containsAll(Collection<?> c) {

            ArrayList<Object> bases = new ArrayList<>();
            Iterator iterator = c.iterator();

            while (iterator.hasNext()) {

                Object obj = iterator.next();
                bases.add(NBTBase.getByValue(obj).handle);
            }
            return handle.containsAll(bases);
        }

        @Override
        @Deprecated
        public boolean addAll(Collection<? extends Entry<String, NBTBase>> c) {

            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean removeAll(Collection<?> c) {

            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean retainAll(Collection<?> c) {

            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {

            handle.clear();
        }

        private class IteratorWrapped implements Iterator<Entry<String, NBTBase>> {

            Iterator<Entry<String, Object>> handle;

            public IteratorWrapped(Iterator<Entry<String, Object>> handle) {

                this.handle = handle;
            }

            public boolean hasNext() {
                return this.handle.hasNext();
            }

            public Entry<String, NBTBase> next() {
                return new NBTTagCompound.EntrySetWrapped.IteratorWrapped.EntryWrapped(handle.next());
            }

            public void remove() {

                this.handle.remove();
            }

            class EntryWrapped implements Entry<String, NBTBase> {

                Entry<String, Object> handle;

                public EntryWrapped(Entry<String, Object> handle) {

                    this.handle = handle;
                }

                public String getKey() {

                    return handle.getKey();
                }

                public NBTBase getValue() {

                    return NBTBase.wrap(handle.getValue());
                }

                public NBTBase setValue(NBTBase value) {

                    NBTBase result = NBTBase.wrap(handle.getValue());
                    handle.setValue(value.clone().handle);
                    return result;
                }
            }
        }
    }

    private class MapValuesWrapped implements Collection<NBTBase> {

        private Collection<Object> handle;

        public MapValuesWrapped(Collection<Object> handle) {

            this.handle = handle;
        }

        public int size() {
            return this.handle.size();
        }

        public boolean isEmpty() {

            return handle.isEmpty();
        }

        public boolean contains(Object o) {

            NBTBase base = NBTBase.getByValue(o);
            return handle.contains(base.handle);
        }

        public Iterator<NBTBase> iterator() {

            return new NBTTagCompound.MapValuesWrapped.IteritorWrapped(handle.iterator());
        }

        public NBTBase[] toArray() {

            NBTBase[] base = new NBTBase[handle.size()];
            int index = 0; Object obj;

            for(Iterator var3 = handle.iterator(); var3.hasNext(); base[index++] = NBTBase.wrap(obj)) {
                obj = var3.next();
            }
            return base;
        }

        public <T> T[] toArray(T[] a) {

            Object[] array = handle.toArray(a);
            int limit = array.length;

            if(a.length < limit) {

                limit = a.length;
            }
            for(int i = 0; i < limit; ++i) {

                array[i] = NBTBase.wrap(array[i]);
            }
            return a;
        }

        public boolean add(NBTBase nbtBase) {

            return handle.add(nbtBase.handle);
        }

        public boolean remove(Object o) {

            NBTBase base = NBTBase.getByValue(o);
            return handle.remove(base.handle);
        }

        public boolean containsAll(Collection<?> c) {

            Iterator iterator = c.iterator();
            Object obj;

            do {

                if(!iterator.hasNext()) {

                    return true;
                }
                obj = iterator.next();
            }
            while(this.contains(obj));

            return false;
        }

        public boolean addAll(Collection<? extends NBTBase> c) {

            boolean result = false;
            Iterator iterator = c.iterator();

            while(iterator.hasNext()) {

                NBTBase base = (NBTBase)iterator.next();

                if(this.handle.add(base.handle)) {

                    result = true;
                }
            }
            return result;
        }

        public boolean removeAll(Collection<?> c) {

            boolean result = false;
            Iterator iterator = c.iterator();

            while(iterator.hasNext()) {

                Object o = iterator.next();
                NBTBase base = NBTBase.getByValue(o);

                if(this.handle.remove(base.handle)) {

                    result = true;
                }
            }
            return result;
        }

        public boolean retainAll(Collection<?> c) {

            ArrayList<Object> bases = new ArrayList<>();
            Iterator iterator = c.iterator();

            while(iterator.hasNext()) {

                Object obj = iterator.next();
                NBTBase base = NBTBase.getByValue(obj);
                bases.add(base.handle);
            }
            return handle.retainAll(bases);
        }

        public void clear() {

            handle.clear();
        }

        private class IteritorWrapped implements Iterator<NBTBase> {

            Iterator<Object> handle;

            public IteritorWrapped(Iterator<Object> handle) {

                this.handle = handle;
            }

            @Override
            public boolean hasNext() {

                return handle.hasNext();
            }

            @Override
            public NBTBase next() {

                return NBTBase.wrap(handle.next());
            }

            @Override
            public void remove() {

                handle.remove();
            }
        }
    }

    private class MapKeySetWrapped implements Set<String> {

        Set<String> handle;

        public MapKeySetWrapped(Set<String> handle) {

            this.handle = handle;
        }

        public int size() {

            return handle.size();
        }

        public boolean isEmpty() {

            return handle.isEmpty();
        }

        public boolean contains(Object o) {

            return handle.contains(o);
        }

        public Iterator<String> iterator() {

            return handle.iterator();
        }

        public Object[] toArray() {

            return handle.toArray();
        }

        public <T> T[] toArray(T[] a) {

            return handle.toArray(a);
        }

        public boolean add(String s) {

            return handle.add(s);
        }

        public boolean remove(Object o) {

            return handle.remove(o);
        }

        public boolean containsAll(Collection<?> c) {

            return handle.containsAll(c);
        }

        public boolean addAll(Collection<? extends String> c) {

            return handle.addAll(c);
        }

        public boolean retainAll(Collection<?> c) {

            return handle.retainAll(c);
        }

        public boolean removeAll(Collection<?> c) {

            return handle.removeAll(c);
        }

        public void clear() {

            handle.clear();
        }
    }
}
