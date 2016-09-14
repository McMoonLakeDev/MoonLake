package com.minecraft.moonlake.data;

import com.minecraft.moonlake.property.*;
import com.minecraft.moonlake.validate.Validate;

/**
 * Created by MoonLake on 2016/6/7.
 */
public final class Conversions {

    private Conversions() {

    }

    /**
     * 将指定对象转换到整数型对象
     *
     * @param object 对象
     * @return 整数型对象
     * @throws IllegalArgumentException 如果转换的对象为 {@code null} 则抛出异常
     */
    public static ReadOnlyIntegerProperty toInt(Object object) {

        Validate.notNull(object, "The object is null.");

        if(object instanceof Number) {

            return new SimpleIntegerProperty(((Number)object).intValue());
        }
        else {

            try {

                return new SimpleIntegerProperty(Integer.parseInt(object.toString()));
            }
            catch (Exception e) {


            }
        }
        return null;
    }

    /**
     * 将指定对象转换到单精度浮点数型对象
     *
     * @param object 对象
     * @return 单精度浮点数型对象
     * @throws IllegalArgumentException 如果转换的对象为 {@code null} 则抛出异常
     */
    public static ReadOnlyFloatProperty toFloat(Object object) {

        Validate.notNull(object, "The object is null.");

        if(object instanceof Number) {

            return new SimpleFloatProperty(((Number)object).floatValue());
        }
        else {

            try {

                return new SimpleFloatProperty(Float.parseFloat(object.toString()));
            }
            catch (Exception e) {


            }
        }
        return null;
    }

    /**
     * 将指定对象转换到双精度浮点数型对象
     *
     * @param object 对象
     * @return 双精度浮点数型对象
     * @throws IllegalArgumentException 如果转换的对象为 {@code null} 则抛出异常
     */
    public static ReadOnlyDoubleProperty toDouble(Object object) {

        Validate.notNull(object, "The object is null.");

        if(object instanceof Number) {

            return new SimpleDoubleProperty(((Number)object).doubleValue());
        }
        else {

            try {

                return new SimpleDoubleProperty(Double.parseDouble(object.toString()));
            }
            catch (Exception e) {


            }
        }
        return null;
    }

    /**
     * 将指定对象转换到长整数型对象
     *
     * @param object 对象
     * @return 长整数型对象
     * @throws IllegalArgumentException 如果转换的对象为 {@code null} 则抛出异常
     */
    public static ReadOnlyLongProperty toLong(Object object) {

        Validate.notNull(object, "The object is null.");

        if(object instanceof Number) {

            return new SimpleLongProperty(((Number)object).longValue());
        }
        else {

            try {

                return new SimpleLongProperty(Long.parseLong(object.toString()));
            }
            catch (Exception e) {


            }
        }
        return null;
    }

    /**
     * 将指定对象转换到短整数型对象
     *
     * @param object 对象
     * @return 短整数型对象
     * @throws IllegalArgumentException 如果转换的对象为 {@code null} 则抛出异常
     */
    public static ReadOnlyObjectProperty<Short> toShort(Object object) {

        if(object instanceof Number) {

            return new SimpleObjectProperty<>(((Number)object).shortValue());
        }
        else {

            try {

                return new SimpleObjectProperty<>(Short.parseShort(object.toString()));
            }
            catch (Exception e) {


            }
        }
        return null;
    }

    /**
     * 将指定对象转换到字节型对象
     *
     * @param object 对象
     * @return 字节型对象
     * @throws IllegalArgumentException 如果转换的对象为 {@code null} 则抛出异常
     */
    public static ReadOnlyObjectProperty<Byte> toByte(Object object) {

        if(object instanceof Number) {

            return new SimpleObjectProperty<>(((Number)object).byteValue());
        }
        else {

            try {

                return new SimpleObjectProperty<>(Byte.parseByte(object.toString()));
            }
            catch (Exception e) {


            }
        }
        return null;
    }
}
