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

import java.util.UUID;

/**
 * <h1>ProfileHistoryCallback</h1>
 * 档案历史记录回调接口
 *
 * @version 1.0
 * @author Month_Light
 * @see ProfileHistory
 * @see ProfileHistoryList
 */
public interface ProfileHistoryCallback {

    /**
     * 查找成功
     *
     * @param id 用户 Id
     * @param historyList 历史记录列表
     */
    void onLookupSucceeded(UUID id, ProfileHistoryList historyList);

    /**
     * 查找失败
     *
     * @param id 用户 Id
     * @param ex 异常
     */
    void onLookupFailed(UUID id, Exception ex);
}
