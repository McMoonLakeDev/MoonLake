/*
 * Copyright (C) 2017 The MoonLake Authors
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


package com.minecraft.moonlake.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * <h1>NBTTagEnd</h1>
 * NBT 结束标签
 *
 * @version 1.0
 * @author Month_Light
 * @see NBTBase
 */
public class NBTTagEnd extends NBTBase {

    /**
     * NBT 结束标签构造函数
     */
    public NBTTagEnd() {
        super("");
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public NBTType getType() {
        return NBTType.END;
    }

    @Override
    public void read(DataInput input) throws IOException {
    }

    @Override
    public void write(DataOutput output) throws IOException {
    }

    @Override
    public String toString() {
        return "END";
    }

    @Override
    public NBTTagEnd clone() {
        return new NBTTagEnd();
    }
}
