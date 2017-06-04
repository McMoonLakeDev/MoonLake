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


package com.minecraft.moonlake.auth.data;

/**
 * <h1>状态服务类</h1>
 * 状态服务回调接口
 *
 * @version 1.0
 * @author Month_Light
 * @see StatusService
 * @see StatusServiceList
 */
public interface StatusServiceCallback {

    /**
     * 检测成功
     *
     * @param serviceList 状态服务列表
     */
    void onCheckSucceeded(StatusServiceList serviceList);

    /**
     * 检测失败
     *
     * @param ex 异常
     */
    void onCheckFailed(Exception ex);
}
