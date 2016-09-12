package com.minecraft.moonlake.value;

import com.minecraft.moonlake.binding.ExpressionHelper;

/**
 * Created by MoonLake on 2016/9/11.
 */
public abstract class ObservableValueBase<T> implements ObservableValue<T> {

    private ExpressionHelper<T> helper;

    /**
     * 将此观察者值添加监听器
     *
     * @param listener 值改变监听器
     */
    @Override
    public void addListener(ChangeListener<? super T> listener) {

        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    /**
     * 将此观察者值清除监听器
     *
     * @param listener 值改变监听器
     */
    @Override
    public void removeListener(ChangeListener<? super T> listener) {

        helper = ExpressionHelper.removeListener(helper, listener);
    }

    /**
     * 通知此观察者值的所有监听器触发值改变事件
     */
    protected void fireValueChangedEvent() {

        ExpressionHelper.fireValueChangedEvent(helper);
    }
}
