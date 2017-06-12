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

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>NBTType</h1>
 * NBT 类型
 *
 * @version 1.0
 * @author Month_Light
 */
public enum NBTType {

    /**
     * NBT 结束标签
     *
     * @see NBTTagEnd
     */
    END(0, NBTTagEnd.class),
    /**
     * NBT 字节类型标签
     *
     * @see NBTTagByte
     */
    BYTE(1, NBTTagByte.class),
    /**
     * NBT 短整数类型标签
     *
     * @see NBTTagShort
     */
    SHORT(2, NBTTagShort.class),
    /**
     * NBT 整数类型标签
     *
     * @see NBTTagInteger
     */
    INTEGER(3, NBTTagInteger.class),
    /**
     * NBT 长整数类型标签
     *
     * @see NBTTagLong
     */
    LONG(4, NBTTagLong.class),
    /**
     * NBT 单精度浮点类型标签
     *
     * @see NBTTagFloat
     */
    FLOAT(5, NBTTagFloat.class),
    /**
     * NBT 双精度浮点类型标签
     *
     * @see NBTTagDouble
     */
    DOUBLE(6, NBTTagDouble.class),
    /**
     * NBT 字节数组类型标签
     *
     * @see NBTTagByteArray
     */
    BYTE_ARRAY(7, NBTTagByteArray.class),
    /**
     * NBT 字符串类型标签
     *
     * @see NBTTagString
     */
    STRING(8, NBTTagString.class),
    /**
     * NBT 列表类型标签
     *
     * @see NBTTagList
     */
    LIST(9, NBTTagList.class),
    /**
     * NBT 复合类型标签
     *
     * @see NBTTagCompound
     */
    COMPOUND(10, NBTTagCompound.class),
    /**
     * NBT 整数数组类型标签
     *
     * @see NBTTagIntegerArray
     */
    INTEGER_ARRAY(11, NBTTagIntegerArray.class),
    ;

    private final int typeId;
    private final Class<? extends NBTBase> clazz;
    private final static Map<Integer, NBTType> ID_MAP;

    static {
        ID_MAP = new HashMap<>();
        for(NBTType type : values())
            ID_MAP.put(type.getTypeId(), type);
    }

    NBTType(int typeId, Class<? extends NBTBase> clazz) {
        this.typeId = typeId;
        this.clazz = clazz;
    }

    /**
     * 获取此 NBT 标签类型的 Id 值 (更详情信息: <a href="http://minecraft-zh.gamepedia.com/NBT%E6%A0%BC%E5%BC%8F#.E6.A0.87.E7.AD.BE.E7.9A.84.E5.AE.9A.E4.B9.89" target="_blank">链接<a/>)
     *
     * @return Id 值
     */
    public int getTypeId() {
        return typeId;
    }

    /**
     * 获取此 NBT 标签类型对应的 Class 类
     *
     * @return 对应的 Class 类
     */
    public Class<? extends NBTBase> getClazz() {
        return clazz;
    }

    /**
     * 从 NBT 类型 Id 获取指定 NBTType 对象
     *
     * @param typeId NBT 类型 Id
     * @return NBTType | null
     */
    public static NBTType fromTypeId(int typeId) {
        switch (typeId) {
            case 0:
                return END;
            case 1:
                return BYTE;
            case 2:
                return SHORT;
            case 3:
                return INTEGER;
            case 4:
                return LONG;
            case 5:
                return FLOAT;
            case 6:
                return DOUBLE;
            case 7:
                return BYTE_ARRAY;
            case 8:
                return STRING;
            case 9:
                return LIST;
            case 10:
                return COMPOUND;
            case 11:
                return INTEGER_ARRAY;
            default:
                return null;
        }
    }

    /**
     * 从 NBT 类型 Id 创建对应的 NBTBase 实例对象
     *
     * @param typeId NBT 类型 Id
     * @return 实例对象 | null
     */
    public static NBTBase createNBTTag(int typeId) {
        return createNBTTag(typeId, "");
    }

    /**
     * 从 NBT 类型 Id 创建对应的 NBTBase 实例对象
     *
     * @param typeId NBT 类型 Id
     * @param name 特殊名
     * @return 实例对象 | null
     */
    public static NBTBase createNBTTag(int typeId, String name) {
        switch (typeId) {
            case 0:
                return new NBTTagEnd();
            case 1:
                return new NBTTagByte(name);
            case 2:
                return new NBTTagShort(name);
            case 3:
                return new NBTTagInteger(name);
            case 4:
                return new NBTTagLong(name);
            case 5:
                return new NBTTagFloat(name);
            case 6:
                return new NBTTagDouble(name);
            case 7:
                return new NBTTagByteArray(name);
            case 8:
                return new NBTTagString(name);
            case 9:
                return new NBTTagList<>(name);
            case 10:
                return new NBTTagCompound(name);
            case 11:
                return new NBTTagIntegerArray(name);
            default:
                return null;
        }
    }
}
