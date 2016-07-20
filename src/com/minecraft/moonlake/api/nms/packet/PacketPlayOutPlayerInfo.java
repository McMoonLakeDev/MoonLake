package com.minecraft.moonlake.api.nms.packet;

import com.minecraft.moonlake.reflect.Reflect;
import com.mojang.authlib.GameProfile;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MoonLake on 2016/7/20.
 */
public class PacketPlayOutPlayerInfo implements Packet<PacketPlayOutPlayerInfo> {

    private PlayerInfoAction action;
    private List<Player> playerList;

    public PacketPlayOutPlayerInfo(PlayerInfoAction action, Player... players) {

        this.action = action;
        this.playerList = Arrays.asList(players);
    }

    public PlayerInfoAction getAction() {

        return action;
    }

    public void setAction(PlayerInfoAction action) {

        this.action = action;
    }

    public List<Player> getPlayerList() {

        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {

        this.playerList = playerList;
    }

    /**
     * 将此数据包发送给指定玩家
     *
     * @param names 玩家名
     */
    @Override
    public void send(String... names) {

        if(playerList.isEmpty()) {

            return;
        }
        try {

            Class<?> PacketPlayOutPlayerInfo = Reflect.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutPlayerInfo");
            Class<?> EnumPlayerInfoAction = Reflect.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutPlayerInfo$EnumPlayerInfoAction");

            Method valueOf = Reflect.getMethod(EnumPlayerInfoAction, "valueOf", String.class);
            Object EnumPlayerInfoAction0 = valueOf.invoke(null, action.name());

            Class<?> Packet = Reflect.PackageType.MINECRAFT_SERVER.getClass("Packet");
            Class<?> CraftPlayer = Reflect.PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftPlayer");
            Class<?> EntityPlayer = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityPlayer");
            Class<?> PlayerConnection = Reflect.PackageType.MINECRAFT_SERVER.getClass("PlayerConnection");

            List<Object> EntityPlayerList = new ArrayList<>();
            Method getHandle = Reflect.getMethod(CraftPlayer, "getHandle");

            for(Player player : playerList) {

                EntityPlayerList.add(getHandle.invoke(player));
            }
            Object ppopi = Reflect.instantiateObject(PacketPlayOutPlayerInfo, EnumPlayerInfoAction0, EntityPlayerList.iterator());

            Player[] players = PacketManager.getPlayersfromNames(names);
            Method sendPacket = Reflect.getMethod(PlayerConnection, "sendPacket", Packet);

            for(Player player : players) {

                Object NMSPlayer = getHandle.invoke(player);
                Field playerConnection = Reflect.getField(EntityPlayer, true, "playerConnection");

                sendPacket.invoke(playerConnection.get(NMSPlayer), ppopi);
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }

    public class PlayerInfoData {

        private int latency;
        private GameMode gameMode;
        private GameProfile profile;
        private String displayName;

        public PlayerInfoData(GameProfile profile, String displayName) {

            this(profile, displayName, GameMode.SURVIVAL);
        }

        public PlayerInfoData(GameProfile profile, String displayName, GameMode gameMode) {

            this(profile, displayName, GameMode.SURVIVAL, 0);
        }

        public PlayerInfoData(GameProfile profile, String displayName, GameMode gameMode, int latency) {

            this.profile = profile;
            this.displayName = displayName;
            this.gameMode = gameMode;
            this.latency = latency;
        }

        public int getLatency() {

            return latency;
        }

        public void setLatency(int latency) {

            this.latency = latency;
        }

        public GameMode getGameMode() {

            return gameMode;
        }

        public void setGameMode(GameMode gameMode) {

            this.gameMode = gameMode;
        }

        public GameProfile getProfile() {

            return profile;
        }

        public void setProfile(GameProfile profile) {

            this.profile = profile;
        }

        public String getDisplayName() {

            return displayName;
        }

        public void setDisplayName(String displayName) {

            this.displayName = displayName;
        }
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
