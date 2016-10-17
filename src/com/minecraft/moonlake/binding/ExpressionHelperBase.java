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

/**
 * Created by MoonLake on 2016/9/11.
 */
public class ExpressionHelperBase {

    protected static int trim(int size, Object[] listeners) {

        for(int index = 0; index < size; index++) {

            final Object listener = listeners[index];

            if(listener instanceof WeakListener) {

                if(((WeakListener)listener).wasGarbageCollected()) {

                    final int numMoved = size - index - 1;

                    if(numMoved > 0) {

                        System.arraycopy(listeners, index + 1, listeners, index, numMoved);
                    }
                    listeners[--size] = null;   // gc listener
                    index--;
                }
            }
        }
        return size;
    }
}
