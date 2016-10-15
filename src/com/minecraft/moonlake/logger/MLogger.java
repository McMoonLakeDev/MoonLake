package com.minecraft.moonlake.logger;

/**
 * <h1>MLogger</h1>
 * 日志接口（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public interface MLogger {

    /**
     * 获取此控制台日志对象的前缀属性
     *
     * @return 前缀属性
     */
    String getPrefix();

    /**
     * 在控制台输出日志消息
     *
     * @param message 消息
     */
    void log(String message);

    /**
     * 在控制台输出警告消息
     *
     * @param message 消息
     */
    void warn(String message);

    /**
     * 在控制台输出信息消息
     *
     * @param message 消息
     */
    void info(String message);

    /**
     * 在控制台输出错误消息
     *
     * @param message 消息
     */
    void error(String message);
}
