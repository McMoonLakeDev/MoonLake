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

import com.minecraft.moonlake.auth.exception.MoonLakeProfileException;

import java.nio.charset.Charset;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

/**
 * <h1>Property</h1>
 * 游戏档案属性类
 *
 * @version 1.0
 * @author Month_Light
 * @see GameProfile
 */
public class Property {

    private String name;
    private String value;
    private String signature;

    /**
     * 游戏档案属性类构造函数
     *
     * @param name 名称
     * @param value 值
     */
    public Property(String name, String value) {
        this(name, value, null);
    }

    /**
     * 游戏档案属性类构造函数
     *
     * @param name 名称
     * @param value 值
     * @param signature 签名
     */
    public Property(String name, String value, String signature) {
        this.name = name;
        this.value = value;
        this.signature = signature;
    }

    /**
     * 获取此游戏档案属性的名称
     *
     * @return 属性名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取此游戏档案的属性值
     *
     * @return 属性值
     */
    public String getValue() {
        return value;
    }

    /**
     * 获取此游戏档案的签名值
     *
     * @return 签名值
     */
    public String getSignature() {
        return signature;
    }

    /**
     * 获取此游戏档案是否拥有签名值
     *
     * @return 是否拥有签名值
     */
    public boolean hasSignature() {
        return signature != null;
    }

    /**
     * 验证此游戏档案的签名值
     *
     * @param key 公钥
     * @return 是否验证成功
     * @throws MoonLakeProfileException 如果验证时错误则抛出异常
     */
    public boolean validateSignature(PublicKey key) throws MoonLakeProfileException {
        if(!hasSignature())
            return false;
        try {
            Signature sig = Signature.getInstance("SHA1withRSA");
            sig.initVerify(key);
            sig.update(value.getBytes());
            return sig.verify(Base64.getDecoder().decode(signature.getBytes(Charset.forName("utf-8"))));
        } catch (Exception e) {
            throw new MoonLakeProfileException("无法验证属性材质数据的签名值.", e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof Property) {
            Property other = (Property) obj;
            return name != null ? name.equals(other.name) : other.name == null &&
                    value != null ? value.equals(other.value) : other.value == null &&
                    hasSignature() ? signature.equals(other.signature) : !other.hasSignature();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (signature != null ? signature.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
