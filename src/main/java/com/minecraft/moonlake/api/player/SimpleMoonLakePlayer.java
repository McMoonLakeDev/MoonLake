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

import com.google.common.io.BaseEncoding;
import com.minecraft.moonlake.api.entity.AttributeType;
import com.minecraft.moonlake.api.packet.wrapper.PacketPlayOutResourcePackSend;
import com.minecraft.moonlake.api.player.advancement.Advancement;
import com.minecraft.moonlake.api.player.advancement.AdvancementKey;
import com.minecraft.moonlake.api.player.advancement.AdvancementProgress;
import com.minecraft.moonlake.api.player.attribute.Attribute;
import com.minecraft.moonlake.api.utility.MinecraftVersion;
import com.minecraft.moonlake.exception.PlayerNotOnlineException;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

/**
 * <h1>SimpleMoonLakePlayer</h1>
 * 月色之湖玩家接口单包装类
 *
 * @version 1.0.1
 * @author Month_Light
 * @see MoonLakePlayer
 * @see AbstractPlayer
 * @see SimpleMoonLakePlayer_v1_8
 * @see SimpleMoonLakePlayer_v1_9
 * @see SimpleMoonLakePlayer_v1_10
 * @see SimpleMoonLakePlayer_v1_11
 * @see SimpleMoonLakePlayer_v1_12
 */
public class SimpleMoonLakePlayer extends AbstractPlayer {

    /**
     * 月色之湖玩家接口单包装类构造函数
     *
     * @param name 玩家名
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家没有在线则抛出异常
     */
    public SimpleMoonLakePlayer(String name) throws IllegalArgumentException, PlayerNotOnlineException {

        super(name);
    }

    /**
     * 月色之湖玩家接口单包装类构造函数
     *
     * @param player 玩家对象
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家没有在线则抛出异常
     */
    public SimpleMoonLakePlayer(Player player) throws IllegalArgumentException, PlayerNotOnlineException {

        super(player);
    }

    @Override
    public void stopSound(String sound) {

    }

    @Override
    public void stopSound(Sound sound) {

    }

    @Override
    public boolean isGliding() {

        return false;
    }

    @Override
    public void setGliding(boolean gliding) {

    }

    @Override
    public void setGlowing(boolean flag) {

    }

    @Override
    public boolean isGlowing() {

        return false;
    }

    @Override
    public void setInvulnerable(boolean flag) {

    }

    @Override
    public boolean isInvulnerable() {

        return false;
    }

    @Override
    public boolean isSilent() {

        return false;
    }

    @Override
    public void setSilent(boolean flag) {

    }

    @Override
    public boolean hasGravity() {

        return false;
    }

    @Override
    public void setGravity(boolean gravity) {

    }

    @Override
    public void addPotionEffect(PotionEffectType type, int level, int time, boolean ambient, boolean particles, Color color) {

    }

    @Override
    public Entity getSpectatorTarget() {

        return null;
    }

    @Override
    public void setSpectatorTarget(Entity entity) {

    }

    @Override
    public ItemStack getItemInHand() {

        return null;
    }

    @Override
    public ItemStack getItemInMainHand() {

        return null;
    }

    @Override
    public ItemStack getItemInOffHand() {

        return null;
    }

    @Override
    public void setItemInHand(ItemStack itemStack) {

    }

    @Override
    public void setItemInMainHand(ItemStack itemStack) {

    }

    @Override
    public void setItemInOffHand(ItemStack itemStack) {

    }

    @Override
    public Attribute getAttribute(AttributeType type) {

        Validate.notNull(type, "The attribute type object is null.");

        return new AttributeExpression(getBukkitPlayer(), type);
    }

    @Override
    public AdvancementProgress getAdvancementProgress(Advancement advancement) {

        return null;
    }

    @Override
    public AdvancementProgress getAdvancementProgress(AdvancementKey key) {

        return null;
    }

    @Override
    public void setResourcePack(String url, byte[] hash) {

        Validate.notNull(url, "The resource pack url string object is null.");
        Validate.notNull(hash, "The resource pack hash value object is null.");
        Validate.notNull(hash.length == 20, "The resource pack hash value length is not 20. (length: " + hash.length + ")");

        PacketPlayOutResourcePackSend pporps = new PacketPlayOutResourcePackSend(url, BaseEncoding.base16().lowerCase().encode(hash));
        pporps.send(getBukkitPlayer());
    }

    @Override
    public MinecraftVersion mcVersion() {

        return MinecraftVersion.getCurrentVersion();
    }
}
