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
