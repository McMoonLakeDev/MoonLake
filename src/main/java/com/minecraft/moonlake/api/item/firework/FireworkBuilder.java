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
 
 
package com.minecraft.moonlake.api.item.firework;

import com.minecraft.moonlake.builder.Builder;
import com.minecraft.moonlake.builder.UncertainParamBuilder;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

/**
 * <hr />
 * <div>
 *     <h1>Firework Builder Library</h1>
 *     <p>By Month_Light Ver: 1.0</p>
 * </div>
 * <hr />
 * <div>
 *     <h1>简单的 demo 使用例子</h1>
 *     <p>ItemLibraryFactorys.fireworkBuilder()</p>
 *     <p style="text-indent: 30px">.withType(FireworkType.STAR)</p>
 *     <p style="text-indent: 30px">.withColor(Color.RED)</p>
 *     <p style="text-indent: 30px">.withFadeRandom()</p>
 *     <p style="text-indent: 30px">.withFlicker()</p>
 *     <p style="text-indent: 30px">.withPower(3)</p>
 *     <p style="text-indent: 30px">.launch({@link Location}, 6)</p>
 *     <p>是不是很简单呀 (○｀ 3′○)</p>
 * </div>
 * <hr />
 *
 * @version 1.0
 * @author Month_Light
 */
public interface FireworkBuilder extends Builder<ItemStack>, UncertainParamBuilder<ItemStack, FireworkBuilder> {

    /**
     * 构建并获取物品栈对象
     *
     * @return ItemStack
     */
    @Override
    ItemStack build();

    /**
     * 构建并获取物品栈对象
     *
     * @param join 烟花构建器
     * @return ItemStack
     */
    @Override
    ItemStack build(FireworkBuilder... join);

    /**
     * 设置烟花效果具有的颜色值
     *
     * @param color 颜色
     * @throws IllegalArgumentException 如果颜色对象为 {@code null} 则抛出异常
     */
    FireworkBuilder withColor(Color color);

    /**
     * 设置烟花效果具有的颜色值
     *
     * @param red 红色值
     * @param green 绿色值
     * @param blue 蓝色值
     * @throws IllegalArgumentException 如果颜色 RGB 不符合值范围则抛出异常 (0 - 255)
     */
    FireworkBuilder withColor(int red, int green, int blue);

    /**
     * 设置烟花效果具有的颜色值从随机 (0 - 255)
     */
    FireworkBuilder withColorRandom();

    /**
     * 设置烟花效果具有的形状类型
     *
     * @param fireworkType 烟花形状类型
     * @throws IllegalArgumentException 如果烟花形状类型对象为 {@code null} 则抛出异常
     */
    FireworkBuilder withType(FireworkType fireworkType);

    /**
     * 设置烟花效果的飞行力量（高度 0 - 127）
     *
     * @param power 力量
     * @throws IllegalArgumentException 如果力量小于 0 或大于 128 则抛出异常
     */
    FireworkBuilder withPower(int power);

    /**
     * 设置烟花效果具有尾径粒子
     */
    FireworkBuilder withTrail();

    /**
     * 设置烟花效果是否具有尾径粒子
     *
     * @param trail 是否具有
     */
    FireworkBuilder withTrail(boolean trail);

    /**
     * 设置烟花效果具有闪烁
     */
    FireworkBuilder withFlicker();

    /**
     * 设置烟花效果是否具有闪烁
     *
     * @param flicker 是否具有
     */
    FireworkBuilder withFlicker(boolean flicker);

    /**
     * 设置烟花效果具有的渐变颜色值
     *
     * @param color 渐变颜色
     * @throws IllegalArgumentException 如果颜色对象为 {@code null} 则抛出异常
     */
    FireworkBuilder withFade(Color color);

    /**
     * 设置烟花效果具有的渐变颜色值
     *
     * @param red 红色值
     * @param green 绿色值
     * @param blue 蓝色值
     * @throws IllegalArgumentException 如果颜色 RGB 不符合值范围则抛出异常 (0 - 255)
     */
    FireworkBuilder withFade(int red, int green, int blue);

    /**
     * 设置烟花效果具有的渐变颜色值从随机 (0 - 255)
     */
    FireworkBuilder withFadeRandom();

    /**
     * 将此烟花发射到指定位置
     *
     * @param location 位置
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     */
    void launch(Location location);

    /**
     * 将此烟花发射到指定位置
     *
     * @param location 位置
     * @param amount 发射数量
     * @throws IllegalArgumentException 如果位置对象为 {@code null} 则抛出异常
     */
    void launch(Location location, int amount);
}
