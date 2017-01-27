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


package com.minecraft.moonlake.api.player.attribute;

import com.minecraft.moonlake.api.entity.AttributeType;

/**
 * <h1>Attribute</h1>
 * 特殊属性接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface Attribute {

    /**
     * 获取此特殊属性的类型
     *
     * @return 类型
     */
    AttributeType getType();

    /**
     * 获取此特殊属性的默认值
     *
     * @return 默认值
     */
    double getDefaultValue();

    /**
     * 获取此特殊属性的当前值
     *
     * @return 当前值
     */
    double getValue();

    /**
     * 设置此特殊属性的新值
     *
     * @param value 新值
     */
    void setValue(double value);
}
