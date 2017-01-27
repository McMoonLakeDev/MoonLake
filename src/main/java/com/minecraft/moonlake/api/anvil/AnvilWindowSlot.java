/*
 * Copyright (C) 2016 The MoonLake Authors
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
 
 
package com.minecraft.moonlake.api.anvil;

/**
 * <h1>AnvilWindowSlot</h1>
 * 铁砧窗口槽位类型
 *
 * @version 1.0
 * @author Month_Light
 */
public enum AnvilWindowSlot {

    /**
     * 铁砧窗口左输入口
     */
    INPUT_LEFT(0),
    /**
     * 铁砧窗口右输入口
     */
    INPUT_RIGHT(1),
    /**
     * 铁砧窗口输出口
     */
    OUTPUT(2),
    ;

    private int slot;

    /**
     * 铁砧窗口槽位类型构造函数
     *
     * @param slot 槽位索引
     */
    AnvilWindowSlot(int slot) {

        this.slot = slot;
    }

    /**
     * 获取此铁砧窗口槽位的值
     *
     * @return 值
     */
    public int getSlot() {

        return slot;
    }

    /**
     * 将指定槽位的值转换为铁砧窗口槽位类型对象
     *
     * @param rawSlot 槽位值
     * @return AnvilWindowSlot
     */
    public static AnvilWindowSlot fromRawSlot(int rawSlot) {

        switch (rawSlot) {

            case 0:
                return INPUT_LEFT;
            case 1:
                return INPUT_RIGHT;
            case 2:
                return OUTPUT;
            default:
                return null;
        }
    }
}
