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

abstract class ConvertedCollection<VI, VO>(private val inner: MutableCollection<VI>) : ConvertedAbstract<VI, VO>(), MutableCollection<VO> {

    override fun add(element: VO): Boolean
            = inner.add(toIn(element))

    override fun addAll(elements: Collection<VO>): Boolean {
        var modified = false
        elements.forEach { modified = modified or add(it) }
        return modified
    }

    override fun clear()
            = inner.clear()

    override fun contains(element: VO): Boolean
            = inner.contains(toIn(element))

    override fun containsAll(elements: Collection<VO>): Boolean
            = elements.any { it -> contains(it) }

    override fun isEmpty(): Boolean
            = inner.isEmpty()

    override fun iterator(): MutableIterator<VO>
            = TransformedIterator.of(inner.iterator(), getOutConverter())

    override fun remove(element: VO): Boolean
            = inner.remove(toIn(element))

    override fun removeAll(elements: Collection<VO>): Boolean {
        var modified = false
        elements.forEach { modified = modified or remove(it) }
        return modified
    }

    override fun retainAll(elements: Collection<VO>): Boolean {
        val copy: MutableList<VI> = ArrayList()
        elements.forEach { copy.add(toIn(it)) }
        return inner.retainAll(copy)
    }

    override val size: Int
        get() = inner.size
}
