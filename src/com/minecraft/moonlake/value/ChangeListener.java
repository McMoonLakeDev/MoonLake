package com.minecraft.moonlake.value;

/**
 * Created by MoonLake on 2016/9/11.
 */
public interface ChangeListener<T> {

    /**
     * 监听该属性的值改变时触发
     *
     * @param observable 观察者值
     * @param oldValue 旧值
     * @param newValue 新值
     */
    void changed(ObservableValue<? extends T> observable, T oldValue, T newValue);
}
