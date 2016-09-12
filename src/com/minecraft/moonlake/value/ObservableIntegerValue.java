package com.minecraft.moonlake.value;

/**
 * Created by MoonLake on 2016/9/11.
 */
public interface ObservableIntegerValue extends ObservableNumberValue {

    /**
     * 获取此观察者整数型值的 int 值
     *
     * @return int 值
     */
    int get();
}
