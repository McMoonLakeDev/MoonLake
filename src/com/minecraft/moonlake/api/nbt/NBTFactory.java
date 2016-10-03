package com.minecraft.moonlake.api.nbt;

import java.util.Collection;
import java.util.Map;

/**
 * Created by MoonLake on 2016/9/23.
 */
public class NBTFactory {

    /**
     * NBT Library Static Instance;
     */
    private static NBTLibrary NBTLibraryInstance;

    /**
     * NBT ItemStack Static Instance
     */
    private static NBTItemStack NBTItemStackInstance;

    /**
     * NBT Entity Static Instance
     */
    private static NBTEntity NBTEntityInstance;

    /**
     * NBT Block Static Instance
     */
    private static NBTBlock NBTBlockInstance;

    /**
     * NBT Chunk Static Instance
     */
    private static NBTChunk NBTChunkInstance;

    private NBTFactory() {

    }

    /**
     * 获取 NBTCompound 实例对象
     *
     * @return NBTCompound
     */
    public static NBTCompound newCompound() {

        return new NBTCompoundExpression();
    }

    /**
     * 获取 NBTCompound 实例对象
     *
     * @param tag NBT 对象
     * @return NBTCompound
     */
    public static NBTCompound newCompound(Object tag) {

        return new NBTCompoundExpression(tag);
    }

    /**
     * 获取 NBTCompound 实例对象
     *
     * @param map Map 对象
     * @return NBTCompound
     */
    public static NBTCompound newCompound(Map map) {

        return new NBTCompoundExpression(map);
    }

    /**
     * 获取 NBTList 实例对象
     *
     * @return NBTList
     */
    public static NBTList newList() {

        return new NBTListExpression();
    }

    /**
     * 获取 NBTList 实例对象
     *
     * @param tag NBT 对象
     * @return NBTList
     */
    public static NBTList newList(Object tag) {

        return new NBTListExpression(tag);
    }

    /**
     * 获取 NBTList 实例对象
     *
     * @param collection 集合对象
     * @return NBTList
     */
    public static NBTList newList(Collection collection) {

        return new NBTListExpression(collection);
    }

    /**
     * 获取 NBTList 实例对象
     *
     * @param array 数组对象
     * @return NBTList
     */
    public static NBTList newList(Object[] array) {

        return new NBTListExpression(array);
    }

    /**
     * 获取 NBTLibrary 对象
     *
     * @return NBTLibrary
     */
    public static NBTLibrary get() {

        if(NBTLibraryInstance == null) {

            NBTLibraryInstance = new NBTExpression();
        }
        return NBTLibraryInstance;
    }

    /**
     * 获取 NBTItemStack 对象
     *
     * @return NBTItemStack
     */
    public static NBTItemStack getItem() {

        if(NBTItemStackInstance == null) {

            NBTItemStackInstance = new NBTItemStackExpression();
        }
        return NBTItemStackInstance;
    }

    /**
     * 获取 NBTEntity 对象
     *
     * @return NBTEntity
     */
    public static NBTEntity getEntity() {

        if(NBTEntityInstance == null) {

            NBTEntityInstance = new NBTEntityExpression();
        }
        return NBTEntityInstance;
    }

    /**
     * 获取 NBTBlock 对象
     *
     * @return NBTBlock
     */
    public static NBTBlock getBlock() {

        if(NBTBlockInstance == null) {

            NBTBlockInstance = new NBTBlockExpression();
        }
        return NBTBlockInstance;
    }

    /**
     * 获取 NBTChunk 对象
     *
     * @return NBTChunk
     */
    public static NBTChunk getChunk() {

        if(NBTChunkInstance == null) {

            NBTChunkInstance = new NBTChunkExpression();
        }
        return NBTChunkInstance;
    }
}
