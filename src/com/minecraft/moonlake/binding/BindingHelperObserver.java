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

import com.minecraft.moonlake.value.InvalidationListener;

import java.lang.ref.WeakReference;

/**
 * Created by MoonLake on 2016/9/11.
 */
public class BindingHelperObserver implements InvalidationListener {

    private final WeakReference<Binding<?>> ref;

    public BindingHelperObserver(Binding<?> binding) {

        if(binding == null) {

            throw new NullPointerException("The binding is null");
        }
        this.ref = new WeakReference<Binding<?>>(binding);
    }

    /**
     * 监听该属性的值验证时触发
     *
     * @param observable 观察者
     */
    @Override
    public void invalidated(Observable observable) {

        final Binding<?> binding = ref.get();

        if(binding == null) {

            observable.removeListener(this);
        }
        else {

            binding.invalidate();
        }
    }
}
