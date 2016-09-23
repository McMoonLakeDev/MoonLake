package com.minecraft.moonlake.api.nbt;

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

    private NBTFactory() {

    }

    /**
     * 获取 NBTLibrary 对象
     *
     * @return NBTLibrary
     */
    public static NBTLibrary core() {

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
    public static NBTItemStack item() {

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
    public static NBTEntity entity() {

        if(NBTEntityInstance == null) {

            NBTEntityInstance = new NBTEntityExpression();
        }
        return NBTEntityInstance;
    }
}
