package com.minecraft.moonlake.logger;

import com.minecraft.moonlake.property.ReadOnlyStringProperty;

/**
 * Created by MoonLake on 2016/8/1.
 */
public interface MLogger {

    /**
     * 获取此控制台日志对象的前缀属性
     *
     * @return 前缀属性
     */
    ReadOnlyStringProperty getPrefix();

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
