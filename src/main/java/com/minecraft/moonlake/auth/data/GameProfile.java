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

import com.google.gson.annotations.Expose;

import java.util.*;

/**
 * <h1>GameProfile</h1>
 * Minecraft 游戏档案类
 *
 * @version 1.0
 * @author Month_Light
 */
public class GameProfile {

    private UUID id;
    private String name;
    private List<Property> properties;
    private boolean legacy;

    // 定义此字段不参与 json 的序列化和反序列化
    @Expose(serialize = false, deserialize = false)
    private Map<TextureType, ProfileTexture> textures;

    /**
     * Minecraft 游戏档案类构造函数
     *
     * @param id 用户 Id
     * @param name 用户名
     */
    public GameProfile(String id, String name) {
        this.id = id != null && !id.equals("") ? UUID.fromString(id) : null;
        this.name = name;
    }

    /**
     * Minecraft 游戏档案类构造函数
     *
     * @param id 用户 Id
     * @param name 用户名
     */
    public GameProfile(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 获取此 Minecraft 游戏档案的用户 Id
     *
     * @return 用户 Id
     */
    public UUID getId() {
        return id;
    }

    /**
     * 获取此 Minecraft 游戏档案的用户名
     *
     * @return 用户名
     */
    public String getName() {
        return name;
    }

    /**
     * 获取此 Minecraft 游戏档案是否为旧版本 (<a href="https://help.mojang.com/customer/en/portal/articles/329530-changing-your-email-address-legacy-minecraft-accounts-", target="_blank">查看详情</a>)
     *
     * @return 是否为旧版本
     */
    public boolean isLegacy() {
        return legacy;
    }

    /**
     * 获取此 Minecraft 游戏档案的属性列表
     *
     * @return 属性列表
     */
    public List<Property> getProperties() {
        if(properties == null)
            this.properties = new ArrayList<>();
        return properties;
    }

    /**
     * 获取此 Minecraft 游戏档案指定名称的属性
     *
     * @param name 名称
     * @return 属性
     */
    public Property getProperty(String name) {
        for(Property property : getProperties())
            if(property.getName().equals(name))
                return property;
        return null;
    }

    /**
     * 获取此 Minecraft 游戏档案的材质
     *
     * @return 材质
     */
    public Map<TextureType, ProfileTexture> getTextures() {
        if(textures == null)
            this.textures = new HashMap<>();
        return textures;
    }

    /**
     * 获取此 Minecraft 游戏档案指定材质类型的材质档案
     *
     * @param type 材质类型
     * @return 材质档案
     */
    public ProfileTexture getTexture(TextureType type) {
        return textures != null ? textures.get(type) : null;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof GameProfile) {
            GameProfile other = (GameProfile) obj;
            return id != null ? id.equals(other.id) : other.id == null &&
                    name != null ? name.equals(other.name) : other.name == null;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GameProfile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", properties=" + properties +
                ", legacy=" + legacy +
                '}';
    }
}
