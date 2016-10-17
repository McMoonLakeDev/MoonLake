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
