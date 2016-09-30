package com.minecraft.moonlake.api.player;

/**
 * Created by MoonLake on 2016/9/14.
 */
public class PlayerLibraryFactory {

    /**
     * 静态 PlayerLibraryFactory 实例对象
     */
    private static PlayerLibraryFactory instance;

    private PlayerLibraryFactory() {

    }

    /**
     * 获取 PlayerLibraryFactory 对象
     *
     * @return PlayerLibraryFactory
     */
    public static PlayerLibraryFactory get() {

        if(instance == null) {

            instance = new PlayerLibraryFactory();
        }
        return instance;
    }

    /**
     * 获取 PlayerLibrary 实例对象
     *
     * @return PlayerLibrary
     */
    public PlayerLibrary player() {

        return new PlayerExpressionWrapped();
    }

    /**
     * 获取 NMSPlayerLibrary 实例对象
     *
     * @return NMSPlayerLibrary
     */
    public NMSPlayerLibrary nmsPlayer() {

        return new NMSPlayerExpression();
    }
}
