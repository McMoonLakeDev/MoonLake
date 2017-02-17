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

import com.minecraft.moonlake.api.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.property.*;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutOpenWindow</h1>
 * 数据包输出打开窗口（详细doc待补充...）
 *
 * @version 2.0
 * @author Month_Light
 */
public class PacketPlayOutOpenWindow extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTOPENWINDOW;
    private final static Class<?> CLASS_CHATMESSAGE;

    static {

        try {

            CLASS_PACKETPLAYOUTOPENWINDOW = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutOpenWindow");
            CLASS_CHATMESSAGE = PackageType.MINECRAFT_SERVER.getClass("ChatMessage");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The net.minecraft.server packet play out open window reflect raw initialize exception.", e);
        }
    }

    private IntegerProperty windowId;
    private ObjectProperty<WindowType> windowType;
    private StringProperty windowTitle;
    private IntegerProperty slotCount;
    private IntegerProperty entityHorseId;

    public PacketPlayOutOpenWindow() {

        this(0, null, null);
    }

    public PacketPlayOutOpenWindow(int windowId, WindowType windowType, String windowTitle) {

        this(windowId, windowType, windowTitle, 0);
    }

    public PacketPlayOutOpenWindow(int windowId, WindowType windowType, String windowTitle, int slotCount) {

        this(windowId, windowType, windowTitle, slotCount, 0);
    }

    public PacketPlayOutOpenWindow(int windowId, WindowType windowType, String windowTitle, int slotCount, int entityHorseId) {

        this.windowId = new SimpleIntegerProperty(windowId);
        this.windowType = new SimpleObjectProperty<>(windowType);
        this.windowTitle = new SimpleStringProperty(windowTitle);
        this.slotCount = new SimpleIntegerProperty(slotCount);
        this.entityHorseId = new SimpleIntegerProperty(entityHorseId);
    }

    public IntegerProperty windowIdProperty() {

        return windowId;
    }

    public ObjectProperty<WindowType> windowTypeProperty() {

        return windowType;
    }

    public StringProperty windowTitleProperty() {

        return windowTitle;
    }

    public IntegerProperty slotCountProperty() {

        return slotCount;
    }

    public IntegerProperty entityHorseIdProperty() {

        return entityHorseId;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        String windowTitle = windowTitleProperty().get();
        WindowType windowType = windowTypeProperty().get();
        Validate.notNull(windowTitle, "The window title object is null.");
        Validate.notNull(windowType, "The window type object is null.");

        try {
            // 先用调用 NMS 的 PacketPlayOutOpenWindow 构造函数
            // 参数 int, String, IChatBaseComponent, int
            // 参数 int, String, IChatBaseComponent, int, int 窗口类型为实体马
            // 进行反射实例发送
            Object nmsTitle = instantiateObject(CLASS_CHATMESSAGE, windowTitle);
            Object packet;

            if(windowType == WindowType.ENTITY_HORSE)
                packet = instantiateObject(CLASS_PACKETPLAYOUTOPENWINDOW, windowId.get(), windowType.toString(), nmsTitle, slotCount.get(), entityHorseId.get());
            else
                packet = instantiateObject(CLASS_PACKETPLAYOUTOPENWINDOW, windowId.get(), windowType.toString(), nmsTitle, slotCount.get());

            sendPacket(players, packet);
            return true;

        } catch (Exception e) {
            // 如果异常了说明 NMS 的 PacketPlayOutNamedEntitySpawn 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 判断字段数量等于 5 个的话就是有此方式
                // 这两个字段分别对应 int, String, IChatBaseComponent, int, int 的 5 个属性
                Object nmsTitle = instantiateObject(CLASS_CHATMESSAGE, windowTitle);
                Object packet = instantiateObject(CLASS_PACKETPLAYOUTOPENWINDOW);

                if(windowType == WindowType.ENTITY_HORSE) {
                    Object[] values = { windowId.get(), windowType.toString(), nmsTitle, slotCount.get(), entityHorseId.get() };
                    setFieldAccessibleAndValueSend(players, 5, CLASS_PACKETPLAYOUTOPENWINDOW, packet, values);
                } else {
                    Object[] values = { windowId.get(), windowType.toString(), nmsTitle, slotCount.get() };
                    setFieldAccessibleAndValueSend(players, 5, CLASS_PACKETPLAYOUTOPENWINDOW, packet, values);
                }
                return true;

            } catch (Exception e1) {
            }
        }
        // 否则前面的方式均不支持则返回 false 并抛出不支持运算异常
        return false;
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
