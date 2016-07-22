package com.minecraft.moonlake.api.nms.packet;

import com.minecraft.moonlake.reflect.Reflect;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by MoonLake on 2016/7/22.
 */
public class PacketPlayOutAbilities extends PacketAbstract<PacketPlayOutAbilities> {

    private PlayerAbilities abilities;

    public PacketPlayOutAbilities(PlayerAbilities abilities) {

        this.abilities = abilities;
    }

    public PacketPlayOutAbilities(boolean isInvulnerable, boolean isFlying, boolean canFly, boolean canInstantlyBuild, boolean mayBuild, float flySpeed, float walkSpeed) {

        this.abilities = new PlayerAbilities(isInvulnerable, isFlying, canFly, canInstantlyBuild, mayBuild, flySpeed, walkSpeed);
    }

    /**
     * 将此数据包发送给指定玩家
     *
     * @param names 玩家名
     */
    @Override
    public void send(String... names) {

        try {

            Class<?> PacketPlayOutAbilities = Reflect.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutAbilities");
            Class<?> PlayerAbilities = Reflect.PackageType.MINECRAFT_SERVER.getClass("PlayerAbilities");
            Object instancePlayerAbilities = Reflect.instantiateObject(PlayerAbilities);

            Field[] NMSFields = instancePlayerAbilities.getClass().getFields();

            for(Field field : NMSFields) {

                field.setAccessible(true);
                field.set(instancePlayerAbilities, abilities.getClass().getField(field.getName()).get(abilities));
            }
            Object ppoa = Reflect.instantiateObject(PacketPlayOutAbilities, instancePlayerAbilities);

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

                sendPacket.invoke(playerConnection.get(NMSPlayer), ppoa);
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static class PlayerAbilities {

        private boolean isInvulnerable;
        private boolean isFlying;
        private boolean canFly;
        private boolean canInstantlyBuild;
        private boolean mayBuild = true;
        private float flySpeed = 0.05F;
        private float walkSpeed = 0.1F;

        public PlayerAbilities() {

        }

        public PlayerAbilities(boolean isInvulnerable, boolean isFlying, boolean canFly, boolean canInstantlyBuild, boolean mayBuild, float flySpeed, float walkSpeed) {

            this.isInvulnerable = isInvulnerable;
            this.isFlying = isFlying;
            this.canFly = canFly;
            this.canInstantlyBuild = canInstantlyBuild;
            this.mayBuild = mayBuild;
            this.flySpeed = flySpeed;
            this.walkSpeed = walkSpeed;
        }

        public boolean isInvulnerable() {

            return isInvulnerable;
        }

        public void setInvulnerable(boolean invulnerable) {

            isInvulnerable = invulnerable;
        }

        public boolean isFlying() {

            return isFlying;
        }

        public void setFlying(boolean flying) {

            isFlying = flying;
        }

        public boolean isCanFly() {

            return canFly;
        }

        public void setCanFly(boolean canFly) {

            this.canFly = canFly;
        }

        public boolean isCanInstantlyBuild() {

            return canInstantlyBuild;
        }

        public void setCanInstantlyBuild(boolean canInstantlyBuild) {

            this.canInstantlyBuild = canInstantlyBuild;
        }

        public boolean isMayBuild() {

            return mayBuild;
        }

        public void setMayBuild(boolean mayBuild) {

            this.mayBuild = mayBuild;
        }

        public float getFlySpeed() {

            return flySpeed;
        }

        public void setFlySpeed(float flySpeed) {

            this.flySpeed = flySpeed;
        }

        public float getWalkSpeed() {

            return walkSpeed;
        }

        public void setWalkSpeed(float walkSpeed) {

            this.walkSpeed = walkSpeed;
        }
    }
}
