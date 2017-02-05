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


package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.nms.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.property.*;
import org.bukkit.entity.Player;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutOpenWindow</h1>
 * 数据包输出打开窗口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link com.minecraft.moonlake.api.packet.wrapper.PacketPlayOutOpenWindow}
 */
@Deprecated
public class PacketPlayOutOpenWindow extends PacketAbstract<PacketPlayOutOpenWindow> {

    private final static Class<?> CLASS_PACKETPLAYOUTOPENWINDOW;
    private final static Class<?> CLASS_CHATMESSAGE;

    static {

        try {

            CLASS_PACKETPLAYOUTOPENWINDOW = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutOpenWindow");
            CLASS_CHATMESSAGE = PackageType.MINECRAFT_SERVER.getClass("ChatMessage");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out open window reflect raw initialize exception.", e);
        }
    }

    private IntegerProperty windowId;
    private ObjectProperty<WindowType> windowType;
    private StringProperty windowTitle;
    private IntegerProperty slotCount;
    private IntegerProperty entityHorseId;

    /**
     * 数据包输出打开窗口类构造函数
     *
     * @param windowId 窗口 Id
     * @param windowType 窗口类型
     * @param windowTitle 窗口标题
     */
    public PacketPlayOutOpenWindow(int windowId, WindowType windowType, String windowTitle) {

        this(windowId, windowType, windowTitle, 0);
    }

    /**
     * 数据包输出打开窗口类构造函数
     *
     * @param windowId 窗口 Id
     * @param windowType 窗口类型
     * @param windowTitle 窗口标题
     * @param slotCount 槽数量
     */
    public PacketPlayOutOpenWindow(int windowId, WindowType windowType, String windowTitle, int slotCount) {

        this.windowId = new SimpleIntegerProperty(windowId);
        this.windowType = new SimpleObjectProperty<>(windowType);
        this.windowTitle = new SimpleStringProperty(windowTitle);
        this.slotCount = new SimpleIntegerProperty(slotCount);
        this.entityHorseId = new SimpleIntegerProperty(0);
    }

    /**
     * 数据包输出打开窗口类构造函数
     *
     * @param windowId 窗口 Id
     * @param windowType 窗口类型
     * @param windowTitle 窗口标题
     * @param slotCount 槽数量
     * @param entityHorseId 实体马 Id (当窗口类型为 {@link WindowType#ENTITY_HORSE} 时有用)
     */
    public PacketPlayOutOpenWindow(int windowId, WindowType windowType, String windowTitle, int slotCount, int entityHorseId) {

        this(windowId, windowType, windowTitle, slotCount);

        this.entityHorseId.set(entityHorseId);
    }

    /**
     * 获取此数据包输出打开窗口的窗口 Id
     *
     * @return 窗口 Id
     */
    public IntegerProperty getWindowId() {

        return windowId;
    }

    /**
     * 获取此数据包输出打开窗口的窗口类型
     *
     * @return 窗口类型
     */
    public ObjectProperty<WindowType> getWindowType() {

        return windowType;
    }

    /**
     * 获取此数据包输出打开窗口的窗口标题
     *
     * @return 窗口标题
     */
    public StringProperty getWindowTitle() {

        return windowTitle;
    }

    /**
     * 获取此数据包输出打开窗口的槽数量
     *
     * @return 槽数量
     */
    public IntegerProperty getSlotCount() {

        return slotCount;
    }

    /**
     * 获取此数据包输出打开窗口的实体马 Id
     *
     * @return 实体马 Id
     */
    public IntegerProperty getEntityHorseId() {

        return entityHorseId;
    }

    @Override
    public void send(Player... players) throws PacketException {

        if(super.fireEvent(this, players)) return;

        try {

            Object nmsChatMessage = instantiateObject(CLASS_CHATMESSAGE, windowTitle.get(), new Object[] { });
            Object packet;

            if(windowType.get() == WindowType.ENTITY_HORSE) {

                packet = instantiateObject(CLASS_PACKETPLAYOUTOPENWINDOW, windowId.get(), windowType.get().toString(), nmsChatMessage, slotCount.get(), entityHorseId.get());
            }
            else {

                packet = instantiateObject(CLASS_PACKETPLAYOUTOPENWINDOW, windowId.get(), windowType.get().toString(), nmsChatMessage, slotCount.get());
            }
            PacketReflect.get().send(players, packet);
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out open window send exception.", e);
        }
    }

    /**
     * <h1>WindowType</h1>
     * 窗口类型（详细doc待补充...）
     *
     * @version 1.0
     * @author Month_Light
     */
    public enum WindowType {

        // 来自 wiki.vg
        // 链接: http://wiki.vg/Inventory
        ///

        /**
         * 窗口类型: 容器
         */
        CONTAINER,
        /**
         * 窗口类型: 箱子
         */
        CHEST,
        /**
         * 窗口类型: 合成台
         */
        CRAFTING_TABLE,
        /**
         * 窗口类型: 熔炉
         */
        FURNACE,
        /**
         * 窗口类型: 发射器
         */
        DISPENSER,
        /**
         * 窗口类型: 附魔台
         */
        ENCHANTING_TABLE,
        /**
         * 窗口类型: 酿造台
         */
        BREWING_STAND,
        /**
         * 窗口类型: 村民
         */
        VILLAGER,
        /**
         * 窗口类型: 信标
         */
        BEACON,
        /**
         * 窗口类型: 铁砧
         */
        ANVIL,
        /**
         * 窗口类型: 漏斗
         */
        HOPPER,
        /**
         * 窗口类型: 投掷器
         */
        DROPPER,
        /**
         * 窗口类型: 实体马
         */
        ENTITY_HORSE,
        ;

        /**
         * 窗口类型类构造函数
         */
        WindowType() {
        }

        @Override
        public String toString() {

            if(this == ENTITY_HORSE) {

                return "EntityHorse";
            }
            return "minecraft:" + name().toLowerCase();
        }
    }
}
