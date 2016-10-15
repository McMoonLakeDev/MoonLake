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

    @Deprecated
    public PotionEffectCustom(int id, int amplifier, int duration) {

        this(id, amplifier, duration, false);
    }

    @Deprecated
    public PotionEffectCustom(int id, int amplifier, int duration, boolean ambient) {

        this(id, amplifier, duration, ambient, false);
    }

    @Deprecated
    public PotionEffectCustom(int id, int amplifier, int duration, boolean ambient, boolean showParticles) {

        this(PotionEffectType.fromId(id), amplifier, duration, ambient, showParticles);
    }

    public PotionEffectCustom(PotionEffectType type, int amplifier, int duration) {

        this(type, amplifier, duration, false);
    }

    public PotionEffectCustom(PotionEffectType type, int amplifier, int duration, boolean ambient) {

        this(type, amplifier, duration, ambient, false);
    }

    public PotionEffectCustom(PotionEffectType type, int amplifier, int duration, boolean ambient, boolean showParticles) {

        Validate.notNull(type, "The potion effect type object is null.");

        this.id = (byte) type.getId();
        this.amplifier = (byte) amplifier;
        this.duration = new SimpleIntegerProperty(duration);
        this.ambient = new SimpleBooleanProperty(ambient);
        this.showParticles = new SimpleBooleanProperty(showParticles);
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

