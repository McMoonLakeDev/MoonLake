package com.minecraft.moonlake.logger;

import com.minecraft.moonlake.property.ReadOnlyStringProperty;
import com.minecraft.moonlake.property.SimpleStringProperty;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <h1>MLoggerWrapped</h1>
 * 日志接口包装类
 *
 * @version 1.0
 * @author Month_Light
 */
public final class MLoggerWrapped implements MLogger {

    private final ReadOnlyStringProperty prefixProperty;
    private final Logger logger;

    /**
     * 日志接口包装类构造函数
     */
    public MLoggerWrapped() {

        this("MoonLake");
    }

    /**
     * 日志接口包装类构造函数
     *
     * @param prefix 消息前缀
     */
    public MLoggerWrapped(String prefix) {

        this.prefixProperty = new SimpleStringProperty(String.format("[%1$s]", prefix));
        this.logger = Logger.getLogger("Minecraft");
    }

    public String getPrefix() {

        return prefixProperty.get();
    }

    @Override
    public void log(String message) {

        log(Level.FINE, message);
    }

    @Override
    public void warn(String message) {

        log(Level.WARNING, message);
    }

    @Override
    public void info(String message) {

        log(Level.INFO, message);
    }

    @Override
    public void error(String message) {

        log(Level.SEVERE, message);
    }

    protected void log(Level level, String message) {

        logger.log(level, String.format("%1$s %2$s", getPrefix(), message));
    }
}
