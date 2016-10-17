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
 
 
package com.minecraft.moonlake.data;

import com.minecraft.moonlake.validate.Validate;

/**
 * Created by MoonLake on 2016/6/7.
 */
public final class Conversions {

    private Conversions() {

    }

    /**
     * 将指定对象转换到整数型对象
     *
     * @param object 对象
     * @return 整数型对象
     * @throws IllegalArgumentException 如果转换的对象为 {@code null} 则抛出异常
     */
    public static int toInt(Object object) {

        Validate.notNull(object, "The object is null.");

        if(object instanceof Number) {

            return ((Number)object).intValue();
        }
        else {

            try {

                return Integer.parseInt(object.toString());
            }
            catch (Exception e) {


            }
        }
        return 0;
    }

    /**
     * 将指定对象转换到单精度浮点数型对象
     *
     * @param object 对象
     * @return 单精度浮点数型对象
     * @throws IllegalArgumentException 如果转换的对象为 {@code null} 则抛出异常
     */
    public static float toFloat(Object object) {

        Validate.notNull(object, "The object is null.");

        if(object instanceof Number) {

            return ((Number)object).floatValue();
        }
        else {

            try {

                return Float.parseFloat(object.toString());
            }
            catch (Exception e) {


            }
        }
        return 0f;
    }

    /**
     * 将指定对象转换到双精度浮点数型对象
     *
     * @param object 对象
     * @return 双精度浮点数型对象
     * @throws IllegalArgumentException 如果转换的对象为 {@code null} 则抛出异常
     */
    public static double toDouble(Object object) {

        Validate.notNull(object, "The object is null.");

        if(object instanceof Number) {

            return ((Number)object).doubleValue();
        }
        else {

            try {

                return Double.parseDouble(object.toString());
            }
            catch (Exception e) {


            }
        }
        return 0d;
    }

    /**
     * 将指定对象转换到长整数型对象
     *
     * @param object 对象
     * @return 长整数型对象
     * @throws IllegalArgumentException 如果转换的对象为 {@code null} 则抛出异常
     */
    public static long toLong(Object object) {

        Validate.notNull(object, "The object is null.");

        if(object instanceof Number) {

            return ((Number)object).longValue();
        }
        else {

            try {

                return Long.parseLong(object.toString());
            }
            catch (Exception e) {


            }
        }
        return 0L;
    }

    /**
     * 将指定对象转换到短整数型对象
     *
     * @param object 对象
     * @return 短整数型对象
     * @throws IllegalArgumentException 如果转换的对象为 {@code null} 则抛出异常
     */
    public static short toShort(Object object) {

        if(object instanceof Number) {

            return ((Number)object).shortValue();
        }
        else {

            try {

                return Short.parseShort(object.toString());
            }
            catch (Exception e) {


            }
        }
        return 0;
    }

    /**
     * 将指定对象转换到字节型对象
     *
     * @param object 对象
     * @return 字节型对象
     * @throws IllegalArgumentException 如果转换的对象为 {@code null} 则抛出异常
     */
    public static byte toByte(Object object) {

        if(object instanceof Number) {

            return ((Number)object).byteValue();
        }
        else {

            try {

                return Byte.parseByte(object.toString());
            }
            catch (Exception e) {


            }
        }
        return 0;
    }
}
