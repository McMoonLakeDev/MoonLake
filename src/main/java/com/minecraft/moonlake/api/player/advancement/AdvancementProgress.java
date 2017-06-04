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


package com.minecraft.moonlake.api.player.advancement;

import java.util.Collection;
import java.util.Date;

/**
 * <h1>AdvancementProgress</h1>
 * Bukkit 1.12+ 成就进度接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface AdvancementProgress {

    /**
     * 获取此成就进度的成就对象
     *
     * @return 成就
     */
    Advancement getAdvancement();

    /**
     * 获取此成就是否已经完成
     *
     * @return 是否已经完成
     */
    boolean isDone();

    /**
     * 将此成就进度的指定准则进行获得
     *
     * @param criteria 准则
     * @return 如果获得成功则为 true, 否则准则不存在或已经获得则为 false
     */
    boolean awardCriteria(String criteria);

    /**
     * 将此成就进度的指定准则进行撤销
     *
     * @param criteria 准则
     * @return 如果撤销成功则为 true, 否则准则不存在或没有获得则为 false
     */
    boolean revokeCriteria(String criteria);

    /**
     * 获取此成就进度指定准则的获得日期
     *
     * @param criteria 准则
     * @return 获得日期
     */
    Date getDateAwarded(String criteria);

    /**
     * 获取此成就进度剩余的准则集合
     *
     * @return 剩余的准则集合
     */
    Collection<String> getRemainingCriteria();

    /**
     * 获取此成就进度已经获得的准则集合
     *
     * @return 已经获得的准则集合
     */
    Collection<String> getAwardedCriteria();
}
