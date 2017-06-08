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

import com.minecraft.moonlake.api.entity.AttributeType;
import com.minecraft.moonlake.api.player.advancement.Advancement;
import com.minecraft.moonlake.api.player.advancement.AdvancementKey;
import com.minecraft.moonlake.api.player.advancement.AdvancementProgress;
import com.minecraft.moonlake.api.player.attribute.Attribute;
import com.minecraft.moonlake.api.utility.MinecraftVersion;
import com.minecraft.moonlake.exception.PlayerNotOnlineException;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

/**
 * <h1>SimpleMoonLakePlayer</h1>
 * 月色之湖玩家接口单包装类 - Bukkit 1.12
 *
 * @version 1.0
 * @author Month_Light
 */
public class SimpleMoonLakePlayer_v1_12 extends SimpleMoonLakePlayer_v1_11 {

    /**
     * 月色之湖玩家接口单包装类 - Bukkit 1.12 构造函数
     *
     * @param name 玩家名
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家没有在线则抛出异常
     */
    public SimpleMoonLakePlayer_v1_12(String name) throws IllegalArgumentException, PlayerNotOnlineException {

        super(name);
    }

    /**
     * 月色之湖玩家接口单包装类 - Bukkit 1.12 构造函数
     *
     * @param player 玩家对象
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家没有在线则抛出异常
     */
    public SimpleMoonLakePlayer_v1_12(Player player) throws IllegalArgumentException, PlayerNotOnlineException {

        super(player);
    }

    //
    // 其实这三个函数从 1.11.2 版本都添加上了
    // 但是我的 v1_11 月色之湖玩家实现类并不想重写
    // 因为可能会有 1.11 的服务端进行使用本插件
    // 所以就只在 v1_12 版本及以后的版本存在

    @Override
    public void setItemCooldown(Material type, int tick) {
        getBukkitPlayer().setCooldown(type, tick);
    }

    @Override
    public boolean hasItemCooldown(Material type) {
        return getBukkitPlayer().hasCooldown(type);
    }

    @Override
    public int getItemCooldown(Material type) {
        return getBukkitPlayer().getCooldown(type);
    }
    ///

    // 重写获取属性函数, 因为 1.12 新增加了飞行速度
    @Override
    public Attribute getAttribute(AttributeType type) {
        Validate.notNull(type, "The attribute type object is null.");
        return new AttributeExpression_v1_12_Plus(getBukkitPlayer(), type);
    }

    @Override
    public String getLanguage() {
        return getBukkitPlayer().getLocale();
    }

    @Override
    public AdvancementProgress getAdvancementProgress(Advancement advancement) {
        Validate.notNull(advancement, "The advancement object is null.");
        return getAdvancementProgress(advancement.getKey());
    }

    @Override
    public AdvancementProgress getAdvancementProgress(AdvancementKey key) {
        Validate.notNull(key, "The advancement key object is null.");
        org.bukkit.advancement.Advancement advancement = Bukkit.getAdvancement(NamespacedKey.minecraft(key.getKey()));
        org.bukkit.advancement.AdvancementProgress progress = advancement != null ? getBukkitPlayer().getAdvancementProgress(advancement) : null;
        return progress != null ? new AdvancementProgressExpression(new AdvancementExpression(key, advancement.getCriteria()), progress) : null;
    }

    @Override
    public MinecraftVersion mcVersion() {

        return MinecraftVersion.V1_12;
    }
}
