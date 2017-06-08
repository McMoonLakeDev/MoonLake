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

import java.util.Iterator;
import java.util.List;

/**
 * <h1>StatusServiceList</h1>
 * 状态服务列表类
 *
 * @version 1.0
 * @author Month_Light
 * @see StatusService
 */
public class StatusServiceList implements Iterable<StatusService> {

    private List<StatusService> serviceList;

    /**
     * 状态服务列表类构造函数
     *
     * @param serviceList 状态服务列表
     */
    public StatusServiceList(List<StatusService> serviceList) {
        this.serviceList = serviceList;
    }

    /**
     * 获取指定域名的状态服务数据
     *
     * @param host 域名
     * @return 状态服务数据
     */
    public StatusService getByHost(String host) {
        for(Iterator<StatusService> iterator = iterator(); iterator().hasNext();) {
            StatusService service = iterator.next();
            if(service.getHost().equals(host))
                return service;
        }
        return null;
    }

    /**
     * 获取此状态服务列表的数量大小
     *
     * @return 数量大小
     */
    public int size() {
        return serviceList.size();
    }

    @Override
    public Iterator<StatusService> iterator() {
        return serviceList.iterator();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof StatusServiceList) {
            StatusServiceList other = (StatusServiceList) obj;
            return serviceList != null ? serviceList.equals(other.serviceList) : other.serviceList == null;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return serviceList != null ? serviceList.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "StatusServiceList{" +
                "serviceList=" + serviceList +
                '}';
    }
}
