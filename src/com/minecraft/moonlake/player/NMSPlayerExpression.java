package com.minecraft.moonlake.player;

import com.minecraft.moonlake.MoonLakePlugin;
import com.minecraft.moonlake.api.nms.packet.PacketPlayOutChat;
import com.minecraft.moonlake.api.nms.packet.PacketPlayOutPlayerListHeaderFooter;
import com.minecraft.moonlake.api.nms.packet.PacketPlayOutTitle;
import com.minecraft.moonlake.api.player.NMSPlayerLibrary;
import com.minecraft.moonlake.api.player.PlayerLibraryFactorys;
import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
import com.minecraft.moonlake.exception.PlayerNotOnlineException;
import com.minecraft.moonlake.property.ReadOnlyBooleanProperty;
import com.minecraft.moonlake.property.ReadOnlyIntegerProperty;
import com.minecraft.moonlake.property.SimpleBooleanProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import com.minecraft.moonlake.reflect.Reflect;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by MoonLake on 2016/9/14.
 */
public class NMSPlayerExpression implements NMSPlayerLibrary {

    public NMSPlayerExpression() {

    }

    @Override
    public ReadOnlyIntegerProperty getPing(String player) {

        Player target = PlayerLibraryFactorys.player().fromName(player);

        if(target == null || !target.isOnline()) {

            throw new PlayerNotOnlineException(player);
        }
        try {

            Method getHandler = Reflect.getMethod("CraftPlayer", Reflect.PackageType.CRAFTBUKKIT_ENTITY, "getHandle");
            Field ping = Reflect.getField("EntityPlayer", Reflect.PackageType.MINECRAFT_SERVER, true, "ping");

            return new SimpleIntegerProperty((Integer) ping.get(getHandler.invoke(target)));
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void sendTitlePacket(String player, String title) {

        new PacketPlayOutTitle(title).send(player);
    }

    @Override
    public void sendTitlePacket(String player, String title, String subTitle) {

        new PacketPlayOutTitle(title, subTitle).send(player);
    }

    @Override
    public void sendTitlePacket(String player, String title, int fadeIn, int stay, int fadeOut) {

        new PacketPlayOutTitle(title, fadeIn, stay, fadeOut).send(player);
    }

    @Override
    public void sendTitlePacket(String player, String title, String subTitle, int fadeIn, int stay, int fadeOut) {

        new PacketPlayOutTitle(title, subTitle, fadeIn, stay, fadeOut).send(player);
    }

    @Override
    public void sendMainChatPacket(String player, String message) {

        new PacketPlayOutChat(message, PacketPlayOutChat.Mode.MAIN).send(player);
    }

    @Override
    public void sendTabListPacket(String player, String header) {

        new PacketPlayOutPlayerListHeaderFooter(header).send(player);
    }

    @Override
    public void sendTabListPacket(String player, String header, String footer) {

        new PacketPlayOutPlayerListHeaderFooter(header, footer).send(player);
    }

    @Override
    public void sendItemCooldownPacket(String player, Material material, int tick) {

        Player target = PlayerLibraryFactorys.player().fromName(player);

        if(target == null || !target.isOnline()) {

            throw new PlayerNotOnlineException(player);
        }
        String itemCooldownMethodName = getItemCooldownMethodName();

        if(itemCooldownMethodName == null) {

            throw new IllegalBukkitVersionException();
        }
        try {

            Class<?> Item = Reflect.PackageType.MINECRAFT_SERVER.getClass("Item");
            Class<?> EntityHuman = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityHuman");
            Class<?> CraftPlayer = Reflect.PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftPlayer");
            Class<?> EntityPlayer = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityPlayer");
            Class<?> ItemCooldown = Reflect.PackageType.MINECRAFT_SERVER.getClass("ItemCooldown");
            Class<?> CraftMagicNumbers = Reflect.PackageType.CRAFTBUKKIT_UTIL.getClass("CraftMagicNumbers");

            Object NMSPlayer = Reflect.getMethod(CraftPlayer, "getHandle").invoke(target);
            Object ItemCooldownInstance = Reflect.getMethod(EntityHuman, itemCooldownMethodName).invoke(NMSPlayer);

            Method a = Reflect.getMethod(ItemCooldown, "a", Item, Integer.class);
            a.invoke(ItemCooldownInstance, Reflect.getMethod(CraftMagicNumbers, "getItem", Material.class).invoke(null, material), tick);
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public ReadOnlyBooleanProperty hasItemCooldown(String player, Material material) {

        Player target = PlayerLibraryFactorys.player().fromName(player);

        if(target == null || !target.isOnline()) {

            throw new PlayerNotOnlineException(player);
        }
        String itemCooldownMethodName = getItemCooldownMethodName();

        if(itemCooldownMethodName == null) {

            throw new IllegalBukkitVersionException();
        }
        try {

            Class<?> Item = Reflect.PackageType.MINECRAFT_SERVER.getClass("Item");
            Class<?> EntityHuman = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityHuman");
            Class<?> CraftPlayer = Reflect.PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftPlayer");
            Class<?> EntityPlayer = Reflect.PackageType.MINECRAFT_SERVER.getClass("EntityPlayer");
            Class<?> ItemCooldown = Reflect.PackageType.MINECRAFT_SERVER.getClass("ItemCooldown");
            Class<?> CraftMagicNumbers = Reflect.PackageType.CRAFTBUKKIT_UTIL.getClass("CraftMagicNumbers");

            Object NMSPlayer = Reflect.getMethod(CraftPlayer, "getHandle").invoke(target);
            Object ItemCooldownInstance = Reflect.getMethod(EntityHuman, itemCooldownMethodName).invoke(NMSPlayer);

            Method a = Reflect.getMethod(ItemCooldown, "a", Item);
            boolean result = (boolean) a.invoke(ItemCooldownInstance, Reflect.getMethod(CraftMagicNumbers, "getItem", Material.class).invoke(null, material));

            return new SimpleBooleanProperty(result);
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    protected final String getItemCooldownMethodName() {

        String version = MoonLakePlugin.getInstances().getBukkitVersion().get();

        return    version.equals("v1_9_R1") ? "da"
                : version.equals("v1_9_R2") ? "db"
                : version.equals("v1_10_R1") ? "df"
                : null;
    }
}
