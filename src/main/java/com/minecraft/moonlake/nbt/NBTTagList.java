/*
 * Copyright (C) 2017 The MoonLake Authors
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

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <h1>NBTTagList</h1>
 * NBT 列表类型数据
 *
 * @param <E> 元素类型
 * @version 1.0
 * @author Month_Light
 * @see NBTTagDatable
 * @see Iterable
 */
public class NBTTagList<E extends NBTBase> extends NBTTagDatable<List<E>> implements Iterable<E> {

    /**
     * 表示此列表内唯一元素的类型 Id 值
     * 由于 NBT 限制, 列表标签只能存储同等类型
     */
    private int elementTypeId;

    /**
     * NBT 列表类型数据构造函数
     */
    public NBTTagList() {
        this(new ArrayList<>());
    }

    /**
     * NBT 列表类型数据构造函数
     *
     * @param value 值
     */
    public NBTTagList(List<E> value) {
        this("", value);
    }

    /**
     * NBT 列表类型数据构造函数
     *
     * @param name 特殊名
     */
    public NBTTagList(String name) {
        this(name, new ArrayList<>());
    }

    /**
     * NBT 列表类型数据构造函数
     *
     * @param name 特殊名
     * @param value 值
     */
    public NBTTagList(String name, List<E> value) {
        super(name, value);
        for(E element : value) // 遍历列表值进行检测
            checkElementType(element);
    }

    @Override
    public NBTType getType() {
        return NBTType.LIST;
    }

    /**
     * 获取此 NBT 列表标签数据的值
     *
     * @return 列表值
     */
    @Override
    public List<E> getValue() {
        // 创建一个新的数组列表并拷贝值, 而不是返回当前 value 引用
        return new ArrayList<>(value);
    }

    /**
     * 检测指定元素和唯一元素类型 Id 是否匹配, 否则抛出异常
     *
     * @param element 待检测的元素
     */
    protected void checkElementType(E element) {
        if(elementTypeId == 0) // 如果唯一元素类型 Id 等于 0 说明当前列表没有存在任何元素, 则进行赋值
            this.elementTypeId = element.getTypeId();
        else if(element.getTypeId() != elementTypeId) // 否则待检测的元素类型 Id 不等于唯一元素类型 Id 的话抛出异常
            throw new IllegalArgumentException("列表元素值不符合的类型 Id, 应为: " + elementTypeId + ", 对应类型: " + NBTType.fromTypeId(elementTypeId) + ".");
    }

    /**
     * 获取此 NBT 列表标签的唯一元素类型 Id
     *
     * @return 唯一元素类型 Id
     */
    public int getElementTypeId() {
        return elementTypeId;
    }

    /**
     * 将此 NBT 列表标签添加新的 NBT 元素标签
     *
     * @param value 值
     * @return 是否成功
     */
    public boolean add(E value) {
        checkElementType(value);
        return super.value.add(value);
    }

    /**
     * 将此 NBT 列表标签删除指定 NBT 元素标签
     *
     * @param value 值
     * @return 是否成功
     */
    public boolean remove(E value) {
        return super.value.remove(value);
    }

    /**
     * 获取此 NBT 列表标签指定索引的元素标签
     *
     * @param index 索引
     * @return 索引标签值
     * @throws IndexOutOfBoundsException 如果索引越界则抛出异常
     */
    public E get(int index) {
        return super.value.get(index);
    }

    /**
     * 获取此 NBT 列表标签元素标签的数量大小
     *
     * @return 数量大小
     */
    public int size() {
        return super.value.size();
    }

    /**
     * 获取此 NBT 列表标签元素标签是否为空
     *
     * @return 是否为空
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 移除此 NBT 列表标签的所有元素标签
     */
    public void clear() {
        super.value.clear();
        this.elementTypeId = 0; // 重置元素类型 id
    }

    /**
     * 设置此 NBT 列表标签的数据值
     *
     * @param value 新值
     */
    @Override
    public void setValue(List<E> value) {
        for(E element : value)
            checkElementType(element);
        super.value = new ArrayList<>(value);
    }

    @Override
    @SuppressWarnings("all")
    public void read(DataInput input) throws IOException {
        int typeId = input.readUnsignedByte();
        super.value = new ArrayList<>();
        this.elementTypeId = typeId;
        int length = input.readInt();
        for(int i = 0; i < length; i++) {
            NBTBase nbt = NBTType.createNBTTag(typeId);
            nbt.read(input);
            add((E) nbt);
        }
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeByte(isEmpty() ? 0 : elementTypeId);
        output.writeInt(size());
        for(E element : getValue())
            element.write(output);
    }

    @Override
    @SuppressWarnings("unchecked")
    public NBTTagList<E> clone() {
        List<E> value = new ArrayList<>();
        for(E element : getValue())
            value.add((E) element.clone()); // 创建 NBTTag 对象的克隆
        return new NBTTagList<>(getName(), value);
    }

    @Override
    public Iterator<E> iterator() {
        return value.iterator();
    }

    @Override
    public boolean equals(Object obj) {
        if(super.equals(obj)) {
            NBTTagList other = (NBTTagList) obj;
            if(elementTypeId == other.elementTypeId)
                return value.equals(other.value);
        }
        return false;
    }

    @Override
    public String toString() {
        return toString(false);
    }

    /**
     * 将此 NBT 列表标签转换为字符串
     *
     * @param json 是否 Json 格式
     * @return 字符串
     */
    public String toString(boolean json) {
        StringBuilder builder = new StringBuilder("[");
        for(int i = 0; i < size(); i++) {
            if(i != 0)
                builder.append(",");
            if(!json)
                builder.append(i).append(":");

            NBTBase nbt = get(i);
            if(nbt instanceof NBTTagList)
                builder.append(((NBTTagList) nbt).toString(json));
            else if(nbt instanceof NBTTagCompound)
                builder.append(((NBTTagCompound) nbt).toString(json));
            else
                builder.append(nbt);
        }
        return builder.append("]").toString();
    }
}
