/*
 * Copyright (C) 2016 The MoonLake Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
 
 
package com.minecraft.moonlake.manager;

import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Color;

import java.util.Random;
import java.util.UUID;

/**
 * <h1>RandomManager</h1>
 * 随机管理实现类
 *
 * @version 1.0
 * @author Month_Light
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

    /**
     * 随机管理实现类构造函数
     */
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
     * 设置此随机管理器的随机对象种子
     *
     * @param seed 种子
     * @throws UnsupportedOperationException 暂时不支持, 未来可能会支持此功能.
     */
    public static void setRandomSeed(long seed) {

        //RANDOM.setSeed(seed);
        throw new UnsupportedOperationException();
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
        return Math.abs(nextInt()) % (max - min + 1) + min;
    }

    /**
     * <p>返回下一个伪随机数，它是此随机数生成器的序列中均匀分布的 {@code int} 值。</p>
     * <p>{@code nextInt} 的常规协定是，伪随机地生成并返回一个 {@code int} 值。所有 {@code 232} 个可能 {@code int} 值的生成概率（大致）相同。</p>
     *
     * @return 下一个伪随机数，它是此随机数生成器的序列中均匀分布的 {@code int} 值。
     */
    public static int nextInt() {

        return getRandom().nextInt();
    }

    /**
     * <p>返回下一个伪随机数，它是此随机数生成器的序列中均匀分布的 {@code int} 值。</p>
     * <p>nextInt 的常规协定是，伪随机地生成并返回一个 {@code int} 值。所有 所有 {@code 232} 个可能 {@code int} 值的生成概率（大致）相同。</p>
     *
     * @param bound 要返回的随机数的范围。必须为正数。
     * @return 下一个伪随机数，在此随机数生成器序列中 {@code 0}（包括）和 {@code bound}（不包括）之间均匀分布的 {@code int} 值。
     * @throws IllegalArgumentException 如果范围值不是整数则抛出异常
     */
    public static int nextInt(int bound) {

        return getRandom().nextInt(bound);
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
        return Math.abs(nextLong()) % (max - min + 1) + min;
    }

    /**
     * <p>返回下一个伪随机数，它是取自此随机数生成器序列的均匀分布的 {@code long} 值。</p>
     * <p>{@code nextLong} 的常规协定是，伪随机地生成并返回一个 {@code long} 值。 </p>
     *
     * @return 下一个伪随机数，它是此随机数生成器序列中均匀分布的 {@code long} 值。
     */
    public static long nextLong() {

        return getRandom().nextLong();
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
        return Math.abs(nextFloat()) * (max - min) + min;
    }

    /**
     * <p>返回下一个伪随机数，它是取自此随机数生成器序列的、在 {@code 0.0} 和 {@code 1.0}之间均匀分布的 {@code float} 值。</p>
     * <p>{@code nextFloat} 的常规协定是，伪随机地生成并返回一个从 {@code 0.0f}（包括）到 {@code 1.0f}（包括）范围内均匀选择（大致）的 {@code float} 值。</p>
     * <p>所有可能的 所有 {@code 224} 个 {@code float} 值（其形式为 {@code m x 2-24}，其中 {@code m} 是一个小于 {@code 224} 的正整数）的生成概率（大致）相同。</p>
     *
     * @return 下一个伪随机数，它是取自此随机数生成器序列的、在 {@code 0.0} 和 {@code 1.0} 之间均匀分布的 {@code float} 值
     */
    public static double nextFloat() {

        return getRandom().nextFloat();
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
        return Math.abs(nextDouble()) * (max - min) + min;
    }

    /**
     * <p>返回下一个伪随机数，它是取自此随机数生成器序列的、在 {@code 0.0} 和 {@code 1.0} 之间均匀分布的 {@code double} 值。 </p>
     * <p>{@code nextDouble} 的常规协定是，伪随机地生成并返回一个从 {@code 0.0d}（包括）到 {@code 1.0d}（不包括）范围内均匀选择（大致）的 {@code double} 值。</p>
     *
     * @return 下一个伪随机数，它是此随机数生成器序列中 {@code 0.0} 和 {@code 1.0} 之间均匀分布的 {@code double} 值
     */
    public static double nextDouble() {

        return getRandom().nextDouble();
    }

    /**
     * <p>返回下一个伪随机数，它是取自此随机数生成器序列的均匀分布的 {@code boolean} 值。</p>
     * <p>{@code nextBoolean} 的常规协定是，伪随机地生成并返回一个 {@code boolean} 值。值 {@code true} 和 {@code false} 的生成概率（大致）相同。 </p>
     *
     * @return 下一个伪随机数，它是此随机数生成器的序列中均匀分布的 {@code boolean} 值。
     */
    public static boolean nextBoolean() {

        return getRandom().nextBoolean();
    }

    /**
     * <p>生成随机字节并将其置于用户提供的 {@code byte} 数组中。所生成的随机字节数等于该 {@code byte} 数组的长度。</p>
     *
     * @param bytes 用随机字节填充的 {@code byte} 数组
     * @throws NullPointerException 如果字节数组对象为 {@code null} 则抛出异常
     */
    public static void nextBytes(byte[] bytes) {

        getRandom().nextBytes(bytes);
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
     * 获取随机的 UUID 字符串对象
     *
     * @return UUID 字符串
     */
    public static String nextUUIDString() {

        return nextUUID().toString();
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
     */
    public static String nextString(int length, char[] randomChar) {

        Validate.notNull(randomChar, "The random char[] object is null.");

        char[] valueChar = new char[length];

        for(int i = 0; i < valueChar.length; i++) {

            valueChar[i] = randomChar[nextInt(randomChar.length)];
        }
        return new String(valueChar);
    }

    /**
     * 获取随机的 RGB 颜色 (0 - 255)
     *
     * @return 随机颜色
     */
    public static Color nextColor() {

        int red = nextInt(256);
        int green = nextInt(256);
        int blue = nextInt(256);

        return Color.fromRGB(red, green, blue);
    }
}
