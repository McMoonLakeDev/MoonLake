package com.minecraft.moonlake.nbt;

import com.minecraft.moonlake.nbt.utils.NBTReflect;

/**
 * Created by MoonLake on 2016/9/21.
 */
public class NBTTagInt extends NBTTagNumberic<Integer> {

    public NBTTagInt() {

        this(0);
    }

    public NBTTagInt(int handle) {

        super(handle);
    }

    public NBTTagInt(boolean ignored, Object tag) {

        super(tag);

        if(NBTReflect.getHandle().getTagType(tag) != 3) {

            throw new IllegalArgumentException("The nbt tag not is nbt tag int object.");
        }
    }

    @Override
    public void setNumber(Number number) {

        set(number.intValue());
    }

    @Override
    public byte getTypeId() {

        return 3;
    }

    @Override
    public Integer get() {

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
