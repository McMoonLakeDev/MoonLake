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

import com.minecraft.moonlake.api.entity.AttributeType;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

/**
 * <h1>AttributeExpression_v1_12_Plus</h1>
 * 特殊属性实现类 - Bukkit 1.12+
 *
 * @version 1.0
 * @author Month_Light
 */
class AttributeExpression_v1_12_Plus extends AttributeExpression_v1_9_Plus {

    /**
     * 特殊属性实现类 - Bukkit 1.12+ 构造函数
     *
     * @param player 玩家
     * @param type 属性类型
     */
    public AttributeExpression_v1_12_Plus(Player player, AttributeType type) {

        super(player, type);
    }

    @Override
    public AttributeType getType() {

        return super.getType();
    }

    @Override
    public double getDefaultValue() {

        return super.getDefaultValue();
    }

    @Override
    public double getValue() {

        return super.getValue();
    }

    @Override
    public void setValue(double value) {

        super.setValue(value);
    }

    @Override
    protected Attribute conver() {

        if(type == AttributeType.FLYING_SPEED) // 这个飞行速度是 1.12 新增加的
            return Attribute.GENERIC_FLYING_SPEED;
        return super.conver();
    }
}
