package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.api.player.ability.AbilityPlayer;
import com.minecraft.moonlake.api.player.inventory.InventoryPlayer;
import com.minecraft.moonlake.property.ReadOnlyStringProperty;
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
 * @see NMSPlayer
 * @see InternetPlayer
 * @see SkinmePlayer
 * @see AbilityPlayer
 * @see InventoryPlayer
 * @version 1.1
 * @author Month_Light
 */
public interface MoonLakePlayer extends AbilityPlayer, InventoryPlayer, NMSPlayer, InternetPlayer, SkinmePlayer, Comparable<MoonLakePlayer> {

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
     * 获取此玩家的名称
     *
     * @return 名称
     */
    ReadOnlyStringProperty getNames();

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
