package com.minecraft.moonlake.api.itemlib.potion;

/**
 * <h1>自定义药水效果: <a href="http://minecraft-zh.gamepedia.com/%E7%8A%B6%E6%80%81%E6%95%88%E6%9E%9C">效果详情</a></h1>
 * @version 1.0
 * @author Month_Light
 */
public class CustomPotionEffect {

    private final int id;
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

    /**
     * 自定义药水效果的ID
     *
     * @return 效果ID
     */
    public int getId() {
        return id;
    }

    /**
     * 自定义药水效果的等级
     *
     * @return 效果等级
     */
    public int getAmplifier() {
        return amplifier;
    }

    /**
     * 自定义药水效果的时间
     *
     * @return 效果时间
     */
    public int getDuration() {
        return duration;
    }

    /**
     * 自定义药水效果的是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     *
     * @return 透明度
     */
    public boolean isAmbient() {
        return ambient;
    }

    /**
     * 设置自定义药水效果的等级
     *
     * @param amplifier 效果等级, 此值为0则为1级
     */
    public void setAmplifier(int amplifier) {
        this.amplifier = amplifier;
    }

    /**
     * 设置自定义药水效果的时间
     *
     * @param duration 效果时间, 此值为20则为1秒
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * 设置自定义药水效果的透明度
     *
     * @param ambient 是否减少玩家被药水效果影响的周围出现粒子效果的透明度
     */
    public void setAmbient(boolean ambient) {
        this.ambient = ambient;
    }

    /**
     * 设置自定义药水效果的可见
     *
     * @param showParticles 是否在玩家被药水效果影响的周围出现粒子效果
     */
    public void setShowParticles(boolean showParticles) {
        this.showParticles = showParticles;
    }

    /**
     * 自定义药水效果的是否在玩家被药水效果影响的周围出现粒子效果
     *
     * @return 粒子效果
     */
    public boolean isShowParticles() {
        return showParticles;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == null) {
            return false;
        }
        if(!(obj instanceof CustomPotionEffect)) {
            return false;
        }
        CustomPotionEffect cpe = (CustomPotionEffect)obj;
        return cpe.hashCode() == this.hashCode();
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

    @Override
    public int hashCode() {
        int result = Integer.hashCode(getId());
        result = 29 * result + Integer.hashCode(getAmplifier()) + Integer.hashCode(getDuration());
        return result;
    }
}
