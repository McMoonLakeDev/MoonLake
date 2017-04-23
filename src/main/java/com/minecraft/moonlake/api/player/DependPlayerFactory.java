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


package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.api.player.depend.DependEconomy;
import com.minecraft.moonlake.api.player.depend.DependEconomyVault;
import com.minecraft.moonlake.api.player.depend.DependPermissionsEx;
import com.minecraft.moonlake.api.player.depend.DependWorldEdit;
import com.minecraft.moonlake.exception.CannotDependException;

/**
 * <h1>DependPlayerFactory</h1>
 * 依赖插件玩家工厂类
 *
 * @version 1.0
 * @author Month_Light
 */
public class DependPlayerFactory {

    private static DependPlayerFactory instance;

    private DependPlayerFactory() {
    }

    /**
     * 获取 DependPlayerFactory 对象
     *
     * @return DependPlayerFactory
     */
    public static DependPlayerFactory get() {

        if(instance == null) {

            instance = new DependPlayerFactory();
        }
        return instance;
    }

    /**
     * 获取 DependEconomy 对象
     *
     * @return DependEconomy
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    public DependEconomy dependEconomy() throws CannotDependException {

        return DependPlayerPluginListener.economyPlayer();
    }

    /**
     * 获取 DependEconomyVault 对象
     *
     * @return DependEconomyVault
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    public DependEconomyVault dependVault() throws CannotDependException {

        return DependPlayerPluginListener.economyVaultPlayer();
    }

    /**
     * 获取 DependWorldEdit 对象
     *
     * @return DependWorldEdit
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    public DependWorldEdit dependWorldEdit() throws CannotDependException {

        return DependPlayerPluginListener.worldEditPlayer();
    }

    /**
     * 获取 DependPermissionsEx 对象
     *
     * @return DependPermissionsEx
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    public DependPermissionsEx dependPermissionsEx() throws CannotDependException {

        return DependPlayerPluginListener.permissionsExPlayer();
    }
}
