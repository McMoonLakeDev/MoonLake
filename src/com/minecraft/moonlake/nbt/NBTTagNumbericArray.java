package com.minecraft.moonlake.nbt;

import com.minecraft.moonlake.api.nbt.NBTReflect;

import java.util.*;

/**
 * Created by MoonLake on 2016/9/21.
 */
public abstract class NBTTagNumbericArray<T extends Number> extends NBTTagDatable implements List<T> {

    public NBTTagNumbericArray(Object object) {

        super(object);
    }

    @Override
    public abstract int size();

    @Override
    public boolean isEmpty() {

        return size() == 0;
    }

    @Override
    public abstract T get(int index);

    @Override
    public Object get() {

        return NBTReflect.getHandle().getValue(handle);
    }

    @Override
    public abstract T set(int index, Number number);

    @Override
    public abstract T remove(int index);

    @Override
    public abstract boolean add(Number number);

    @Override
    public boolean containsAll(Collection<?> collection) {

        for(final Object obj : collection) {

            if(!contains(obj)) {

                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {

        for(final Number number : collection) {

            add(number);
        }
        return !collection.isEmpty();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> collection) {

        for(final Number number : collection) {

            add(index++, number);
        }
        return !collection.isEmpty();
    }

    @Override
    public void add(int pos, Number value) {

        while (size() > pos) {

            add(Integer.valueOf(0));
        }
        if(size() == pos) {

            add(value);
        }
        else {

            add(pos, value);
        }
    }

    @Override
    public Object[] toArray() {

        Object[] objects = new Object[size()];

        for(int i = 0; i < size(); i++) {

            objects[i] = get(i);
        }
        return objects;
    }

    @Override
    public <R> R[] toArray(R[] array) {

        int limit = size() < array.length ? size() : array.length;

        for(int i = 0; i < limit; i++) {

            array[i] = (R) get(i);
        }
        return array;
    }

    @Override
    public ListIterator<T> listIterator() {

        return listIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {

        if(index < 0 || index > size()) {

            throw new IndexOutOfBoundsException("The nbt tag numberic array index out of bounds: " + index);
        }
        return new ListItr(index);
    }

    @Override
    public Iterator<T> iterator() {

        return new Itr();
    }

    private class Itr implements Iterator<T> {

        int cursor = 0;
        int lastRet = -1;

        private Itr() {

        }

        @Override
        public boolean hasNext() {

            return cursor != NBTTagNumbericArray.this.size();
        }

        @Override
        public T next() {

            try {

                T next = NBTTagNumbericArray.this.get(cursor);
                lastRet = (cursor++);

                return next;
            }
            catch (IndexOutOfBoundsException e) {

                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {

            if(this.lastRet == -1) {

                throw new IllegalStateException();
            }
            try {

                NBTTagNumbericArray.this.remove(lastRet);

                if(lastRet < cursor) {

                    cursor -= 1;
                }
                lastRet = -1;
            }
            catch (IndexOutOfBoundsException e) {

                throw new ConcurrentModificationException();
            }
        }
    }

    private class ListItr extends NBTTagNumbericArray<T>.Itr implements ListIterator<T> {

        ListItr(int index) {

            this.cursor = index;
        }

        @Override
        public boolean hasPrevious() {

            return cursor != 0;
        }

        @Override
        public T previous() {

            try {

                int i = cursor - 1;
                T previous = NBTTagNumbericArray.this.get(i);
                lastRet = (cursor = i);
                return previous;
            }
            catch (IndexOutOfBoundsException e) {

                throw new NoSuchElementException();
            }
        }

        @Override
        public int nextIndex() {

            return cursor;
        }

        @Override
        public int previousIndex() {

            return cursor - 1;
        }

        @Override
        public void set(T t) {

            if(lastRet == -1) {

                throw new IllegalStateException();
            }
            try {

                NBTTagNumbericArray.this.set(lastRet, t);
            }
            catch (IndexOutOfBoundsException e) {

                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void add(T t) {

            try {

                NBTTagNumbericArray.this.add(cursor++, t);
                lastRet = -1;
            }
            catch (IndexOutOfBoundsException e) {

                throw new ConcurrentModificationException();
            }
        }
    }
}
