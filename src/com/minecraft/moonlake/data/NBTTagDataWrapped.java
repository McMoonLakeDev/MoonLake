package com.minecraft.moonlake.data;

import com.minecraft.moonlake.api.nbt.NBTCompound;
import com.minecraft.moonlake.api.nbt.NBTList;

/**
 * Created by MoonLake on 2016/7/17.
 */
public class NBTTagDataWrapped extends ConversionDataWrapped implements NBTTagData {

    public NBTTagDataWrapped(Object obj) {

        super(obj);
    }

    @Override
    public NBTCompound asCompound() {

        return obj instanceof NBTCompound ? (NBTCompound) obj : null;
    }

    @Override
    public NBTList asList() {

        return obj instanceof NBTList ? (NBTList) obj : null;
    }
}
