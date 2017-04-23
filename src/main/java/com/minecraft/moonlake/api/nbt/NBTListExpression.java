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

import com.minecraft.moonlake.nbt.exception.NBTException;

import java.util.*;

/**
 * <h1>NBTListExpression</h1>
 * NBT 列表接口实现类
 *
 * @version 1.0
 * @author Month_Light
 */
class NBTListExpression implements NBTList {

    private final List<Object> handleList;
    private final Object handle;

    /**
     * NBT 列表接口实现类构造函数
     */
    public NBTListExpression() {

        this(NBTReflect.getHandle().createTagList());
    }

    /**
     * NBT 列表接口实现类构造函数
     *
     * @param tag NBT Tag 对象
     */
    public NBTListExpression(Object tag) {

        assert NBTReflect.getHandle().getTagType(tag) == 9;

        this.handle = tag;
        this.handleList = NBTReflect.getHandle().getHandleList(tag);
    }

    /**
     * NBT 列表接口实现类构造函数
     *
     * @param collection Collection 对象
     */
    public NBTListExpression(Collection collection) {

        this(NBTReflect.getHandle().createTagList());

        Iterator iterator = collection.iterator();

        while(iterator.hasNext()) {

            Object obj = iterator.next();
            add(obj);
        }
    }

    /**
     * NBT 列表接口实现类构造函数
     *
     * @param array Object[] 对象
     */
    public NBTListExpression(Object[] array) {

        this(NBTReflect.getHandle().createTagList());

        for(int i = 0; i < array.length; i++) {

            add(array[i]);
        }
    }

    public List<Object> getHandleList() {

        return handleList;
    }

    public Object getHandle() {

        return handle;
    }

    public Object getHandleCopy() {

        return NBTReflect.getHandle().cloneTag(handle);
    }

    public byte getType() {

        return size() == 0 ? 0 : NBTReflect.getHandle().getNBTTagListType(handle);
    }

    public void setType(byte type) {

        NBTReflect.getHandle().setNBTTagListType(handle, type);
    }

    private Object convertToCurrentType(Object obj) {

        byte type = getType();

        if(type == 0) {

            Object tag = NBTReflect.getHandle().createTag(obj);
            type = NBTReflect.getHandle().getTagType(tag);
            setType(type);
            return tag;
        }
        else {

            return NBTReflect.getHandle().createTag(obj, type);
        }
    }

    public <T extends List<Object>> T toList(T list) {

        return toCollection(list);
    }

    public <T extends Collection<Object>> T toCollection(T collection) {

        collection.clear();

        Iterator iterator = handleList.iterator();

        while(iterator.hasNext()) {

            Object nbtTag = iterator.next();
            byte type = NBTReflect.getHandle().getTagType(nbtTag);

            if(type == 9) {

                collection.add(NBTReflect.fromNBTList(nbtTag).toList(new ArrayList<>()));
            }
            else if(type == 10) {

                collection.add(NBTReflect.fromNBTCompound(nbtTag).toMap(new HashMap<>()));
            }
            else {

                collection.add(NBTReflect.getHandle().getValue(nbtTag));
            }
        }
        return collection;
    }

    public ArrayList<Object> toArrayList() {

        return toList(new ArrayList<>());
    }

    @Override
    public NBTList clone() {

        return new NBTListExpression(NBTReflect.getHandle().cloneTag(handle));
    }

    @Override
    public boolean equals(Object obj) {

        return obj instanceof NBTListExpression && handle.equals(((NBTListExpression) obj).handle);
    }

    @Override
    public int size() {

        return handleList.size();
    }

    @Override
    public boolean isEmpty() {

        return handleList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {

        return handleList.contains(NBTReflect.getHandle().createTag(o));
    }

    @Override
    public NBTListExpression.NBTIterator iterator() {

        return new NBTIterator(handleList.listIterator());
    }

    @Override
    public Object[] toArray() {

        Object[] result = new Object[size()];
        int index = 0; Object obj;

        for(NBTListExpression.NBTIterator itr = iterator(); itr.hasNext(); result[index++] = obj) {

            obj = itr.next();
        }
        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {

        int size = size();

        if(size > a.length) {

            size = a.length;
        }
        for(int i = 0; i < size; i++) {

            a[i] = (T) get(i);
        }
        return a;
    }

    @Override
    public boolean add(Object o) {

        Object tag = convertToCurrentType(o);
        return handleList.add(tag);
    }

    @Override
    public boolean remove(Object o) {

        return handleList.remove(NBTReflect.getHandle().createTag(o));
    }

    @Override
    public boolean containsAll(Collection<?> c) {

        Iterator iterator = c.iterator();
        Object value;

        do {

            if(!iterator.hasNext()) {

                return true;
            }
            value = iterator.next();
        }
        while (handleList.contains(NBTReflect.getHandle().createTag(value)));

        return false;
    }

    @Override
    public boolean addAll(Collection<?> c) {

        boolean modified = false;
        Object tag;

        for(Iterator iterator = c.iterator(); iterator.hasNext(); modified |= handleList.add(tag)) {

            Object obj = iterator.next();
            tag = convertToCurrentType(obj);
        }
        return modified;
    }

    @Override
    public boolean addAll(int index, Collection<?> c) {

        boolean modified = false;
        Iterator iterator = c.iterator();

        while (iterator.hasNext()) {

            Object obj = iterator.next();

            if(obj != null) {

                Object tag = convertToCurrentType(obj);
                modified = true;
                handleList.add(index++, tag);
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {

        boolean modified = false;
        Object obj;

        for(Iterator iterator = c.iterator(); iterator.hasNext(); modified |= handleList.remove(NBTReflect.getHandle().createTag(obj))) {

            obj = iterator.next();
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {

        boolean modified = false;
        NBTListExpression.NBTIterator itr = iterator();

        while(itr.hasNext()) {

            if(!c.contains(itr.next())) {

                itr.remove();;
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void clear() {

        handleList.clear();
    }

    @Override
    public Object get(int index) {

        return NBTReflect.getHandle().getValue(handleList.get(index));
    }

    @Override
    public Object set(int index, Object element) {

        if(element == null) {

            return remove(index);
        }
        else {

            Object tag = convertToCurrentType(element);
            Object oldTag = handleList.set(index, tag);
            return NBTReflect.getHandle().getValue(oldTag);
        }
    }

    @Override
    public void add(int index, Object element) {

        if(element != null) {

            Object tag = convertToCurrentType(element);
            handleList.add(index, tag);
        }
    }

    @Override
    public Object remove(int index) {

        return NBTReflect.getHandle().getValue(handleList.remove(index));
    }

    @Override
    public int indexOf(Object o) {

        return handleList.indexOf(NBTReflect.getHandle().createTag(o));
    }

    @Override
    public int lastIndexOf(Object o) {

        return handleList.lastIndexOf(NBTReflect.getHandle().createTag(o));
    }

    @Override
    public NBTListExpression.NBTIterator listIterator() {

        return new NBTIterator(handleList.listIterator());
    }

    @Override
    public NBTListExpression.NBTIterator listIterator(int index) {

        return new NBTIterator(handleList.listIterator(index));
    }

    @Override
    public NBTListExpression.NBTSubList subList(int fromIndex, int toIndex) {

        return new NBTSubList(this, fromIndex, toIndex);
    }

    @Override
    public String toString() {

        NBTListExpression.NBTIterator itr = iterator();

        if(!itr.hasNext()) {

            return "[]";
        }
        else {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('[');

            while(true) {

                Object obj = itr.next();
                if(obj instanceof String) {
                    stringBuilder.append("\"");
                    stringBuilder.append(obj.toString().replace("\"", "\\\"")); // escape char: " --> \"
                    stringBuilder.append("\"");
                }
                else {
                    stringBuilder.append(obj);
                }

                if(!itr.hasNext()) {

                    return stringBuilder.append(']').toString();
                }
                stringBuilder.append(",");
            }
        }
    }

    public class NBTSubList extends NBTListExpression {

        private final NBTListExpression list;
        private final int offset;
        private int size;

        private NBTSubList(NBTListExpression list, int fromIndex, int toIndex) {

            if(fromIndex < 0) {

                throw new NBTException("The nbt list sub list from index not less than 0.");
            }
            else if(toIndex > list.size()) {

                throw new NBTException("The nbt list sub list to index not greater than size.");
            }
            else if(fromIndex > toIndex) {

                throw new NBTException("The nbt list sub list from index not less than to index.");
            }
            this.list = list;
            this.offset = fromIndex;
            this.size = toIndex - fromIndex;
        }

        @Override
        public Object set(int index, Object element) {

            if(element == null) {

                return remove(index);
            }
            else {

                rangeCheck(index);
                return list.set(index + offset, element);
            }
        }

        @Override
        public Object get(int index) {

            rangeCheck(index);
            return list.get(index + offset);
        }

        @Override
        public int size() {

            return size;
        }

        @Override
        public void add(int index, Object element) {

            if(element != null) {

                rangeCheckForAdd(index);
                list.add(index + offset, element);
                ++size;
            }
        }

        @Override
        public Object remove(int index) {

            rangeCheck(index);
            --size;
            return list.remove(index + offset);
        }

        @Override
        public boolean addAll(Collection<?> c) {

            return addAll(size, c);
        }

        @Override
        public boolean addAll(int index, Collection<?> c) {

            ArrayList<Object> objectToAdd = new ArrayList<>();
            Iterator iterator = c.iterator();

            while(iterator.hasNext()) {

                Object obj = iterator.next();

                if(obj != null) {

                    objectToAdd.add(obj);
                }
            }
            rangeCheckForAdd(index);
            int cSize = objectToAdd.size();

            if(cSize == 0) {

                return false;
            }
            else {

                list.addAll(index + offset, objectToAdd);
                size += cSize;
                return true;
            }
        }

        @Override
        public NBTListExpression.NBTIterator iterator() {

            return listIterator();
        }

        @Override
        public NBTIterator listIterator(int index) {

            rangeCheckForAdd(index);

            return new NBTListExpression.NBTIterator(list.listIterator(index + offset)) {

                @Override
                public boolean hasNext() {

                    return nextIndex() < size;
                }

                @Override
                public Object next() {

                    if(hasNext()) {

                        return super.next();
                    }
                    else {

                        throw new NoSuchElementException();
                    }
                }

                @Override
                public boolean hasPrevious() {

                    return previousIndex() >= 0;
                }

                @Override
                public Object previous() {

                    if(hasPrevious()) {

                        return super.previous();
                    }
                    else {

                        throw new NoSuchElementException();
                    }
                }

                @Override
                public int nextIndex() {

                    return super.nextIndex() - offset;
                }

                @Override
                public int previousIndex() {

                    return super.previousIndex() - offset;
                }

                @Override
                public void remove() {

                    super.remove();
                    size--;
                }

                @Override
                public void set(Object o) {

                    if(o == null) {

                        remove();
                    }
                    else {

                        super.set(o);
                    }
                }

                @Override
                public void add(Object o) {

                    if(o != null) {

                        super.add(o);
                        size++;
                    }
                }
            };
        }

        private void rangeCheck(int index) {

            if(index < 0 || index >= size) {

                throw new IndexOutOfBoundsException("The index out of bounds, index: " + index + ", size: " + size);
            }
        }

        private void rangeCheckForAdd(int index) {

            if(index < 0 || index >= size) {

                throw new IndexOutOfBoundsException("The index out of bounds, index: " + index + ", size: " + size);
            }
        }
    }

    public class NBTIterator implements ListIterator<Object> {

        protected ListIterator<Object> iterator;

        private NBTIterator(ListIterator<Object> iterator) {

            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {

            return iterator.hasNext();
        }

        @Override
        public Object next() {

            return NBTReflect.getHandle().getValue(iterator.next());
        }

        @Override
        public boolean hasPrevious() {

            return iterator.hasPrevious();
        }

        @Override
        public Object previous() {

            return NBTReflect.getHandle().getValue(iterator.previous());
        }

        @Override
        public int nextIndex() {

            return iterator.nextIndex();
        }

        @Override
        public int previousIndex() {

            return iterator.previousIndex();
        }

        @Override
        public void remove() {

            iterator.remove();
        }

        @Override
        public void set(Object o) {

            if(o == null) {

                remove();
            }
            else {

                Object tag = NBTListExpression.this.convertToCurrentType(o);
                iterator.set(tag);
            }
        }

        @Override
        public void add(Object o) {

            Object tag = NBTListExpression.this.convertToCurrentType(o);
            iterator.add(tag);
        }
    }
}
