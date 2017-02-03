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
 
 
package com.minecraft.moonlake.api.item;

import com.minecraft.moonlake.api.item.firework.FireworkLibrary;
import com.minecraft.moonlake.api.item.meta.MetaLibrary;
import com.minecraft.moonlake.api.item.potion.PotionEffectCustom;
import com.minecraft.moonlake.api.item.potion.PotionLibrary;
import com.minecraft.moonlake.api.item.potion.PotionType;
import com.minecraft.moonlake.api.item.skull.SkullLibrary;
import org.bukkit.inventory.ItemStack;

/**
 * <div>
 *     <h1>ItemStack 物品栈操作支持库</h1>
 *     <p>By Month_Light Ver: 1.2</p>
 * </div>
 * <hr />
 * <div>
 *     <h1>那么他的作用都有什么呢 (╯‵□′)╯︵┻━┻</h1>
 *     <table>
 *         <tr><th>函数简介</th><th>函数调用</th></tr>
 *         <tr>
 *             <td>设置物品栈的显示名称</td>
 *             <td>{@link #setDisplayName(ItemStack, String)}</td>
 *         </tr>
 *         <tr>
 *             <td>设置物品栈的标签信息</td>
 *             <td>{@link #setLore(ItemStack, String...)}</td>
 *         </tr>
 *         <tr>
 *             <td>设置物品栈的特殊属性</td>
 *             <td>{@link #setAttribute(ItemStack, AttributeModify)}</td>
 *         </tr>
 *         <tr>
 *             <td>创建自定义药水物品栈</td>
 *             <td>{@link #createCustomPotion(PotionType, int, PotionEffectCustom...)}</td>
 *         </tr>
 *     </table>
 *     <h2>还有很多待您可以实现调用的 (๑• . •๑)</h2>
 * </div>
 * <hr />
 * <div>
 *     <h1>由于此类是接口类，所以您必须通过 {@link ItemLibraryFactory} 或者 {@link ItemLibraryFactorys} 获取实例对象</h1>
 *     <p>{@link ItemLibraryFactory} 他是实例单例模式工厂，获取的都是新的实例对象。</p>
 *     <p>{@link ItemLibraryFactorys} 他是静态单例模式工厂，获取的都是静态单一对象。</p>
 *     <h2>使用方法:</h2>
 *     <p>实例对象 -> {@code ItemLibrary itemLibrary = ItemLibraryFactory.get().item();} {@link ItemLibraryFactory#item()}</p>
 *     <p>静态对象 -> {@code ItemLibrary itemLibrary = ItemLibraryFactorys.item();} {@link ItemLibraryFactorys#item()}</p>
 *     <h2 style="color: red">注意:</h2>
 *     <p>由于 Java 没有引用传值，所以您设置物品栈时必须把返回值重新赋值给源对象才行！</p>
 *     <p>例如:</p>
 *     <p>{@code ItemLibrary itemLibrary = ItemLibraryFactorys.item();}</p>
 *     <p>{@code ItemStack itemStack = itemLibrary.create(Material.IRON_SWORD);}</p>
 *     <p>{@code itemStack = itemLibrary.setDisplayName(itemStack, "自定义名称");}</p>
 *     <p>必须重新赋值 ItemStack 对象后才会生效，因为 Java 传的对象都是值引用！！！</p>
 * </div>
 * <hr />
 * <div>
 *     <h1>您不仅可以通过这个支持库来创建和操作物品栈，还可以通过物品栈建造来操作！</h1>
 *     <p>详情查看: {@link ItemBuilder}</p>
 * </div>
 * <hr />
 *
 * @version 1.2
 * @author Month_Light
 * @see CraftLibrary
 * @see MetaLibrary
 * @see AttributeLibrary
 * @see PotionLibrary
 * @see SkullLibrary
 * @see FireworkLibrary
 */
public interface ItemLibrary extends CraftLibrary, MetaLibrary, AttributeLibrary, PotionLibrary, SkullLibrary, FireworkLibrary {

    /**
     * 获取指定物品栈类型是否为工具
     *
     * @param itemStack 物品栈
     * @return true 则物品栈类型为工具
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    boolean isTool(ItemStack itemStack);

    /**
     * 获取指定物品栈类型是否为武器
     *
     * @param itemStack 物品栈
     * @return true 则物品栈类型为武器
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    boolean isWeapon(ItemStack itemStack);

    /**
     * 获取指定物品栈类型是否为护甲
     *
     * @param itemStack 物品栈
     * @return true 则物品栈类型为护甲
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    boolean isArmor(ItemStack itemStack);

    /**
     * 获取指定物品栈类型是否为皮革护甲
     *
     * @param itemStack 物品栈
     * @return true 则物品栈类型为皮革护甲
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    boolean isLeatherArmor(ItemStack itemStack);

    /**
     * 获取指定物品栈类型是否为药水
     *
     * @param itemStack 物品栈
     * @return true 则物品栈类型为药水
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    boolean isPotion(ItemStack itemStack);

    /**
     * 获取指定物品栈类型是否为书
     *
     * @param itemStack 物品栈
     * @return true 则物品栈类型为书
     * @throws IllegalArgumentException 如果物品栈对象为 {@code null} 则抛出异常
     */
    boolean isWrittenBook(ItemStack itemStack);
}
