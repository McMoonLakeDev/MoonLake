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
public class NBTTagByteArray extends NBTTagNumbericArray<Byte> {

    public NBTTagByteArray() {

        this(new byte[0]);
    }

    public NBTTagByteArray(byte[] object) {

        super(object);
    }


    public NBTTagByteArray(boolean ignored, Object tag) {

        super(tag);

        if(NBTReflect.getHandle().getTagType(tag) != 7) {

            throw new IllegalArgumentException("The nbt tag not is nbt tag byte[] object.");
        }
    }

    @Override
    public int hashCode() {

        return handle.hashCode();
    }

    @Override
    public byte[] get() {

        return (byte[]) super.get();
    }

    @Override
    public void set(Object value) {

        NBTReflect.getHandle().setValue(handle, value);
    }

    public void set(byte[] value) {

        set((Object) value);
    }

    @Override
    public int size() {

        return get().length;
    }

    @Override
    public boolean contains(Object o) {

        return byteArrayList(get()).contains(o);
    }

    @Override
    public Byte get(int index) {

        byte[] array = get();
        return index >= array.length ? null : array[index];
    }

    @Override
    public Byte set(int index, Number number) {

        Byte res = get(index);
        byte[] result = get();
        LinkedList<Object> list = new LinkedList<>();
        byte byte0;

        for(int i = 0; i < result.length; ++i) {

            byte0 = result[i];
            list.add(byte0);
        }
        while(list.size() <= index) {

            list.add(0);
        }
        list.set(index, number.byteValue());
        result = new byte[list.size()];
        int index0 = 0;

        for(Iterator iterator = list.iterator(); iterator.hasNext(); result[index0++] = byte0) {

            byte0 = (Byte) iterator.next();
        }
        set(result);
        return res;
    }

    @Override
    public Byte remove(int index) {

        Byte res = get(index);
        byte[] result = get();

        if(index >= 0 && index < result.length) {

            LinkedList<Object> list = new LinkedList<>();
            byte byte0;

            for(int i = 0; i < result.length; ++i) {

                byte0 = result[i];
                list.add(byte0);
            }
            while(list.size() <= index) {

                list.add(0);
            }
            list.remove(index);
            result = new byte[list.size()];
            int index0 = 0;

            for(Iterator iterator = list.iterator(); iterator.hasNext(); result[index0++] = byte0) {

                byte0 = (Byte) iterator.next();
            }
            set(result);
            return res;
        }
        else {

            return res;
        }
    }

    @Override
    public int indexOf(Object o) {

        return byteArrayList(get()).indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {

        return byteArrayList(get()).lastIndexOf(o);
    }

    @Override
    public List<Byte> subList(int fromIndex, int toIndex) {

        byte[] result = new byte[toIndex - fromIndex];
        int index = 0;

        for(int i = fromIndex; i < toIndex; ++i) {

            result[index++] = result[i];
        }
        return new NBTTagByteArray(result);
    }

    @Override
    public boolean add(Number number) {

        byte[] result = get();
        LinkedList<Object> list = new LinkedList<>();
        byte byte0;

        for(int i = 0; i < result.length; ++i) {

            byte0 = result[i];
            list.add(byte0);
        }
        list.add(number.byteValue());
        result = new byte[list.size()];
        int index = 0;

        for(Iterator iterator = list.iterator(); iterator.hasNext(); result[index++] = byte0) {

            byte0 = (Byte) iterator.next();
        }
        set(result);
        return false;
    }

    @Override
    public boolean remove(Object o) {

        ArrayList<Byte> bytes = byteArrayList(get());
        boolean result = bytes.remove(o);

        if(result) {

            set(listToByteArr(bytes));
        }
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {

        ArrayList<Byte> bytes = byteArrayList(get());
        boolean result = bytes.removeAll(c);

        if(result) {

            set(listToByteArr(bytes));
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {

        ArrayList<Byte> bytes = byteArrayList(get());
        boolean result = bytes.retainAll(c);

        if(result) {

            set(listToByteArr(bytes));
        }
        return result;
    }

    @Override
    public void clear() {

        set(new byte[0]);
    }

    @Override
    public String toString() {

        return Arrays.toString(get());
    }

    @Override
    public byte getTypeId() {

        return 7;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof NBTBase) {

            obj = ((NBTBase) obj).getHandle();
        }
        return handle.equals(obj);
    }


    public static ArrayList<Byte> byteArrayList(byte[] in) {

        ArrayList<Byte> temp = new ArrayList<>();

        for(int i = 0; i < in.length; ++i) {

            temp.add(in[i]);
        }
        return temp;
    }

    public static byte[] listToByteArr(Collection<Byte> in) {

        byte[] temp = new byte[in.size()];
        int index = 0; Byte anIn;

        for(Iterator iterator = in.iterator(); iterator.hasNext(); temp[index++] = anIn) {

            anIn = (Byte) iterator.next();
        }
        return temp;
    }
}
