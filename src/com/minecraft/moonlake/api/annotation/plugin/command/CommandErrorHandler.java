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

public class CommandErrorHandler {

    public CommandErrorHandler() {
    }

    public void handleCommandException(CommandException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {
    }

    public void handleCommandPermissionException(CommandPermissionException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {
    }

    public void handleCommandIllegalSenderException(CommandIllegalSenderException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {
    }

    public void handleCommandUnhandledException(CommandUnhandledException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {
    }

    public void handleCommandInvalidLengthException(CommandInvalidLengthException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {
    }

    public void handleCommandArgumentParseException(CommandArgumentParseException exception, CommandSender sender, org.bukkit.command.Command command, String[] args) {
    }
}
