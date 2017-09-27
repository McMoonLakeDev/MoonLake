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

package com.minecraft.moonlake.api.region

class RegionIteratorFlat3D(region: RegionFlat, private val flatIterator: MutableIterator<RegionVector2D>) : MutableIterator<RegionVectorBlock> {

    private val minY: Int = region.minimumY
    private val maxY: Int = region.maximumY
    private var next2D: RegionVector2D? = if(flatIterator.hasNext()) flatIterator.next() else null
    private var nextY: Int = minY

    constructor(region: RegionFlat) : this(region, region.asRegionFlat().iterator())

    override fun hasNext(): Boolean
            = next2D != null

    override fun next(): RegionVectorBlock {
        if(!hasNext())
            throw NoSuchElementException()
        val current = RegionVectorBlock(next2D!!.blockX, nextY, next2D!!.blockZ)
        when {
            nextY < maxY -> nextY += 1
            flatIterator.hasNext() -> {
                next2D = flatIterator.next()
                nextY = minY
            }
            else -> next2D = null
        }
        return current
    }

    override fun remove()
            = throw UnsupportedOperationException()
}
