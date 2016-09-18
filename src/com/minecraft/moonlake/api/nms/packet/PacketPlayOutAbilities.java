package com.minecraft.moonlake.api.nms.packet;

import com.minecraft.moonlake.property.*;
import com.minecraft.moonlake.reflect.Reflect;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by MoonLake on 2016/7/22.
 */
public class PacketPlayOutAbilities extends PacketAbstract<PacketPlayOutAbilities> {

    private ReadOnlyObjectProperty<PlayerAbilities> abilities;

    public PacketPlayOutAbilities(Player player) {

        try {

            Class<?> EntityHuman = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityHuman");
            Class<?> CraftPlayer = Reflect.PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftPlayer");
            Class<?> PlayerAbilities = Reflect.PackageType.MINECRAFT_SERVER.getClass("PlayerAbilities");
            Class<PlayerAbilities> PlayerAbilities1 = PacketPlayOutAbilities.PlayerAbilities.class;

            Object NMSPlayer = Reflect.getMethod(CraftPlayer, "getHandle").invoke(player);
            Object instancePlayerAbilities = Reflect.getField(EntityHuman, true, "abilities").get(NMSPlayer);

            PacketPlayOutAbilities.PlayerAbilities instancePlayerAbilities1 = new PlayerAbilities();

            Field[] Fields = PlayerAbilities1.getFields();

            for(Field field : Fields) {

                field.setAccessible(true);
                field.set(instancePlayerAbilities1, instancePlayerAbilities.getClass().getField(field.getName()).get(instancePlayerAbilities));
            }
            this.abilities = new SimpleObjectProperty<>(instancePlayerAbilities1);
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }

    public PacketPlayOutAbilities(PlayerAbilities abilities) {

        this.abilities = new SimpleObjectProperty<>(abilities);
    }

    public PacketPlayOutAbilities(boolean isInvulnerable, boolean isFlying, boolean canFly, boolean canInstantlyBuild, boolean mayBuild, float flySpeed, float walkSpeed) {

        this.abilities = new SimpleObjectProperty<>(new PlayerAbilities(isInvulnerable, isFlying, canFly, canInstantlyBuild, mayBuild, flySpeed, walkSpeed));
    }

    public ReadOnlyObjectProperty<PlayerAbilities> getAbilities() {

        return abilities;
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

            Reflect.setValue(instancePlayerAbilities, true, "isInvulnerable", abilities.get().getIsInvulnerable().get());
            Reflect.setValue(instancePlayerAbilities, true, "isFlying", abilities.get().getIsFlying().get());
            Reflect.setValue(instancePlayerAbilities, true, "canFly", abilities.get().getCanFly().get());
            Reflect.setValue(instancePlayerAbilities, true, "canInstantlyBuild", abilities.get().getCanInstantlyBuild().get());
            Reflect.setValue(instancePlayerAbilities, true, "mayBuild", abilities.get().getMayBuild().get());
            Reflect.setValue(instancePlayerAbilities, true, "flySpeed", abilities.get().getFlySpeed().get());
            Reflect.setValue(instancePlayerAbilities, true, "walkSpeed", abilities.get().getWalkSpeed().get());

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

        private BooleanProperty isInvulnerable;
        private BooleanProperty isFlying;
        private BooleanProperty canFly;
        private BooleanProperty canInstantlyBuild;
        private BooleanProperty mayBuild;
        private FloatProperty flySpeed;
        private FloatProperty walkSpeed;

        public PlayerAbilities() {

            this(false, false, false, false, true, 0.05f, 0.1f);
        }

        public PlayerAbilities(boolean isInvulnerable, boolean isFlying, boolean canFly, boolean canInstantlyBuild, boolean mayBuild, float flySpeed, float walkSpeed) {

            this.isInvulnerable = new SimpleBooleanProperty(isInvulnerable);
            this.isFlying = new SimpleBooleanProperty(isFlying);
            this.canFly = new SimpleBooleanProperty(canFly);
            this.canInstantlyBuild = new SimpleBooleanProperty(canInstantlyBuild);
            this.mayBuild = new SimpleBooleanProperty(mayBuild);
            this.flySpeed = new SimpleFloatProperty(flySpeed);
            this.walkSpeed = new SimpleFloatProperty(walkSpeed);
        }

        public BooleanProperty getIsInvulnerable() {

            return isInvulnerable;
        }

        public BooleanProperty getIsFlying() {

            return isFlying;
        }

        public BooleanProperty getCanFly() {

            return canFly;
        }

        public BooleanProperty getCanInstantlyBuild() {

            return canInstantlyBuild;
        }

        public BooleanProperty getMayBuild() {

            return mayBuild;
        }

        public FloatProperty getFlySpeed() {

            return flySpeed;
        }

        public FloatProperty getWalkSpeed() {

            return walkSpeed;
        }
    }
}
