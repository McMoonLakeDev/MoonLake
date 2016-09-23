package com.minecraft.moonlake.nbt;

import com.minecraft.moonlake.api.nbt.NBTReflect;

/**
 * Created by MoonLake on 2016/9/21.
 */
public class NBTTagByte extends NBTTagNumberic<Byte> {

    public NBTTagByte() {

        this((byte) 0);
    }

    public NBTTagByte(byte handle) {

        super(handle);
    }

    public NBTTagByte(boolean ignored, Object tag) {

        super(tag);

        if(NBTReflect.getHandle().getTagType(tag) != 1) {

            throw new IllegalArgumentException("The nbt tag not is nbt tag byte object.");
        }
    }

    @Override
    public void setNumber(Number number) {

        set(number.byteValue());
    }

    @Override
    public byte getTypeId() {

        return 1;
    }

    @Override
    public Byte get() {

        return (Byte) NBTReflect.getHandle().getValue(handle);
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
