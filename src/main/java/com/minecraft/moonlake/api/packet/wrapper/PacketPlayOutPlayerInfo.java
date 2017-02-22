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
 
 
package com.minecraft.moonlake.api.packet.wrapper;

import com.minecraft.moonlake.api.packet.Packet;
import com.minecraft.moonlake.api.packet.PacketPlayOut;
import com.minecraft.moonlake.api.packet.PacketPlayOutBukkit;
import com.minecraft.moonlake.api.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.api.player.PlayerLibraryFactorys;
import com.minecraft.moonlake.manager.PlayerManager;
import com.minecraft.moonlake.property.*;
import com.minecraft.moonlake.validate.Validate;
import com.mojang.authlib.GameProfile;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutPlayerInfo</h1>
 * 数据包输出玩家信息（详细doc待补充...）
 *
 * @version 2.0
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketPlayOutBukkit
 */
public class PacketPlayOutPlayerInfo extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTPLAYERINFO;
    private final static Class<?> CLASS_PACKETPLAYOUTPLAYERINFO_PLAYERINFODATA;
    private final static Class<?> CLASS_PACKETPLAYOUTPLAYERINFO_ENUMPLAYERINFOACTION;
    private final static Class<?> CLASS_ENUMGAMEMODE;
    private final static Class<?> CLASS_CHATSERIALIZER;
    private final static Method METHOD_CHARSERIALIZER_A;
    private final static Method METHOD_GETBYID;
    private final static Method METHOD_VALUEOF;

    static {

        try {

            CLASS_PACKETPLAYOUTPLAYERINFO = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutPlayerInfo");
            CLASS_PACKETPLAYOUTPLAYERINFO_PLAYERINFODATA = PackageType.MINECRAFT_SERVER.getClass(getServerVersion().equals("v1_8_R1") ? "PlayerInfoData" : "PacketPlayOutPlayerInfo$PlayerInfoData");
            CLASS_PACKETPLAYOUTPLAYERINFO_ENUMPLAYERINFOACTION = PackageType.MINECRAFT_SERVER.getClass(getServerVersion().equals("v1_8_R1") ? "EnumPlayerInfoAction" : "PacketPlayOutPlayerInfo$EnumPlayerInfoAction");
            CLASS_ENUMGAMEMODE = PackageType.MINECRAFT_SERVER.getClass(getServerVersion().equals("v1_8_R1") || getServerVersionNumber() >= 10 ? "EnumGamemode" : "WorldSettings$EnumGamemode");
            CLASS_CHATSERIALIZER =  PackageType.MINECRAFT_SERVER.getClass(getServerVersion().equals("v1_8_R1") ? "ChatSerializer" : "IChatBaseComponent$ChatSerializer");
            METHOD_VALUEOF = getMethod(CLASS_PACKETPLAYOUTPLAYERINFO_ENUMPLAYERINFOACTION, "valueOf", String.class);
            METHOD_CHARSERIALIZER_A = getMethod(CLASS_CHATSERIALIZER, "a", String.class);
            METHOD_GETBYID = getMethod(CLASS_ENUMGAMEMODE, "getById", int.class);
        }
        catch (Exception e) {

            throw new PacketInitializeException("The net.minecraft.server packet play out player info reflect raw initialize exception.", e);
        }
    }

    private ObjectProperty<Action> action;
    private DataListProperty dataList;

    /**
     * 数据包输出玩家信息构造函数
     */
    public PacketPlayOutPlayerInfo() {

        this(null, new ArrayList<>());
    }

    /**
     * 数据包输出玩家信息构造函数
     *
     * @param action 交互类型
     * @param players 玩家
     */
    public PacketPlayOutPlayerInfo(Action action, Player... players) {

        this.action = new SimpleObjectProperty<>(action);
        this.dataList = new DataListProperty();
        this.dataList.add(players);
    }

    /**
     * 数据包输出玩家信息构造函数
     *
     * @param action 交互类型
     * @param data 数据
     */
    public PacketPlayOutPlayerInfo(Action action, Data... data) {

        this(action, Arrays.asList(data));
    }

    /**
     * 数据包输出玩家信息构造函数
     *
     * @param action 交互类型
     * @param dataList 数据列表
     */
    public PacketPlayOutPlayerInfo(Action action, List<Data> dataList) {

        this.action = new SimpleObjectProperty<>(action);
        this.dataList = new DataListProperty(dataList);
    }

    /**
     * 获取此数据包输出玩家信息的交互类型属性
     *
     * @return 交互类型属性
     */
    public ObjectProperty<Action> actionProperty() {

        return action;
    }

    /**
     * 获取此数据包输出玩家信息的数据列表属性
     *
     * @return 数据列表属性
     */
    public DataListProperty dataListProperty() {

        return dataList;
    }

    @Override
    @SuppressWarnings({ "deprecation", "unchecked" })
    protected boolean sendPacket(Player... players) throws Exception {

        // 触发事件判断如果为 true 则阻止发送
        if(super.fireEvent(this, players))
            return true;

        Action action = actionProperty().get();
        List<Data> dataList = dataListProperty().get();
        Validate.notNull(action, "The action object is null.");
        Validate.notNull(dataList, "The data list object is null.");

        try {
            // 直接用反射设置字段方式发送, 字段 EnumPlayerInfoAction, List
            Object packet = instantiateObject(CLASS_PACKETPLAYOUTPLAYERINFO);
            Object nmsAction = METHOD_VALUEOF.invoke(null, action.name());
            // 貌似第二个字段 List 是终态, 所以获取到后只能进行 add 操作
            Field field = CLASS_PACKETPLAYOUTPLAYERINFO.getDeclaredFields()[1];
            field.setAccessible(true);
            List list = (List) field.get(packet);
            // 然后将 Data 数据转换为 PlayerInfoData 并添加到 List 内
            // 构造函数的参数为 GameProfile, int, EnumGamemode, IChatBaseComponent
            for(Data data : dataList) {
                // 循环遍历 Data 列表进行转换到 PlayerInfoData 然后添加到 List 内
                Object nmsPlayerListName = METHOD_CHARSERIALIZER_A.invoke(null, "{\"text\":\"" + data.playerListName.get() + "\"}");
                Object nmsGamemode = METHOD_GETBYID.invoke(null, data.gameMode.get().getValue()); // @SuppressWarnings("deprecation")
                Object nmsData = null;
                // 不是很懂 bukkit，反编译明明是四个参数, 但是传入后说找不到构造函数
                // 打印 PlayerInfoData 类的构造函数后发现多出了个 PacketPlayOutPlayerInfo 参数
                // 所以为了成功只能 try 四个参数的，如果异常再使用五个参数的
                try {
                    // 先使用四个参数的方式
                    Object[] values = { data.gameProfile.get(), data.ping.get(), nmsGamemode, nmsPlayerListName };
                    nmsData = instantiateObject(CLASS_PACKETPLAYOUTPLAYERINFO_PLAYERINFODATA, values);
                } catch (Exception e1) {
                    // 如果异常说明需要五个参数, 那么使用此方式
                    Object[] values = { packet, data.gameProfile.get(), data.ping.get(), nmsGamemode, nmsPlayerListName };
                    nmsData = instantiateObject(CLASS_PACKETPLAYOUTPLAYERINFO_PLAYERINFODATA, values);
                }
                list.add(nmsData);
            }
            // 之后设置第一个字段的 Action 值就行了, List 已经 add 了
            setFieldAccessibleAndValueSend(players, 1, CLASS_PACKETPLAYOUTPLAYERINFO, packet, nmsAction);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 否则前面的方式均不支持则返回 false 并抛出不支持运算异常
        return false;
    }

    /**
     * <h1>Action</h1>
     * 数据包输出玩家信息交互类型
     *
     * @version 1.0
     * @author Month_Light
     */
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
     * <h1>Data</h1>
     * 数据包输出玩家信息数据类
     *
     * @version 1.0
     * @author Month_Light
     */
    public final static class Data {

        private IntegerProperty ping;
        private ObjectProperty<GameProfile> gameProfile;
        private ObjectProperty<GameMode> gameMode;
        private StringProperty playerListName;

        /**
         * 数据包输出玩家信息数据构造函数
         *
         * @param ping 延迟
         * @param gameProfile 游戏简介
         * @param gameMode 游戏模式
         * @param playerListName 玩家列表名称
         */
        public Data(int ping, GameProfile gameProfile, GameMode gameMode, String playerListName) {

            this.ping = new SimpleIntegerProperty(ping);
            this.gameProfile = new SimpleObjectProperty<>(gameProfile);
            this.gameMode = new SimpleObjectProperty<>(gameMode);
            this.playerListName = new SimpleStringProperty(playerListName);
        }

        /**
         * 获取此数据包输出玩家信息数据的延迟属性
         *
         * @return 延迟属性
         */
        public IntegerProperty pingProperty() {

            return ping;
        }

        /**
         * 获取此数据包输出玩家信息数据的游戏简介属性
         *
         * @return 游戏简介属性
         */
        public ObjectProperty<GameProfile> gameProfileProperty() {

            return gameProfile;
        }

        /**
         * 获取此数据包输出玩家信息数据的游戏模式属性
         *
         * @return 游戏模式属性
         */
        public ObjectProperty<GameMode> gameModeProperty() {

            return gameMode;
        }

        /**
         * 获取此数据包输出玩家信息数据的玩家列表名称属性
         *
         * @return 玩家列表名称属性
         */
        public StringProperty playerListNameProperty() {

            return playerListName;
        }
    }

    /**
     * <h1>DataListProperty</h1>
     * 数据包输出玩家信息数据列表属性类
     *
     * @version 1.0
     * @author Month_Light
     */
    public final static class DataListProperty extends ObjectPropertyBase<List<Data>> {

        /**
         * 数据包输出玩家信息数据列表属性构造函数
         */
        public DataListProperty() {

            super(new ArrayList<>());
        }

        /**
         * 数据包输出玩家信息数据列表属性构造函数
         *
         * @param dataList 数据列表
         */
        public DataListProperty(List<Data> dataList) {

            super(dataList);
        }

        /**
         * 将此数据包输出玩家信息数据列表添加数据
         *
         * @param data 数据
         */
        public void add(Data data) {

            get().add(data);
        }

        /**
         * 将此数据包输出玩家信息数据列表添加数据
         *
         * @param players 玩家
         */
        public void add(Player... players) {

            Validate.notNull(players, "The players object is null.");

            for(Player player : players)
                add(player);
        }

        /**
         * 将此数据包输出玩家信息数据列表添加数据
         *
         * @param player 玩家
         */
        public void add(Player player) {

            GameProfile gameProfile = PlayerManager.getProfile(player);
            int ping = PlayerLibraryFactorys.nmsPlayer().getPing(player.getName());
            add(ping, gameProfile, player.getGameMode(), player.getPlayerListName());
        }

        /**
         * 将此数据包输出玩家信息数据列表添加数据
         *
         * @param gameProfile 游戏简介
         * @param gameMode 游戏模式
         * @param playerListName 玩家列表名称
         */
        public void add(GameProfile gameProfile, GameMode gameMode, String playerListName) {

            add(0, gameProfile, gameMode, playerListName);
        }

        /**
         * 将此数据包输出玩家信息数据列表添加数据
         *
         * @param ping 延迟
         * @param gameProfile 游戏简介
         * @param gameMode 游戏模式
         * @param playerListName 玩家列表名称
         */
        public void add(int ping, GameProfile gameProfile, GameMode gameMode, String playerListName) {

            get().add(new Data(ping, gameProfile, gameMode, playerListName));
        }

        /**
         * 获取此数据包输出玩家信息数据列表指定索引的数据
         *
         * @param index 索引
         * @return 数据
         */
        public Data get(int index) {

            return get().get(index);
        }

        /**
         * 获取此数据包输出玩家信息数据列表第一个索引的数据
         *
         * @return 数据
         */
        public Data getFirst() {

            return get(0);
        }

        /**
         * 获取此数据包输出玩家信息数据列表最后一个索引的数据
         *
         * @return 数据
         */
        public Data getLast() {

            return get(size() - 1);
        }

        /**
         * 获取此数据包输出玩家信息数据列表的大小
         *
         * @return 大小
         */
        public int size() {

            return get().size();
        }
    }
}
