/*
 * Copyright (C) 2016 The MoonLake Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
 
 
package com.minecraft.moonlake.api.player;

import org.bukkit.Material;

/**
 * <h1>PlayerExpressionWrapped</h1>
 * 玩家支持库实现包装类
 * @deprecated 已过时, 将于 v2.0 删除.
 */
@Deprecated
class PlayerExpressionWrapped extends PlayerExpression { // TODO 2.0

    private NMSPlayerExpression nmsPlayerExpression;
    private ItemCooldownExpression itemCooldownExpression;

    /**
     * 玩家支持库实现包装类构造函数
     *
     * @deprecated 已过时, 将于 v2.0 删除.
     */
    @Deprecated
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
