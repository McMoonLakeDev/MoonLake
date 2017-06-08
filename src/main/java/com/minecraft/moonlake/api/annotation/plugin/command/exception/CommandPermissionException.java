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

/**
 * <h1>CommandPermissionException</h1>
 * 命令权限错误异常类
 *
 * @version 1.1
 * @author Month_Light
 */
public class CommandPermissionException extends CommandException {

    private static final long serialVersionUID = 19993591275811075L;
    private String permission;

    /**
     * 命令权限错误异常类构造函数
     *
     * @param permission 权限
     */
    public CommandPermissionException(String permission) {

        super("The moonlake command sender not has permission exception.");

        this.permission = permission;
    }

    /**
     * 获取此命令的权限值
     *
     * @return 权限
     */
    public String getPermission() {

        return permission;
    }
}
