package com.minecraft.moonlake.api.nms.packet;

import com.minecraft.moonlake.reflect.Reflect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by MoonLake on 2016/7/21.
 */
public class PacketPlayOutPosition extends PacketAbstract<PacketPlayOutPosition> {

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private Set<PlayerTeleportFlag> flags;
    private int g;

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

    public PacketPlayOutPosition(double x, double y, double z, float yaw, float pitch, Set<PlayerTeleportFlag> flags, int g) {

        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.flags = flags;
        this.g = g;
    }

    public double getX() {

        return x;
    }

    public void setX(double x) {

        this.x = x;
    }

    public double getY() {

        return y;
    }

    public void setY(double y) {

        this.y = y;
    }

    public double getZ() {

        return z;
    }

    public void setZ(double z) {

        this.z = z;
    }

    public float getYaw() {

        return yaw;
    }

    public void setYaw(float yaw) {

        this.yaw = yaw;
    }

    public float getPitch() {

        return pitch;
    }

    public void setPitch(float pitch) {

        this.pitch = pitch;
    }

    public Set<PlayerTeleportFlag> getFlags() {

        return flags;
    }

    public void setFlags(Set<PlayerTeleportFlag> flags) {

        this.flags = flags;
    }

    public int getG() {

        return g;
    }

    public void setG$(int g) {

        this.g = g;
    }

    /**
     * 将此数据包发送给指定玩家
     *
     * @param names 玩家名
     */
    @Override
    public void send(String... names) {

        try {

            Class<?> PacketPlayOutPosition = Reflect.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutPosition");

            Set<Object> instanceFlasg = new HashSet<>();

            if(flags != null) {

                Class<?> EnumPlayerTeleportFlags = Reflect.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutPosition$EnumPlayerTeleportFlags");
                Method valueOf = Reflect.getMethod(EnumPlayerTeleportFlags, "valueOf", String.class);

                for(PlayerTeleportFlag flag : flags) {

                    instanceFlasg.add(valueOf.invoke(null, flag.name()));
                }
            }
            Object ppop = Reflect.instantiateObject(PacketPlayOutPosition, x, y, z, yaw, pitch, instanceFlasg, g);

            Class<?> Packet = Reflect.PackageType.MINECRAFT_SERVER.getClass("Packet");
            Class<?> CraftPlayer = Reflect.PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftPlayer");
            Class<?> EntityPlayer = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityPlayer");
            Class<?> PlayerConnection = Reflect.PackageType.MINECRAFT_SERVER.getClass("PlayerConnection");

            Method getHandle = Reflect.getMethod(CraftPlayer, "getHandle");
            Player[] players = PacketManager.getPlayersfromNames(names);

            Method sendPacket = Reflect.getMethod(PlayerConnection, "sendPacket", Packet);

            for(Player player : players) {

                Object NMSPlayer = getHandle.invoke(player);
                Field playerConnection = Reflect.getField(EntityPlayer, true, "playerConnection");

                sendPacket.invoke(playerConnection.get(NMSPlayer), ppop);
            }
        }
        catch (Exception e) {

            e.printStackTrace();
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
