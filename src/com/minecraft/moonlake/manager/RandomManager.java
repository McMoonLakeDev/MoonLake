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

    /**
     * 获取指定范围的整数型随机数
     *
     * @param min 最低范围
     * @param max 最大范围
     * @return 范围内的随机数
     */
    public static int getRandomNumber(int min, int max) {

        return Math.abs(random.nextInt()) % (max - min + 1) + min;
    }
}
