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


package com.minecraft.moonlake.auth.service;

import java.net.Proxy;

/**
 * <h1>MoonLakeAuthService</h1>
 * 月色之湖认证服务接口
 *
 * @version 1.0
 * @author Month_Light
 */
public interface MoonLakeAuthService {

    // Minecraft 认证服务实现方法
    // 详情查看在线的 wiki 维基百科
    // http://wiki.vg/Mojang_API
    // http://wiki.vg/Authentication
    ///

    /**
     * 获取此月色之湖认证服务的代理对象
     *
     * @return 代理
     */
    Proxy getProxy();
}
