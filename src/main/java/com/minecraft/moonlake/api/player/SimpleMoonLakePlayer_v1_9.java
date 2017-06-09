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

import com.minecraft.moonlake.api.entity.AttributeType;
import com.minecraft.moonlake.api.player.advancement.Advancement;
import com.minecraft.moonlake.api.player.advancement.AdvancementKey;
import com.minecraft.moonlake.api.player.advancement.AdvancementProgress;
import com.minecraft.moonlake.api.player.attribute.Attribute;
import com.minecraft.moonlake.api.utility.MinecraftVersion;
import com.minecraft.moonlake.exception.IllegalBukkitVersionException;
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
 * 月色之湖玩家接口单包装类 - Bukkit 1.9
 *
 * @version 1.0
 * @author Month_Light
 */
public class SimpleMoonLakePlayer_v1_9 extends SimpleMoonLakePlayer_v1_8 {

    /**
     * 月色之湖玩家接口单包装类 - Bukkit 1.9 构造函数
     *
     * @param name 玩家名
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家没有在线则抛出异常
     */
    public SimpleMoonLakePlayer_v1_9(String name) throws IllegalArgumentException, PlayerNotOnlineException {

        super(name);
    }

    /**
     * 月色之湖玩家接口单包装类 - Bukkit 1.9 构造函数
     *
     * @param player 玩家对象
     * @throws IllegalArgumentException 如果玩家名对象为 {@code null} 则抛出异常
     * @throws PlayerNotOnlineException 如果玩家没有在线则抛出异常
     */
    public SimpleMoonLakePlayer_v1_9(Player player) throws IllegalArgumentException, PlayerNotOnlineException {

        super(player);
    }

    //
    // 这些函数只有 Bukkit 1.10 版本才拥有

    @Override
    public void stopSound(String sound) {

        throw new IllegalBukkitVersionException("The method not support 1.9 and old version.");
    }

    @Override
    public void stopSound(Sound sound) {

        throw new IllegalBukkitVersionException("The method not support 1.9 and old version.");
    }

    @Override
    public boolean isSilent() {

        throw new IllegalBukkitVersionException("The method not support 1.9 and old version.");
    }

    @Override
    public void setSilent(boolean flag) {

        throw new IllegalBukkitVersionException("The method not support 1.9 and old version.");
    }

    @Override
    public boolean hasGravity() {

        throw new IllegalBukkitVersionException("The method not support 1.9 and old version.");
    }

    @Override
    public void setGravity(boolean gravity) {

        throw new IllegalBukkitVersionException("The method not support 1.9 and old version.");
    }

    @Override
    public void addPotionEffect(PotionEffectType type, int level, int time, boolean ambient, boolean particles, Color color) {

        super.addPotionEffect(type, level, time, ambient, particles, color);
    }

    ///

    @Override
    public boolean isGliding() {

        return getBukkitPlayer().isGliding();
    }

    @Override
    public void setGliding(boolean gliding) {

        getBukkitPlayer().setGliding(gliding);
    }

    @Override
    public void setGlowing(boolean flag) {

        getBukkitPlayer().setGlowing(flag);
    }

    @Override
    public boolean isGlowing() {

        return getBukkitPlayer().isGlowing();
    }

    @Override
    public void setInvulnerable(boolean flag) {

        getBukkitPlayer().setInvulnerable(flag);
    }

    @Override
    public boolean isInvulnerable() {

        return getBukkitPlayer().isInvulnerable();
    }

    @Override
    public Entity getSpectatorTarget() {

        return getBukkitPlayer().getSpectatorTarget();
    }

    @Override
    public void setSpectatorTarget(Entity entity) {

        getBukkitPlayer().setSpectatorTarget(entity);
    }

    @Override
    public ItemStack getItemInHand() {

        return getItemInMainHand();
    }

    @Override
    public ItemStack getItemInMainHand() {

        return getBukkitPlayer().getInventory().getItemInMainHand();
    }

    @Override
    public ItemStack getItemInOffHand() {

        return getBukkitPlayer().getInventory().getItemInOffHand();
    }

    @Override
    public void setItemInHand(ItemStack itemStack) {

        setItemInMainHand(itemStack);
    }

    @Override
    public void setItemInMainHand(ItemStack itemStack) {

        getBukkitPlayer().getInventory().setItemInMainHand(itemStack);
    }

    @Override
    public void setItemInOffHand(ItemStack itemStack) {

        getBukkitPlayer().getInventory().setItemInOffHand(itemStack);
    }

    @Override
    public Attribute getAttribute(AttributeType type) {

        Validate.notNull(type, "The attribute type object is null.");

        return new AttributeExpression_v1_9_Plus(getBukkitPlayer(), type);
    }

    @Override
    public MinecraftVersion mcVersion() {

        return MinecraftVersion.V1_9;
    }

    //

    @Override
    public AdvancementProgress getAdvancementProgress(Advancement advancement) {

        throw new IllegalBukkitVersionException("The method not support 1.9 and old version.");
    }

    @Override
    public AdvancementProgress getAdvancementProgress(AdvancementKey key) {

        throw new IllegalBukkitVersionException("The method not support 1.9 and old version.");
    }

    ///
}
