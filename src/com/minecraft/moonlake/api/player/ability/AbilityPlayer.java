package com.minecraft.moonlake.api.player.ability;

import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
import com.minecraft.moonlake.exception.PlayerNotOnlineException;
import com.minecraft.moonlake.property.ReadOnlyBooleanProperty;
import org.bukkit.Material;

/**
 * Created by MoonLake on 2016/9/29.
 */
public interface AbilityPlayer {

    /**
     * 给此玩家设置物品栈冷却时间
     *
     * @param material 物品栈类型
     * @param tick 时间 Tick (1s = 20tick)
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     * @throws IllegalBukkitVersionException 如果服务器 Bukkit 版本不支持则抛出异常
     */
    void setItemCooldown(Material material, int tick);

    /**
     * 获取此玩家物品栈类型是否拥有冷却时间
     *
     * @param material 物品栈类型
     * @return true 则物品栈类型拥有冷却时间
     * @throws IllegalArgumentException 如果物品栈类型对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家不在线则抛出异常
     * @throws IllegalBukkitVersionException 如果服务器 Bukkit 版本不支持则抛出异常
     */
    ReadOnlyBooleanProperty hasItemCooldown(Material material);
}
