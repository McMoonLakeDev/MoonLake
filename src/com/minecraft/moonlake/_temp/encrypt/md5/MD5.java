package com.minecraft.moonlake._temp.encrypt.md5;

/**
 * Created by MoonLake on 2016/6/28.
 */
public class MD5 implements MD5Data {

    private final String md5;

    public MD5(String md5) {

        this.md5 = md5;
    }

    /**
     * 获取 MD5 加密后的 32 位数据
     *
     * @return 32 位 MD5 数据
     */
    @Override
    public String to32Bit() {

        return md5;
    }

    /**
     * 获取 MD5 加密后的 32 位大写数据
     *
     * @return 32 位 MD5 大写数据
     */
    @Override
    public String to32BitUpperCase() {

        return to32Bit().toUpperCase();
    }

    /**
     * 获取 MD5 加密后的 16 位数据
     *
     * @return 16 位 MD5 数据
     */
    @Override
    public String to16Bit() {

        return md5.substring(8, 24);
    }

    /**
     * 获取 MD5 加密后的 16 位大写数据
     *
     * @return 16 位 MD5 大写数据
     */
    @Override
    public String to16BitUpperCase() {

        return to16Bit().toUpperCase();
    }
}
