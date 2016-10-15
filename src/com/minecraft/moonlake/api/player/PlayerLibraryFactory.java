package com.minecraft.moonlake.api.player;

/**
 * <h1>PlayerLibraryFactory</h1>
 * 玩家支持库工厂类
 *
 * @version 1.0
 * @author Month_Light
 */
public class PlayerLibraryFactory {

    /**
     * 静态 PlayerLibraryFactory 实例对象
     */
    private static PlayerLibraryFactory instance;

    /**
     * 玩家支持库工厂类构造函数
     */
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
