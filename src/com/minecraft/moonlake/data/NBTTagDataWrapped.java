/*
 * Copyright (C) 2016 The MoonLake Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
 
 
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
