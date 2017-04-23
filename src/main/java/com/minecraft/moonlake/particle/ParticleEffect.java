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
 
 
package com.minecraft.moonlake.particle;

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.packet.wrapper.PacketPlayOutWorldParticles;
import com.minecraft.moonlake.api.utility.MinecraftVersion;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.*;

/**
 * <hr />
 * <div>
 *     <h1>Minecraft Particle Effect Library</h1>
 *     <p>By DarkBlade12 Ver: 1.0</p>
 *     <p>By Month_Light Modify Ver: 1.2</p>
 * </div>
 * <hr />
 * <div>
 *     <h1>如何使用并播放粒子效果?</h1>
 *     <h2>首先不同的粒子效果也是需要注意他的需求属性</h2>
 *     <ul>
 *         <li>{@link #EXPLOSION_LARGE} 大型爆炸没有需求属性.</li>
 *         <li>{@link #FIREWORKS_SPARK} 烟花火星有矢量方向属性.</li>
 *         <li>{@link #WATER_BUBBLE} 水泡不仅拥有矢量方向属性还有需要水方块.</li>
 *         <li>{@link #ITEM_CRACK} 物品破裂不仅拥有矢量方向属性还有物品数据属性.</li>
 *     </ul>
 *     <h2>需要针对不同需求属性的粒子效果调用不同的 {@code display} 函数才能正常播放粒子效果.</h2>
 *     <ul>
 *         <li>无属性播放: {@link #display(float, float, float, float, int, Location, double)}</li>
 *         <li>效果数据播放: {@link #display(ParticleData, float, float, float, float, int, Location, double)}</li>
 *         <li>效果颜色播放: {@link #display(ParticleColor, Location, double)}</li>
 *     </ul>
 *     <h2>调用例子:</h2>
 *     <p>播放大型爆炸粒子效果: ParticleEffect.EXPLOSION_LARGE.display(0f, 0f, 0f, 0f, 1, player.getLocation(), 32d);</p>
 *     <p>播放红色尘埃粒子效果: ParticleEffect.REDSTONE.display(new OrdiaryColor(Color.GREEN), player.getLocation(), 32d);</p>
 *     <p>播放方块破碎粒子效果: ParticleEffect.BLOCK_CRACK.display(new BlockData(Material.DIAMOND_BLOCK, 0), 0f, 0f, 0f, 0f, 1, player.getLocation(), 32d);</p>
 * </div>
 * <hr />
 * <div>
 *     <h1>此支持库由 <a href="https://github.com/DarkBlade12/ParticleEffect" target="_blank">DarkBlade12</a> 原著做!</h1>
 *     <h2>现支持库由 <a href="https://github.com/u2g/MoonLake" target="_blank">u2g</a> 进行二次修改和汉化!</h2>
 * </div>
 * <hr />
 *
 * @version 1.2
 * @author DarkBlade12
 * @author Month_Light Modify
 */
public enum ParticleEffect {

    //
    // 带有 ? 的均为不确定意义
    ///

    /**
     * 粒子效果: 普通型爆炸 (服务器版本支持: 全版本 | 需求: 矢量方向)
     */
    EXPLOSION_NORMAL("explode", 0, ParticleProperty.DIRECTIONAL),
    /**
     * 粒子效果: 大型爆炸 (服务器版本支持: 全版本)
     */
    EXPLOSION_LARGE("largeexplode", 1),
    /**
     * 粒子效果: 巨大型爆炸 (服务器版本支持: 全版本)
     */
    EXPLOSION_HUGE("hugeexplosion", 2),
    /**
     * 粒子效果: 烟花火星 (服务器版本支持: 全版本 | 需求: 矢量方向)
     */
    FIREWORKS_SPARK("fireworksSpark", 3, ParticleProperty.DIRECTIONAL),
    /**
     * 粒子效果: 水泡 (服务器版本支持: 全版本 | 需求: 矢量方向, 需求: 水方块)
     */
    WATER_BUBBLE("bubble", 4, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_WATER),
    /**
     * 粒子效果: 水花溅起 (服务器版本支持: 全版本 | 需求: 矢量方向)
     */
    WATER_SPLASH("splash", 5, ParticleProperty.DIRECTIONAL),
    /**
     * 粒子效果: 水尾波 (服务器版本支持: 1.7+ | 需求: 矢量方向)
     */
    WATER_WAKE("wake", 6, MinecraftVersion.WORLD_UPDATE, ParticleProperty.DIRECTIONAL),
    /**
     * 粒子效果: 虚空? (服务器版本支持: 全版本 | 需求: 水方块)
     */
    SUSPENDED("suspended", 7, ParticleProperty.REQUIRES_WATER),
    /**
     * 粒子效果: 深度虚空? (服务器版本支持: 全版本 | 需求: 矢量方向)
     */
    SUSPENDED_DEPTH("depthSuspend", 8, ParticleProperty.DIRECTIONAL),
    /**
     * 粒子效果: 暴击 (服务器版本支持: 全版本 | 需求: 矢量方向)
     */
    CRIT("crit", 9, ParticleProperty.DIRECTIONAL),
    /**
     * 粒子效果: 魔法暴击 (服务器版本支持: 全版本 | 需求: 矢量方向)
     */
    CRIT_MAGIC("magicCrit", 10, ParticleProperty.DIRECTIONAL),
    /**
     * 粒子效果: 普通型烟雾 (服务器版本支持: 全版本 | 需求: 矢量方向)
     */
    SMOKE_NORMAL("smoke", 11, ParticleProperty.DIRECTIONAL),
    /**
     * 粒子效果: 大型烟雾 (服务器版本支持: 全版本 | 需求: 矢量方向)
     */
    SMOKE_LARGE("largesmoke", 12, ParticleProperty.DIRECTIONAL),
    /**
     * 粒子效果: 药水符咒 (服务器版本支持: 全版本)
     */
    SPELL("spell", 13),
    /**
     * 粒子效果: 瞬间药水符咒 (服务器版本支持: 全版本)
     */
    SPELL_INSTANT("instantSpell", 14),
    /**
     * 粒子效果: 实体药水符咒 (服务器版本支持: 全版本 | 需求: 效果颜色)
     */
    SPELL_MOB("mobSpell", 15, ParticleProperty.COLORABLE),
    /**
     * 粒子效果: 实体药水符咒环境 (服务器版本支持: 全版本 | 需求: 效果颜色)
     */
    SPELL_MOB_AMBIENT("mobSpellAmbient", 16, ParticleProperty.COLORABLE),
    /**
     * 粒子效果: 女巫魔法 (服务器版本支持: 全版本)
     */
    SPELL_WITCH("witchMagic", 17),
    /**
     * 粒子效果: 滴水 (服务器版本支持: 全版本)
     */
    DRIP_WATER("dripWater", 18),
    /**
     * 粒子效果: 滴岩浆 (服务器版本支持: 全版本)
     */
    DRIP_LAVA("dripLava", 19),
    /**
     * 粒子效果: 生气村民 (服务器版本支持: 全版本)
     */
    VILLAGER_ANGRY("angryVillager", 20),
    /**
     * 粒子效果: 高兴村民 (服务器版本支持: 全版本 | 需求: 矢量方向)
     */
    VILLAGER_HAPPY("happyVillager", 21, ParticleProperty.DIRECTIONAL),
    /**
     * 粒子效果: 菌丝孢子 (服务器版本支持: 全版本)
     */
    TOWN_AURA("townaura", 22),
    /**
     * 粒子效果: 音符 (服务器版本支持: 全版本 | 需求: 效果颜色)
     */
    NOTE("note", 23, ParticleProperty.COLORABLE),
    /**
     * 粒子效果: 传送门 (服务器版本支持: 全版本 | 需求: 矢量方向)
     */
    PORTAL("portal", 24, ParticleProperty.DIRECTIONAL),
    /**
     * 粒子效果: 附魔台 (服务器版本支持: 全版本 | 需求: 矢量方向)
     */
    ENCHANTMENT_TABLE("enchantmenttable", 25, ParticleProperty.DIRECTIONAL),
    /**
     * 粒子效果: 火焰 (服务器版本支持: 全版本 | 需求: 矢量方向)
     */
    FLAME("flame", 26, ParticleProperty.DIRECTIONAL),
    /**
     * 粒子效果: 岩浆 (服务器版本支持: 全版本)
     */
    LAVA("lava", 27),
    /**
     * 粒子效果: 脚印 (服务器版本支持: 全版本)
     */
    FOOTSTEP("footstep", 28),
    /**
     * 粒子效果: 云 (服务器版本支持: 全版本 | 需求: 矢量方向)
     */
    CLOUD("cloud", 29, ParticleProperty.DIRECTIONAL),
    /**
     * 粒子效果: 红色尘 (服务器版本支持: 全版本 | 需求: 效果颜色)
     */
    REDSTONE("reddust", 30, ParticleProperty.COLORABLE),
    /**
     * 粒子效果: 雪球碎裂 (服务器版本支持: 全版本)
     */
    SNOWBALL("snowballpoof", 31),
    /**
     * 粒子效果: 雪铲? (服务器版本支持: 全版本 | 需求: 矢量方向)
     */
    SNOW_SHOVEL("snowshovel", 32, ParticleProperty.DIRECTIONAL),
    /**
     * 粒子效果: 史莱姆 (服务器版本支持: 全版本)
     */
    SLIME("slime", 33),
    /**
     * 粒子效果: 红心 (服务器版本支持: 全版本)
     */
    HEART("heart", 34),
    /**
     * 粒子效果: 屏障 (服务器版本支持: 1.8+)
     */
    BARRIER("barrier", 35, MinecraftVersion.V1_8),
    /**
     * 粒子效果: 物品破裂 (服务器版本支持: 全部 | 需求: 矢量方向, 需求: 物品数据)
     */
    ITEM_CRACK("iconcrack", 36, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),
    /**
     * 粒子效果: 方块破裂 (服务器版本支持: 全部 | 需求: 方块数据)
     */
    BLOCK_CRACK("blockcrack", 37, ParticleProperty.REQUIRES_DATA),
    /**
     * 粒子效果: 方块尘 (服务器版本支持: 1.7+ | 需求: 矢量方向, 需求: 方块数据)
     */
    BLOCK_DUST("blockdust", 38, MinecraftVersion.WORLD_UPDATE, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),
    /**
     * 粒子效果: 水滴 (服务器版本支持: 1.8+)
     */
    WATER_DROP("droplet", 39, MinecraftVersion.V1_8),
    /**
     * 粒子效果: 物品获取? (服务器版本支持: 1.8+)
     */
    ITEM_TAKE("take", 40, MinecraftVersion.V1_8),
    /**
     * 粒子效果: 怪物外观 (远古守护者) (服务器版本支持: 1.8+)
     */
    MOB_APPEARANCE("mobappearance", 41, MinecraftVersion.V1_8),
    /**
     * 粒子效果: 龙息 (服务器版本支持: 1.9+)
     */
    DRAGON_BREATH("dragonbreath", 42, MinecraftVersion.V1_9),
    /**
     * 粒子效果: 末地烛 (服务器版本支持: 1.9+)
     */
    END_ROD("endRod", 43, MinecraftVersion.V1_9),
    /**
     * 粒子效果: 伤害指示器 (服务器版本支持: 1.9+)
     */
    DAMAGE_INDICATOR("damageIndicator", 44, MinecraftVersion.V1_9),
    /**
     * 粒子效果: 扫荡攻击 (服务器版本支持: 1.9+)
     */
    SWEEP_ATTACK("sweepAttack", 45, MinecraftVersion.V1_9),
    /**
     * 粒子效果: 掉落尘 (服务器版本支持: 1.10+ | 需求: 方块数据)
     */
    FALLING_DUST("fallingdust", 46, MinecraftVersion.V1_10, ParticleProperty.REQUIRES_DATA),
    /**
     * 粒子效果: 不死图腾 (服务器版本支持: 1.11+)
     */
    TOTEM("totem", 47, MinecraftVersion.V1_11),
    /**
     * 粒子效果: 羊驼口水 (服务器版本支持: 1.11+)
     */
    SPIT("spit", 48, MinecraftVersion.V1_11),
    ;

    private final String name;
    private final int id;
    private final MinecraftVersion requiredVersion;
    private final List<ParticleProperty> properties;
    private final static Map<String, ParticleEffect> NAME_MAP;
    private final static Map<Integer, ParticleEffect> ID_MAP;

    static {

        NAME_MAP = new HashMap<>();
        ID_MAP = new HashMap<>();

        for(ParticleEffect particleEffect : ParticleEffect.values()) {

            NAME_MAP.put(particleEffect.name, particleEffect);
            ID_MAP.put(particleEffect.id, particleEffect);
        }
    }

    /**
     * 粒子效果实现类构造函数
     *
     * @param name 粒子效果名称
     * @param id 粒子效果 Id
     * @param properties 粒子效果属性
     */
    ParticleEffect(String name, int id, ParticleProperty... properties) {

        this(name, id, null, properties);
    }

    /**
     * 粒子效果实现类构造函数
     *
     * @param name 粒子效果名称
     * @param id 粒子效果 Id
     * @param requiredVersion 需求版本
     * @param properties 粒子效果属性
     */
    ParticleEffect(String name, int id, MinecraftVersion requiredVersion, ParticleProperty... properties) {

        this.name = name;
        this.id = id;
        this.requiredVersion = requiredVersion;
        this.properties = Arrays.asList(properties);
    }

    /**
     * 获取此粒子效果的名字
     *
     * @return 名字
     */
    public String getName() {

        return name;
    }

    /**
     * 获取此粒子效果的 ID
     *
     * @return ID
     */
    public int getId() {

        return id;
    }

    /**
     * 获取此粒子效果需求的次版本号
     *
     * @return 次版本号
     * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link #getRequiredMCVersion()}
     */
    @Deprecated
    public int getRequiredVersion() { // TODO 2.0

        return requiredVersion != null ? requiredVersion.getMinor() : -1;
    }

    /**
     * 获取此粒子效果需求的版本
     *
     * @return 需求版本
     */
    @Nullable
    public MinecraftVersion getRequiredMCVersion() {

        return requiredVersion;
    }

    /**
     * 获取此粒子效果是否拥有指定属性
     *
     * @param property 属性
     * @return true 则拥有
     */
    public boolean hasProperty(ParticleProperty property) {

        return properties.contains(property);
    }

    /**
     * 获取此粒子效果是否支持当前服务端版本
     *
     * @return 是否支持当前服务端版本
     */
    public boolean isSupported() {

        return requiredVersion == null || MoonLakeAPI.currentMCVersion().isOrLater(requiredVersion);
    }

    /**
     * 将指定粒子效果名称转换到粒子效果
     *
     * @param name 粒子效果名称
     * @return ParticleEffect
     */
    public static ParticleEffect fromName(String name) {

        return NAME_MAP.containsKey(name) ? NAME_MAP.get(name) : null;
    }

    /**
     * 将指定粒子效果 Id 转换到粒子效果
     *
     * @param id 粒子效果 Id
     * @return ParticleEffect
     */
    public static ParticleEffect fromId(int id) {

        return ID_MAP.containsKey(id) ? ID_MAP.get(id) : null;
    }

    /**
     * 获取指定位置是否为水方块
     *
     * @param location 位置
     * @return 是否为水方块
     */
    private static boolean isWater(Location location) {

        Material material = location.getBlock().getType();
        return material == Material.WATER || material == Material.STATIONARY_WATER;
    }

    /**
     * 获取指定位置距离玩家是否为长距离
     *
     * @param location 位置
     * @param players 玩家
     * @return 是否为长距离
     */
    private static boolean isLongDistance(Location location, List<Player> players) {

        for(Player player : players) {

            if(!location.getWorld().getName().equals(player.getWorld().getName()) || player.getLocation().distanceSquared(location) <= 65536d) {

                continue;
            }
            return true;
        }
        return false;
    }

    /**
     * 判断指定粒子效果和粒子效果数据是否符合
     *
     * @param effect 粒子效果
     * @param data 粒子效果数据
     * @return true 数据则符合
     */
    private static boolean isDataCorrect(ParticleEffect effect, ParticleData data) {

        return (effect == BLOCK_CRACK || effect == BLOCK_DUST) && data instanceof BlockData || effect == ITEM_CRACK && data instanceof ItemData;
    }

    /**
     * 判断指定粒子效果和粒子效果颜色是否符合
     *
     * @param effect 粒子效果
     * @param color 粒子效果颜色
     * @return true 数据则符合
     */
    private static boolean isColorCorrect(ParticleEffect effect, ParticleColor color) {

        return (effect == SPELL_MOB || effect == SPELL_MOB_AMBIENT || effect == REDSTONE) && color instanceof OrdinaryColor || effect == NOTE && color instanceof NoteColor;
    }

    /**
     * 将此粒子效果在指定位置播放
     *
     * @param offsetX X 偏移量
     * @param offsetY Y 偏移量
     * @param offsetZ Z 偏移量
     * @param speed 速度
     * @param amount 数量
     * @param center 位置
     * @param range 范围
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果需要添加效果数据则抛出异常
     * @throws ParticleException 如果粒子效果需要水方块才能播放则抛出异常
     */
    public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleException {

        if(!isSupported()) {

            throw new ParticleException("这个粒子效果 " + this + " 不支持您的服务端版本.");
        }
        if(hasProperty(ParticleProperty.REQUIRES_DATA)) {

            throw new ParticleException("这个粒子效果 " + this + " 要求添加效果数据.");
        }
        if(hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center)) {

            throw new ParticleException("这个粒子效果没有水方块在中心位置.");
        }
        new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, range > 256d, null).sendTo(center, range);
    }

    /**
     * 将此粒子效果在指定位置播放
     *
     * @param offsetX X 偏移量
     * @param offsetY Y 偏移量
     * @param offsetZ Z 偏移量
     * @param speed 速度
     * @param amount 数量
     * @param center 位置
     * @param players 玩家
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果需要添加效果数据则抛出异常
     * @throws ParticleException 如果粒子效果需要水方块才能播放则抛出异常
     */
    public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List<Player> players) throws ParticleException {

        if(!isSupported()) {

            throw new ParticleException("这个粒子效果 " + this + " 不支持您的服务端版本.");
        }
        if(hasProperty(ParticleProperty.REQUIRES_DATA)) {

            throw new ParticleException("这个粒子效果 " + this + " 要求添加效果数据.");
        }
        if(hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center)) {

            throw new ParticleException("这个粒子效果没有水方块在中心位置.");
        }
        new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), null).sendTo(center, players);
    }

    /**
     * 将此粒子效果在指定位置播放
     *
     * @param offsetX X 偏移量
     * @param offsetY Y 偏移量
     * @param offsetZ Z 偏移量
     * @param speed 速度
     * @param amount 数量
     * @param center 位置
     * @param players 玩家
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果需要添加效果数据则抛出异常
     * @throws ParticleException 如果粒子效果需要水方块才能播放则抛出异常
     */
    public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) throws ParticleException {

        display(offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
    }

    /**
     * 将此粒子效果在指定位置播放
     *
     * @param direction 矢量方向
     * @param speed 速度
     * @param center 位置
     * @param range 范围
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果需要添加效果数据则抛出异常
     * @throws ParticleException 如果粒子效果需要水方块才能播放则抛出异常
     * @throws ParticleException 如果粒子效果没有矢量方向则抛出异常
     */
    public void display(Vector direction, float speed, Location center, double range) throws ParticleException {

        if(!isSupported()) {

            throw new ParticleException("这个粒子效果 " + this + " 不支持您的服务端版本.");
        }
        if(hasProperty(ParticleProperty.REQUIRES_DATA)) {

            throw new ParticleException("这个粒子效果 " + this + " 要求添加效果数据.");
        }
        if(!hasProperty(ParticleProperty.DIRECTIONAL)) {

            throw new ParticleException("这个粒子效果没有矢量方向属性.");
        }
        if(hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center)) {

            throw new ParticleException("这个粒子效果没有水方块在中心位置.");
        }
        new ParticlePacket(this, direction, speed, range > 256d, null).sendTo(center, range);
    }

    /**
     * 将此粒子效果在指定位置播放
     *
     * @param direction 矢量方向
     * @param speed 速度
     * @param center 位置
     * @param players 玩家
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果需要添加效果数据则抛出异常
     * @throws ParticleException 如果粒子效果需要水方块才能播放则抛出异常
     * @throws ParticleException 如果粒子效果没有矢量方向则抛出异常
     */
    public void display(Vector direction, float speed, Location center, List<Player> players) throws ParticleException {

        if(!isSupported()) {

            throw new ParticleException("这个粒子效果 " + this + " 不支持您的服务端版本.");
        }
        if(hasProperty(ParticleProperty.REQUIRES_DATA)) {

            throw new ParticleException("这个粒子效果 " + this + " 要求添加效果数据.");
        }
        if(!hasProperty(ParticleProperty.DIRECTIONAL)) {

            throw new ParticleException("这个粒子效果没有矢量方向属性.");
        }
        if(hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center)) {

            throw new ParticleException("这个粒子效果没有水方块在中心位置.");
        }
        new ParticlePacket(this, direction, speed, isLongDistance(center, players), null).sendTo(center, players);
    }

    /**
     * 将此粒子效果在指定位置播放
     *
     * @param direction 矢量方向
     * @param speed 速度
     * @param center 位置
     * @param players 玩家
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果需要添加效果数据则抛出异常
     * @throws ParticleException 如果粒子效果需要水方块才能播放则抛出异常
     * @throws ParticleException 如果粒子效果没有矢量方向则抛出异常
     */
    public void display(Vector direction, float speed, Location center, Player... players) throws ParticleException {

        display(direction, speed, center, Arrays.asList(players));
    }

    /**
     * 将此粒子效果在指定位置播放
     *
     * @param color 效果颜色
     * @param center 位置
     * @param range 范围
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果没有颜色属性则抛出异常
     * @throws ParticleException 如果粒子效果的颜色属性不符合则抛出异常
     */
    public void display(ParticleColor color, Location center, double range) throws ParticleException {

        if(!isSupported()) {

            throw new ParticleException("这个粒子效果 " + this + " 不支持您的服务端版本.");
        }
        if(!hasProperty(ParticleProperty.COLORABLE)) {

            throw new ParticleException("这个粒子效果没有效果颜色属性.");
        }
        if(!isColorCorrect(this, color)) {

            throw new ParticleException("这个粒子效果和效果颜色对象不符合.");
        }
        new ParticlePacket(this, color, range > 256d).sendTo(center, range);
    }

    /**
     * 将此粒子效果在指定位置播放
     *
     * @param color 效果颜色
     * @param center 位置
     * @param players 玩家
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果没有颜色属性则抛出异常
     * @throws ParticleException 如果粒子效果的颜色属性不符合则抛出异常
     */
    public void display(ParticleColor color, Location center, List<Player> players) throws ParticleException {

        if (!isSupported()) {

            throw new ParticleException("这个粒子效果 " + this + " 不支持您的服务端版本.");
        }
        if (!hasProperty(ParticleProperty.COLORABLE)) {

            throw new ParticleException("这个粒子效果没有效果颜色属性.");
        }
        if (!isColorCorrect(this, color)) {

            throw new ParticleException("这个粒子效果和效果颜色对象不符合.");
        }
        new ParticlePacket(this, color, isLongDistance(center, players)).sendTo(center, players);
    }

    /**
     * 将此粒子效果在指定位置播放
     *
     * @param color 效果颜色
     * @param center 位置
     * @param players 玩家
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果没有颜色属性则抛出异常
     * @throws ParticleException 如果粒子效果的颜色属性不符合则抛出异常
     */
    public void display(ParticleColor color, Location center, Player... players) throws ParticleException {

        display(color, center, Arrays.asList(players));
    }

    /**
     * 将此粒子效果在指定位置播放
     *
     * @param data 效果数据
     * @param offsetX X 偏移量
     * @param offsetY Y 偏移量
     * @param offsetZ Z 偏移量
     * @param speed 速度
     * @param amount 数量
     * @param center 位置
     * @param range 范围
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果没有数据属性则抛出异常
     * @throws ParticleException 如果粒子效果的数据属性不符合则抛出异常
     */
    public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleException {

        if(!isSupported()) {

            throw new ParticleException("这个粒子效果 " + this + " 不支持您的服务端版本.");
        }
        if(!hasProperty(ParticleProperty.REQUIRES_DATA)) {

            throw new ParticleException("这个粒子效果没有数据属性.");
        }
        if(!isDataCorrect(this, data)) {

            throw new ParticleException("粒子效果数据属性不正确: " + data + " 从 " + this);
        }
        new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, range > 256d, data).sendTo(center, range);
    }

    /**
     * 将此粒子效果在指定位置播放
     *
     * @param data 效果数据
     * @param offsetX X 偏移量
     * @param offsetY Y 偏移量
     * @param offsetZ Z 偏移量
     * @param speed 速度
     * @param amount 数量
     * @param center 位置
     * @param players 玩家
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果没有数据属性则抛出异常
     * @throws ParticleException 如果粒子效果的数据属性不符合则抛出异常
     */
    public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List<Player> players) throws ParticleException {

        if(!isSupported()) {

            throw new ParticleException("这个粒子效果 " + this + " 不支持您的服务端版本.");
        }
        if(!hasProperty(ParticleProperty.REQUIRES_DATA)) {

            throw new ParticleException("这个粒子效果没有数据属性.");
        }
        if(!isDataCorrect(this, data)) {

            throw new ParticleException("粒子效果数据属性不正确: " + data + " 从 " + this);
        }
        new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), data).sendTo(center, players);
    }

    /**
     * 将此粒子效果在指定位置播放
     *
     * @param data 效果数据
     * @param offsetX X 偏移量
     * @param offsetY Y 偏移量
     * @param offsetZ Z 偏移量
     * @param speed 速度
     * @param amount 数量
     * @param center 位置
     * @param players 玩家
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果没有数据属性则抛出异常
     * @throws ParticleException 如果粒子效果的数据属性不符合则抛出异常
     */
    public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) throws ParticleException {

        display(data, offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
    }

    /**
     * 将此粒子效果在指定位置播放
     *
     * @param data 效果数据
     * @param direction 矢量方向
     * @param speed 速度
     * @param center 位置
     * @param range 范围
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果没有数据属性则抛出异常
     * @throws ParticleException 如果粒子效果的数据属性不符合则抛出异常
     */
    public void display(ParticleData data, Vector direction, float speed, Location center, double range) throws ParticleException {

        if(!isSupported()) {

            throw new ParticleException("这个粒子效果 " + this + " 不支持您的服务端版本.");
        }
        if(!hasProperty(ParticleProperty.REQUIRES_DATA)) {

            throw new ParticleException("这个粒子效果没有数据属性.");
        }
        if(!isDataCorrect(this, data)) {

            throw new ParticleException("粒子效果数据属性不正确: " + data + " 从 " + this);
        }
        new ParticlePacket(this, direction, speed, range > 256d, data).sendTo(center, range);
    }

    /**
     * 将此粒子效果在指定位置播放
     *
     * @param data 效果数据
     * @param direction 矢量方向
     * @param speed 速度
     * @param center 位置
     * @param players 玩家
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果没有数据属性则抛出异常
     * @throws ParticleException 如果粒子效果的数据属性不符合则抛出异常
     */
    public void display(ParticleData data, Vector direction, float speed, Location center, List<Player> players) throws ParticleException {

        if(!isSupported()) {

            throw new ParticleException("这个粒子效果 " + this + " 不支持您的服务端版本.");
        }
        if(!hasProperty(ParticleProperty.REQUIRES_DATA)) {

            throw new ParticleException("这个粒子效果没有数据属性.");
        }
        if(!isDataCorrect(this, data)) {

            throw new ParticleException("粒子效果数据属性不正确: " + data + " 从 " + this);
        }
        new ParticlePacket(this, direction, speed, isLongDistance(center, players), data).sendTo(center, players);
    }

    /**
     * 将此粒子效果在指定位置播放
     *
     * @param data 效果数据
     * @param direction 矢量方向
     * @param speed 速度
     * @param center 位置
     * @param players 玩家
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果没有数据属性则抛出异常
     * @throws ParticleException 如果粒子效果的数据属性不符合则抛出异常
     */
    public void display(ParticleData data, Vector direction, float speed, Location center, Player... players) throws ParticleException {

        display(data, direction, speed, center, Arrays.asList(players));
    }

    /**
     * 将此粒子效果在指定位置播放
     *
     * @param center 位置
     * @param range 范围
     * @param offsetX X 偏移量
     * @param offsetY Y 偏移量
     * @param offsetZ Z 偏移量
     * @param speed 速度
     * @param amount 数量
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果需要添加效果数据则抛出异常
     * @throws ParticleException 如果粒子效果需要水方块才能播放则抛出异常
     */
    public void display(Location center, double range, float offsetX, float offsetY, float offsetZ, float speed, int amount) throws ParticleException {

        display(offsetX, offsetY, offsetZ, speed, amount, center, range);
    }

    /**
     * 将此粒子效果在指定位置播放
     *
     * @param center 位置
     * @param range 范围
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果需要添加效果数据则抛出异常
     * @throws ParticleException 如果粒子效果需要水方块才能播放则抛出异常
     */
    public void display(Location center, double range) throws ParticleException {

        display(0f, 0f, 0f, 0f, 1, center, range);
    }

    /**
     * 将此粒子效果在指定位置播放
     *
     * @param data 效果数据
     * @param center 位置
     * @param range 范围
     * @param offsetX X 偏移量
     * @param offsetY Y 偏移量
     * @param offsetZ Z 偏移量
     * @param speed 速度
     * @param amount 数量
     * @throws ParticleException 如果粒子效果不支持版本则抛出异常
     * @throws ParticleException 如果粒子效果没有数据属性则抛出异常
     * @throws ParticleException 如果粒子效果的数据属性不符合则抛出异常
     * @throws ParticleException 如果粒子效果需要添加效果数据则抛出异常
     * @throws ParticleException 如果粒子效果需要水方块才能播放则抛出异常
     */
    public void display(ParticleData data, Location center, double range, float offsetX, float offsetY, float offsetZ, float speed, int amount) throws ParticleException {

        if(hasProperty(ParticleProperty.REQUIRES_DATA)) {

            display(data, offsetX, offsetY, offsetZ, speed, amount, center, range);
        }
        else {

            display(offsetX, offsetY, offsetZ, speed, amount, center, range);
        }
    }

    /**
     * <h1>ParticlePacket</h1>
     * 粒子效果数据包实现类
     *
     * @version 1.0
     * @author Month_Light
     */
    public final static class ParticlePacket {

        private final ParticleEffect effect;
        private float offsetX;
        private final float offsetY;
        private final float offsetZ;
        private final float speed;
        private final int amount;
        private final boolean longDistance;
        private final ParticleData data;

        /**
         * 粒子效果数据包实现类
         *
         * @param effect 粒子效果
         * @param offsetX X 偏移量
         * @param offsetY Y 偏移量
         * @param offsetZ Z 偏移量
         * @param speed 速度
         * @param amount 数量
         * @param longDistance 长距离
         * @param data 粒子效果数据
         * @throws ParticleException 如果初始化错误则抛出异常
         */
        public ParticlePacket(ParticleEffect effect, float offsetX, float offsetY, float offsetZ, float speed, int amount, boolean longDistance, ParticleData data) throws ParticleException {

            if(speed < 0.0f) {

                throw new ParticleException("粒子效果速度不能小于 0");
            }
            if(amount < 0) {

                throw new ParticleException("粒子效果数量不能小于 0");
            }
            this.effect = effect;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.offsetZ = offsetZ;
            this.speed = speed;
            this.amount = amount;
            this.longDistance = longDistance;
            this.data = data;
        }

        /**
         * 粒子效果数据包实现类
         *
         * @param effect 粒子效果
         * @param direction 矢量方向
         * @param speed 速度
         * @param longDistance 长距离
         * @param data 粒子效果数据
         * @throws ParticleException 如果初始化错误则抛出异常
         */
        public ParticlePacket(ParticleEffect effect, Vector direction, float speed, boolean longDistance, ParticleData data) throws ParticleException {

            if(speed < 0.0f) {

                throw new ParticleException("粒子效果速度不能小于 0");
            }
            this.effect = effect;
            this.offsetX = (float)direction.getX();
            this.offsetY = (float)direction.getY();
            this.offsetZ = (float)direction.getZ();
            this.speed = speed;
            this.amount = 1;
            this.longDistance = longDistance;
            this.data = data;
        }

        /**
         * 粒子效果数据包实现类
         *
         * @param effect 粒子效果
         * @param color 颜色
         * @param longDistance 长距离
         */
        public ParticlePacket(ParticleEffect effect, ParticleColor color, boolean longDistance) {

            this(effect, color.getValueX(), color.getValueY(), color.getValueZ(), 1f, 0, longDistance, null);

            if (effect == ParticleEffect.REDSTONE && color instanceof OrdinaryColor && ((OrdinaryColor) color).getRed() == 0) {

                offsetX = Float.MIN_NORMAL;
            }
        }

        /**
         * 获取当前服务端的版本号
         *
         * @return 版本号
         * @deprecated 已过时, 将于 v2.0 删除. 请使用 {@link MoonLakeAPI#currentMCVersion()}
         */
        @Deprecated
        public static int getVersion() {

            return MoonLakeAPI.currentMCVersion().getMinor();
        }

        /**
         * 将此粒子效果数据包发送到指定位置
         *
         * @param center 位置
         * @param player 玩家
         */
        public void sendTo(Location center, Player player) {

            sendToWithBukkit(center, player);
        }

        /**
         * 将此粒子效果数据包发送到指定位置
         *
         * @param center 位置
         * @param players 玩家
         */
        public void sendTo(Location center, List<Player> players) {

            if(players == null || players.isEmpty()) {

                throw new ParticleException("粒子效果数据包的玩家列表为空");
            }
            else {

                Iterator<Player> i$ = players.iterator();

                while (i$.hasNext()) {

                    sendTo(center, i$.next());
                }
            }
        }

        /**
         * 将此粒子效果数据包发送到指定位置
         *
         * @param center 位置
         * @param range 范围
         */
        public void sendTo(Location center, double range) {

            if(range < 1.0d) {

                throw new ParticleException("粒子效果数据包范围小于 1");
            }
            else {

                String worldName = center.getWorld().getName();
                double squared = range * range;
                Iterator<? extends Player> i$ = Bukkit.getOnlinePlayers().iterator();

                while (i$.hasNext()) {

                    Player player = i$.next();

                    if(player.getWorld().getName().equals(worldName) && player.getLocation().distanceSquared(center) <= squared) {

                        sendTo(center, player);
                    }
                }
            }
        }

        /**
         * 将此粒子效果发送到 Bukkit 服务端
         *
         * @param center 位置
         * @param player 玩家
         */
        private void sendToWithBukkit(Location center, Player player) {

            try {

                int[] arguments = new int[0];

                if(data != null)
                    arguments = effect == ITEM_CRACK ? data.getPacketData() : new int[] { data.getPacketData()[0] | data.getPacketData()[1] << 12 };

                new PacketPlayOutWorldParticles(effect, longDistance, center.getX(), center.getY(), center.getZ(), offsetX, offsetY, offsetZ, speed, amount, arguments).send(player);
            }
            catch (Exception e) {

                e.printStackTrace();
                throw new ParticleException("粒子效果数据包发送时异常: " + e.getMessage());
            }
        }
    }

    /**
     * 粒子效果特殊属性值
     */
    public static enum ParticleProperty {

        /**
         * 粒子效果需要水才能播放
         */
        REQUIRES_WATER,
        /**
         * 粒子效果需要数据才能播放
         */
        REQUIRES_DATA,
        /**
         * 粒子效果需要用偏移量设置方向
         */
        DIRECTIONAL,
        /**
         * 粒子效果需要用偏移量设置颜色
         */
        COLORABLE
        ;
    }

    /**
     * <h1>ParticleData</h1>
     * 粒子效果数据类（详细doc待补充...）
     *
     * @version 1.1
     * @author Month_Light
     */
    public static abstract class ParticleData {

        private final Material material;
        private final byte data;
        private final int[] packetData;

        /**
         * 粒子效果数据类构造函数
         *
         * @param material 物品栈类型
         * @param data 物品栈数据
         */
        @SuppressWarnings("deprecation")
        public ParticleData(Material material, byte data) {

            this.material = material;
            this.data = data;
            this.packetData = new int[] { material.getId(), data };
        }

        /**
         * 获取此粒子效果数据的物品栈类型
         *
         * @return 物品栈类型
         */
        public Material getMaterial() {

            return material;
        }

        /**
         * 获取此粒子效果数据的物品栈数据
         *
         * @return 物品栈数据
         */
        public byte getData() {

            return data;
        }

        /**
         * 获取此粒子效果数据的数据包数据
         *
         * @return 数据包数据
         */
        public int[] getPacketData() {

            return packetData;
        }

        /**
         * 获取此粒子效果数据的数据包字符串数据
         *
         * @return 数据包字符串数据
         */
        public String getPacketDataString() {

            return ("_" + packetData[0] + "_" + packetData[1]);
        }
    }

    /**
     * <h1>BlockData</h1>
     * 粒子效果方块数据类（详细doc待补充...）
     *
     * @version 1.1
     * @author Month_Light
     */
    public final static class BlockData extends ParticleData {

        /**
         * 粒子效果方块数据类构造函数
         *
         * @param material 物品栈类型
         * @param data 物品栈数据
         * @throws IllegalArgumentException 如果物品栈类型不为方块类型 {@link Material#isBlock()} 则抛出异常
         */
        public BlockData(Material material, byte data) throws IllegalArgumentException {

            super(material, data);

            if(!material.isBlock()) {

                throw new IllegalArgumentException("粒子效果物品类型数据不是方块类型");
            }
        }
    }

    /**
     * <h1>ItemData</h1>
     * 粒子效果物品数据类（详细doc待补充...）
     *
     * @version 1.1
     * @author Month_Light
     */
    public final static class ItemData extends ParticleData {

        /**
         * 粒子效果物品数据类构造函数
         *
         * @param material 物品栈类型
         * @param data 物品栈数据
         */
        public ItemData(Material material, byte data) {

            super(material, data);
        }
    }

    /**
     * <h1>ParticleColor</h1>
     * 粒子效果颜色抽象类（详细doc待补充...）
     *
     * @version 1.1
     * @author Month_Light
     */
    public static abstract class ParticleColor {

        /**
         * 获取此粒子效果的 X 颜色值
         *
         * @return X 颜色值
         */
        public abstract float getValueX();

        /**
         * 获取此粒子效果的 Y 颜色值
         *
         * @return Y 颜色值
         */
        public abstract float getValueY();

        /**
         * 获取此粒子效果的 Z 颜色值
         *
         * @return Z 颜色值
         */
        public abstract float getValueZ();
    }

    /**
     * <h1>OrdinaryColor</h1>
     * 粒子效果普通颜色类（详细doc待补充...）
     *
     * @version 1.1
     * @author Month_Light
     */
    public final static class OrdinaryColor extends ParticleColor {

        private final int red;
        private final int green;
        private final int blue;

        /**
         * 粒子效果普通颜色类构造函数
         *
         * @param color 颜色
         */
        public OrdinaryColor(Color color) {

            this(color.getRed(), color.getGreen(), color.getBlue());
        }

        /**
         * 粒子效果普通颜色类构造函数
         *
         * @param red 红色值
         * @param green 绿色值
         * @param blue 蓝色值
         * @throws IllegalArgumentException 如果颜色值小于 0 或大于 255 则抛出异常
         */
        public OrdinaryColor(int red, int green, int blue) {

            Color.fromBGR(red, green, blue);

            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        /**
         * 获取此粒子效果普通颜色的红色值
         *
         * @return 红色值
         */
        public int getRed() {

            return red;
        }

        /**
         * 获取此粒子效果普通颜色的绿色值
         *
         * @return 绿色值
         */
        public int getGreen() {

            return green;
        }

        /**
         * 获取此粒子效果普通颜色的蓝色值
         *
         * @return 蓝色值
         */
        public int getBlue() {

            return blue;
        }

        @Override
        public float getValueX() {

            return (float) red / 255f;
        }

        @Override
        public float getValueY() {

            return (float) green / 255f;
        }

        @Override
        public float getValueZ() {

            return (float) blue / 255f;
        }
    }

    /**
     * <h1>NoteColor</h1>
     * 粒子效果音符颜色类（详细doc待补充...）
     *
     * @version 1.1
     * @author Month_Light
     */
    public final static class NoteColor extends ParticleColor {

        private final int note;

        /**
         * 粒子效果音符颜色类构造函数
         *
         * @param note 音符值
         * @throws IllegalArgumentException 如果音符值小于 0 或大于 24 则抛出异常
         */
        public NoteColor(int note) {

            if (note < 0) {

                throw new IllegalArgumentException("这个音符值不能小于 0");
            }
            if (note > 24) {

                throw new IllegalArgumentException("这个音符值不能大于 24");
            }
            this.note = note;
        }

        @Override
        public float getValueX() {

            return (float) note / 24f;
        }

        @Override
        public float getValueY() {

            return 0;
        }

        @Override
        public float getValueZ() {

            return 0;
        }
    }
}
