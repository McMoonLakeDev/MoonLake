package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.nms.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import org.bukkit.entity.Player;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutNamedEntitySpawn</h1>
 * 数据包输出名称实体生成（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class PacketPlayOutNamedEntitySpawn extends PacketAbstract<PacketPlayOutNamedEntitySpawn> {

    private final static Class<?> CLASS_PACKETPLAYOUTNAMEDENTITYSPAWN;

    static {

        try {

            CLASS_PACKETPLAYOUTNAMEDENTITYSPAWN = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutNamedEntitySpawn");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out named entity spawn reflect raw initialize exception.", e);
        }
    }

    private ObjectProperty<Player> entity;

    @Deprecated
    public PacketPlayOutNamedEntitySpawn() {

        this(null);
    }

    public PacketPlayOutNamedEntitySpawn(Player entity) {

        this.entity = new SimpleObjectProperty<>(entity);
    }

    public ObjectProperty<Player> getEntity() {

        return entity;
    }

    @Override
    public void send(Player... players) throws PacketException {

        try {

            Object nmsEntity = PacketReflect.get().getNMSPlayer(entity.get());
            Object packet = instantiateObject(CLASS_PACKETPLAYOUTNAMEDENTITYSPAWN, nmsEntity);

            PacketReflect.get().send(players, packet);
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out named entity spawn send exception.", e);
        }
    }
}
