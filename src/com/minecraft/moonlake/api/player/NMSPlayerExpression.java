package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.exception.PlayerNotOnlineException;
import com.minecraft.moonlake.nms.packet.PacketPlayOutChat;
import com.minecraft.moonlake.nms.packet.PacketPlayOutPlayerListHeaderFooter;
import com.minecraft.moonlake.nms.packet.PacketPlayOutTitle;
import com.minecraft.moonlake.property.ReadOnlyIntegerProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import com.minecraft.moonlake.reflect.Reflect;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by MoonLake on 2016/9/14.
 */
class NMSPlayerExpression implements NMSPlayerLibrary {

    public NMSPlayerExpression() {

    }

    @Override
    public ReadOnlyIntegerProperty getPing(String player) {

        Validate.notNull(player, "The player string object is null.");

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

        Validate.notNull(player, "The player string object is null.");
        Validate.notNull(title, "The title string object is null.");

        new PacketPlayOutTitle(title).send(player);
    }

    @Override
    public void sendTitlePacket(String player, String title, String subTitle) {

        Validate.notNull(player, "The player string object is null.");
        Validate.notNull(title, "The title string object is null.");
        Validate.notNull(subTitle, "The sub title string object is null.");

        new PacketPlayOutTitle(title, subTitle).send(player);
    }

    @Override
    public void sendTitlePacket(String player, String title, int fadeIn, int stay, int fadeOut) {

        Validate.notNull(player, "The player string object is null.");
        Validate.notNull(title, "The title string object is null.");

        new PacketPlayOutTitle(title, fadeIn, stay, fadeOut).send(player);
    }

    @Override
    public void sendTitlePacket(String player, String title, String subTitle, int fadeIn, int stay, int fadeOut) {

        Validate.notNull(player, "The player string object is null.");
        Validate.notNull(title, "The title string object is null.");
        Validate.notNull(subTitle, "The sub title string object is null.");

        new PacketPlayOutTitle(title, subTitle, fadeIn, stay, fadeOut).send(player);
    }

    @Override
    public void sendMainChatPacket(String player, String message) {

        Validate.notNull(player, "The player string object is null.");
        Validate.notNull(message, "The message string object is null.");

        new PacketPlayOutChat(message, PacketPlayOutChat.Mode.MAIN).send(player);
    }

    @Override
    public void sendTabListPacket(String player, String header) {

        Validate.notNull(player, "The player string object is null.");
        Validate.notNull(header, "The header string object is null.");

        new PacketPlayOutPlayerListHeaderFooter(header).send(player);
    }

    @Override
    public void sendTabListPacket(String player, String header, String footer) {

        Validate.notNull(player, "The player string object is null.");
        Validate.notNull(header, "The header string object is null.");
        Validate.notNull(footer, "The footer string object is null.");

        new PacketPlayOutPlayerListHeaderFooter(header, footer).send(player);
    }
}
