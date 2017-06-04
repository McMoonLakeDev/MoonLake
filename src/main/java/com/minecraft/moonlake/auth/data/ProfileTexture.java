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

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>ProfileTexture</h1>
 * 档案材质类
 *
 * @version 1.0
 * @author Month_Light
 * @see GameProfile
 */
public class ProfileTexture {

    private final String url;
    private final Map<String, String> metadata;

    /**
     * 档案材质类构造函数
     *
     * @param url 链接
     * @param metadata 元数据
     */
    public ProfileTexture(String url, Map<String, String> metadata) {
        this.url = url;
        this.metadata = metadata;
    }

    /**
     * 获取此档案材质的 URL 链接
     *
     * @return 链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * 获取此档案材质的元数据
     *
     * @return 元数据
     */
    public Map<String, String> getMetadata() {
        if(metadata == null)
            return new HashMap<>();
        return metadata;
    }

    /**
     * 获取此档案材质指定名称的元数据
     *
     * @param name 名称
     * @return 元数据
     */
    public String getMetadata(String name) {
        return metadata != null ? metadata.get(name) : null;
    }

    /**
     * 获取此档案材质的哈希值
     *
     * @return 哈希值
     */
    public String getHash() {
        String url = this.url.endsWith("/") ? this.url.substring(0, this.url.length() - 1) : this.url;
        int lash = url.lastIndexOf("/");
        int dot = url.lastIndexOf(".");
        if(dot < lash)
            dot = url.length();
        return url.substring(lash + 1, dot != -1 ? dot : url.length());
    }

    @Override
    public String toString() {
        return "ProfileTexture{" +
                "url='" + url + '\'' +
                ", hash=" + getHash() +
                '}';
    }
}
