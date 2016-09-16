package com.minecraft.moonlake.api.fancy;

/**
 * Created by MoonLake on 2016/9/16.
 */
public enum FancyMessageStyle {

    /**
     * 花式消息样式类型: 模糊
     */
    OBFUSCATED("Obfuscated"),
    /**
     * 花式消息样式类型: 加粗
     */
    BOLD("Bold"),
    /**
     * 花式消息样式类型: 删除线
     */
    STRIKETHROUGH("Strikethrough"),
    /**
     * 花式消息样式类型: 下划线
     */
    UNDERLINED("Underlined"),
    /**
     * 花式消息样式类型: 斜体
     */
    ITALIC("Italic"),
    ;

    private final String type;

    FancyMessageStyle(String type) {

        this.type = type;
    }

    /**
     * 获取花式消息样式的类型
     *
     * @return 类型名
     */
    public String getType() {

        return type;
    }
}
