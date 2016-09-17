package com.minecraft.moonlake.encrypt;

import com.minecraft.moonlake.validate.Validate;

/**
 * Created by MoonLake on 2016/9/17.
 */
public class EncryptFactory {

    /**
     * Static EncrypetFactor Instance
     */
    private static EncryptFactory encryptFactoryInstance;

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
