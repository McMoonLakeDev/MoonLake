package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.api.player.ability.AbilityLibrary;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by MoonLake on 2016/9/14.
 */
public interface PlayerLibrary extends NMSPlayerLibrary, AbilityLibrary {

    /**
     * 获取 Player 对象从名称
     *
     * @param name 玩家名
     * @return Player
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     */
    Player fromName(String name);

    /**
     * 获取 Player 对象从 UUID
     *
     * @param uuid UUID
     * @return Player
     * @throws IllegalArgumentException 如果 {@code UUID} 对象为 {@code null} 则抛出异常
     */
    Player fromUUID(UUID uuid);

    /**
     * 获取 Player 对象从 UUID 字符串
     *
     * @param uuid UUID 字符串
     * @return Player
     * @deprecated 不推荐使用 UUID 字符串
     * @see PlayerLibrary#fromUUID(UUID)
     * @throws IllegalArgumentException 如果 {@code UUID} 字符串对象为 {@code null} 则抛出异常
     */
    @Deprecated
    Player fromUUID(String uuid);
}
