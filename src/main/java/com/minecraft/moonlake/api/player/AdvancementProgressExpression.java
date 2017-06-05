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


package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.api.player.advancement.Advancement;
import com.minecraft.moonlake.api.player.advancement.AdvancementProgress;

import java.util.Collection;
import java.util.Date;

/**
 * <h1>AdvancementProgressExpression</h1>
 * Bukkit 1.12+ 成就进度实现类
 *
 * @version 1.0
 * @author Month_Light
 * @see AdvancementProgress
 */
class AdvancementProgressExpression implements AdvancementProgress {

    private final Advancement advancement;
    private final org.bukkit.advancement.AdvancementProgress progress;

    /**
     * Bukkit 1.12+ 成就进度实现类构造函数
     *
     * @param advancement 成就对象
     * @param progress 成就进度
     */
    public AdvancementProgressExpression(Advancement advancement, org.bukkit.advancement.AdvancementProgress progress) {
        this.advancement = advancement;
        this.progress = progress;
    }

    @Override
    public Advancement getAdvancement() {
        return advancement;
    }

    @Override
    public boolean isDone() {
        return progress.isDone();
    }

    @Override
    public boolean awardCriteria(String criteria) {
        return progress.awardCriteria(criteria);
    }

    @Override
    public boolean revokeCriteria(String criteria) {
        return progress.revokeCriteria(criteria);
    }

    @Override
    public Date getDateAwarded(String criteria) {
        return progress.getDateAwarded(criteria);
    }

    @Override
    public Collection<String> getRemainingCriteria() {
        return progress.getRemainingCriteria();
    }

    @Override
    public Collection<String> getAwardedCriteria() {
        return progress.getAwardedCriteria();
    }
}
