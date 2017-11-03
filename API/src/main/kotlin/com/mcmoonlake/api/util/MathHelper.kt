/*
 * Copyright (C) 2016-Present The MoonLake (mcmoonlake@hotmail.com)
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

@file:JvmName("MathHelper")

package com.mcmoonlake.api.util

/** Math Helper */

/**
 * * Returns the value of the [num] parameter, clamped to be within the lower and upper limits given by the [min] and [max] parameters.
 * * 返回 [num] 参数的值, 被钳位在由 [min] 和 [max] 参数给出的下限和上限内.
 */
fun clamp(num: Int, min: Int, max: Int): Int
        = if(num < min) min else if(num > max) max else num

/**
 * * Returns the value of the [num] parameter, clamped to be within the lower and upper limits given by the [min] and [max] parameters.
 * * 返回 [num] 参数的值, 被钳位在由 [min] 和 [max] 参数给出的下限和上限内.
 */
fun clamp(num: Float, min: Float, max: Float): Float
        = if(num < min) min else if(num > max) max else num

/**
 * * Returns the value of the [num] parameter, clamped to be within the lower and upper limits given by the [min] and [max] parameters.
 * * 返回 [num] 参数的值, 被钳位在由 [min] 和 [max] 参数给出的下限和上限内.
 */
fun clamp(num: Double, min: Double, max: Double): Double
        = if(num < min) min else if(num > max) max else num
