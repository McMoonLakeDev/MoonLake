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
 
 
package com.minecraft.moonlake.encrypt;

import com.minecraft.moonlake.validate.Validate;

/**
 * <h1>EncryptFactory</h1>
 * 加密工厂类
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v2.0 删除.
 */
@Deprecated
public class EncryptFactory {

    /**
     * Static EncrypetFactor Instance
     */
    private static EncryptFactory encryptFactoryInstance;

    /**
     * 加密工厂类构造函数
     */
    private EncryptFactory() {

    }

    /**
     * 获取 EncryptFactory 对象
     *
     * @return EncryptFactory
     */
    public static EncryptFactory get() {

        if(encryptFactoryInstance == null) {

            encryptFactoryInstance = new EncryptFactory();
        }
        return encryptFactoryInstance;
    }

    /**
     * 获取指定 Encrypt 加密实例对象
     *
     * @param encrypt Encrypt Class
     * @param <T> Encrypt
     * @return Encrypt
     * @throws IllegalArgumentException 如果 Encrypt Class 对象为 {@code null} 则抛出异常
     */
    public <T extends Encrypt> T instance(Class<T> encrypt) {

        Validate.notNull(encrypt, "The encrypt class object is null.");

        T t = null;

        try {

            t = encrypt.getConstructor().newInstance();
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        return t;
    }
}
