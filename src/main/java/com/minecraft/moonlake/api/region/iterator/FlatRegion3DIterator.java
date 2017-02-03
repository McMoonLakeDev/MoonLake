/*
 * Copyright (C) 2017 The MoonLake Authors
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


package com.minecraft.moonlake.api.region.iterator;

import com.minecraft.moonlake.api.region.FlatRegion;
import com.minecraft.moonlake.api.region.RegionBlockVector;
import com.minecraft.moonlake.api.region.RegionVector2D;
import com.minecraft.moonlake.validate.Validate;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <h1>FlatRegion3DIterator</h1>
 * 平面区域方块矢量迭代器
 *
 * @version 1.0
 * @author Month_Light
 */
public class FlatRegion3DIterator implements Iterator<RegionBlockVector> {

    private Iterator<RegionVector2D> flatIterator;
    private int minY;
    private int maxY;
    private RegionVector2D next2D;
    private int nextY;

    /**
     * 平面区域方块矢量迭代器构造函数
     *
     * @param region 区域对象
     * @param flatIterator 平面区域矢量 2D 迭代器对象
     * @throws IllegalArgumentException 如果区域对象为 {@code null} 则抛出异常
     */
    public FlatRegion3DIterator(FlatRegion region, Iterator<RegionVector2D> flatIterator) {

        Validate.notNull(region);

        this.flatIterator = Validate.checkNotNull(flatIterator);
        this.minY = region.getMinimumY();
        this.maxY = region.getMaximumY();

        if (flatIterator.hasNext()) {

            this.next2D = flatIterator.next();
        }
        else {

            this.next2D = null;
        }
        this.nextY = this.minY;
    }

    /**
     * 平面区域方块矢量迭代器构造函数
     *
     * @param region 区域对象
     * @throws IllegalArgumentException 如果区域对象为 {@code null} 则抛出异常
     */
    public FlatRegion3DIterator(FlatRegion region) {

        this(region, region.asFlatRegion().iterator());
    }

    @Override
    public boolean hasNext() {

        return next2D != null;
    }

    @Override
    public RegionBlockVector next() {

        if (!hasNext())
            throw new NoSuchElementException();

        RegionBlockVector current = new RegionBlockVector(next2D.getBlockX(), nextY, next2D.getBlockZ());

        if (nextY < maxY) {

            nextY += 1;
        }
        else if (flatIterator.hasNext()) {

            next2D = flatIterator.next();
            nextY = minY;
        }
        else {

            next2D = null;
        }
        return current;
    }

    @Override
    public void remove() {

        throw new UnsupportedOperationException();
    }
}
