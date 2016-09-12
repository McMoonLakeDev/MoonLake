package com.minecraft.moonlake.api;

import com.minecraft.moonlake.value.InvalidationListener;

/**
 * Created by MoonLake on 2016/9/11.
 */
public interface Observable {

    void addListener(InvalidationListener listener);

    void removeListener(InvalidationListener listener);
}
