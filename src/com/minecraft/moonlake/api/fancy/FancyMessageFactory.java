package com.minecraft.moonlake.api.fancy;

/**
 * <h1>FancyMessageFactory</h1>
 * 花式消息工厂类
 *
 * @version 1.0
 * @author Month_Light
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
