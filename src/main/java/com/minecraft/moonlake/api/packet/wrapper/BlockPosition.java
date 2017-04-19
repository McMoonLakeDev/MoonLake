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

import com.minecraft.moonlake.api.nms.exception.NMSException;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.property.IntegerProperty;
import com.minecraft.moonlake.property.ObjectPropertyBase;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import com.minecraft.moonlake.reflect.accessors.MethodAccessor;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * <h1>BlockPosition</h1>
 * 方块位置封装类（详细doc待补充...）
 *
 * @version 1.2
 * @author Month_Light
 */
public class BlockPosition {

    private static volatile ConstructorAccessor<?> blockPositionConstructor;
    private static volatile MethodAccessor blockPositionGetXMethod;
    private static volatile MethodAccessor blockPositionGetYMethod;
    private static volatile MethodAccessor blockPositionGetZMethod;

    static {

        Class<?> blockPositionClass = MinecraftReflection.getMinecraftBlockPositionClass();
        blockPositionConstructor = Accessors.getConstructorAccessor(blockPositionClass, int.class, int.class, int.class);
        blockPositionGetXMethod = Accessors.getMethodAccessor(blockPositionClass, "getX");
        blockPositionGetYMethod = Accessors.getMethodAccessor(blockPositionClass, "getY");
        blockPositionGetZMethod = Accessors.getMethodAccessor(blockPositionClass, "getZ");
    }

    /**
     * Zero Block Position
     */
    public final static BlockPosition ZERO = new BlockPosition(0, 0, 0);

    private final IntegerProperty x;
    private final IntegerProperty y;
    private final IntegerProperty z;

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
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof BlockPosition) {
            BlockPosition other = (BlockPosition) obj;
            return getX() == other.getX() && getY() == other.getY() && getZ() == other.getZ();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = x.hashCode();
        result = 31 * result + y.hashCode();
        result = 31 * result + z.hashCode();
        return result;
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

            return blockPositionConstructor.invoke(getX(), getY(), getZ());
        }
        catch (Exception e) {

            throw new NMSException("The as nms block position exception.", e);
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
        Validate.isTrue(MinecraftReflection.isBlockPosition(nmsBlockPosition), "The nms block position object is not instance.");

        try {

            int x = (int) blockPositionGetXMethod.invoke(nmsBlockPosition);
            int y = (int) blockPositionGetYMethod.invoke(nmsBlockPosition);
            int z = (int) blockPositionGetZMethod.invoke(nmsBlockPosition);
            return new BlockPosition(x, y, z);
        }
        catch (Exception e) {

            throw new NMSException("The from nms block position exception.", e);
        }
    }

    /**
     * <h1>BlockPositionProperty</h1>
     * 方块位置封装属性类
     *
     * @version 1.0
     * @author Month_Light
     */
    public final static class BlockPositionProperty extends ObjectPropertyBase<BlockPosition> {

        /**
         * 方块位置封装属性类构造函数
         */
        public BlockPositionProperty() {
        }

        /**
         * 方块位置封装属性类构造函数
         *
         * @param blockPosition 方块位置对象
         */
        public BlockPositionProperty(BlockPosition blockPosition) {

            super(blockPosition);
        }

        /**
         * 方块位置封装属性类构造函数
         *
         * @param x X 坐标
         * @param y Y 坐标
         * @param z Z 坐标
         */
        public BlockPositionProperty(int x, int y, int z) {

            super(new BlockPosition(x, y, z));
        }

        /**
         * 获取此方块位置的 X 坐标属性
         *
         * @return X
         */
        public IntegerProperty xProperty() {

            return get().x;
        }

        /**
         * 获取此方块位置的 Y 坐标属性
         *
         * @return Y
         */
        public IntegerProperty yProperty() {

            return get().y;
        }

        /**
         * 获取此方块位置的 Z 坐标属性
         *
         * @return Z
         */
        public IntegerProperty zProperty() {

            return get().z;
        }

        @Override
        public String toString() {

            return "BlockPositionProperty [value: " + get() + "]";
        }
    }
}
