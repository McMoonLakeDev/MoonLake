package com.minecraft.moonlake.binding;

import com.minecraft.moonlake.collections.ObservableList;
import com.minecraft.moonlake.value.ObservableValue;

/**
 * Created by MoonLake on 2016/9/11.
 */
public interface Binding<T> extends ObservableValue<T> {

    /**
     * 检测绑定是否是有效的
     *
     * @return true 则有效 else 无效
     */
    boolean isValid();

    /**
     * 标记作为无效的绑定,这迫使该值计算下一次请求
     */
    void invalidate();

    /**
     * 获取此绑定的依赖
     *
     * @return 依赖
     */
    ObservableList<?> getDependencies();

    /**
     * 处理掉此绑定
     */
    void dispose();
}
