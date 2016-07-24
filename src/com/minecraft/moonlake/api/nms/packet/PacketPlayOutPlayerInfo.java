package com.minecraft.moonlake.api.nms.packet;

import com.minecraft.moonlake.MoonLakePlugin;
import com.minecraft.moonlake.reflect.Reflect;
import org.bukkit.entity.Player;

/**
 * Created by MoonLake on 2016/7/20.
 */
public class PacketPlayOutPlayerInfo extends PacketAbstract<PacketPlayOutPlayerInfo> {

    private PlayerInfoAction action;
    private Player player;

    public PacketPlayOutPlayerInfo(PlayerInfoAction action, Player player) {

        this.action = action;
        this.player = player;
    }

    public PlayerInfoAction getAction() {

        return action;
    }

    public void setAction(PlayerInfoAction action) {

        this.action = action;
    }

    public Player getPlayer() {

        return player;
    }

    public void setPlayer(Player player) {

        this.player = player;
    }

    /**
     * 将此数据包发送给指定玩家
     *
     * @param names 玩家名
     */
    @Override
    public void send(String... names) {

    }

    /**
     * 自动匹配此数据包对象
     *
     * @param action 信息交互
     * @param player 玩家
     * @return 数据包对象
     */
    public static <T extends PacketPlayOutPlayerInfo> T autoMatch(PlayerInfoAction action, Player player) {

        String version = MoonLakePlugin.getInstances().getBukkitVersion();

        try {

            Class<?> target = Class.forName("com.minecraft.moonlake.api.nms.packet.PacketPlayOutPlayerInfo_" + version);

            return (T) Reflect.instantiateObject(target, action, player);
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    public enum PlayerInfoAction {

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
}
