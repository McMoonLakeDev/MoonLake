package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.property.ReadOnlyBooleanProperty;
import com.minecraft.moonlake.property.ReadOnlyIntegerProperty;
import org.bukkit.Material;

/**
 * Created by MoonLake on 2016/9/14.
 */
class PlayerExpressionWrapped extends PlayerExpression {

    private final NMSPlayerExpression nmsPlayerExpression;
    private final ItemCooldownExpression itemCooldownExpression;

    public PlayerExpressionWrapped() {

        this.nmsPlayerExpression = new NMSPlayerExpression();
        this.itemCooldownExpression = new ItemCooldownExpression();
    }

    @Override
    public ReadOnlyIntegerProperty getPing(String player) {

        return nmsPlayerExpression.getPing(player);
    }

    @Override
    public void sendTitlePacket(String player, String title) {

        nmsPlayerExpression.sendTitlePacket(player, title);
    }

    @Override
    public void sendTitlePacket(String player, String title, String subTitle) {

        nmsPlayerExpression.sendTitlePacket(player, title, subTitle);
    }

    @Override
    public void sendTitlePacket(String player, String title, int fadeIn, int stay, int fadeOut) {

        nmsPlayerExpression.sendTitlePacket(player, title, fadeIn, stay, fadeOut);
    }

    @Override
    public void sendTitlePacket(String player, String title, String subTitle, int fadeIn, int stay, int fadeOut) {

        nmsPlayerExpression.sendTitlePacket(player, title, subTitle, fadeIn, stay, fadeOut);
    }

    @Override
    public void sendMainChatPacket(String player, String message) {

        nmsPlayerExpression.sendMainChatPacket(player, message);
    }

    @Override
    public void sendTabListPacket(String player, String header) {

        nmsPlayerExpression.sendTitlePacket(player, header);
    }

    @Override
    public void sendTabListPacket(String player, String header, String footer) {

        nmsPlayerExpression.sendTabListPacket(player, header, footer);
    }

    @Override
    public void setItemCooldown(String player, Material material, int tick) {

        itemCooldownExpression.setItemCooldown(player, material, tick);
    }

    @Override
    public ReadOnlyBooleanProperty hasItemCooldown(String player, Material material) {

        return itemCooldownExpression.hasItemCooldown(player, material);
    }
}
