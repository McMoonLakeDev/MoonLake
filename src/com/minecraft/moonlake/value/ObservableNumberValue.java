package com.minecraft.moonlake.value;

/**
 * Created by MoonLake on 2016/9/11.
 */
public interface ObservableNumberValue extends ObservableValue<Number> {

    /**
     * 获取此观察者数字值的 int 类型值
     *
     * @return int 类型值
     */
    int intValue();

    /**
     * 获取此观察者数字值的 long 类型值
     *
     * @return long 类型值
     */
    long longValue();

    /**
     * 获取此观察者数字值的 float 类型值
     *
     * @return float 类型值
     */
    float floatValue();

    /**
     * 获取此观察者数字值的 double 类型值
     *
     * @return double 类型值
     */
    double doubleValue();
}
