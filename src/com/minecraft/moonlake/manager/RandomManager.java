package com.minecraft.moonlake.manager;

import java.util.Random;

/**
 * Created by MoonLake on 2016/7/17.
 */
public class RandomManager extends MoonLakeManager {

    private final static Random random;

    static {

        random = new Random(System.nanoTime());
    }

    /**
     * 获取随机类实例对象
     *
     * @return 随机类实例对象
     */
    public static Random getRandom() {

        return random;
    }
}
