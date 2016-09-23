package com.minecraft.moonlake.nbt;

import com.minecraft.moonlake.nbt.utils.NBTReflect;

/**
 * Created by MoonLake on 2016/9/21.
 */
public class NBTTagString extends NBTTagDatable<String> {

    public NBTTagString() {

        this("");
    }

    public NBTTagString(String handle) {

        super(NBTReflect.getHandle().createTag(handle, (byte) 8));
    }


    public NBTTagString(boolean ignored, Object tag) {

        super(tag);

        if(NBTReflect.getHandle().getTagType(tag) != 8) {

            throw new IllegalArgumentException("The nbt tag not is nbt tag string object.");
        }
    }

    @Override
    public byte getTypeId() {

        return 8;
    }

    @Override
    public int hashCode() {

        return handle.hashCode();
    }

    @Override
    public String toString() {

        return get();
    }

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof NBTBase) {

            obj = ((NBTBase) obj).getHandle();
        }
        return handle.equals(obj);
    }
}
