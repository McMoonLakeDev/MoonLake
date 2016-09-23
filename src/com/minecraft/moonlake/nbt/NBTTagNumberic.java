package com.minecraft.moonlake.nbt;

/**
 * Created by MoonLake on 2016/9/21.
 */
public abstract class NBTTagNumberic<T extends Number> extends NBTTagDatable<T> {

    public NBTTagNumberic(Object handle) {

        super(handle);
    }

    public abstract void setNumber(Number number);

    public final String toString() {

        return get().toString();
    }
}
