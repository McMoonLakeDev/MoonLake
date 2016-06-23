package com.minecraft.moonlake.api.encrypt.md5;

import com.minecraft.moonlake.api.encrypt.EncryptData;

/**
 * Created by MoonLake on 2016/6/4.
 */
public interface MD5Data extends EncryptData {

    /**
     * 获取 MD5 加密后的 32 位数据
     *
     * @return 32 位 MD5 数据
     */
    String to32Bit();

    /**
     * 获取 MD5 加密后的 32 位大写数据
     *
     * @return 32 位 MD5 大写数据
     */
    String to32BitUpperCase();

    /**
     * 获取 MD5 加密后的 16 位数据
     *
     * @return 16 位 MD5 数据
     */
    String to16Bit();

    /**
     * 获取 MD5 加密后的 16 位大写数据
     *
     * @return 16 位 MD5 大写数据
     */
    String to16BitUpperCase();
}
