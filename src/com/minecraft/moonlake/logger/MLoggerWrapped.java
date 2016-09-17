package com.minecraft.moonlake.logger;

import com.minecraft.moonlake.property.ReadOnlyObjectProperty;
import com.minecraft.moonlake.property.ReadOnlyStringProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.property.SimpleStringProperty;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by MoonLake on 2016/9/12.
 */
public final class MLoggerWrapped implements MLogger {

    private final ReadOnlyStringProperty prefixProperty;
    private final ReadOnlyObjectProperty<Logger> loggerProperty;

    public MLoggerWrapped() {

        this("MoonLake");
    }

    public MLoggerWrapped(String prefix) {

        this.prefixProperty = new SimpleStringProperty(String.format("[%1$s]", prefix));
        this.loggerProperty = new SimpleObjectProperty<>(Logger.getLogger("Minecraft"));
    }

    public ReadOnlyStringProperty getPrefixProperty() {

        return prefixProperty;
    }

    public ReadOnlyObjectProperty<Logger> getLoggerProperty() {

        return loggerProperty;
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

        getLoggerProperty().get().log(level, String.format("%1$s %2$s", getPrefixProperty().get(), message));
    }
}
