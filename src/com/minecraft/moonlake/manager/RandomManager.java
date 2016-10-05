package com.minecraft.moonlake.manager;

import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Color;

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
     * @throws IllegalArgumentException 如果最低范围大于最大范围则抛出异常
     * @deprecated {@link #nextInt(int, int)}
     */
    @Deprecated
    public static int getRandomNumber(int min, int max) {

        return nextInt(min, max);
    }

    /**
     * 获取指定范围的整数型随机数
     *
     * @param min 最低范围
     * @param max 最大范围
     * @return 范围内的随机数
     * @throws IllegalArgumentException 如果最低范围大于最大范围则抛出异常
     */
    public static int nextInt(final int min, final int max) {

        Validate.isTrue(min < max, "The random int value (min > max) exception.");

        if(min == max) {

            return min;
        }
        return Math.abs(getRandom().nextInt()) % (max - min + 1) + min;
    }

    /**
     * 获取指定范围的长整数型随机数
     *
     * @param min 最低范围
     * @param max 最大范围
     * @return 范围内的随机数
     * @throws IllegalArgumentException 如果最低范围大于最大范围则抛出异常
     */
    public static long nextLong(final long min, final long max) {

        Validate.isTrue(min < max, "The random int value (min > max) exception.");

        if(min == max) {

            return min;
        }
        return Math.abs(getRandom().nextLong()) % (max - min + 1) + min;
    }

    /**
     * 获取指定范围的单精度浮点型随机数
     *
     * @param min 最低范围
     * @param max 最大范围
     * @return 范围内的随机数
     * @throws IllegalArgumentException 如果最低范围大于最大范围则抛出异常
     */
    public static double nextFloat(final float min, final float max) {

        Validate.isTrue(min < max, "The random double value (min > max) exception.");

        if(min == max) {

            return min;
        }
        return Math.abs(getRandom().nextFloat()) * (max - min) + min;
    }

    /**
     * 获取指定范围的双精度浮点型随机数
     *
     * @param min 最低范围
     * @param max 最大范围
     * @return 范围内的随机数
     * @throws IllegalArgumentException 如果最低范围大于最大范围则抛出异常
     */
    public static double nextDouble(final double min, final double max) {

        Validate.isTrue(min < max, "The random double value (min > max) exception.");

        if(min == max) {

            return min;
        }
        return Math.abs(getRandom().nextDouble()) * (max - min) + min;
    }

    /**
     * 获取随机的 UUID 对象
     *
     * @return UUID
     * @deprecated {@link #nextUUID()}
     */
    @Deprecated
    public static UUID getRandomUUID() {

        return nextUUID();
    }

    /**
     * 获取随机的 UUID 对象
     *
     * @return UUID
     */
    public static UUID nextUUID() {

        return UUID.randomUUID();
    }

    /**
     * 获取随机长度的字符串
     *
     * @param length 长度
     * @return 随机字符串
     * @deprecated {@link #nextString(int)}
     */
    @Deprecated
    public static String getRandomString(int length) {

        return nextString(length);
    }

    /**
     * 获取随机长度的字符串
     *
     * @param length 长度
     * @return 随机字符串
     */
    public static String nextString(int length) {

        return nextString(length, STRING_CHAR);
    }

    /**
     * 获取随机长度的字符串
     *
     * @param length 长度
     * @return 随机字符串
     * @throws IllegalArgumentException 如果随机字符数组对象为 {@code null} 则抛出异常
     * @deprecated {@link #nextString(int, char[])}
     */
    @Deprecated
    public static String getRandomString(int length, char[] randomChar) {

        return nextString(length, randomChar);
    }

    /**
     * 获取随机长度的字符串
     *
     * @param length 长度
     * @return 随机字符串
     * @throws IllegalArgumentException 如果随机字符数组对象为 {@code null} 则抛出异常
     */
    public static String nextString(int length, char[] randomChar) {

        Validate.notNull(randomChar, "The random char[] object is null.");

        char[] valueChar = new char[length];

        for(int i = 0; i < valueChar.length; i++) {

            valueChar[i] = randomChar[getRandom().nextInt(randomChar.length)];
        }
        return new String(valueChar);
    }

    /**
     * 获取随机的 RGB 颜色 (0 - 255)
     *
     * @return 随机颜色
     * @deprecated {@link #nextColor()}
     */
    @Deprecated
    public static Color getRandomColor() {

        return nextColor();
    }

    /**
     * 获取随机的 RGB 颜色 (0 - 255)
     *
     * @return 随机颜色
     */
    public static Color nextColor() {

        int red = getRandom().nextInt(256);
        int green = getRandom().nextInt(256);
        int blue = getRandom().nextInt(256);

        return Color.fromRGB(red, green, blue);
    }
}
