package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.nms.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * Created by MoonLake on 2016/9/29.
 */
public class PacketPlayOutEntityDestroy extends PacketAbstract<PacketPlayOutEntityDestroy> {

    private final static Class<?> CLASS_PACKETPLAYOUTENTITYDESTROY;

    static {

        try {

            CLASS_PACKETPLAYOUTENTITYDESTROY = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutEntityDestroy");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out entity destroy reflect raw initialize exception.");
        }
    }

    private ObjectProperty<int[]> entityIds;

    @Deprecated
    public PacketPlayOutEntityDestroy() {

        this(0);
    }

    public PacketPlayOutEntityDestroy(Entity... entities) {

        int index = 0;
        int[] entityIds = new int[entities.length];

        for(final Entity entity : entities) {

            entityIds[index++] = entity.getEntityId();
        }
        this.entityIds = new SimpleObjectProperty<>(entityIds);
    }

    public PacketPlayOutEntityDestroy(int... entityIds) {

        this.entityIds = new SimpleObjectProperty<>(entityIds);
    }

    public ObjectProperty<int[]> getEntityIds() {

        return entityIds;
    }

    @Override
    public void send(Player... players) throws PacketException {

        try {

            Object packet = instantiateObject(CLASS_PACKETPLAYOUTENTITYDESTROY, new Object[] { entityIds.get() });

            PacketReflect.get().send(players, packet);
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out entity destroy send exception.", e);
        }
    }
}
