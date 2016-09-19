package com.minecraft.moonlake.data;

import com.minecraft.moonlake.property.*;

/**
 * Created by MoonLake on 2016/7/17.
 */
public class ConversionDataWrapped extends ConversionDataExpression {

    public ConversionDataWrapped(Object obj) {

        super(obj);
    }

    /**
     * 将数据转换到字符串
     *
     * @return 字符串
     */
    @Override
    public ReadOnlyStringProperty asString() {

        return new SimpleStringProperty(obj != null ? obj.toString() : "");
    }

    /**
     * 将数据转换到整数
     *
     * @return 整数
     */
    @Override
    public ReadOnlyIntegerProperty asInt() {

        return obj != null ? Conversions.toInt(obj) : null;
    }

    /**
     * 将数据转换到双精度浮点数
     *
     * @return 双精度浮点数
     */
    @Override
    public ReadOnlyDoubleProperty asDouble() {

        return obj != null ? Conversions.toDouble(obj) : null;
    }

    /**
     * 将数据转换到单精度浮点数
     *
     * @return 单精度浮点数
     */
    @Override
    public ReadOnlyFloatProperty asFloat() {

        return obj != null ? Conversions.toFloat(obj) : null;
    }

    /**
     * 将数据转换到长整数
     *
     * @return 长整数
     */
    @Override
    public ReadOnlyLongProperty asLong() {

        return obj != null ? Conversions.toLong(obj) : null;
    }

    /**
     * 将数据转换到短整数
     *
     * @return 短整数
     */
    @Override
    public ReadOnlyObjectProperty<Short> asShort() {

        return obj != null ? Conversions.toShort(obj) : null;
    }

    /**
     * 将数据转换到字符
     *
     * @return 字符
     */
    @Override
    public ReadOnlyObjectProperty<Character> asChar() {

        return new SimpleObjectProperty<>(obj != null && obj instanceof Character ? (char)obj : null);
    }

    /**
     * 将数据转换到布尔值
     *
     * @return 布尔值
     */
    @Override
    public ReadOnlyBooleanProperty asBoolean() {

        return new SimpleBooleanProperty(obj != null && obj instanceof Boolean ? (Boolean)obj : Boolean.FALSE);
    }

    /**
     * 将数据转换到字节
     *
     * @return 字节
     */
    @Override
    public ReadOnlyObjectProperty<Byte> asByte() {

        return obj != null ? Conversions.toByte(obj) : null;
    }
}
