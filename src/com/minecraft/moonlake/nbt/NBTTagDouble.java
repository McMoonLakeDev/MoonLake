package com.minecraft.moonlake.nbt;

import com.minecraft.moonlake.nbt.utils.NBTReflect;

/**
 * Created by MoonLake on 2016/9/21.
 */
public class NBTTagDouble extends NBTTagNumberic<Double> {

    public NBTTagDouble() {

        this(0d);
    }

    public NBTTagDouble(double handle) {

        super(handle);
    }

    public NBTTagDouble(boolean ignored, Object tag) {

        super(tag);

        if(NBTReflect.getHandle().getTagType(tag) != 6) {

            throw new IllegalArgumentException("The nbt tag not is nbt tag double object.");
        }
    }

    @Override
    public void setNumber(Number number) {

        set(number.doubleValue());
    }

    @Override
    public byte getTypeId() {

        return 6;
    }

    @Override
    public Double get() {

        return (Double) NBTReflect.getHandle().getValue(handle);
    }

    @Override
    public int hashCode() {

        return handle.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof NBTBase) {

            obj = ((NBTBase) obj).getHandle();
        }
        return handle.equals(obj);
    }
}
