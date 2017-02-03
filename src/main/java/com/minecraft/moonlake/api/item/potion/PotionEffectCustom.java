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
 
 
package com.minecraft.moonlake.api.item.potion;

import com.minecraft.moonlake.property.*;
import com.minecraft.moonlake.validate.Validate;

/**
 * <h1>PotionEffectCustom</h1>
 * 药水效果自定义包装类
 *
 * @version 1.0
 * @author Month_Light
 */
public class PotionEffectCustom {

    private byte id;
    private byte amplifier;
    private IntegerProperty duration;
    private BooleanProperty ambient;
    private BooleanProperty showParticles;

    /**
     * 药水效果自定义包装类构造函数
     *
     * @param id 药水 Id
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @deprecated 已过时，请使用 {@link #PotionEffectCustom(PotionEffectType, int, int)}
     * @see #PotionEffectCustom(PotionEffectType, int, int)
     */
    @Deprecated
    public PotionEffectCustom(int id, int amplifier, int duration) {

        this(id, amplifier, duration, false);
    }

    /**
     * 药水效果自定义包装类构造函数
     *
     * @param id 药水 Id
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @deprecated 已过时，请使用 {@link #PotionEffectCustom(PotionEffectType, int, int, boolean)}
     * @see #PotionEffectCustom(PotionEffectType, int, int, boolean)
     */
    @Deprecated
    public PotionEffectCustom(int id, int amplifier, int duration, boolean ambient) {

        this(id, amplifier, duration, ambient, false);
    }

    /**
     * 药水效果自定义包装类构造函数
     *
     * @param id 药水 Id
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     * @deprecated 已过时，请使用 {@link #PotionEffectCustom(PotionEffectType, int, int, boolean, boolean)}
     * @see #PotionEffectCustom(PotionEffectType, int, int, boolean, boolean)
     */
    @Deprecated
    public PotionEffectCustom(int id, int amplifier, int duration, boolean ambient, boolean showParticles) {

        this(PotionEffectType.fromId(id), amplifier, duration, ambient, showParticles);
    }

    /**
     * 药水效果自定义包装类构造函数
     *
     * @param type 药水效果类型
     * @param amplifier 药水等级
     * @param duration 药水时间
     */
    public PotionEffectCustom(PotionEffectType type, int amplifier, int duration) {

        this(type, amplifier, duration, false);
    }

    /**
     * 药水效果自定义包装类构造函数
     *
     * @param type 药水效果类型
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     */
    public PotionEffectCustom(PotionEffectType type, int amplifier, int duration, boolean ambient) {

        this(type, amplifier, duration, ambient, false);
    }

    /**
     * 药水效果自定义包装类构造函数
     *
     * @param type 药水效果类型
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     */
    public PotionEffectCustom(PotionEffectType type, int amplifier, int duration, boolean ambient, boolean showParticles) {

        Validate.notNull(type, "The potion effect type object is null.");

        this.id = (byte) type.getId();
        this.amplifier = (byte) amplifier;
        this.duration = new SimpleIntegerProperty(duration);
        this.ambient = new SimpleBooleanProperty(ambient);
        this.showParticles = new SimpleBooleanProperty(showParticles);
    }

    /**
     * 药水效果自定义包装类构造函数
     *
     * @param type 药水效果类型
     * @param amplifier 药水等级
     * @param duration 药水时间
     */
    public PotionEffectCustom(com.minecraft.moonlake.enums.PotionEffectType type, int amplifier, int duration) {

        this(type, amplifier, duration, false);
    }

    /**
     * 药水效果自定义包装类构造函数
     *
     * @param type 药水效果类型
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     */
    public PotionEffectCustom(com.minecraft.moonlake.enums.PotionEffectType type, int amplifier, int duration, boolean ambient) {

        this(type, amplifier, duration, ambient, false);
    }

    /**
     * 药水效果自定义包装类构造函数
     *
     * @param type 药水效果类型
     * @param amplifier 药水等级
     * @param duration 药水时间
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     */
    public PotionEffectCustom(com.minecraft.moonlake.enums.PotionEffectType type, int amplifier, int duration, boolean ambient, boolean showParticles) {

        this(type.to(), amplifier, duration, ambient, showParticles);
    }

    public byte getId() {

        return id;
    }

    public byte getAmplifier() {

        return amplifier;
    }

    public IntegerProperty getDuration() {

        return duration;
    }

    public BooleanProperty getAmbient() {

        return ambient;
    }

    public BooleanProperty getShowParticles() {

        return showParticles;
    }

    @Deprecated
    @SuppressWarnings("deprecation")
    public PotionEffectType getType() {

        return PotionEffectType.fromId(id);
    }
}

