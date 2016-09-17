package com.minecraft.moonlake.api.fancy;

/**
 * Created by MoonLake on 2016/9/16.
 */
public class FancyMessageFactory {

    /**
     * Static Fancy Message Instance
     */
    private static FancyMessageFactory fancyMessageInstance;

    private FancyMessageFactory() {

    }

    /**
     * 获取 FancyMessageFactory 对象
     *
     * @return FancyMessageFactory
     */
    public static FancyMessageFactory get() {

        if(fancyMessageInstance == null) {

            fancyMessageInstance = new FancyMessageFactory();
        }
        return fancyMessageInstance;
    }

    /**
     * 获取 FancyMessage 实例对象
     *
     * @param text 文本
     * @return FancyMessage
     */
    public FancyMessage message(String text) {

        return new FancyMessageExpression(text);
    }

    /**
     * 获取 FancyMessage 实例对象
     *
     * @param text 文本
     * @return FancyMessage
     */
    public FancyMessage message(TextualComponent text) {

        return new FancyMessageExpression(text);
    }
}
