package com.minecraft.moonlake.nbt;

import com.minecraft.moonlake.nbt.utils.NBTReflect;

import java.util.*;

/**
 * Created by MoonLake on 2016/9/21.
 */
public class NBTTagList extends NBTBase implements List<NBTBase> {

    public NBTTagList() {

        this(NBTReflect.getHandle().createTagList());
    }

    public NBTTagList(Object handle) {

        super(handle);
    }

    public NBTTagList(boolean ignored, Object tag) {

        super(tag);

        if(NBTReflect.getHandle().getTagType(tag) != 9) {

            throw new IllegalArgumentException("The nbt tag not is nbt tag list object.");
        }
    }

    public List<Object> getHandleList() {

        return NBTReflect.getHandle().getHandleList(handle);
    }

    public Byte getByte(int index) {

        NBTBase base = get(index);
        return base instanceof NBTTagByte ? ((NBTTagByte) base).get() : null;
    }

    public Short getShort(int index) {

        NBTBase base = get(index);
        return base instanceof NBTTagShort ? ((NBTTagShort) base).get() : null;
    }

    public Integer getInt(int index) {

        NBTBase base = get(index);
        return base instanceof NBTTagInt ? ((NBTTagInt) base).get() : null;
    }

    public Long getLong(int index) {

        NBTBase base = get(index);
        return base instanceof NBTTagLong ? ((NBTTagLong) base).get() : null;
    }

    public Float getFloat(int index) {

        NBTBase base = get(index);
        return base instanceof NBTTagFloat ? ((NBTTagFloat) base).get() : null;
    }

    public Double getDouble(int index) {

        NBTBase base = get(index);
        return base instanceof NBTTagDouble ? ((NBTTagDouble) base).get() : null;
    }

    public Number getNumber(int index) {

        NBTBase base = get(index);
        return base instanceof NBTTagNumberic ? (Number) ((NBTTagNumberic) base).get() : null;
    }

    public int[] getIntArray(int index) {

        NBTBase base = get(index);
        return base instanceof NBTTagIntArray ? ((NBTTagIntArray) base).get() : null;
    }

    public byte[] getByteArray(int index) {

        NBTBase base = get(index);
        return base instanceof NBTTagByteArray ? ((NBTTagByteArray) base).get() : null;
    }

    public NBTTagList getList(int index) {

        NBTBase base = get(index);
        return base instanceof NBTTagList ? (NBTTagList) base : null;
    }

    public NBTTagCompound getCompound(int index) {

        NBTBase base = get(index);
        return base instanceof NBTTagCompound ? (NBTTagCompound) base : null;
    }

    public NBTTagList compound(int index) {

        NBTBase base = get(index);

        if(base instanceof NBTTagList) {

            return (NBTTagList) base;
        }
        else {

            NBTTagList list = new NBTTagList();
            set(index, list);
            return list;
        }
    }

    public NBTTagList list(int index) {

        NBTBase base = get(index);

        if(base instanceof NBTTagList) {

            return (NBTTagList) base;
        }
        else {

            NBTTagList list = new NBTTagList();
            set(index, list);
            return list;
        }
    }

    public NBTTagList clone() {

        return new NBTTagList(false, cloneHandle());
    }

    @Override
    public byte getTypeId() {

        return 9;
    }

    @Override
    public int size() {

        return getHandleList().size();
    }

    @Override
    public boolean isEmpty() {

        return getHandleList().isEmpty();
    }

    @Override
    public boolean contains(Object o) {

        return getHandleList().contains(NBTBase.getByValue(o).handle);
    }

    @Override
    public Iterator<NBTBase> iterator() {

        return listIterator();
    }

    @Override
    public Object[] toArray() {

        List list = getHandleList();
        NBTBase[] bases = new NBTBase[list.size()];
        int index = 0; Object tag;

        for(Iterator iterator = list.iterator(); iterator.hasNext(); bases[index++] = NBTBase.wrap(NBTBase.cloneHandle(tag))) {

            tag = iterator.next();
        }
        return bases;
    }

    @Override
    public <T> T[] toArray(T[] a) {

        List list = getHandleList();
        int limit = list.size();

        if(a.length < limit) {

            limit = a.length;
        }
        for(int i = 0; i < limit; ++i) {

            a[i] = (T) NBTBase.wrap(NBTBase.cloneHandle(list.get(i)));
        }
        return a;
    }

    @Override
    public boolean add(NBTBase base) {

        byte listType = getSubTypeId();

        if(listType != 0 && listType != base.getTypeId()) {

            throw new IllegalArgumentException("The nbt tag type id is illegal.");
        }
        else {

            if(listType == 0) {

                setSubTypeId(base.getTypeId());
            }
            boolean r = getHandleList().add(base.getHandle());
            return true;
        }
    }

    void addB(NBTBase base) {

        byte listType = getSubTypeId();

        if(listType != 0 && listType != base.getTypeId()) {

            throw new IllegalArgumentException("The nbt tag type id is illegal.");
        }
        else {

            if(listType == 0) {

                setSubTypeId(base.getTypeId());
            }
        }
    }

    public boolean addValue(Object value) {

        return add(NBTBase.getByValue(value));
    }

    public boolean leftShift(Object value) {

        return add(NBTBase.getByValue(value));
    }

    @Override
    public boolean remove(Object o) {

        return getHandleList().remove(NBTBase.getByValue(o).getHandle());
    }

    @Override
    public boolean containsAll(Collection<?> c) {

        ArrayList<Object> tags = new ArrayList<>();
        Iterator iterator = c.iterator();

        while(iterator.hasNext()) {

            Object obj = iterator.next();
            tags.add(NBTBase.getByValue(obj).handle);
        }
        return getHandleList().containsAll(tags);
    }

    public boolean addAllB(Collection<? extends NBTBase> c) {

        List<Object> list = getHandleList();
        Iterator iterator = c.iterator();

        while(iterator.hasNext()) {

            NBTBase base = (NBTBase) iterator.next();
            list.add(NBTBase.cloneHandle(base.handle));
        }
        return !c.isEmpty();
    }

    @Override
    public boolean addAll(Collection<? extends NBTBase> c) {

        List<Object> list = getHandleList();
        NBTBase base;

        for(Iterator iterator = c.iterator(); iterator.hasNext(); list.add(NBTBase.cloneHandle(base.handle))) {

            base = (NBTBase) iterator.next();
            byte listType = getSubTypeId();

            if(listType != 0 && listType != base.getTypeId()) {

                throw new IllegalArgumentException("The nbt tag type id is illegal.");
            }
            if(listType == 0) {

                setSubTypeId(base.getTypeId());
            }
        }
        return !c.isEmpty();
    }

    @Override
    public boolean addAll(int index, Collection<? extends NBTBase> c) {

        List<Object> list = this.getHandleList();
        Iterator iterator = c.iterator();

        while(iterator.hasNext()) {

            NBTBase base = (NBTBase) iterator.next();
            list.add(index++, NBTBase.cloneHandle(base.handle));
        }
        return !c.isEmpty();
    }

    public void addValue(int index, Object value) {

        NBTBase base = NBTBase.getByValue(value);
        byte listType = getSubTypeId();

        if(listType != 0 && listType != base.getTypeId()) {

            throw new IllegalArgumentException("The nbt tag type id is illegal.");
        }
        else {

            if (listType == 0) {

                setSubTypeId(base.getTypeId());
            }
            NBTBase def = base.getDefault();
            List<Object> list = this.getHandleList();

            while (this.size() < index) {

                list.add(def);
            }

            if (this.size() == index) {

                list.add(base.getHandle());
            }
            else {

                list.add(index, base.getHandle());
            }
        }
    }

    public byte getSubTypeId() {

        return getHandleList().isEmpty() ? 0 : NBTReflect.getHandle().getNBTTagListType(handle);
    }

    private void setSubTypeId(byte type) {

        NBTReflect.getHandle().setNBTTagListType(handle, type);
    }

    public NBTBase getRandom() {

        List<Object> list = getHandleList();

        if(list.isEmpty()) {

            return null;
        }
        else {

            Object r = list.get(new Random().nextInt(list.size()));
            return NBTBase.getByValue(r);
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {

        ArrayList<Object> tags = new ArrayList<>();
        Iterator iterator = c.iterator();

        while(iterator.hasNext()) {

            Object obj = iterator.next();
            tags.add(NBTBase.getByValue(obj).handle);
        }
        return getHandleList().removeAll(tags);
    }

    @Override
    public boolean retainAll(Collection<?> c) {

        ArrayList<Object> tags = new ArrayList<>();
        Iterator iterator = c.iterator();

        while(iterator.hasNext()) {

            Object obj = iterator.next();
            tags.add(NBTBase.getByValue(obj).handle);
        }
        return getHandleList().retainAll(tags);
    }

    @Override
    public void clear() {

        getHandleList().clear();
    }

    @Override
    public int hashCode() {

        return handle.hashCode();
    }

    @Override
    public NBTBase get(int index) {

        List<Object> list = getHandleList();

        if(index >= 0 && index < list.size()) {

            Object obj = getHandleList().get(index);
            return NBTBase.wrap(obj);
        }
        return null;
    }

    @Override
    public NBTBase set(int index, NBTBase element) {

        return setValue(index, element);
    }

    private NBTBase setValue(int index, Object value) {

        NBTBase base = NBTBase.getByValue(value);
        byte type = base.getTypeId();

        if(type == 0) {

            throw new IllegalArgumentException("The nbt tag value type id is illegal.");
        }
        else {

            List<Object> list = getHandleList();
            byte listType = getSubTypeId();

            if(listType != 0 && listType != base.getTypeId()) {

                throw new IllegalArgumentException("The nbt tag type id is illegal.");
            }
            else {

                if(listType == 0) {

                    setSubTypeId(type);
                }
                while (list.size() == index) {

                    list.add(base.getHandle());
                    return null;
                }
                if(list.size() == index) {

                    list.add(base.getHandle());
                    return null;
                }
                else {

                    Object obj = list.set(index, base.getHandle());
                    return NBTBase.getByValue(obj);
                }
            }
        }
    }

    @Override
    public void add(int index, NBTBase element) {

        byte listType = getSubTypeId();

        if(listType != 0 && listType != element.getTypeId()) {

            throw new IllegalArgumentException("The nbt tag type id is illegal.");
        }
        else {

            if(listType == 0) {

                setSubTypeId(element.getTypeId());
            }
            getHandleList().add(index, element.getHandle());
        }
    }

    public void add(Integer index, Object element) {

        add(index, NBTBase.getByValue(element));
    }

    @Override
    public NBTBase remove(int index) {

        List<Object> list = getHandleList();

        if(index >= 0 && index < list.size()) {

            Object obj = getHandleList().remove(index);
            return obj == null ? null : NBTBase.getByValue(obj);
        }
        return null;
    }

    @Override
    public int indexOf(Object o) {

        return getHandleList().indexOf(NBTBase.getByValue(o).handle);
    }

    @Override
    public int lastIndexOf(Object o) {

        return getHandleList().lastIndexOf(NBTBase.getByValue(o).handle);
    }

    @Override
    public ListIterator<NBTBase> listIterator() {

        return listIterator(0);
    }

    @Override
    public ListIterator<NBTBase> listIterator(int index) {

        return new ListIteratorWrapped(getHandleList().listIterator(index));
    }

    @Override
    public List<NBTBase> subList(int fromIndex, int toIndex) {

        NBTTagList sub = new NBTTagList();
        Iterator iterator = getHandleList().subList(fromIndex, toIndex).iterator();

        while(iterator.hasNext()) {

            Object tag = iterator.next();
            sub.add(NBTBase.wrap(NBTBase.cloneHandle(tag)));
        }
        return sub;
    }

    @Override
    public String toString() {

        Iterator iterator = iterator();

        if(!iterator.hasNext()) {

            return "[]";
        }
        else {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('[');

            while(true) {

                NBTBase base = (NBTBase) iterator.next();

                stringBuilder.append(base instanceof NBTTagString ? "\"" + base + "\"" : base);

                if(!iterator.hasNext()) {

                    return stringBuilder.append(']').toString();
                }
                stringBuilder.append(",");
            }
        }
    }

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof NBTBase) {

            obj = ((NBTBase) obj).getHandle();
        }
        return handle.equals(obj);
    }

    private class ListIteratorWrapped implements ListIterator<NBTBase> {

        public ListIterator<Object> handle;

        public ListIteratorWrapped(ListIterator<Object> handle) {

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
        public boolean hasPrevious() {

            return handle.hasPrevious();
        }

        @Override
        public NBTBase previous() {

            return NBTBase.wrap(handle.previous());
        }

        @Override
        public int nextIndex() {

            return handle.nextIndex();
        }

        @Override
        public int previousIndex() {

            return handle.previousIndex();
        }

        @Override
        public void remove() {

            handle.remove();
        }

        @Override
        public void set(NBTBase nbtBase) {

            handle.set(NBTBase.cloneHandle(nbtBase.handle));
        }

        @Override
        public void add(NBTBase nbtBase) {

            handle.add(NBTBase.cloneHandle(nbtBase.handle));
        }
    }
}
