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

/**
 * <h1>PlayerLibraryFactory</h1>
 * 玩家支持库工厂类
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v2.0 删除.
 */
@Deprecated
public class PlayerLibraryFactory { // TODO 2.0

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
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
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
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
    public PlayerLibrary player() {

        return new PlayerExpressionWrapped();
    }

    /**
     * 获取 NMSPlayerLibrary 实例对象
     *
     * @return NMSPlayerLibrary
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
    public NMSPlayerLibrary nmsPlayer() {

        return new NMSPlayerExpression();
    }
}
