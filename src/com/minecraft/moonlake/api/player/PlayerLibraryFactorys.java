package com.minecraft.moonlake.api.player;

/**
 * Created by MoonLake on 2016/9/14.
 */
public class PlayerLibraryFactorys {

    private static PlayerLibrary playerLibraryInstance;

    private PlayerLibraryFactorys() {

    }

    /**
     * 获取 PlayerLibrary 对象
     *
     * @return PlayerLibrary
     */
    public static PlayerLibrary player() {

        if(playerLibraryInstance == null) {

            playerLibraryInstance = PlayerLibraryFactory.get().player();
        }
        return playerLibraryInstance;
    }

    /**
     * 获取 NMSPlayerLibrary 对象
     *
     * @return NMSPlayerLibrary
     */
    public static NMSPlayerLibrary nmsPlayer() {

        return player();
    }
}
