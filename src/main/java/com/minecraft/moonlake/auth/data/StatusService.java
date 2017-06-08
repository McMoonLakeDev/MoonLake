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
 * <h1>StatusService</h1>
 * 状态服务类
 *
 * @version 1.0
 * @author Month_Light
 * @see Comparable
 */
public class StatusService implements Comparable<StatusService.Type> {

    private final String host;
    private final Type type;

    /**
     * 状态服务类构造函数
     *
     * @param host 域名
     * @param type 状态类型
     */
    public StatusService(String host, Type type) {
        this.host = host;
        this.type = type;
    }

    /**
     * 获取此状态服务的域名
     *
     * @return 域名
     */
    public String getHost() {
        return host;
    }

    /**
     * 获取此状态服务的状态类型
     *
     * @return 状态类型
     */
    public Type getType() {
        return type;
    }

    /**
     * 获取此状态服务是否不可用
     *
     * @return 是否不可用
     */
    public boolean isUnavailable() {
        return type == Type.RED;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof StatusService) {
            StatusService other = (StatusService) obj;
            return host != null ? host.equals(other.host) : other.host == null &&
                    type != null ? type == other.type : other.type == null;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = host != null ? host.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StatusService{" +
                "host='" + host + '\'' +
                ", type=" + type +
                ", isUnavailable=" + isUnavailable() +
                '}';
    }

    @Override
    public int compareTo(Type other) {
        if(other == null)
            return 1;
        return type.compareTo(other);
    }

    /**
     * <h1>Type</h1>
     * 状态服务类型
     */
    public enum Type {

        /**
         * 状态服务类型: 绿色 (无问题)
         */
        GREEN,
        /**
         * 状态服务类型: 黄色 (小问题)
         */
        YELLOW,
        /**
         * 状态服务类型: 红色 (不可用)
         */
        RED,
        ;

        /**
         * 从指定名称获取状态服务类型
         *
         * @param name 名称
         * @return Type | null
         */
        public static Type fromName(String name) {
            switch (name.toLowerCase()) {
                case "green":
                    return GREEN;
                case "yellow":
                    return YELLOW;
                case "red":
                    return RED;
                default:
                    return null;
            }
        }
    }
}
