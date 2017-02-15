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

import com.minecraft.moonlake.nms.exception.NMSException;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>AnvilWindowReflect</h1>
 * 铁砧窗口反射实现类
 *
 * @version 1.0
 * @author Month_Light
 */
class AnvilWindowReflect {

    private Class<?> CLASS_ANVILWINDOW;
    private Class<?> CLASS_CRAFTPLAYER;
    private Class<?> CLASS_ENTITYPLAYER;
    private Class<?> CLASS_ENTITYHUMAN;
    private Class<?> CLASS_CRAFTINVENTORYVIEW;
    private Class<?> CLASS_PACKET;
    private Class<?> CLASS_CHATMESSAGE;
    private Class<?> CLASS_PLAYERCONNECTION;
    private Class<?> CLASS_PACKETPLAYOUTOPENWINDOW;
    private Class<?> CLASS_CONTAINER;
    private Class<?> CLASS_ICRAFTING;
    private Constructor<?> CONSTRUCTOR_ANVILWINDOW;
    private Method METHOD_GETHANDLE;
    private Method METHOD_GETBUKKITVIEW;
    private Method METHOD_GETTOPINVENTORY;
    private Method METHOD_NEXTCONTAINERCOUNTER;
    private Method METHOD_SENDPACKET;
    private Method METHOD_ADDSLOTLISTENER;
    private Field FIELD_PLAYERCONNECTION;
    private Field FIELD_ACTIVECONTAINER;
    private Field FIELD_WINDOWID;

    private static AnvilWindowReflect anvilWindowReflectInstance;

    /**
     * 铁砧窗口反射实现类构造函数
     *
     * @throws NMSException 如果初始化错误则抛出异常
     */
    private AnvilWindowReflect() throws NMSException {

        try {

            CLASS_ANVILWINDOW = Class.forName(AnvilWindowExpression.class.getName() + "_" + getServerVersion());
            CLASS_CRAFTPLAYER = PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftPlayer");
            CLASS_ENTITYPLAYER = PackageType.MINECRAFT_SERVER.getClass("EntityPlayer");
            CLASS_ENTITYHUMAN = PackageType.MINECRAFT_SERVER.getClass("EntityHuman");
            CLASS_CRAFTINVENTORYVIEW = PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftInventoryView");
            CLASS_PACKET = PackageType.MINECRAFT_SERVER.getClass("Packet");
            CLASS_CHATMESSAGE = PackageType.MINECRAFT_SERVER.getClass("ChatMessage");
            CLASS_PLAYERCONNECTION = PackageType.MINECRAFT_SERVER.getClass("PlayerConnection");
            CLASS_PACKETPLAYOUTOPENWINDOW = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutOpenWindow");
            CLASS_CONTAINER = PackageType.MINECRAFT_SERVER.getClass("Container");
            CLASS_ICRAFTING = PackageType.MINECRAFT_SERVER.getClass("ICrafting");

            CONSTRUCTOR_ANVILWINDOW = CLASS_ANVILWINDOW.getConstructor(Player.class, AnvilWindowExpression.class);

            METHOD_GETHANDLE = getMethod(CLASS_CRAFTPLAYER, "getHandle");
            METHOD_GETBUKKITVIEW = getMethod(CLASS_ANVILWINDOW, "getBukkitView");
            METHOD_GETTOPINVENTORY = getMethod(CLASS_CRAFTINVENTORYVIEW, "getTopInventory");
            METHOD_NEXTCONTAINERCOUNTER = getMethod(CLASS_ENTITYPLAYER, "nextContainerCounter");
            METHOD_SENDPACKET = getMethod(CLASS_PLAYERCONNECTION, "sendPacket", CLASS_PACKET);
            METHOD_ADDSLOTLISTENER = getMethod(CLASS_ANVILWINDOW, "addSlotListener", CLASS_ICRAFTING);

            FIELD_PLAYERCONNECTION = getField(CLASS_ENTITYPLAYER, true, "playerConnection");
            FIELD_ACTIVECONTAINER = getField(CLASS_ENTITYHUMAN, true, "activeContainer");
            FIELD_WINDOWID = getField(CLASS_CONTAINER, true, "windowId");
        }
        catch (Exception e) {

            throw new NMSException("The nms anvil window reflect raw initialize exception.", e);
        }
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

            Object nmsPlayer = METHOD_GETHANDLE.invoke(player);

            if(anvilWindow.anvilInventory == null) {

                Object nmsAnvil = CONSTRUCTOR_ANVILWINDOW.newInstance(player, anvilWindow);
                Object cbBukkitView = METHOD_GETBUKKITVIEW.invoke(nmsAnvil);
                anvilWindow.anvilInventory = (Inventory) METHOD_GETTOPINVENTORY.invoke(cbBukkitView);
                anvilWindow.anvilInventoryHandle = nmsAnvil;
            }
            Object nmsAnvil = anvilWindow.anvilInventoryHandle;
            int id = (int) METHOD_NEXTCONTAINERCOUNTER.invoke(nmsPlayer);
            Object nmsChatMessage = instantiateObject(CLASS_CHATMESSAGE, "Repairing", new Object[] { });
            Object nmsPacket = instantiateObject(CLASS_PACKETPLAYOUTOPENWINDOW, id, "minecraft:anvil", nmsChatMessage, 0);

            METHOD_SENDPACKET.invoke(FIELD_PLAYERCONNECTION.get(nmsPlayer), nmsPacket);
            FIELD_ACTIVECONTAINER.set(nmsPlayer, nmsAnvil);
            FIELD_WINDOWID.set(FIELD_ACTIVECONTAINER.get(nmsPlayer), id);
            METHOD_ADDSLOTLISTENER.invoke(FIELD_ACTIVECONTAINER.get(nmsPlayer), nmsPlayer);
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
