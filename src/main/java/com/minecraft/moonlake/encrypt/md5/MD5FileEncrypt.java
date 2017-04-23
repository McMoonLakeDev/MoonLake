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
 
 
package com.minecraft.moonlake.encrypt.md5;

import com.minecraft.moonlake.encrypt.Encrypt;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.validate.Validate;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/**
 * <h1>MD5FileEncrypt</h1>
 * MD5 文件加密实现类
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v2.0 删除.
 */
@Deprecated
public class MD5FileEncrypt implements Encrypt {

    private ObjectProperty<File> file;

    /**
     * MD5 文件加密实现类构造函数
     */
    public MD5FileEncrypt() {

        this(null);
    }

    /**
     * MD5 文件加密实现类构造函数
     *
     * @param file 目标文件
     */
    public MD5FileEncrypt(File file) {

        this.file = new SimpleObjectProperty<>(file);
    }

    /**
     * 获取 MD5 加密文件对象
     *
     * @return 文件对象
     */
    public ObjectProperty<File> getFile() {

        return file;
    }

    /**
     * 获取文件源加密的数据
     *
     * @return 经过加密的数据
     * @throws IllegalArgumentException 如果文件对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果文件对象的文件不存在或不是文件则抛出异常
     */
    @Override
    public MD5 encrypt() {

        Validate.notNull(file.get(), "The md5 file object is null.");
        Validate.isTrue(file.get().exists() && file.get().isFile(), "The md5 file object not exists or not is file.");

        String md5 = "";
        FileInputStream in = null;
        MappedByteBuffer byteBuffer = null;

        try {

            in = new FileInputStream(file.get());
            byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.get().length());
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md.digest());

            md5 = bi.toString(16);
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        finally {

            if(in != null) {

                try {

                    in.close();
                }
                catch (Exception e) {

                }
            }
        }
        return md5 != null && !md5.equals("") ? new MD5(md5) : null;
    }
}
