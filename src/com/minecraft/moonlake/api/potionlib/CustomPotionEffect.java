package com.minecraft.moonlake.api.potionlib;

/**
 * <h1>自定义药水效果: <a href="http://minecraft-zh.gamepedia.com/%E7%8A%B6%E6%80%81%E6%95%88%E6%9E%9C">效果详情</a></h1>
 * @version 1.0
 * @author Month_Light
 */
public class CustomPotionEffect {

    private int id;
    private int amplifier;
    private int duration;
    private boolean ambient;
    private boolean showParticles;

    /**
     * 自定义药水效果
     *
     * @param id 效果ID
     * @param amplifier 效果等级, 此值为0则为1级
     * @param duration 效果时间, 此值为20则为1秒
     */
    public CustomPotionEffect(int id, int amplifier, int duration) {

        this(id, amplifier, duration, false, true);
    }

    /**
     * 自定义药水效果
     *
     * @param id 效果ID
     * @param amplifier 效果等级, 此值为0则为1级
     * @param duration 效果时间, 此值为20则为1秒
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     */
    public CustomPotionEffect(int id, int amplifier, int duration, boolean showParticles) {

        this(id, amplifier, duration, false, showParticles);
    }

    /**
     * 自定义药水效果
     *
     * @param id 效果ID
     * @param amplifier 效果等级, 此值为0则为1级
     * @param duration 效果时间, 此值为20则为1秒
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     */
    public CustomPotionEffect(int id, int amplifier, int duration, boolean ambient, boolean showParticles) {

        this.id = id;
        this.amplifier = amplifier;
        this.duration = duration;
        this.ambient = ambient;
        this.showParticles = showParticles;
    }

    public int getId() {
        return id;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isAmbient() {
        return ambient;
    }

    public boolean isShowParticles() {
        return showParticles;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == null) {
            return false;
        }
        if(obj instanceof CustomPotionEffect) {

            CustomPotionEffect cpe = (CustomPotionEffect)obj;

            return
                    this == cpe &&
                    id == cpe.getId() &&
                    amplifier == cpe.getAmplifier() &&
                    duration == cpe.getDuration() &&
                    ambient == cpe.isAmbient() &&
                    showParticles == cpe.isShowParticles();
        }
        return false;
    }

    @Override
    public String toString() {

        StringBuilder toString = new StringBuilder("CustomPotionEffect{").append(id + ",").append(amplifier + ",").append(duration + ",").append(ambient + ",").append(showParticles);
        return toString.append("}").toString();
    }

    @Override
    public CustomPotionEffect clone() {

        CustomPotionEffect cpe = new CustomPotionEffect(id, amplifier, duration, ambient, showParticles);
        return cpe;
    }
}
