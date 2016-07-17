package com.minecraft.moonlake.data;

/**
 * Created by MoonLake on 2016/6/12.
 */
public interface ConversionData {

    /**
     * 将数据转换到字符串
     *
     * @return 字符串
     */
    String asString();

    /**
     * 将数据转换到整数
     *
     * @return 整数
     */
    int asInt();

    /**
     * 将数据转换到双精度浮点数
     *
     * @return 双精度浮点数
     */
    double asDouble();

    /**
     * 将数据转换到单精度浮点数
     *
     * @return 单精度浮点数
     */
    float asFloat();

    /**
     * 将数据转换到长整数
     *
     * @return 长整数
     */
    long asLong();

    /**
     * 将数据转换到短整数
     *
     * @return 短整数
     */
    short asShort();

    /**
     * 将数据转换到字符
     *
     * @return 字符
     */
    char asChar();

    /**
     * 将数据转换到布尔值
     *
     * @return 布尔值
     */
    boolean asBoolean();

    /**
     * 将数据转换到字节
     *
     * @return 字节
     */
    byte asByte();
}
