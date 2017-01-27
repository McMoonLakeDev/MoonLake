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

import com.minecraft.moonlake.api.region.Region;
import com.minecraft.moonlake.api.region.RegionBlockVector;
import com.minecraft.moonlake.api.region.RegionVector;
import com.minecraft.moonlake.validate.Validate;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <h1>RegionIterator</h1>
 * 区域方块矢量迭代器
 *
 * @version 1.0
 * @author Month_Light
 */
public class RegionIterator implements Iterator<RegionBlockVector> {

    private final Region region;
    private final int maxX;
    private final int maxY;
    private final int maxZ;
    private final RegionVector min;
    private int nextX;
    private int nextY;
    private int nextZ;

    /**
     * 区域方块矢量迭代器构造函数
     *
     * @param region 区域
     * @throws IllegalArgumentException 如果区域对象为 {@code null} 则抛出异常
     */
    public RegionIterator(Region region) {

        this.region = Validate.checkNotNull(region);

        RegionVector max = region.getMaximumPoint();

        this.maxX = max.getBlockX();
        this.maxY = max.getBlockY();
        this.maxZ = max.getBlockZ();
        this.min = region.getMinimumPoint();
        this.nextX = this.min.getBlockX();
        this.nextY = this.min.getBlockY();
        this.nextZ = this.min.getBlockZ();

        this.forward();
    }

    private void forward() {

        while(hasNext() && !region.contains(new RegionVector(nextX, nextY, nextZ)))
            forwardOne();
    }

    private void forwardOne() {

        if (++this.nextX <= this.maxX)
            return;

        this.nextX = this.min.getBlockX();

        if (++this.nextY <= this.maxY)
            return;

        this.nextY = this.min.getBlockY();

        if (++this.nextZ <= this.maxZ)
            return;

        this.nextX = 0x7fffffff;
    }

    @Override
    public boolean hasNext() {

        return nextX != 0x7fffffff;
    }

    @Override
    public RegionBlockVector next() {

        if(!hasNext())
            throw new NoSuchElementException();

        RegionBlockVector answer = new RegionBlockVector(nextX, nextY, nextZ);

        forwardOne();
        forward();

        return answer;
    }

    @Override
    public void remove() {

        throw new UnsupportedOperationException();
    }
}
