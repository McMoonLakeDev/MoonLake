package com.minecraft.moonlake.exception;

/**
 * <h1>NotImplementedException</h1>
 * 函数未实现异常（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class NotImplementedException extends MoonLakeException {

    /**
     * 函数未实现异常类构造函数
     */
    public NotImplementedException() {

        super("The method not implementsed exception.");
    }

    /**
     * 函数未实现异常类构造函数
     *
     * @param message 异常消息
     */
    public NotImplementedException(String message) {

        super(message);
    }
}
