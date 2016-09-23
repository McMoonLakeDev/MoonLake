package com.minecraft.moonlake.nbt;

import com.minecraft.moonlake.api.nbt.NBTReflect;

/**
 * Created by MoonLake on 2016/9/21.
 */
public class NBTTagFloat extends NBTTagNumberic<Float> {

    public NBTTagFloat() {

        this(0f);
    }

    public NBTTagFloat(float handle) {

        super(handle);
    }

    public NBTTagFloat(boolean ignored, Object tag) {

        super(tag);

        if(NBTReflect.getHandle().getTagType(tag) != 5) {

            throw new IllegalArgumentException("The nbt tag not is nbt tag float object.");
        }
    }

    @Override
    public void setNumber(Number number) {

        set(number.floatValue());
    }

    @Override
    public byte getTypeId() {

        return 5;
    }

    @Override
    public Float get() {

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
