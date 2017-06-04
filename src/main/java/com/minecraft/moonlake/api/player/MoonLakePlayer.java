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
 
 
package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.api.VersionAdapter;
import com.minecraft.moonlake.api.player.ability.AbilityPlayer;
import com.minecraft.moonlake.api.player.advancement.AdvancementPlayer;
import com.minecraft.moonlake.api.player.attribute.AttributePlayer;
import com.minecraft.moonlake.api.player.depend.DependPlayer;
import com.minecraft.moonlake.api.player.inventory.InventoryPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * <hr />
 * <div>
 *     <h1>Minecraft MoonLake Player Interface</h1>
 *     <p>By Month_Light Ver: 1.1</p>
 * </div>
 * <hr />
 * <div>
 *     <h1>如果派生子类应该这样写继承关系:</h1>
 *     <p>public class SonPlayer extends {@link AbstractPlayer} { }</p>
 *     <hr />
 *     <h2>如果使用接口来实现继承关系:</h2>
 *     <p>public interface SonPlayer extends MoonLakePlayer { }</p>
 *     <h2>那么你的实现类就应该这样来继承关系:</h2>
 *     <p>public class SonPlayerWrapped extends {@link AbstractPlayer} implements SonPlayer { }</p>
 * </div>
 * <hr />
 *
 * @see AbstractPlayer
 * @see SimpleMoonLakePlayer
 * @see NMSPlayer
 * @see InternetPlayer
 * @see SkinmePlayer
 * @see AbilityPlayer
 * @see AttributePlayer
 * @see InventoryPlayer
 * @see DependPlayer
 * @version 1.1
 * @author Month_Light
 */
public interface MoonLakePlayer extends AbilityPlayer, AttributePlayer, AdvancementPlayer, InventoryPlayer, NMSPlayer, InternetPlayer, SkinmePlayer, DependPlayer, Comparable<MoonLakePlayer>, VersionAdapter.Minecraft {

    /**
     * 获取此玩家的 Bukkit 玩家对象
     *
     * @return Bukkit 玩家对象
     */
    Player getBukkitPlayer();

    /**
     * 获取此玩家的名称
     *
     * @return 名称
     */
    String getName();

    /**
     * 获取此玩家的 UUID
     *
     * @return UUID
     */
    UUID getUniqueId();

    /**
     * 比较两个对象
     *
     * @param target 目标对象
     * @return true 则对象相同 else 不同
     */
    @Override
    int compareTo(MoonLakePlayer target);

    /**
     * 判断此对象是否和另个对象完全符合
     *
     * @param object 对象
     * @return 是否符合、一致、匹配
     */
    @Override
    boolean equals(Object object);
}
