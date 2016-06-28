package com.minecraft.moonlake.api.encrypt;

/**
 * Created by MoonLake on 2016/6/4.
 */
public interface Encrypt {

    /**
     * 获取字符串源加密的数据
     *
     * @return 经过加密的数据 字符串源空则返回 null
     */
    EncryptData encrypt();
}
