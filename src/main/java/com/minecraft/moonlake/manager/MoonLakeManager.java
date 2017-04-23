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
 
 
package com.minecraft.moonlake.manager;

import com.minecraft.moonlake.MoonLakePlugin;
import com.minecraft.moonlake.api.MoonLake;
import com.minecraft.moonlake.api.MoonLakeCore;

/**
 * <h1>MoonLakeManager</h1>
 * 月色之湖管理抽象类
 *
 * @version 1.0
 * @author Month_Light
 */
@SuppressWarnings("deprecation")
public abstract class MoonLakeManager implements MoonLakeCore {

    private final static MoonLake MAIN;

    static {

        MAIN = MoonLakePlugin.getInstances();
    }

    /**
     * 获取月色之湖核心API插件实例对象
     *
     * @return 实例对象
     * @deprecated 已过时, 将于 v2.0 去除. 请使用 {@link #getMoonLakes()}
     */
    @Override
    @Deprecated
    public MoonLake getInstance() { // TODO 2.0

        return MAIN;
    }

    @Override
    public MoonLake getMoonLake() {

        return MAIN;
    }

    /**
     * 获取月色之湖核心API插件实例对象
     *
     * @return 实例对象
     * @deprecated 已过时, 将于 v2.0 去除. 请使用 {@link #getMoonLakes()}
     */
    @Deprecated
    protected static MoonLake getMain() { // TODO 2.0

        return MAIN;
    }

    /**
     * 获取月色之湖核心API插件实例对象
     *
     * @return 实例对象
     */
    protected static MoonLake getMoonLakes() {

        return MAIN;
    }
}
