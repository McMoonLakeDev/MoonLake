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


package com.minecraft.moonlake.api.annotation.plugin.command.exception;

public class CommandArgumentParseException extends CommandException {

    private String argument;
    private Class<?> type;

    public CommandArgumentParseException() {

        this("The command argument parse exception.");
    }

    public CommandArgumentParseException(String message) {

        super(message);
    }

    public CommandArgumentParseException(String message, Throwable cause) {

        super(message, cause);
    }

    public CommandArgumentParseException(String message, String argument, Class<?> type) {

        super(message);

        this.argument = argument;
        this.type = type;
    }

    public CommandArgumentParseException(String message, Throwable cause, String argument, Class<?> type) {

        super(message, cause);

        this.argument = argument;
        this.type = type;
    }

    public String getArgument() {

        return argument;
    }

    public Class<?> getType() {

        return type;
    }
}
