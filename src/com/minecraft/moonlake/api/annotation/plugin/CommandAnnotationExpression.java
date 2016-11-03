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

class CommandAnnotationExpression extends PluginAnnotationAbstract implements CommandAnnotation {

    public CommandAnnotationExpression() {
    }

    @Override
    public void load(Plugin plugin, Object obj) throws MoonLakeException {

        registerCommand(plugin, obj);
    }

    @Override
    public Set<CommandAnnotated> registerCommand(Plugin plugin, Object obj) throws MoonLakeException {

        Validate.notNull(plugin, "The plugin object is null.");
        Validate.notNull(obj, "The obj object is null.");

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

                    if(method1.getName().equals(method.getName())) {

                        commandCompletionMethod = method1;
                        commandCompletionAnnotation = method1.getAnnotation(CommandCompletion.class);
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
}
