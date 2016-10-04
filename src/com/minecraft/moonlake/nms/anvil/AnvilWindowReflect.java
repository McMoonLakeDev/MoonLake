package com.minecraft.moonlake.nms.anvil;

import com.minecraft.moonlake.nms.exception.NMSException;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * Created by MoonLake on 2016/10/3.
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

    private AnvilWindowReflect() throws NMSException {

        try {

            CLASS_ANVILWINDOW = Class.forName(AnvilWindow.class.getName() + "_" + getServerVersion());
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

    public static AnvilWindowReflect get() {

        if(anvilWindowReflectInstance == null) {

            anvilWindowReflectInstance = new AnvilWindowReflect();
        }
        return anvilWindowReflectInstance;
    }

    public void openAnvil(Player player, AnvilWindow anvilWindow) throws NMSException {

        Validate.notNull(player, "The player object is null.");

        try {

            Object nmsPlayer = METHOD_GETHANDLE.invoke(player);

            if(anvilWindow.anvilInventory == null) {

                Object nmsAnvil = instantiateObject(CLASS_ANVILWINDOW, nmsPlayer);
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
}
