package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.nms.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.property.*;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import static com.minecraft.moonlake.reflect.Reflect.PackageType;
import static com.minecraft.moonlake.reflect.Reflect.getMethod;
import static com.minecraft.moonlake.reflect.Reflect.instantiateObject;

/**
 * Created by MoonLake on 2016/9/29.
 */
public class PacketPlayOutPosition extends PacketAbstract<PacketPlayOutPosition> {

    private final static Class<?> CLASS_PACKETPLAYOUTPOSITION;
    private final static Class<?> CLASS_PACKETPLAYOUTPOSITION_ENUMPLAYERTELEPORTFLAGS;
    private final static Method METHOD_VALUEOF;

    static {

        try {

            CLASS_PACKETPLAYOUTPOSITION = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutPosition");
            CLASS_PACKETPLAYOUTPOSITION_ENUMPLAYERTELEPORTFLAGS = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutPosition$EnumPlayerTeleportFlags");
            METHOD_VALUEOF = getMethod(CLASS_PACKETPLAYOUTPOSITION_ENUMPLAYERTELEPORTFLAGS, "valueOf", String.class);
        }
        catch (Exception e) {

            throw new PacketInitializeException();
        }
    }

    private DoubleProperty x;
    private DoubleProperty y;
    private DoubleProperty z;
    private FloatProperty yaw;
    private FloatProperty pitch;
    private Set<PlayerTeleportFlag> flags;
    private IntegerProperty g;

    @Deprecated
    public PacketPlayOutPosition() {

        this(0d, 0d, 0d);
    }

    public PacketPlayOutPosition(Location location) {

        this(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), null, 0);
    }

    public PacketPlayOutPosition(Location location, Set<PlayerTeleportFlag> flags, int g) {

        this(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), flags, g);
    }

    public PacketPlayOutPosition(double x, double y, double z) {

        this(x, y, z, 0f, 0f);
    }

    public PacketPlayOutPosition(double x, double y, double z, float yaw, float pitch) {

        this(x, y, z, yaw, pitch, null, 0);
    }

    public PacketPlayOutPosition(double x, double y, double z, float yaw, float pitch, Set<PlayerTeleportFlag> flags) {

        this(x, y, z, yaw, pitch, flags, 0);
    }

    public PacketPlayOutPosition(double x, double y, double z, float yaw, float pitch, Set<PlayerTeleportFlag> flags, int g) {

        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        this.z = new SimpleDoubleProperty(z);
        this.yaw = new SimpleFloatProperty(yaw);
        this.pitch = new SimpleFloatProperty(pitch);
        this.flags = flags;
        this.g = new SimpleIntegerProperty(g);
    }

    public DoubleProperty getX() {

        return x;
    }

    public DoubleProperty getY() {

        return y;
    }

    public DoubleProperty getZ() {

        return z;
    }

    public FloatProperty getYaw() {

        return yaw;
    }

    public FloatProperty getPitch() {

        return pitch;
    }

    public Set<PlayerTeleportFlag> getFlags() {

        return flags;
    }

    public IntegerProperty getG() {

        return g;
    }

    @Override
    public void send(Player... players) throws PacketException {

        try {

            Set<Object> nmsFlags = new HashSet<>();

            if(flags != null) {

                for(final PlayerTeleportFlag flag : flags) {

                    nmsFlags.add(METHOD_VALUEOF.invoke(null, flag.name()));
                }
            }
            Object packet = instantiateObject(CLASS_PACKETPLAYOUTPOSITION, x.get(), y.get(), z.get(), yaw.get(), pitch.get(), nmsFlags, g.get());

            PacketReflect.get().send(players, packet);
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out position send exception.", e);
        }
    }

    public enum PlayerTeleportFlag {

        X(0),
        Y(1),
        Z(2),
        Y_ROT(3),
        X_ROT(4),;

        private final int id;

        PlayerTeleportFlag(int id) {

            this.id = id;
        }

        public int getId() {

            return id;
        }
    }
}
