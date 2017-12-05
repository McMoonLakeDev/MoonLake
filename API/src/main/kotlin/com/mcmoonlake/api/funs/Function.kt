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

package com.mcmoonlake.api.funs

/**
 * ## Function (函数)
 *
 * * Represents a function that accepts one argument and produces a result.
 * * 表示接受一个参数并产生结果的函数.
 *
 * @author lgou2w
 * @since 2.0
 * @param T Input type.
 * @param T 输入类型.
 * @param R Result type.
 * @param R 结果类型.
 */
interface Function<in T, out R> {

    /**
     * * Applies this function to the given parameter.
     * * 将此函数应用与给定的参数.
     *
     * @param param Parameter.
     * @param param 参数.
     */
    fun apply(param: T): R
}
