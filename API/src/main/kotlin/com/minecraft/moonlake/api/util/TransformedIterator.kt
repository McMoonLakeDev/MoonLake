/*
 * Copyright (C) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Copyright (C) 2016-2017 The MoonLake (mcmoonlake@hotmail.com)
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

package com.minecraft.moonlake.api.util

import com.minecraft.moonlake.api.funs.Function
import com.minecraft.moonlake.api.toFunction

abstract class TransformedIterator<in T, out R>(private val iterator: MutableIterator<T>) : MutableIterator<R> {

    /** api */

    abstract fun transform(param: T): R

    override fun hasNext(): Boolean
            = iterator.hasNext()

    override fun next(): R
            = transform(iterator.next())

    override fun remove()
            = iterator.remove()

    /** static */

    companion object {

        @JvmStatic
        @JvmName("of")
        fun <T, R> of(iterator: MutableIterator<T>, function: Function<T, R>): MutableIterator<R> = object: TransformedIterator<T, R>(iterator) {
            override fun transform(param: T): R
                    = function.apply(param)
        }

        @JvmStatic
        @JvmName("of")
        fun <T, R> of(iterator: MutableIterator<T>, function: (_: T) -> R): MutableIterator<R>
                = of(iterator, function.toFunction())
    }
}
