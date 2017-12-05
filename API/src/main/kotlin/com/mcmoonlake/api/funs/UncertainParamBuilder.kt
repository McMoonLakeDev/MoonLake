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
 * ## UncertainParamBuilder (不定长度参数构建者)
 *
 * @see [Builder]
 * @see [SingleParamBuilder]
 * @author lgou2w
 * @since 2.0
 * @param R Result type.
 * @param R 结果类型.
 * @param P Input type.
 * @param P 输入类型.
 */
interface UncertainParamBuilder<out R, in P> {

    /**
     * * Apply the given parameters and build a result type object.
     * * 应用给定参数并构建一个结果类型对象.
     *
     * @param params Parameter.
     * @param params 参数.
     */
    fun build(vararg params: P): R
}
