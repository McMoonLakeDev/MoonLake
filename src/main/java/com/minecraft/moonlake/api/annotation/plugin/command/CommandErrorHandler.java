/*
 * Copyright (C) 2016 The MoonLake Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package com.minecraft.moonlake.api.annotation.plugin.command;

import com.minecraft.moonlake.api.annotation.plugin.command.exception.*;
import org.bukkit.command.CommandSender;

/**
 * <h1>CommandErrorHandler</h1>
 * 命令错误处理器类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class CommandErrorHandler {

    /**
     * 命令错误处理器类构造函数
     */
    public CommandErrorHandler() {
    }

    /**
     * 处理命令错误异常
     *
     * @param exception 异常
     * @param sender 命令执行者
     * @param command 命令
     * @param args 命令参数
     */
    public void handleCommandException(CommandException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {
    }

    /**
     * 处理命令权限错误异常
     *
     * @param exception 异常
     * @param sender 命令执行者
     * @param command 命令
     * @param args 命令参数
     */
    public void handleCommandPermissionException(CommandPermissionException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {
    }

    /**
     * 处理命令非法执行者异常
     *
     * @param exception 异常
     * @param sender 命令执行者
     * @param command 命令
     * @param args 命令参数
     */
    public void handleCommandIllegalSenderException(CommandIllegalSenderException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {
    }

    /**
     * 处理命令未处理错误异常
     *
     * @param exception 异常
     * @param sender 命令执行者
     * @param command 命令
     * @param args 命令参数
     */
    public void handleCommandUnhandledException(CommandUnhandledException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {
    }

    /**
     * 处理命令参数长度错误异常
     *
     * @param exception 异常
     * @param sender 命令执行者
     * @param command 命令
     * @param args 命令参数
     */
    public void handleCommandInvalidLengthException(CommandInvalidLengthException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {
    }

    /**
     * 处理命令参数解析错误异常
     *
     * @param exception 异常
     * @param sender 命令执行者
     * @param command 命令
     * @param args 命令参数
     */
    public void handleCommandArgumentParseException(CommandArgumentParseException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {
    }
}
