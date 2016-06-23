package com.minecraft.moonlake.mmorpg.lib.encrypt;

/**
 * Created by MoonLake on 2016/6/4.
 */
public class MoonLakeEncrypt {

    /**
     * 获取 MoonLake 加密类的指定类对象的实例
     *
     * @param clazz 加密类
     * @return 类的实例对象 异常返回 null
     */
    public static <T extends Encrypt> T getInstance(Class<T> clazz) {

        T instance = null;

        if(clazz != null) {

            try {

                instance = clazz.newInstance();
            }
            catch (Exception e) {

            }
            return instance;
        }
        return instance;
    }
}
