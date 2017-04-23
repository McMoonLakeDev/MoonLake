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
 
 
package com.minecraft.moonlake.api.anvil;

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.nms.exception.NMSException;
import com.minecraft.moonlake.api.packet.wrapper.PacketPlayOutOpenWindow;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.reflect.accessors.Accessors;
import com.minecraft.moonlake.reflect.accessors.FieldAccessor;
import com.minecraft.moonlake.reflect.accessors.MethodAccessor;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.lang.reflect.Constructor;

/**
 * <h1>AnvilWindowReflect</h1>
 * 铁砧窗口反射实现类
 *
 * @version 1.1
 * @author Month_Light
 */
class AnvilWindowReflect {

    private volatile Class<?> CLASS_ANVILWINDOW;
    private volatile Class<?> CLASS_CRAFTINVENTORYVIEW;
    private volatile Class<?> CLASS_CONTAINER;
    private volatile Class<?> CLASS_ICRAFTING;
    private volatile Constructor<?> anvilWindowConstructor;
    private volatile MethodAccessor anvilWindowGetBukkitViewMethod;
    private volatile MethodAccessor craftInventoryViewGetTopInventoryMethod;
    private volatile MethodAccessor entityPlayerNextContainerCounterMethod;
    private volatile MethodAccessor anvilWindowAddSlotListenerMethod;
    private volatile FieldAccessor entityHumanActiveContainerField;
    private volatile FieldAccessor containerWindowIdField;

    private static AnvilWindowReflect anvilWindowReflectInstance;

    /**
     * 铁砧窗口反射实现类构造函数
     *
     * @throws NMSException 如果初始化错误则抛出异常
     */
    private AnvilWindowReflect() throws NMSException {

        try {
            CLASS_ANVILWINDOW = Class.forName(AnvilWindowExpression.class.getName() + "_" + MoonLakeAPI.currentBukkitVersionString());
            anvilWindowConstructor = CLASS_ANVILWINDOW.getConstructor(Player.class, AnvilWindowExpression.class);
        } catch (Exception e) {
            throw new NMSException("The nms anvil window reflect raw initialize exception.", e);
        }
        CLASS_CRAFTINVENTORYVIEW = MinecraftReflection.getCraftBukkitClass("inventory.CraftInventoryView");
        CLASS_CONTAINER = MinecraftReflection.getMinecraftClass("Container");
        CLASS_ICRAFTING = MinecraftReflection.getMinecraftClass("ICrafting");
        Class<?> entityPlayerClass = MinecraftReflection.getMinecraftEntityPlayerClass();
        Class<?> entityHumanClass = MinecraftReflection.getMinecraftEntityHumanClass();
        anvilWindowGetBukkitViewMethod = Accessors.getMethodAccessor(CLASS_ANVILWINDOW, "getBukkitView");
        craftInventoryViewGetTopInventoryMethod = Accessors.getMethodAccessor(CLASS_CRAFTINVENTORYVIEW, "getTopInventory");
        entityPlayerNextContainerCounterMethod = Accessors.getMethodAccessor(entityPlayerClass, "nextContainerCounter");
        anvilWindowAddSlotListenerMethod = Accessors.getMethodAccessor(CLASS_ANVILWINDOW, "addSlotListener", CLASS_ICRAFTING);
        entityHumanActiveContainerField = Accessors.getFieldAccessor(entityHumanClass, "activeContainer", true);
        containerWindowIdField = Accessors.getFieldAccessor(CLASS_CONTAINER, "windowId", true);
    }

    /**
     * 获取 AnvilWindowReflect 对象
     *
     * @return AnvilWindowReflect
     */
    public static AnvilWindowReflect get() {

        if(anvilWindowReflectInstance == null) {

            anvilWindowReflectInstance = new AnvilWindowReflect();
        }
        return anvilWindowReflectInstance;
    }

    /**
     * 将指定铁砧窗口打开给指定玩家
     *
     * @param player 玩家
     * @param anvilWindow 铁砧窗口
     * @throws IllegalArgumentException 如果玩家对象为 {@code null} 则抛出异常
     * @throws IllegalArgumentException 如果铁砧窗口对象为 {@code null} 则抛出异常
     * @throws NMSException 如果打开错误则抛出异常
     */
    public void openAnvil(Player player, AnvilWindowExpression anvilWindow) throws NMSException {

        Validate.notNull(player, "The player object is null.");
        Validate.notNull(anvilWindow, "The anvil window object is null.");

        try {

            Object entityPlayer = MinecraftReflection.getEntityPlayer(player);

            if(anvilWindow.anvilInventory == null) {

                Object nmsAnvil = anvilWindowConstructor.newInstance(player, anvilWindow);
                Object cbBukkitView = anvilWindowGetBukkitViewMethod.invoke(nmsAnvil);
                anvilWindow.anvilInventory = (Inventory) craftInventoryViewGetTopInventoryMethod.invoke(cbBukkitView);
                anvilWindow.anvilInventoryHandle = nmsAnvil;
            }
            Object nmsAnvil = anvilWindow.anvilInventoryHandle;
            int id = (int) entityPlayerNextContainerCounterMethod.invoke(entityPlayer);
            new PacketPlayOutOpenWindow(id, PacketPlayOutOpenWindow.WindowType.ANVIL, "Repairing", 0).send(player);

            entityHumanActiveContainerField.set(entityPlayer, nmsAnvil);
            containerWindowIdField.set(entityHumanActiveContainerField.get(entityPlayer), id);
            anvilWindowAddSlotListenerMethod.invoke(entityHumanActiveContainerField.get(entityPlayer), entityPlayer);
        }
        catch (Exception e) {

            throw new NMSException("The open nms anvil window exception.", e);
        }
    }

    public String callAnvilInputEvent(Player player, AnvilWindowExpression anvilWindow, String input) {

        if(anvilWindow.getInputEventHandle() == null)
            return input;

        // 铁砧窗口的输入事件处理器不为 null 则处理
        AnvilWindowInputEvent awie = new AnvilWindowInputEvent(player, anvilWindow, input);
        anvilWindow.getInputEventHandle().execute(awie);

        if(!awie.isCancelled())
            return awie.getInput();

        return null;
    }
}
