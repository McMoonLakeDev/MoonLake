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


package com.minecraft.moonlake.api.i18n;

import java.io.File;

/**
 * <h1>I18n</h1>
 * 国际化语言接口类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface I18n {

    /**
     * 获取加载的语言文件
     *
     * @return 语言文件
     */
    File getFile();

    /**
     * 重新加载语言文件
     */
    void reload();

    /**
     * 重新加载语言文件
     *
     * @param prefix 前缀
     */
    void reload(String prefix);

    /**
     * 获取语言文件指定键的值
     *
     * @param key 键
     * @return 值
     */
    String t(String key);

    /**
     * 获取语言文件指定键的值
     *
     * @param key 键
     * @param args 格式化参数
     * @return 值
     */
    String t(String key, Object... args);

    /**
     * 获取语言文件是否有指定键
     *
     * @param key 键
     * @return 是否有指定键
     */
    boolean has(String key);
}
