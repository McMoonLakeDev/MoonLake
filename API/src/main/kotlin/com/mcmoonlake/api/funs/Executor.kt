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
 * ## Executor (执行者)
 *
 * @author lgou2w
 * @since 2.0
 * @param T Execution parameter type.
 * @param T 执行参数类型.
 */
interface Executor<in T> {

    /**
     * * Executes the given parameter type object.
     * * 执行给定的参数类型对象.
     *
     * @param param Parameter.
     * @param param 参数.
     */
    fun execute(param: T)
}
