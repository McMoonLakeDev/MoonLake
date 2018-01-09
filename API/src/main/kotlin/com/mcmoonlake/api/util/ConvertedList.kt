/*
 *  ProtocolLib - Bukkit server library that allows access to the Minecraft protocol.
 *  Copyright (C) 2012 Kristian S. Stangeland
 *
 *  This program is free software; you can redistribute it and/or modify it under the terms of the
 *  GNU General Public License as published by the Free Software Foundation; either version 2 of
 *  the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with this program;
 *  if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 *  02111-1307 USA
 */

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

package com.mcmoonlake.api.util

abstract class ConvertedList<VI, VO>(
        private val inner: MutableList<VI>
) : ConvertedCollection<VI, VO>(inner),
        MutableList<VO> {

    override fun add(index: Int, element: VO)
            = inner.add(index, toIn(element))

    override fun addAll(index: Int, elements: Collection<VO>): Boolean
            = inner.addAll(index, getInnerCollection(elements.toMutableList()))

    override fun get(index: Int): VO
            = toOut(inner[index])

    override fun indexOf(element: VO): Int
            = inner.indexOf(toIn(element))

    override fun lastIndexOf(element: VO): Int
            = inner.lastIndexOf(toIn(element))

    override fun listIterator(): MutableListIterator<VO>
            = listIterator(0)

    override fun listIterator(index: Int): MutableListIterator<VO> {
        val innerIterator = inner.listIterator(index)
        return object: MutableListIterator<VO> {
            override fun hasPrevious(): Boolean
                    = innerIterator.hasPrevious()
            override fun nextIndex(): Int
                    = innerIterator.nextIndex()
            override fun previous(): VO
                    = toOut(innerIterator.previous())
            override fun previousIndex(): Int
                    = innerIterator.previousIndex()
            override fun add(element: VO)
                    = innerIterator.add(toIn(element))
            override fun hasNext(): Boolean
                    = innerIterator.hasNext()
            override fun next(): VO
                    = toOut(innerIterator.next())
            override fun remove()
                    = innerIterator.remove()
            override fun set(element: VO)
                    = innerIterator.set(toIn(element))
        }
    }

    override fun removeAt(index: Int): VO
            = toOut(inner.removeAt(index))

    override fun set(index: Int, element: VO): VO
            = toOut(inner.set(index, toIn(element)))

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<VO> {
        return object: ConvertedList<VI, VO>(inner.subList(fromIndex, toIndex)) {
            override fun toIn(outer: VO): VI
                    = this@ConvertedList.toIn(outer)
            override fun toOut(inner: VI): VO
                    = this@ConvertedList.toOut(inner)
        }
    }

    private fun getInnerCollection(elements: MutableCollection<VO>): ConvertedCollection<VO, VI> {
        return object: ConvertedCollection<VO, VI>(elements) {
            override fun toIn(outer: VI): VO
                    = this@ConvertedList.toOut(outer)
            override fun toOut(inner: VO): VI
                    = this@ConvertedList.toIn(inner)
        }
    }
}
