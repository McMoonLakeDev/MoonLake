package com.minecraft.moonlake.data;

import com.minecraft.moonlake.api.nbt.NBTCompound;
import com.minecraft.moonlake.api.nbt.NBTList;
import com.minecraft.moonlake.property.*;

/**
 * Created by MoonLake on 2016/6/14.
 */
public interface NBTTagData extends ConversionData {

    /**
     * 将数据转换到字符串
     *
     * @return 字符串
     */
    ReadOnlyStringProperty asString();

    /**
     * 将数据转换到整数
     *
     * @return 整数
     */
    ReadOnlyIntegerProperty asInt();

    /**
     * 将数据转换到双精度浮点数
     *
     * @return 双精度浮点数
     */
    ReadOnlyDoubleProperty asDouble();

    /**
     * 将数据转换到单精度浮点数
     *
     * @return 单精度浮点数
     */
    ReadOnlyFloatProperty asFloat();

    /**
     * 将数据转换到长整数
     *
     * @return 长整数
     */
    ReadOnlyLongProperty asLong();

    /**
     * 将数据转换到短整数
     *
     * @return 短整数
     */
    ReadOnlyObjectProperty<Short> asShort();

    /**
     * 将数据转换到字符
     *
     * @return 字符
     */
    ReadOnlyObjectProperty<Character> asChar();

    /**
     * 将数据转换到布尔值
     *
     * @return 布尔值
     */
    ReadOnlyBooleanProperty asBoolean();

    /**
     * 将数据转换到字节
     *
     * @return 字节
     */
    ReadOnlyObjectProperty<Byte> asByte();

    /**
     * 将数据转换到复合对象
     *
     * @return 复合对象
     */
    NBTCompound asCompound();

    /**
     * 将数据转换到列表对象
     *
     * @return 列表对象
     */
    NBTList asList();
}
