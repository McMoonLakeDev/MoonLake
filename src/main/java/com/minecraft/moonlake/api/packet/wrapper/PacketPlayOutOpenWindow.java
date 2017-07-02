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
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.property.*;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.ConstructorAccessor;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

/**
 * <h1>PacketPlayOutOpenWindow</h1>
 * 数据包输出打开窗口（详细doc待补充...）
 *
 * @version 2.0
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketPlayOutBukkit
 */
public class PacketPlayOutOpenWindow extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTOPENWINDOW;
    private static volatile ConstructorAccessor<?> packetPlayOutOpenWindowVoidConstructor;
    private static volatile ConstructorAccessor<?> packetPlayOutOpenWindowHorseConstructor;
    private static volatile ConstructorAccessor<?> packetPlayOutOpenWindowConstructor;

    static {

        CLASS_PACKETPLAYOUTOPENWINDOW = MinecraftReflection.getMinecraftClass("PacketPlayOutOpenWindow");
        Class<?> iChatBaseComponentClass = MinecraftReflection.getIChatBaseComponentClass();
        packetPlayOutOpenWindowVoidConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTOPENWINDOW);
        packetPlayOutOpenWindowConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTOPENWINDOW, int.class, String.class, iChatBaseComponentClass, int.class);
        packetPlayOutOpenWindowHorseConstructor = Accessors.getConstructorAccessor(CLASS_PACKETPLAYOUTOPENWINDOW, int.class, String.class, iChatBaseComponentClass, int.class, int.class);
    }

    private IntegerProperty windowId;
    private ObjectProperty<WindowType> windowType;
    private StringProperty windowTitle;
    private IntegerProperty slotCount;
    private IntegerProperty entityHorseId;

    /**
     * 数据包输出打开窗口构造函数
     */
    public PacketPlayOutOpenWindow() {

        this(0, null, null);
    }

    /**
     * 数据包输出打开窗口构造函数
     *
     * @param windowId 窗口 Id
     * @param windowType 窗口类型
     * @param windowTitle 窗口标题
     */
    public PacketPlayOutOpenWindow(int windowId, WindowType windowType, String windowTitle) {

        this(windowId, windowType, windowTitle, 0);
    }

    /**
     * 数据包输出打开窗口构造函数
     *
     * @param windowId 窗口 Id
     * @param windowType 窗口类型
     * @param windowTitle 窗口标题
     * @param slotCount 槽位数量
     */
    public PacketPlayOutOpenWindow(int windowId, WindowType windowType, String windowTitle, int slotCount) {

        this(windowId, windowType, windowTitle, slotCount, 0);
    }

    /**
     * 数据包输出打开窗口构造函数
     *
     * @param windowId 窗口 Id
     * @param windowType 窗口类型
     * @param windowTitle 窗口标题
     * @param slotCount 槽位数量
     * @param entityHorseId 实体马 Id
     */
    public PacketPlayOutOpenWindow(int windowId, WindowType windowType, String windowTitle, int slotCount, int entityHorseId) {

        this.windowId = new SimpleIntegerProperty(windowId);
        this.windowType = new SimpleObjectProperty<>(windowType);
        this.windowTitle = new SimpleStringProperty(windowTitle);
        this.slotCount = new SimpleIntegerProperty(slotCount);
        this.entityHorseId = new SimpleIntegerProperty(entityHorseId);
    }

    /**
     * 获取此数据包输出打开窗口的窗口 Id 属性
     *
     * @return 窗口 Id 属性
     */
    public IntegerProperty windowIdProperty() {

        return windowId;
    }

    /**
     * 获取此数据包输出打开窗口的窗口类型属性
     *
     * @return 窗口类型属性
     */
    public ObjectProperty<WindowType> windowTypeProperty() {

        return windowType;
    }

    /**
     * 获取此数据包输出打开窗口的窗口标题属性
     *
     * @return 窗口标题属性
     */
    public StringProperty windowTitleProperty() {

        return windowTitle;
    }

    /**
     * 获取此数据包输出打开窗口的槽位数量属性
     *
     * @return 槽位数量属性
     */
    public IntegerProperty slotCountProperty() {

        return slotCount;
    }

    /**
     * 获取此数据包输出打开窗口的实体马 Id 属性
     *
     * @return 实体马 Id 属性
     */
    public IntegerProperty entityHorseIdProperty() {

        return entityHorseId;
    }

    @Override
    public Class<?> getPacketClass() {

        return CLASS_PACKETPLAYOUTOPENWINDOW;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        // 触发事件判断如果为 true 则阻止发送
        if(super.fireEvent(this, players))
            return true;

        String windowTitle = windowTitleProperty().get();
        WindowType windowType = windowTypeProperty().get();
        Validate.notNull(windowTitle, "The window title object is null.");
        Validate.notNull(windowType, "The window type object is null.");

        try {
            MinecraftReflection.sendPacket(players, packet());
            return true;
        } catch (Exception e) {
            printException(e);
        }
        // 否则前面的方式均不支持则返回 false 并抛出不支持运算异常
        return false;
    }

    @Nullable
    @Override
    public Object packet() {

        String windowTitle = windowTitleProperty().get();
        WindowType windowType = windowTypeProperty().get();
        Validate.notNull(windowTitle, "The window title object is null.");
        Validate.notNull(windowType, "The window type object is null.");

        try {
            // 先用调用 NMS 的 PacketPlayOutOpenWindow 构造函数
            // 参数 int, String, IChatBaseComponent, int
            // 参数 int, String, IChatBaseComponent, int, int 窗口类型为实体马
            // 进行反射实例发送
            Object title = MinecraftReflection.getChatMessage(windowTitle);

            if(windowType == WindowType.ENTITY_HORSE)
                return packetPlayOutOpenWindowHorseConstructor.invoke(windowId.get(), windowType.toString(), title, slotCount.get(), entityHorseId.get());
            else
                return packetPlayOutOpenWindowConstructor.invoke(windowId.get(), windowType.toString(), title, slotCount.get());

        } catch (Exception e) {
            printException(e);
            // 如果异常了说明 NMS 的 PacketPlayOutNamedEntitySpawn 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 判断字段数量等于 5 个的话就是有此方式
                // 这两个字段分别对应 int, String, IChatBaseComponent, int, int 的 5 个属性
                Object title = MinecraftReflection.getChatMessage(windowTitle);
                Object packet = packetPlayOutOpenWindowVoidConstructor.invoke();

                if(windowType == WindowType.ENTITY_HORSE) {
                    Object[] values = { windowId.get(), windowType.toString(), title, slotCount.get(), entityHorseId.get() };
                    return setFieldAccessibleAndValueGet(5, CLASS_PACKETPLAYOUTOPENWINDOW, packet, values);
                } else {
                    Object[] values = { windowId.get(), windowType.toString(), title, slotCount.get() };
                    return setFieldAccessibleAndValueGet(4, CLASS_PACKETPLAYOUTOPENWINDOW, packet, values);
                }
            } catch (Exception e1) {
                printException(e1);
            }
        }
        return null;
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
