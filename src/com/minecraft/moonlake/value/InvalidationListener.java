package com.minecraft.moonlake.value;

import com.minecraft.moonlake.binding.Observable;

/**
 * Created by MoonLake on 2016/9/11.
 */
public interface InvalidationListener {

    /**
     * 监听该属性的值验证时触发
     *
     * @param observable 观察者
     */
    void invalidated(Observable observable);
}
