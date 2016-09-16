package com.minecraft.moonlake.manager;

import java.util.Random;
import java.util.UUID;

/**
 * Created by MoonLake on 2016/7/17.
 */
public class RandomManager extends MoonLakeManager {

    private final static Random RANDOM;
    private final static char[] STRING_CHAR;

    static {

        RANDOM = new Random(System.nanoTime());
        STRING_CHAR = new char[] {

                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
        };
    }

    private RandomManager() {

    }

    /**
     * 获取随机类实例对象
     *
     * @return 随机类实例对象
     */
    public static Random getRandom() {

        return RANDOM;
    }

    /**
     * 获取指定范围的整数型随机数
     *
     * @param min 最低范围
     * @param max 最大范围
     * @return 范围内的随机数
     */
    public static int getRandomNumber(int min, int max) {

        return Math.abs(getRandom().nextInt()) % (max - min + 1) + min;
    }

    /**
     * 获取随机的 UUID 对象
     *
     * @return UUID
     */
    public static UUID getRandomUUID() {

        return UUID.randomUUID();
    }

    /**
     * 获取随机长度的字符串
     *
     * @param length 长度
     * @return 随机字符串
     */
    public static String getRandomString(int length) {

        return getRandomString(length, STRING_CHAR);
    }

    /**
     * 获取随机长度的字符串
     *
     * @param length 长度
     * @return 随机字符串
     * @throws IllegalArgumentException 如果随机字符数组对象为 {@code null} 则抛出异常
     */
    public static String getRandomString(int length, char[] randomChar) {

        char[] valueChar = new char[length];

        for(int i = 0; i < valueChar.length; i++) {

            valueChar[i] = randomChar[RandomManager.getRandom().nextInt(randomChar.length)];
        }
        return new String(valueChar);
    }
}
