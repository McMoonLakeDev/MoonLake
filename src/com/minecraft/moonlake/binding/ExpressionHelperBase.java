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
