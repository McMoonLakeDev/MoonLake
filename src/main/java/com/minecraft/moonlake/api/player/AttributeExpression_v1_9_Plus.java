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
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

/**
 * <h1>AttributeExpression_v1_9_Plus</h1>
 * 特殊属性实现类 - Bukkit 1.9 - 1.11
 *
 * @version 1.0
 * @author Month_Light
 */
class AttributeExpression_v1_9_Plus extends AttributeExpression {

    /**
     * 特殊属性实现类 - Bukkit 1.9 - 1.11 构造函数
     *
     * @param player 玩家
     * @param type 属性类型
     */
    public AttributeExpression_v1_9_Plus(Player player, AttributeType type) {

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

        AttributeInstance instance = instance();
        return instance != null ? instance.getValue() : super.getValue();
    }

    @Override
    public void setValue(double value) {

        AttributeInstance instance = instance();

        if(instance == null) {

            super.setValue(value);
            return;
        }
        instance.setBaseValue(value);
    }

    /**
     * 获取玩家指定特殊属性的实例对象
     *
     * @return 属性对象 没有返回 null
     */
    protected final AttributeInstance instance() {

        Attribute attribute = conver();
        return attribute != null ? player.getAttribute(attribute) : null;
    }

    /**
     * 将此特殊属性转换为 Bukkit 的特殊属性
     *
     * @return Bukkit 的特殊属性 没有返回 null
     */
    protected org.bukkit.attribute.Attribute conver() {

        switch (type) {

            case MAX_HEALTH:
                return Attribute.GENERIC_MAX_HEALTH;
            case FOLLOW_RANGE:
                return Attribute.GENERIC_FOLLOW_RANGE;
            case KNOCK_BACK_RESISTANCE:
                return Attribute.GENERIC_KNOCKBACK_RESISTANCE;
            case MOVEMENT_SPEED:
                return Attribute.GENERIC_MOVEMENT_SPEED;
            case ATTACK_DAMAGE:
                return Attribute.GENERIC_ATTACK_DAMAGE;
            case ATTACK_SPEED:
                return Attribute.GENERIC_ATTACK_SPEED;
            case ARMOR:
                return Attribute.GENERIC_ARMOR;
            case ARMOR_TOUGHNESS:
                return null;
            case LUCK:
                return Attribute.GENERIC_LUCK;
            default:
                return null;
        }
    }
}
