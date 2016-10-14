package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.nms.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.property.BooleanProperty;
import com.minecraft.moonlake.property.FloatProperty;
import com.minecraft.moonlake.property.SimpleBooleanProperty;
import com.minecraft.moonlake.property.SimpleFloatProperty;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutAbilities</h1>
 * 数据包输出玩家能力（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class PacketPlayOutAbilities extends PacketAbstract<PacketPlayOutAbilities> {

    private final static Class<?> CLASS_PACKETPLAYOUTABILITIES;
    private final static Class<?> CLASS_PLAYERABILITIES;
    private final static Field FIELD_ISINVULNERABLE;
    private final static Field FIELD_ISFLYING;
    private final static Field FIELD_CANFLY;
    private final static Field FIELD_CANINSTANTLYBUILD;
    private final static Field FIELD_MAYBUILD;
    private final static Field FIELD_FLYSPEED;
    private final static Field FIELD_WALKSPEED;

    static {

        try {

            CLASS_PACKETPLAYOUTABILITIES = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutAbilities");
            CLASS_PLAYERABILITIES = PackageType.MINECRAFT_SERVER.getClass("PlayerAbilities");
            FIELD_ISINVULNERABLE = getField(CLASS_PLAYERABILITIES, true, "isInvulnerable");
            FIELD_ISFLYING = getField(CLASS_PLAYERABILITIES, true, "isFlying");
            FIELD_CANFLY = getField(CLASS_PLAYERABILITIES, true, "canFly");
            FIELD_CANINSTANTLYBUILD = getField(CLASS_PLAYERABILITIES, true, "canInstantlyBuild");
            FIELD_MAYBUILD = getField(CLASS_PLAYERABILITIES, true, "mayBuild");
            FIELD_FLYSPEED = getField(CLASS_PLAYERABILITIES, true, "flySpeed");
            FIELD_WALKSPEED = getField(CLASS_PLAYERABILITIES, true, "walkSpeed");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out abilities reflect raw initialize exception.", e);
        }
    }

    private PlayerAbilities abilities;

    @Deprecated
    public PacketPlayOutAbilities() {

        this(new PlayerAbilities());
    }

    public PacketPlayOutAbilities(PlayerAbilities abilities) {

        this.abilities = abilities;
    }

    public PlayerAbilities getAbilities() {

        return abilities;
    }

    public void setAbilities(PlayerAbilities abilities) {

        this.abilities = abilities;
    }

    @Override
    public void send(Player... players) throws PacketException {

        try {

            if(abilities == null) {

                throw new PacketException("The nms packet play out abilities object is null.");
            }
            Object nmsAbilities = instantiateObject(CLASS_PLAYERABILITIES);

            FIELD_ISINVULNERABLE.set(nmsAbilities, abilities.isInvulnerable.get());
            FIELD_ISFLYING.set(nmsAbilities, abilities.isFlying.get());
            FIELD_CANFLY.set(nmsAbilities, abilities.canFly.get());
            FIELD_CANINSTANTLYBUILD.set(nmsAbilities, abilities.canInstantlyBuild.get());
            FIELD_MAYBUILD.set(nmsAbilities, abilities.mayBuild.get());
            FIELD_FLYSPEED.set(nmsAbilities, abilities.flySpeed.get());
            FIELD_WALKSPEED.set(nmsAbilities, abilities.walkSpeed.get());

            Object packet = instantiateObject(CLASS_PACKETPLAYOUTABILITIES, nmsAbilities);

            PacketReflect.get().send(players, packet);
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out abilities send exception.", e);
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
