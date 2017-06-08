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

import java.awt.image.BufferedImage;

/**
 * <h1>SkinRawImageCallback</h1>
 * 皮肤图片回调接口
 *
 * @param <T> 类型
 * @version 1.0
 * @author Month_Light
 */
public interface SkinRawImageCallback<T> {

    /**
     * 查找成功
     *
     * @param param 参数
     * @param skinRawImage 图片
     */
    void onLookupSucceeded(T param, BufferedImage skinRawImage);

    /**
     * 查找失败
     *
     * @param param 参数
     * @param ex 异常
     */
    void onLookupFailed(T param, Exception ex);
}
