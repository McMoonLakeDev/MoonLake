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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <h1>ProfileHistoryList</h1>
 * 档案历史记录列表
 *
 * @version 1.0
 * @author Month_Light
 * @see ProfileHistory
 * @see Iterable
 */
public class ProfileHistoryList implements Iterable<ProfileHistory> {

    private final List<ProfileHistory> historyList;

    /**
     * 档案历史记录列表构造函数
     *
     * @param historyList 历史记录列表
     */
    public ProfileHistoryList(List<ProfileHistory> historyList) {
        this.historyList = historyList;
    }

    /**
     * 获取当前档案历史记录列表最新(当前)正在使用的档案
     *
     * @return 档案历史记录
     */
    public ProfileHistory getLatest() {
        if(historyList.size() == 1)
            return historyList.get(0);
        Collections.sort(historyList);
        return historyList.get(historyList.size() - 1);
    }

    /**
     * 获取此档案历史记录列表的数量大小
     *
     * @return 数量大小
     */
    public int size() {
        return historyList.size();
    }

    @Override
    public Iterator<ProfileHistory> iterator() {
        return historyList.iterator();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof ProfileHistoryList) {
            ProfileHistoryList other = (ProfileHistoryList) obj;
            return historyList != null ? historyList.equals(other.historyList) : other.historyList == null;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return historyList != null ? historyList.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ProfileHistoryList{" +
                "historyList=" + historyList +
                '}';
    }
}
