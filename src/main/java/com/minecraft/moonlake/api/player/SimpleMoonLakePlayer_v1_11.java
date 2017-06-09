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

import com.minecraft.moonlake.api.player.advancement.Advancement;
import com.minecraft.moonlake.api.player.advancement.AdvancementKey;
import com.minecraft.moonlake.api.player.advancement.AdvancementProgress;
import com.minecraft.moonlake.api.utility.MinecraftVersion;
import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
import com.minecraft.moonlake.exception.PlayerNotOnlineException;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

/**
 * <h1>SimpleMoonLakePlayer</h1>
 * 月色之湖玩家接口单包装类 - Bukkit 1.11
 *
 * @version 1.0
 * @author Month_Light
 */
public class SimpleMoonLakePlayer_v1_11 extends SimpleMoonLakePlayer_v1_10 {

    /**
     * 月色之湖玩家接口单包装类 - Bukkit 1.11 构造函数
     *
     * @param name 玩家名
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家没有在线则抛出异常
     */
    public SimpleMoonLakePlayer_v1_11(String name) throws IllegalArgumentException, PlayerNotOnlineException {

        super(name);
    }

    /**
     * 月色之湖玩家接口单包装类 - Bukkit 1.11 构造函数
     *
     * @param player 玩家对象
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家没有在线则抛出异常
     */
    public SimpleMoonLakePlayer_v1_11(Player player) throws IllegalArgumentException, PlayerNotOnlineException {

        super(player);
    }

    @Override
    public void setResourcePack(String url, byte[] hash) {

        Validate.notNull(url, "The resource pack url string object is null.");
        Validate.notNull(hash, "The resource pack hash value object is null.");
        Validate.notNull(hash.length == 20, "The resource pack hash value length is not 20. (length: " + hash.length + ")");

        getBukkitPlayer().setResourcePack(url, hash);
    }

    @Override
    public MinecraftVersion mcVersion() {

        return MinecraftVersion.V1_11;
    }

    //

    @Override
    public AdvancementProgress getAdvancementProgress(Advancement advancement) {

        throw new IllegalBukkitVersionException("The method not support 1.11 and old version.");
    }

    @Override
    public AdvancementProgress getAdvancementProgress(AdvancementKey key) {

        throw new IllegalBukkitVersionException("The method not support 1.10 and old version.");
    }

    ///
}
