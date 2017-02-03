/*
 * Copyright (C) 2016 The MoonLake Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
 
 
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
