package com.minecraft.moonlake.nbt;

import com.minecraft.moonlake.api.nbt.NBTReflect;

/**
 * Created by MoonLake on 2016/9/21.
 */
public class NBTTagLong extends NBTTagNumberic<Long> {

    public NBTTagLong() {

        this(0L);
    }

    public NBTTagLong(long handle) {

        super(handle);
    }

    public NBTTagLong(boolean ignored, Object tag) {

        super(tag);

        if(NBTReflect.getHandle().getTagType(tag) != 4) {

            throw new IllegalArgumentException("The nbt tag not is nbt tag long object.");
        }
    }

    @Override
    public void setNumber(Number number) {

        set(number.longValue());
    }

    @Override
    public byte getTypeId() {

        return 4;
    }

    @Override
    public Long get() {

        return (Long) NBTReflect.getHandle().getValue(handle);
    }

    @Override
    public void set(Long value) {

        NBTReflect.getHandle().setValue(handle, value);
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
