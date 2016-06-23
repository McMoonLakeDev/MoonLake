package com.minecraft.moonlake.api.encrypt;

/**
 * Created by MoonLake on 2016/6/4.
 */
public interface Encrypt {

    /**
     * 获取加密的字符串源
     *
     * @return
     */
    String getSource();

    /**
     * 设置加密的字符串源
     *
     * @param source 字符串源
     */
    void setSource(String source);

    /**
     * 获取字符串源加密的数据
     *
     * @return 经过加密的数据 字符串源空则返回 null
     */
    <T extends EncryptData> T encrypt();
}
