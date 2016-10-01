package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.reflect.Reflect;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

/**
 * Created by MoonLake on 2016/9/29.
 */
public abstract class PacketPlayOutPlayerInfo<T extends PacketPlayOutPlayerInfo> extends PacketAbstract<T> {

    private ObjectProperty<Action> action;
    private ObjectProperty<Player> player;

    @Deprecated
    public PacketPlayOutPlayerInfo() {

        this(null, null);
    }

    public PacketPlayOutPlayerInfo(Action action, Player player) {

        this.action = new SimpleObjectProperty<>(action);
        this.player = new SimpleObjectProperty<>(player);
    }

    public ObjectProperty<Action> getAction() {

        return action;
    }

    public ObjectProperty<Player> getPlayer() {

        return player;
    }

    public abstract void send(Player... player) throws PacketException;

    public enum Action {

        /**
         * 玩家信息交互类型: 添加玩家
         */
        ADD_PLAYER,
        /**
         * 玩家信息交互类型: 更新游戏模式
         */
        UPDATE_GAME_MODE,
        /**
         * 玩家信息交互类型: 更新延迟
         */
        UPDATE_LATENCY,
        /**
         * 玩家信息交互类型: 更新显示名称
         */
        UPDATE_DISPLAY_NAME,
        /**
         * 玩家信息交互类型: 删除玩家
         */
        REMOVE_PLAYER,;
    }

    /**
     * 获取对应版本的 PacketPlayOutPlayerInfo 实例对象
     *
     * @param action 交互
     * @param player 玩家
     * @return PacketPlayOutPlayerInfo
     * @throws IllegalArgumentException 如果交互对象或玩家对象为 {@code null} 则抛出异常
     * @throws PacketException 如果不存在或获取错误则抛出异常
     */
    public static <T extends PacketPlayOutPlayerInfo> T get(Action action, Player player) throws PacketException {

        Validate.notNull(action, "The player info action object is null.");
        Validate.notNull(player, "The target player object is null.");

        String version = Reflect.getServerVersion();

        try {

            @SuppressWarnings("unchecked")
            Class<T> target = (Class<T>) Class.forName(PacketPlayOutPlayerInfo.class.getName() + "_" + version);

            return target.getConstructor(Action.class, Player.class).newInstance(action, player);
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out play player info get exception.", e);
        }
    }

    /**
     * 获取对应版本的 PacketPlayOutPlayerInfo 实例对象
     *
     * @param action 交互
     * @param player 玩家
     * @return PacketPlayOutPlayerInfo
     * @throws IllegalArgumentException 如果交互对象或玩家对象为 {@code null} 则抛出异常
     * @throws PacketException 如果不存在或获取错误则抛出异常
     */
    public static <T extends PacketPlayOutPlayerInfo> T get(Action action, MoonLakePlayer player) throws PacketException {

        Validate.notNull(player, "The player object is null.");

        return get(action, player.getBukkitPlayer());
    }
}
