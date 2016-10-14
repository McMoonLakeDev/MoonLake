package com.minecraft.moonlake.api.player;

import org.bukkit.Material;

/**
 * <h1>PlayerExpressionWrapped</h1>
 * 玩家支持库实现包装类
 */
class PlayerExpressionWrapped extends PlayerExpression {

    private final NMSPlayerExpression nmsPlayerExpression;
    private final ItemCooldownExpression itemCooldownExpression;

    public PlayerExpressionWrapped() {

        this.nmsPlayerExpression = new NMSPlayerExpression();
        this.itemCooldownExpression = new ItemCooldownExpression();
    }

    @Override
    public int getPing(String player) {

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
    public boolean hasItemCooldown(String player, Material material) {

        return itemCooldownExpression.hasItemCooldown(player, material);
    }
}
