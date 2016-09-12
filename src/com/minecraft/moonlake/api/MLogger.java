package com.minecraft.moonlake.api;

import java.util.logging.Level;

/**
 * Created by MoonLake on 2016/8/1.
 */
public interface MLogger {

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

    class Wrapped implements MLogger {

        private String prefix;
        private java.util.logging.Logger logger;

        public Wrapped() {

            this("MoonLake");
        }

        public Wrapped(String prefix) {

            this.prefix = "[" + prefix + "] ";
            this.logger = java.util.logging.Logger.getLogger("Minecraft");
        }

        /**
         * 在控制台输出日志消息
         *
         * @param message 消息
         */
        @Override
        public void log(String message) {

            log(Level.SEVERE, message);
        }

        /**
         * 在控制台输出警告消息
         *
         * @param message 消息
         */
        @Override
        public void warn(String message) {

            log(Level.WARNING, message);
        }

        /**
         * 在控制台输出信息消息
         *
         * @param message 消息
         */
        @Override
        public void info(String message) {

            log(Level.INFO, message);
        }

        protected void log(Level level, String message) {

            logger.log(level, prefix + message);
        }
    }
}
