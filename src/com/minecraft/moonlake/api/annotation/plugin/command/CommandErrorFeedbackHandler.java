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
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * <h1>CommandErrorFeedbackHandler</h1>
 * 命令错误反馈处理器类
 *
 * @version 1.0
 * @author Month_Light
 */
public class CommandErrorFeedbackHandler extends CommandErrorHandler {

    /**
     * 命令错误反馈处理器类构造函数
     */
    public CommandErrorFeedbackHandler() {
    }

    public void handleCommandException(CommandException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {

        sender.sendMessage(ChatColor.RED + "未知错误, 请查看控制台的错误反馈: " + exception.getMessage());

        throw exception;
    }

    public void handleCommandPermissionException(CommandPermissionException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {

        sender.sendMessage(ChatColor.RED + "你使用这个命令需要 " + exception.getPermission() + " 权限.");
    }

    public void handleCommandIllegalSenderException(CommandIllegalSenderException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {

        sender.sendMessage(ChatColor.RED + "控制台不能使用这个命令.");
    }

    public void handleCommandUnhandledException(CommandUnhandledException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {

        sender.sendMessage(ChatColor.RED + "未知错误, 请查看控制台的错误反馈: " + exception.getMessage());

        throw exception;
    }

    public void handleCommandInvalidLengthException(CommandInvalidLengthException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {

        sender.sendMessage(ChatColor.RED + exception.getMessage());
        sender.sendMessage(ChatColor.RED + "请使用: /" + command.getName() + " " + command.getUsage());
    }

    public void handleCommandArgumentParseException(CommandArgumentParseException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {

        sender.sendMessage(ChatColor.RED + exception.getMessage());
    }
}
