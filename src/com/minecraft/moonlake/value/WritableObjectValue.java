package com.minecraft.moonlake.value;

/**
 * Created by MoonLake on 2016/9/11.
 */
public interface WritableObjectValue<T> extends WritableValue<T> {

    /**
     * 获取此包装的值
     *
     * @return 包装值
     */
    T get();

    /**
     * 设置此包装的新值
     *
     * @param value 新包装值
     */
    void set(T value);

    @Override
    void setValue(T value);
}
