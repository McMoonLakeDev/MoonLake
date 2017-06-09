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


package com.minecraft.moonlake.api.utility;

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.player.*;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

/**
 * <h1>MoonLakeReflection</h1>
 * 月色之湖反射效用类
 *
 * @version 1.0
 * @author Month_Light
 */
public class MoonLakeReflection {

    private final static Class<? extends SimpleMoonLakePlayer> CLASS_SIMPLEMOONLAKEPLAYER;
    private static volatile ConstructorAccessor<? extends SimpleMoonLakePlayer> simpleMoonLakePlayerConstructor;

    static {

        MinecraftVersion mcVersion = MoonLakeAPI.currentMCVersion();

        if(mcVersion == null) // Not Support
            CLASS_SIMPLEMOONLAKEPLAYER = null;
        else if(mcVersion.equalsMinor(MinecraftVersion.V1_8)) // Bukkit 1.8
            CLASS_SIMPLEMOONLAKEPLAYER = SimpleMoonLakePlayer_v1_8.class;
        else if(mcVersion.equalsMinor(MinecraftVersion.V1_9)) // Bukkit 1.9
            CLASS_SIMPLEMOONLAKEPLAYER = SimpleMoonLakePlayer_v1_9.class;
        else if(mcVersion.equalsMinor(MinecraftVersion.V1_10)) // Bukkit 1.10
            CLASS_SIMPLEMOONLAKEPLAYER = SimpleMoonLakePlayer_v1_10.class;
        else if(mcVersion.equalsMinor(MinecraftVersion.V1_11)) // Bukkit 1.11
            CLASS_SIMPLEMOONLAKEPLAYER = SimpleMoonLakePlayer_v1_11.class;
        else if(mcVersion.equalsMinor(MinecraftVersion.V1_12)) // Bukkit 1.12
            CLASS_SIMPLEMOONLAKEPLAYER = SimpleMoonLakePlayer_v1_12.class;
        else // Not Support
            CLASS_SIMPLEMOONLAKEPLAYER = null;
    }

    /**
     * 月色之湖反射效用类构造函数
     */
    private MoonLakeReflection() {
    }

    /**
     * 获取月色之湖对应服务端的 SimpleMoonLakePlayer 类对象
     *
     * @return SimpleMoonLakePlayer Class
     */
    public static Class<? extends SimpleMoonLakePlayer> getSimpleMoonLakePlayerClass() {
        return CLASS_SIMPLEMOONLAKEPLAYER;
    }

    /**
     * 获取月色之湖对应服务端的 SimpleMoonLakePlayer 类构造对象
     *
     * @return SimpleMoonLakePlayer Constructor
     */
    public static ConstructorAccessor<? extends SimpleMoonLakePlayer> getSimpleMoonLakePlayerConstructor() {
        if(simpleMoonLakePlayerConstructor == null)
            simpleMoonLakePlayerConstructor = Accessors.getConstructorAccessor(getSimpleMoonLakePlayerClass(), Player.class);
        return simpleMoonLakePlayerConstructor;
    }

    /**
     * 获取月色之湖对应服务端的 SimpleMoonLakePlayer 实例对象
     *
     * @param player 玩家
     * @return SimpleMoonLakePlayer Instance
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     */
    public static SimpleMoonLakePlayer getSimpleMoonLakePlayerInstance(Player player) {
        Validate.notNull(player, "The player object is null.");
        return getSimpleMoonLakePlayerConstructor().invoke(player);
    }
}
