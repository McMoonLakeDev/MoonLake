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
 * <h1>NBTTagDataWrapped</h1>
 * NBT 数据接口包装类
 *
 * @version 1.0
 * @author Month_Light
 */
@Deprecated
public class NBTTagDataWrapped extends ConversionDataWrapped implements NBTTagData {

    /**
     * NBT 数据接口包装类构造函数
     *
     * @param obj 对象
     */
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
