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


package com.minecraft.moonlake.api.annotation.plugin;

import com.minecraft.moonlake.api.annotation.plugin.command.*;
import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

/**
 * <h1>CommandAnnotationExpression</h1>
 * 命令注解接口实现类
 *
 * @version 1.0
 * @author Month_Light
 * @see CommandAnnotation
 */
class CommandAnnotationExpression extends PluginAnnotationAbstract implements CommandAnnotation {

    /**
     * 命令注解接口实现类构造函数
     */
    public CommandAnnotationExpression() {
    }

    @Override
    public void load(Plugin plugin, Object obj) throws MoonLakeException {

        registerCommand(plugin, obj);
    }

    @Override
    public void load(Plugin plugin, MoonLakeCommand obj) throws MoonLakeException {

        registerCommand(plugin, obj);
    }

    @Override
    public Set<CommandAnnotated> registerCommand(Plugin plugin, Object obj) throws MoonLakeException {

        Validate.notNull(plugin, "The plugin object is null.");
        Validate.notNull(obj, "The obj object is null.");

        if(!(obj instanceof MoonLakeCommand)) {
            // warn new version need implements MoonLakeCommand
            plugin.getLogger().log(Level.WARNING, "[MoonLake] 警告: 新版本核心插件命令类需要实现 MoonLakeCommand 接口. (请注意更新!)");
        }

        Class<?> clazz = obj.getClass();
        Set<CommandAnnotated> commands = new HashSet<>();
        Set<Method> commandCompletionMethods = new HashSet<>();

        for(final Method method : clazz.getDeclaredMethods()) {

            CommandCompletion commandCompletionAnnotation = method.getAnnotation(CommandCompletion.class);

            if(commandCompletionAnnotation != null) {

                commandCompletionMethods.add(method);
            }
        }
        for(final Method method : clazz.getDeclaredMethods()) {

            Command commandAnnotation = method.getAnnotation(Command.class);

            if(commandAnnotation != null) {

                Method commandCompletionMethod = null;
                CommandCompletion commandCompletionAnnotation = null;
                CommandPermission commandPermissionAnnotation = method.getAnnotation(CommandPermission.class);

                for(final Method method1 : commandCompletionMethods) {

                    CommandCompletion commandCompletion = method1.getAnnotation(CommandCompletion.class);

                    if(method1.getName().equals(method.getName()) || commandCompletion.name().equals(commandAnnotation.name())) {

                        commandCompletionMethod = method1;
                        commandCompletionAnnotation = commandCompletion;
                    }
                }
                CommandAnnotated commandAnnotated = new CommandAnnotatedExpression(
                        obj,
                        method,
                        commandAnnotation,
                        commandPermissionAnnotation,
                        commandCompletionMethod,
                        commandCompletionAnnotation);
                commands.add(((CommandAnnotatedExpression) commandAnnotated).register());
            }
        }
        return commands;
    }

    @Override
    public Set<CommandAnnotated> registerCommand(Plugin plugin, MoonLakeCommand obj) throws MoonLakeException {

        return registerCommand(plugin, (Object) obj);
    }
}
