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
 * @deprecated 已过时, 将于 v2.0 删除.
 */
@Deprecated
public class PlayerLibraryFactorys { // TODO 2.0

    @Deprecated
    private static PlayerLibrary playerLibraryInstance;

    /**
     * 玩家支持库静态工厂类构造函数
     *
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
    private PlayerLibraryFactorys() {

    }

    /**
     * 获取 PlayerLibrary 对象
     *
     * @return PlayerLibrary
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
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
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
    public static NMSPlayerLibrary nmsPlayer() {

        return player();
    }

    /**
     * 获取依赖经济插件玩家 DependEconomyPlayer 对象
     *
     * @return DependEconomyPlayer
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
    public static DependEconomyPlayer economyPlayer() throws CannotDependException {

        return DependPlayerPluginListener.economyPlayer();
    }

    /**
     * 获取是否成功依赖经济插件玩家
     *
     * @return 是否成功依赖
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
    public static boolean isDependEconomyPlayerHook() {

        return DependPlayerPluginListener.isDependEconomyPlayerHook();
    }

    /**
     * 获取依赖权限插件玩家 DependPermissionsExPlayer 对象
     *
     * @return DependPermissionsExPlayer
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
    public static DependPermissionsExPlayer permissionsExPlayer() throws CannotDependException {

        return DependPlayerPluginListener.permissionsExPlayer();
    }

    /**
     * 获取是否成功依赖权限插件玩家
     *
     * @return 是否成功依赖
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
    public static boolean isDependPermissionsExPlayerHook() {

        return DependPlayerPluginListener.isDependPermissionsExPlayerHook();
    }

    /**
     * 获取依赖 Vault 经济插件玩家 DependEconomyVaultPlayer 对象
     *
     * @return DependEconomyVaultPlayer
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
    public static DependEconomyVaultPlayer economyVaultPlayer() throws CannotDependException {

        return DependPlayerPluginListener.economyVaultPlayer();
    }

    /**
     * 获取是否成功依赖 Vault 经济插件玩家
     *
     * @return 是否成功依赖
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
    public static boolean isDependEconomyVaultPlayerHook() {

        return DependPlayerPluginListener.isDependEconomyVaultPlayerHook();
    }

    /**
     * 获取依赖传送书插件玩家 DependWorldEditPlayer 对象
     *
     * @return DependWorldEditPlayer
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
    public static DependWorldEditPlayer worldEditPlayer() throws CannotDependException {

        return DependPlayerPluginListener.worldEditPlayer();
    }

    /**
     * 获取是否成功依赖 WorldEdit 创世神插件玩家
     *
     * @return 是否成功依赖
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
    public static boolean isDependWorldEditPlayerHook() {

        return DependPlayerPluginListener.isDependWorldEditPlayerHook();
    }
}
