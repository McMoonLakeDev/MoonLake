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
import com.minecraft.moonlake.api.player.advancement.AdvancementKey;

import java.util.Collection;

class AdvancementExpression implements Advancement {

    private final AdvancementKey key;
    private final Collection<String> criteria;

    public AdvancementExpression(AdvancementKey key, Collection<String> criteria) {
        this.key = key;
        this.criteria = criteria;
    }

    @Override
    public AdvancementKey getKey() {
        return key;
    }

    @Override
    public Collection<String> getCriteria() {
        return criteria;
    }
}
