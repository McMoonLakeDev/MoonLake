package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.nms.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.property.*;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * Created by MoonLake on 2016/9/29.
 */
public class PacketPlayOutExplosion extends PacketAbstract<PacketPlayOutExplosion> {

    private final static Class<?> CLASS_PACKETPLAYOUTEXPLOSION;
    private final static Class<?> CLASS_BLOCKPOSITION;
    private final static Class<?> CLASS_VEC3D;

    static {

        try {

            CLASS_PACKETPLAYOUTEXPLOSION = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutExplosion");
            CLASS_BLOCKPOSITION = PackageType.MINECRAFT_SERVER.getClass("BlockPosition");
            CLASS_VEC3D = PackageType.MINECRAFT_SERVER.getClass("Vec3D");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out explosion reflect raw initialize exception.", e);
        }
    }

    private DoubleProperty x;
    private DoubleProperty y;
    private DoubleProperty z;
    private FloatProperty radius;
    private List<BlockPosition> records;
    private Vector vector;

    @Deprecated
    public PacketPlayOutExplosion() {

        this(0d, 0d, 0d);
    }

    public PacketPlayOutExplosion(double x, double y, double z) {

        this(x, y, z, 0f, null, null);
    }

    public PacketPlayOutExplosion(Location location, float radius) {

        this(location.getX(), location.getY(), location.getZ(), radius, null, null);
    }

    public PacketPlayOutExplosion(Location location, float radius, List<BlockPosition> records, Vector vector) {

        this(location.getX(), location.getY(), location.getZ(), radius, records, vector);
    }

    public PacketPlayOutExplosion(double x, double y, double z, float radius) {

        this(x, y, z, radius, null, null);
    }

    public PacketPlayOutExplosion(double x, double y, double z, float radius, List<BlockPosition> records, Vector vector) {

        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        this.z = new SimpleDoubleProperty(z);
        this.radius = new SimpleFloatProperty(radius);
        this.records = records == null ? new ArrayList<>() : records;
        this.vector = vector == null ? new Vector() : vector;
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

    public FloatProperty getRadius() {

        return radius;
    }

    public List<BlockPosition> getRecords() {

        return records;
    }

    public Vector getVector() {

        return vector;
    }

    public void setRecords(List<BlockPosition> records) {

        this.records = records == null ? new ArrayList<>() : records;
    }

    public void setVector(Vector vector) {

        this.vector = vector == null ? new Vector() : vector;
    }

    @Override
    public void send(Player... players) throws PacketException {

        try {

            List<Object> nmsBlockPosition = new ArrayList<>();

            if(records != null && records.size() > 0) {

                for(final BlockPosition blockPosition : records) {

                    Object object = instantiateObject(CLASS_BLOCKPOSITION, blockPosition.x.get(), blockPosition.y.get(), blockPosition.z.get());

                    nmsBlockPosition.add(object);
                }
            }
            Object nmsVec3D = instantiateObject(CLASS_VEC3D, vector.getX(), vector.getY(), vector.getZ());
            Object packet = instantiateObject(CLASS_PACKETPLAYOUTEXPLOSION, x.get(), y.get(), z.get(), radius.get(), nmsBlockPosition, nmsVec3D);

            PacketReflect.get().send(players, packet);
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out explosion send exception.", e);
        }
    }

    public static class BlockPosition {

        private final ReadOnlyIntegerProperty x;
        private final ReadOnlyIntegerProperty y;
        private final ReadOnlyIntegerProperty z;

        public BlockPosition() {

            this(0, 0, 0);
        }

        public BlockPosition(double x, double y, double z) {

            this((int) x, (int) y, (int) z);
        }

        public BlockPosition(int x, int y, int z) {

            this.x = new SimpleIntegerProperty(x);
            this.y = new SimpleIntegerProperty(y);
            this.z = new SimpleIntegerProperty(z);
        }

        public ReadOnlyIntegerProperty getX() {

            return x;
        }

        public ReadOnlyIntegerProperty getY() {

            return y;
        }

        public ReadOnlyIntegerProperty getZ() {

            return z;
        }
    }
}
