package com.minecraft.moonlake.value;

import com.minecraft.moonlake.binding.Observable;

/**
 * Created by MoonLake on 2016/9/11.
 */
public interface ObservableValue<T> extends Observable {

    /**
     * 获取此观察者的值
     *
     * @return 观察者值
     */
    T getValue();

    /**
     * 将此观察者值添加监听器
     *
     * @param listener 值改变监听器
     */
    void addListener(ChangeListener<? super T> listener);

    /**
     * 将此观察者值清除监听器
     *
     * @param listener 值改变监听器
     */
    void removeListener(ChangeListener<? super T> listener);
}
