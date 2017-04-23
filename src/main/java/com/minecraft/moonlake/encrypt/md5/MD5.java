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

import com.minecraft.moonlake.encrypt.EncryptData;
import com.minecraft.moonlake.property.ReadOnlyStringProperty;
import com.minecraft.moonlake.property.SimpleStringProperty;

/**
 * <h1>MD5</h1>
 * MD5 加密数据类
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v2.0 删除.
 */
@Deprecated
public class MD5 implements EncryptData {

    private ReadOnlyStringProperty md5;

    /**
     * MD5 加密数据类构造函数
     *
     * @param md5 MD5 值
     */
    public MD5(String md5) {

        this.md5 = new SimpleStringProperty(md5);
    }

    /**
     * 获取 MD5 加密后的 32 位数据
     *
     * @return 32 位 MD5 数据
     */
    public String to32Bit() {

        return md5.get();
    }

    /**
     * 获取 MD5 加密后的 32 位大写数据
     *
     * @return 32 位 MD5 大写数据
     */
    public String to32BitUpperCase() {

        return to32Bit().toUpperCase();
    }

    /**
     * 获取 MD5 加密后的 16 位数据
     *
     * @return 16 位 MD5 数据
     */
    public String to16Bit() {

        return md5.get().substring(8, 24);
    }

    /**
     * 获取 MD5 加密后的 16 位大写数据
     *
     * @return 16 位 MD5 大写数据
     */
    public String to16BitUpperCase() {

        return to16Bit().toUpperCase();
    }
}
