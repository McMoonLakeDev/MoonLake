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
import com.minecraft.moonlake.api.player.attribute.Attribute;
import com.minecraft.moonlake.manager.EntityManager;
import org.bukkit.entity.Player;

/**
 * <h1>AttributeExpression</h1>
 * 特殊属性实现类
 *
 * @version 1.0
 * @author Month_Light
 */
class AttributeExpression implements Attribute {

    protected final Player player;
    protected final AttributeType type;

    /**
     * 特殊属性实现类构造函数
     *
     * @param player 玩家
     * @param type 属性类型
     */
    public AttributeExpression(Player player, AttributeType type) {

        this.player = player;
        this.type = type;
    }

    @Override
    public AttributeType getType() {

        return type;
    }

    @Override
    public double getDefaultValue() {

        return type.getDef();
    }

    @Override
    public double getValue() {

        return EntityManager.getAttributeValue(player, type);
    }

    @Override
    public void setValue(double value) {

        EntityManager.setAttributeValue(player, type, value);
    }
}
