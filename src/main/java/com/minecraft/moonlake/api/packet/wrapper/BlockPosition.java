/*
 * Copyright (C) 2016 The MoonLake Authors
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


package com.minecraft.moonlake.api.packet.wrapper;

import com.minecraft.moonlake.nms.exception.NMSException;
import com.minecraft.moonlake.property.ReadOnlyIntegerProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>BlockPosition</h1>
 * 方块位置封装类（详细doc待补充...）
 *
 * @version 1.1
 * @author Month_Light
 */
public class BlockPosition {

    private final static Class<?> CLASS_BLOCKPOSITION;
    private final static Class<?> CLASS_BASEBLOCKPOSITION;
    private final static Method METHOD_GETX;
    private final static Method METHOD_GETY;
    private final static Method METHOD_GETZ;

    static {

        try {

            CLASS_BLOCKPOSITION = PackageType.MINECRAFT_SERVER.getClass("BlockPosition");
            CLASS_BASEBLOCKPOSITION = PackageType.MINECRAFT_SERVER.getClass("BaseBlockPosition");
            METHOD_GETX = getMethod(CLASS_BASEBLOCKPOSITION, "getX");
            METHOD_GETY = getMethod(CLASS_BASEBLOCKPOSITION, "getY");
            METHOD_GETZ = getMethod(CLASS_BASEBLOCKPOSITION, "getZ");
        }
        catch (Exception e) {

            throw new NMSException("The nms block position initialize exception.", e);
        }
    }

    /**
     * Zero Block Position
     */
    public final static BlockPosition ZERO = new BlockPosition(0, 0, 0);

    private final ReadOnlyIntegerProperty x;
    private final ReadOnlyIntegerProperty y;
    private final ReadOnlyIntegerProperty z;

    /**
     * 方块位置封装类构造函数
     */
    public BlockPosition() {

        this(0, 0, 0);
    }

    /**
     * 方块位置封装类构造函数
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     */
    public BlockPosition(double x, double y, double z) {

        this((int) x, (int) y, (int) z);
    }

    /**
     * 方块位置封装类构造函数
     *
     * @param x X 坐标
     * @param y Y 坐标
     * @param z Z 坐标
     */
    public BlockPosition(int x, int y, int z) {

        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.z = new SimpleIntegerProperty(z);
    }

    /**
     * 获取方块位置的 X 坐标
     *
     * @return X 坐标
     */
    public int getX() {

        return x.get();
    }

    /**
     * 获取方块位置的 Y 坐标
     *
     * @return Y 坐标
     */
    public int getY() {

        return y.get();
    }

    /**
     * 获取方块位置的 Z 坐标
     *
     * @return Z 坐标
     */
    public int getZ() {

        return z.get();
    }

    /**
     * 获取此方块位置在指定世界的方块对象
     *
     * @param world 世界
     * @return Block
     * @throws IllegalArgumentException 如果世界对象为 {@code null} 则抛出异常
     */
    public Block getBlock(World world) {

        return Validate.checkNotNull(world).getBlockAt(getX(), getY(), getZ());
    }

    @Override
    public String toString() {

        return "BlockPosition{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", z=" + getZ() +
                '}';
    }

    /**
     * 将此 BlockPosition 转换到 NMS 的 BlockPosition 对象
     *
     * @return NMS BlockPosition
     * @throws NMSException 如果转换错误则抛出异常
     */
    public Object asNMS() throws NMSException {

        try {

            return instantiateObject(CLASS_BLOCKPOSITION, getX(),getY(), getZ());
        }
        catch (Exception e) {

            throw new NMSException();
        }
    }

    /**
     * 将指定 NMS 的 BlockPosition 对象转换到此 BlockPosition 对象
     *
     * @param nmsBlockPosition NMS BlockPosition
     * @return BlockPosition
     * @throws IllegalArgumentException 如果 NMS BlockPosition 对象为 {@code null} 或不是实例则抛出异常
     * @throws NMSException 如果转换错误则抛出异常
     */
    public static BlockPosition fromNMS(Object nmsBlockPosition) throws NMSException {

        Validate.notNull(nmsBlockPosition, "The nms block position object is null.");
        Validate.isTrue(CLASS_BASEBLOCKPOSITION.isInstance(nmsBlockPosition), "The nms block position object is not instance.");

        try {

            int x = (int) METHOD_GETX.invoke(nmsBlockPosition);
            int y = (int) METHOD_GETY.invoke(nmsBlockPosition);
            int z = (int) METHOD_GETZ.invoke(nmsBlockPosition);
            return new BlockPosition(x, y, z);
        }
        catch (Exception e) {

            throw new NMSException();
        }
    }
}
