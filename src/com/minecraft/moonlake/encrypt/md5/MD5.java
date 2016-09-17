package com.minecraft.moonlake.encrypt.md5;

import com.minecraft.moonlake.encrypt.EncryptData;
import com.minecraft.moonlake.property.ReadOnlyStringProperty;
import com.minecraft.moonlake.property.SimpleStringProperty;

/**
 * Created by MoonLake on 2016/6/28.
 */
public class MD5 implements EncryptData {

    private ReadOnlyStringProperty md5;

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
