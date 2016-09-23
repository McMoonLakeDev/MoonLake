package com.minecraft.moonlake.nbt;

import com.minecraft.moonlake.api.nbt.NBTReflect;

/**
 * Created by MoonLake on 2016/9/21.
 */
public class NBTTagShort extends NBTTagNumberic<Short> {

    public NBTTagShort() {

        this((short) 0);
    }

    public NBTTagShort(short handle) {

        super(handle);
    }

    public NBTTagShort(boolean ignored, Object tag) {

        super(tag);

        if(NBTReflect.getHandle().getTagType(tag) != 2) {

            throw new IllegalArgumentException("The nbt tag not is nbt tag short object.");
        }
    }

    @Override
    public void setNumber(Number number) {

        set(number.shortValue());
    }

    @Override
    public byte getTypeId() {

        return 2;
    }

    @Override
    public Short get() {

        return super.get();
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
