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

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.annotation.plugin.command.exception.*;
import com.minecraft.moonlake.api.chat.ChatColor;
import com.minecraft.moonlake.api.chat.ChatComponentFancy;
import com.minecraft.moonlake.util.StringUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * <h1>CommandErrorMoonLakeHandler</h1>
 * 命令错误月色之湖处理器类
 *
 * @version 1.0
 * @author Month_Light
 */
public class CommandErrorMoonLakeHandler extends CommandErrorFeedbackHandler {

    /**
     * 命令错误月色之湖处理器类构造函数
     */
    public CommandErrorMoonLakeHandler() {
    }

    public void handleCommandException(CommandException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {

        if(sender instanceof Player) {

            MoonLakeAPI.chatComponentSent(
                    new ChatComponentFancy("MoonLake")
                        .color(ChatColor.DARK_AQUA)
                        .then(" >> ")
                        .color(ChatColor.GRAY)
                        .then("未知错误, 请查看控制台的错误反馈: ")
                        .color(ChatColor.RED)
                        .then("异常信息")
                        .color(ChatColor.AQUA)
                        .withUnderlined()
                        .tooltipText(exception.getMessage())
                    , sender);

            throw exception;
        }
        super.handleCommandException(exception, sender, command, args);
    }

    public void handleCommandPermissionException(CommandPermissionException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {

        if(sender instanceof Player) {

            MoonLakeAPI.chatComponentSent(
                    new ChatComponentFancy("MoonLake")
                        .color(ChatColor.DARK_AQUA)
                        .then(" >> ")
                        .color(ChatColor.GRAY)
                        .then("你没有使用这个命令的")
                        .color(ChatColor.RED)
                        .then("权限")
                        .color(ChatColor.RED)
                        .withUnderlined()
                        .tooltipText(exception.getPermission())
                        .then(".")
                        .color(ChatColor.RED)
                    , sender);
            return;
        }
        super.handleCommandPermissionException(exception, sender, command, args);
    }

    public void handleCommandIllegalSenderException(CommandIllegalSenderException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {

        sender.sendMessage(StringUtil.toColor("&3MoonLake &7>> &c控制台不能使用这个命令."));
    }

    public void handleCommandUnhandledException(CommandUnhandledException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {

        if(sender instanceof Player) {

            MoonLakeAPI.chatComponentSent(
                    new ChatComponentFancy("MoonLake")
                            .color(ChatColor.DARK_AQUA)
                            .then(" >> ")
                            .color(ChatColor.GRAY)
                            .then("未知错误, 请查看控制台的错误反馈: ")
                            .color(ChatColor.RED)
                            .then("异常信息")
                            .color(ChatColor.AQUA)
                            .withUnderlined()
                            .tooltipText(exception.getMessage())
                    , sender);

            throw exception;
        }
        super.handleCommandUnhandledException(exception, sender, command, args);
    }

    public void handleCommandInvalidLengthException(CommandInvalidLengthException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {

        if(sender instanceof Player) {

            MoonLakeAPI.chatComponentSent(
                    new ChatComponentFancy("MoonLake")
                    .color(ChatColor.DARK_AQUA)
                    .then(" >> ")
                    .color(ChatColor.GRAY)
                    .then("错误的命令参数长度.")
                    .color(ChatColor.RED)
                    .tooltipText(exception.getMessage())
                    , sender);
            MoonLakeAPI.chatComponentSent(
                    new ChatComponentFancy("MoonLake")
                            .color(ChatColor.DARK_AQUA)
                            .then(" >> ")
                            .color(ChatColor.GRAY)
                            .then("请使用: /" + command.getName() + " " + command.getUsage())
                            .color(ChatColor.RED)
                    , sender);
            return;
        }
        super.handleCommandInvalidLengthException(exception, sender, command, args);
    }

    public void handleCommandArgumentParseException(CommandArgumentParseException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {

        if(sender instanceof Player) {

            MoonLakeAPI.chatComponentSent(
                    new ChatComponentFancy("MoonLake")
                            .color(ChatColor.DARK_AQUA)
                            .then(" >> ")
                            .color(ChatColor.GRAY)
                            .then("此命令参数解析错误: ")
                            .color(ChatColor.RED)
                            .then("异常信息")
                            .color(ChatColor.AQUA)
                            .withUnderlined()
                            .tooltipText(exception.getType().getSimpleName() + " -> " + exception.getArgument() + "\n\n" + exception.getMessage())
                    , sender);
            return;
        }
        super.handleCommandArgumentParseException(exception, sender, command, args);
    }
}
