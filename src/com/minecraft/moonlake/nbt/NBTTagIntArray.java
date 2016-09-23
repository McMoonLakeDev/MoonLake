package com.minecraft.moonlake.nbt;

import com.minecraft.moonlake.nbt.utils.NBTReflect;

import java.util.*;


/**
 * Created by MoonLake on 2016/9/21.
 */
public class NBTTagIntArray extends NBTTagNumbericArray<Integer> {

    public NBTTagIntArray() {

        this(new int[0]);
    }

    public NBTTagIntArray(int[] object) {

        super(object);
    }


    public NBTTagIntArray(boolean ignored, Object tag) {

        super(tag);

        if(NBTReflect.getHandle().getTagType(tag) != 11) {

            throw new IllegalArgumentException("The nbt tag not is nbt tag int[] object.");
        }
    }

    public void set(int[] value) {

        super.set(value);
    }

    @Override
    public int[] get() {

        return (int[]) super.get();
    }

    @Override
    public int size() {

        return get().length;
    }

    @Override
    public boolean contains(Object o) {

        return intArrToList(get()).contains(o);
    }

    @Override
    public Integer get(int index) {

        int[] array = get();
        return index >= array.length ? null : array[index];
    }

    @Override
    public Integer set(int index, Number number) {

        Integer res = get(index);
        int[] result = get();
        LinkedList<Object> list = new LinkedList<>();
        int int0;

        for(int i = 0; i < result.length; ++i) {

            int0 = result[i];
            list.add(int0);
        }
        while(list.size() <= index) {

            list.add(0);
        }
        list.set(index, number.byteValue());
        result = new int[list.size()];
        int index0 = 0;

        for(Iterator iterator = list.iterator(); iterator.hasNext(); result[index0++] = int0) {

            int0 = (Integer) iterator.next();
        }
        set(result);
        return res;
    }

    @Override
    public Integer remove(int index) {

        Integer res = get(index);
        int[] result = get();

        if(index >= 0 && index < result.length) {

            LinkedList<Object> list = new LinkedList<>();
            int int0;

            for(int i = 0; i < result.length; ++i) {

                int0 = result[i];
                list.add(int0);
            }
            while(list.size() <= index) {

                list.add(0);
            }
            list.remove(index);
            result = new int[list.size()];
            int index0 = 0;

            for(Iterator iterator = list.iterator(); iterator.hasNext(); result[index0++] = int0) {

                int0 = (Integer) iterator.next();
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

        return intArrToList(get()).indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {

        return intArrToList(get()).lastIndexOf(o);
    }

    @Override
    public List<Integer> subList(int fromIndex, int toIndex) {

        int[] result = new int[toIndex - fromIndex];
        int index = 0;

        for(int i = fromIndex; i < toIndex; ++i) {

            result[index++] = result[i];
        }
        return new NBTTagIntArray(result);
    }

    @Override
    public boolean add(Number number) {

        int[] result = get();
        LinkedList<Object> list = new LinkedList<>();
        int int0;

        for(int i = 0; i < result.length; ++i) {

            int0 = result[i];
            list.add(int0);
        }
        list.add(number.byteValue());
        result = new int[list.size()];
        int index = 0;

        for(Iterator iterator = list.iterator(); iterator.hasNext(); result[index++] = int0) {

            int0 = (Integer) iterator.next();
        }
        set(result);
        return false;
    }

    @Override
    public boolean remove(Object o) {

        ArrayList<Integer> integers = intArrToList(get());
        boolean result = integers.remove(o);

        if(result) {

            set(listToIntArr(integers));
        }
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {

        ArrayList<Integer> integers = intArrToList(get());
        boolean result = integers.removeAll(c);

        if(result) {

            set(listToIntArr(integers));
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {

        ArrayList<Integer> integers = intArrToList(get());
        boolean result = integers.retainAll(c);

        if(result) {

            set(listToIntArr(integers));
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

        return 11;
    }

    @Override
    public int hashCode() {

        return handle.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof NBTBase) {

            obj = ((NBTBase) obj).getHandle();
        }
        return handle.equals(obj);
    }

    public static ArrayList<Integer> intArrToList(int[] in) {

        return null;
    }

    public static int[] listToIntArr(Collection<Integer> in) {

        return null;
    }
}
