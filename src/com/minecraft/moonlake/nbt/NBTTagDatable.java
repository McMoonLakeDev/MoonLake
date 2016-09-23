package com.minecraft.moonlake.nbt;

import com.minecraft.moonlake.nbt.utils.NBTReflect;

/**
 * Created by MoonLake on 2016/9/21.
 */
public abstract class NBTTagDatable<T> extends NBTBase {

    public NBTTagDatable(Object handle) {

        super(handle);
    }

    public T get() {

        return (T) NBTReflect.getHandle().getValue(handle);
    }

    public void set(T value) {

        NBTReflect.getHandle().setValue(handle, value);
    }
}
