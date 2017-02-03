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

import com.minecraft.moonlake.binding.WeakListener;

import java.lang.ref.WeakReference;

/**
 * Created by MoonLake on 2016/9/11.
 */
public final class WeakChangeListener<T> implements ChangeListener<T>, WeakListener {

    private final WeakReference<ChangeListener<T>> ref;

    public WeakChangeListener(ChangeListener<T> listener) {

        if(listener == null) {

            throw new NullPointerException("The changed listener is null.");
        }
        this.ref = new WeakReference<ChangeListener<T>>(listener);
    }

    /**
     * 获取如果连接的监听器需要回收,在这种情况下,监听器可以从观察者清除
     *
     * @return true 如果连接的监听器需要回收
     */
    @Override
    public boolean wasGarbageCollected() {

        return (ref.get() == null);
    }

    /**
     * 监听该属性的值改变时触发
     *
     * @param observable 观察者值
     * @param oldValue   旧值
     * @param newValue   新值
     */
    @Override
    public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {

        ChangeListener<T> listener = ref.get();

        if(listener != null) {

            listener.changed(observable, oldValue, newValue);
        }
        else {

            observable.removeListener(this);
        }
    }
}
