package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.nms.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.property.IntegerProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import org.bukkit.entity.Player;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutHeldItemSlot</h1>
 * 数据包输出手持物品索引（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class PacketPlayOutHeldItemSlot extends PacketAbstract<PacketPlayOutHeldItemSlot> {

    private final static Class<?> CLASS_PACKETPLAYOUTHELDITEMSLOT;

    static {

        try {

            CLASS_PACKETPLAYOUTHELDITEMSLOT = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutHeldItemSlot");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out held item slot reflect raw initialize exception.", e);
        }
    }

    private IntegerProperty heldItemSlot;

    @Deprecated
    public PacketPlayOutHeldItemSlot() {

        this(0);
    }

    public PacketPlayOutHeldItemSlot(int heldItemSlot) {

        this.heldItemSlot = new SimpleIntegerProperty(heldItemSlot);
    }

    public IntegerProperty getHeldItemSlot() {

        return heldItemSlot;
    }

    @Override
    public void send(Player... players) throws PacketException {

        try {

            Object packet = instantiateObject(CLASS_PACKETPLAYOUTHELDITEMSLOT, heldItemSlot.get());

            PacketReflect.get().send(players, packet);
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out held item slot send exception.", e);
        }
    }
}
