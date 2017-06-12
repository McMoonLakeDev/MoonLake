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

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * <h1>NBTUtil</h1>
 * NBT 利用类
 *
 * @version 1.0
 * @author Month_Light
 */
public final class NBTUtil {

    static {
    }

    private NBTUtil() {
    }

    /**
     * 从指定输入流读取 NBT 标签对象
     *
     * @param input 输入流
     * @return NBT 标签对象
     * @throws IOException 如果 IO 错误则抛出异常
     */
    public static NBTBase read(InputStream input) throws IOException {
        return read((DataInput) new DataInputStream(input));
    }

    /**
     * 从指定数据输入流读取 NBT 标签对象
     *
     * @param input 数据输入流
     * @return NBT 标签对象
     * @throws IOException 如果 IO 错误则抛出异常
     */
    @SuppressWarnings("all")
    public static NBTBase read(DataInput input) throws IOException {
        int typeId = input.readUnsignedByte();
        if(typeId == 0)
            return null;
        String name = input.readUTF();
        NBTBase nbt = NBTType.createNBTTag(typeId, name);
        nbt.read(input);
        return nbt;
    }

    /**
     * 将指定 NBT 标签对象写入到输出流
     *
     * @param output 输出流
     * @param nbt NBT 标签对象
     * @throws IOException 如果 IO 错误则抛出异常
     */
    public static void write(OutputStream output, NBTBase nbt) throws IOException {
        write((DataOutput) new DataOutputStream(output), nbt);
    }

    /**
     * 将指定 NBT 标签对象写入到数据输出流
     *
     * @param output 数据输出流
     * @param nbt NBT 标签对象
     * @throws IOException 如果 IO 错误则抛出异常
     */
    public static void write(DataOutput output, NBTBase nbt) throws IOException {
        output.writeByte(nbt.getTypeId());
        output.writeUTF(nbt.getName());
        nbt.write(output);
    }

    /**
     * 从指定文件读取 NBT 复合标签对象
     *
     * @param file 文件
     * @return NBT 复合标签对象
     * @throws IOException 如果 IO 错误则抛出异常
     */
    public static NBTTagCompound readFile(File file) throws IOException {
        return readFile(file, true);
    }

    /**
     * 从指定文件读取 NBT 复合标签对象
     *
     * @param file 文件
     * @param compressed 是否压缩数据
     * @return NBT 复合标签对象
     * @throws IOException 如果 IO 错误则抛出异常
     */
    public static NBTTagCompound readFile(File file, boolean compressed) throws IOException {
        InputStream input = new FileInputStream(file);
        if(compressed)
            input = new GZIPInputStream(input);
        NBTBase nbt = read(input);
        if(!(nbt instanceof NBTTagCompound))
            throw new IllegalStateException("根 NBT 数据类型不是 NBTTagCompound 类型.");
        return (NBTTagCompound) nbt;
    }

    /**
     * 将指定 NBT 复合标签对象写入到指定文件
     *
     * @param nbt NBT 复合对象
     * @param file 文件
     * @throws IOException 如果 IO 错误则抛出异常
     */
    public static void writeFile(NBTTagCompound nbt, File file) throws IOException {
        writeFile(nbt, file, true);
    }

    /**
     * 将指定 NBT 复合标签对象写入到指定文件
     *
     * @param nbt NBT 复合对象
     * @param file 文件
     * @param compressed 是否压缩数据
     * @throws IOException 如果 IO 错误则抛出异常
     */
    public static void writeFile(NBTTagCompound nbt, File file, boolean compressed) throws IOException {
        if(!file.exists()) {
            if(file.getParentFile() != null && !file.getParentFile().exists())
                file.getParentFile().mkdirs();
            file.createNewFile();
        }
        OutputStream out = new FileOutputStream(file);
        if(compressed)
            out = new GZIPOutputStream(out);
        write(out, nbt);
        out.close();
    }
}
