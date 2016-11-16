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
 
 
package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.exception.CannotDependException;

/**
 * <h1>PlayerLibraryFactorys</h1>
 * 玩家支持库静态工厂类
 *
 * @version 1.0
 * @author Month_Light
 */
public class PlayerLibraryFactorys {

    private static PlayerLibrary playerLibraryInstance;

    private static DependEconomyPlayer dependEconomyPlayer;
    private static Boolean dependEconomyPlayerHook = null;
    private static DependPermissionsExPlayer dependPermissionsExPlayer;
    private static Boolean dependPermissionsExPlayerHook = null;
    private static DependEconomyVaultPlayer dependEconomyVaultPlayer;
    private static Boolean dependEconomyVaultPlayerHook = null;

    /**
     * 玩家支持库静态工厂类构造函数
     */
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

    /**
     * 获取依赖经济插件玩家 DependEconomyPlayer 对象
     *
     * @return DependEconomyPlayer
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    public static DependEconomyPlayer economyPlayer() throws CannotDependException {

        if(dependEconomyPlayer == null && dependEconomyPlayerHook == null) {

            try {

                dependEconomyPlayer = new DependEconomyPlayer();
                dependEconomyPlayerHook = true;
            }
            catch (Exception e) {

                dependEconomyPlayerHook = false;

                throw new CannotDependException("The hook 'MoonLakeEconomy' plugin failed exception.", e);
            }
        }
        return dependEconomyPlayer;
    }

    /**
     * 获取是否成功依赖经济插件玩家
     *
     * @return 是否成功依赖
     */
    public static boolean isDependEconomyPlayerHook() {

        return dependEconomyPlayerHook;
    }

    /**
     * 获取依赖权限插件玩家 DependPermissionsExPlayer 对象
     *
     * @return DependPermissionsExPlayer
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    public static DependPermissionsExPlayer permissionsExPlayer() throws CannotDependException {

        if(dependPermissionsExPlayer == null && dependPermissionsExPlayerHook == null) {

            try {

                dependPermissionsExPlayer = new DependPermissionsExPlayer();
                dependPermissionsExPlayerHook = true;
            }
            catch (Exception e) {

                dependPermissionsExPlayerHook = false;

                throw new CannotDependException("The hook 'PermissionsEx' plugin failed exception.", e);
            }
        }
        return dependPermissionsExPlayer;
    }

    /**
     * 获取是否成功依赖权限插件玩家
     *
     * @return 是否成功依赖
     */
    public static boolean isDependPermissionsExPlayerHook() {

        return dependPermissionsExPlayerHook;
    }

    /**
     * 获取依赖 Vault 经济插件玩家 DependEconomyVaultPlayer 对象
     *
     * @return DependEconomyVaultPlayer
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    public static DependEconomyVaultPlayer economyVaultPlayer() throws CannotDependException {

        if(dependEconomyVaultPlayer == null && dependEconomyVaultPlayerHook == null) {

            try {

                dependEconomyVaultPlayer = new DependEconomyVaultPlayer();
                dependEconomyVaultPlayerHook = true;
            }
            catch (Exception e) {

                dependEconomyVaultPlayerHook = false;

                throw new CannotDependException("The hook 'Vault' plugin 'Economy' module failed exception.", e);
            }
        }
        return dependEconomyVaultPlayer;
    }

    /**
     * 获取是否成功依赖 Vault 经济插件玩家
     *
     * @return 是否成功依赖
     */
    public static boolean isDependEconomyVaultPlayerHook() {

        return dependEconomyVaultPlayerHook;
    }
}
