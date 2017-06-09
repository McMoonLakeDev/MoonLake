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
import com.minecraft.moonlake.api.annotation.plugin.command.exception.*;
import com.minecraft.moonlake.api.nms.exception.NMSException;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.exception.MoonLakeException;
import com.minecraft.moonlake.manager.PlayerManager;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <h1>CommandAnnotatedExpression</h1>
 * 注解命令类接口实现类
 *
 * @version 1.0.1
 * @author Month_Light
 */
class CommandAnnotatedExpression implements CommandAnnotated {

    private final Object commandClass;
    private final Method commandMethod;
    private final Command commandAnnotation;
    private final CommandPermission commandPermissionAnnotation;
    private final Method commandCompletionMethod;
    private final CommandCompletion commandCompletionAnnotation;
    private final CommandErrorHandler commandErrorHandler;
    private final Class<?>[] commandMethodParameters;
    private final Class<?>[] commandCompletionMethodParameters;
    private String name;
    private String[] aliases;
    private String usage;
    private String description;
    private String permission;
    private String permissionMessage;
    private String fallBackPrefix;
    private CommandBukkit commandObject;
    private CommandMap commandMap;

    /**
     * 注解命令类接口实现类构造函数
     *
     * @param commandClass 预注册对象
     * @param commandMethod 命令函数
     * @param commandAnnotation 命令函数注解
     * @param commandPermissionAnnotation 命令权限注解
     * @param commandCompletionMethod 命令 TAB 补全函数
     * @param commandCompletionAnnotation 命令 TAB 补全注解
     */
    public CommandAnnotatedExpression(Object commandClass, Method commandMethod, Command commandAnnotation, CommandPermission commandPermissionAnnotation, Method commandCompletionMethod, CommandCompletion commandCompletionAnnotation) {

        this.commandClass = commandClass;
        this.commandMethod = commandMethod;
        this.commandAnnotation = commandAnnotation;
        this.commandPermissionAnnotation = commandPermissionAnnotation;
        this.commandCompletionMethod = commandCompletionMethod;
        this.commandCompletionAnnotation = commandCompletionAnnotation;
        this.commandMethodParameters = commandMethod != null ? commandMethod.getParameterTypes() : null;
        this.commandCompletionMethodParameters = commandCompletionMethod != null ? commandCompletionMethod.getParameterTypes() : null;
        this.name = commandAnnotation.name().isEmpty() ? commandMethod.getName() : commandAnnotation.name();
        this.aliases = commandAnnotation.aliases();
        this.usage = commandAnnotation.usage();
        this.description = commandAnnotation.description();
        this.fallBackPrefix = commandAnnotation.fallBackPrefix();

        if(commandAnnotation.errorHandler() != null) {

            try {

                commandErrorHandler = commandAnnotation.errorHandler().newInstance();
            }
            catch (Exception e) {

                throw new MoonLakeException("The moonlake command error handler exception.", e);
            }
        }
        else {

            commandErrorHandler = null;
        }
        if(commandPermissionAnnotation != null) {

            if(!commandPermissionAnnotation.value().isEmpty()) {

                this.permission = commandPermissionAnnotation.value();
            }
            if(!commandPermissionAnnotation.message().isEmpty()) {

                this.permissionMessage = commandPermissionAnnotation.message();
            }
        }
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public String[] getAliases() {

        return aliases;
    }

    @Override
    public String getUsage() {

        return usage;
    }

    @Override
    public String getDescription() {

        return description;
    }

    @Override
    public String getPermission() {

        return permission;
    }

    @Override
    public String getPermissionMessage() {

        return permissionMessage;
    }

    /**
     * 注册此注解命令
     *
     * @return 注解命令
     */
    protected CommandAnnotated register() {

        CommandBukkit commandBukkit = new CommandBukkit(name);
        commandObject = commandBukkit;

        if(description != null) {

            commandBukkit.setDescription(description);
        }
        if(usage != null) {

            commandBukkit.setUsage(usage);
        }
        if(permission != null) {

            commandBukkit.setPermission(permission);
        }
        if(permissionMessage != null) {

            commandBukkit.setPermissionMessage(permissionMessage);
        }
        if(aliases.length != 0) {

            commandBukkit.setAliases(Arrays.asList(aliases));
        }
        getCommandMap().register(fallBackPrefix != null ? fallBackPrefix : "", commandBukkit);

        return commandBukkit.executor = this;
    }

    /**
     * 处理此注解命令的执行函数
     *
     * @param sender 命令执行者
     * @param command 命令
     * @param label 命令标签
     * @param args 命令参数
     * @return 是否执行成功
     */
    protected boolean onCommand(CommandSender sender, CommandBukkit command, String label, String[] args) {

        try {

            if(!hasPermission(sender)) {

                throw new CommandPermissionException(permission);
            }
            if(args.length < commandAnnotation.min()) {

                throw new CommandInvalidLengthException(commandAnnotation.min(), args.length);
            }
            if(commandAnnotation.max() != -1 && args.length > commandAnnotation.max()) {

                throw new CommandInvalidLengthException(commandAnnotation.max(), args.length);
            }
            try {

                if(commandMethodParameters == null || commandMethodParameters.length == 0) {

                    throw new CommandException("The command method '" + this.commandMethod.getName() + " in " + this.commandClass + " is missing the CommandSender parameter.");
                }
                if(!CommandSender.class.isAssignableFrom(commandMethodParameters[0])) {

                    throw new CommandException("The first parameter of method '" + this.commandMethod.getName() + " in " + this.commandClass + " is no CommandSender.");
                }
                if((Player.class.isAssignableFrom(commandMethodParameters[0]) || MoonLakePlayer.class.isAssignableFrom(commandMethodParameters[0])) && !(sender instanceof Player)) {

                    throw new CommandIllegalSenderException();
                }
                if(commandMethodParameters.length - 1 < commandAnnotation.min() || (commandAnnotation.max() != -1 && commandMethodParameters.length - 1 > commandAnnotation.max())) {

                    throw new CommandException("The parameter length of method '" + this.commandMethod.getName() + " in " + this.commandClass + " is not in the specified argument length range.");
                }
                Object[] methodParameterTypeObjects = new Object[commandMethodParameters.length];

                for(int i = 1; i < args.length + 1; i++) {

                    if(i == commandMethodParameters.length - 1) {

                        CommandArgument commandArgument = getMethodParameterAnnotation(commandMethod, commandMethodParameters.length - 1, CommandArgument.class);

                        if(commandArgument != null) {

                            methodParameterTypeObjects[methodParameterTypeObjects.length - 1] = commandArguments(args, i - 1, commandArgument.arg());
                            break;
                        }
                        if(String[].class.isAssignableFrom(commandMethodParameters[commandMethodParameters.length - 1])) {

                            methodParameterTypeObjects[methodParameterTypeObjects.length - 1] = getLeftoverArguments(args, i - 1);
                            break;
                        }
                    }
                    if(i >= methodParameterTypeObjects.length) {

                        break;
                    }
                    methodParameterTypeObjects[i] = parseArgument(commandMethodParameters[i], args[i - 1]);
                }
                if(MoonLakePlayer.class.isAssignableFrom(commandMethodParameters[0])) {

                    methodParameterTypeObjects[0] = PlayerManager.getCache((Player) sender);
                }
                else {

                    methodParameterTypeObjects[0] = sender;
                }
                if(commandMethodParameters.length - 1 > args.length) {

                    for(int i = args.length; i < commandMethodParameters.length; i++) {

                        if(methodParameterTypeObjects[i] == null) {

                            CommandArgumentOptional commandArgumentOptional = getMethodParameterAnnotation(commandMethod, i, CommandArgumentOptional.class);

                            if(commandArgumentOptional != null && !commandArgumentOptional.def().isEmpty()) {

                                methodParameterTypeObjects[i] = parseArgument(commandMethodParameters[i], commandArgumentOptional.def());
                            }
                        }
                    }
                }
                commandMethod.invoke(commandClass, methodParameterTypeObjects);
            }
            catch (InvocationTargetException e) {

                Throwable cause = e.getCause();

                if(cause instanceof CommandException) {

                    throw ((CommandException) cause);
                }
                throw new CommandUnhandledException("The unhandled exception while invoking command method in " + commandClass + "#" + commandMethod.getName(), cause);
            }
            catch (CommandException e) {

                throw e;
            }
            catch (Throwable throwable) {

                throw new CommandUnhandledException("The Unhandled exception in " + this.commandClass + "#" + this.commandMethod.getName(), throwable);
            }
            return true;
        }
        catch (CommandPermissionException e) {

            if(commandErrorHandler != null) {

                commandErrorHandler.handleCommandPermissionException(e, sender, command, args);

                return false;
            }
            throw e;
        }
        catch (CommandIllegalSenderException e) {

            if(commandErrorHandler != null) {

                commandErrorHandler.handleCommandIllegalSenderException(e, sender, command, args);

                return false;
            }
            throw e;
        }
        catch (CommandArgumentParseException e) {

            if(commandErrorHandler != null) {

                commandErrorHandler.handleCommandArgumentParseException(e, sender, command, args);

                return false;
            }
            throw e;
        }
        catch (CommandInvalidLengthException e) {

            if(commandErrorHandler != null) {

                commandErrorHandler.handleCommandInvalidLengthException(e, sender, command, args);

                return false;
            }
            throw e;
        }
        catch(CommandUnhandledException e) {

            if(commandErrorHandler != null) {

                commandErrorHandler.handleCommandUnhandledException(e, sender, command, args);

                return false;
            }
            throw e;
        }
        catch (CommandException e) {

            if(commandErrorHandler != null) {

                commandErrorHandler.handleCommandException(e, sender, command, args);

                return false;
            }
            throw e;
        }
    }

    /**
     * 处理此注解命令的 TAB 补全
     *
     * @param sender 命令执行者
     * @param command 命令
     * @param alias 命令别称
     * @param args 命令参数
     * @return 补全集合
     */
    protected List<String> onTabComplete(CommandSender sender, CommandBukkit command, String alias, String[] args) {

        if(commandCompletionAnnotation == null || commandCompletionMethod == null) {

            return null;
        }
        if(!hasPermission(sender)) {

            return null;
        }
        try {

            if(commandCompletionMethodParameters == null || commandCompletionMethodParameters.length <= 1) {

                throw new CommandException("The completion method '" + commandCompletionMethod.getName() + " in " + commandClass + " is missing the List or CommandSender parameter");
            }
            if(!List.class.isAssignableFrom(commandCompletionMethodParameters[0])) {

                throw new CommandException("The first parameter of method '" + commandCompletionMethod.getName() + " in " + commandCompletionMethod + " is no List");
            }
            if(!CommandSender.class.isAssignableFrom(commandCompletionMethodParameters[1])) {

                throw new CommandException("The second parameter of method '" + commandCompletionMethod.getName() + " in " + commandCompletionMethod + " is no CommandSender");
            }
            if (Player.class.isAssignableFrom(commandCompletionMethodParameters[1]) || MoonLakePlayer.class.isAssignableFrom(commandCompletionMethodParameters[1])) {

                if (!(sender instanceof Player)) {

                    return null;
                }
            }
            Object[] methodParameterTypeObjects = new Object[commandCompletionMethodParameters.length];

            for(int i = 2; i < args.length + 2; i++) {

                if(i == commandCompletionMethodParameters.length - 1) {

                    CommandArgument commandArgument = getMethodParameterAnnotation(commandCompletionMethod, commandCompletionMethodParameters.length - 1, CommandArgument.class);

                    if(commandArgument != null) {

                        methodParameterTypeObjects[methodParameterTypeObjects.length - 1] = commandArguments(args, i - 1, commandArgument.arg());
                        break;
                    }
                    else if(String[].class.isAssignableFrom(commandCompletionMethodParameters[commandCompletionMethodParameters.length - 1])) {

                        methodParameterTypeObjects[methodParameterTypeObjects.length - 1] = getLeftoverArguments(args, i - 1);
                        break;
                    }
                }
                if(i >= methodParameterTypeObjects.length) {

                    break;
                }
                if(args[i - 2] == null || args[i - 2].isEmpty()) {

                    methodParameterTypeObjects[i] = null;
                }
                else {

                    try {

                        methodParameterTypeObjects[i] = parseArgument(commandCompletionMethodParameters[i], args[i - 2]);
                    }
                    catch (Exception e) {
                    }
                }
            }
            @SuppressWarnings("unchecked")
            List<String> list = (List<String>) (methodParameterTypeObjects[0] = new ArrayList<String>());

            if(MoonLakePlayer.class.isAssignableFrom(commandCompletionMethodParameters[1])) {

                methodParameterTypeObjects[1] = PlayerManager.getCache((Player) sender);
            }
            else {

                methodParameterTypeObjects[1] = sender;
            }
            commandCompletionMethod.invoke(commandClass, methodParameterTypeObjects);

            if(list.isEmpty())
                // 如果为空表示对应的 tab 函数可能没有处理
                // 则返回 bukkit 命令自带的处理函数
                return command.superTabComplete(sender, alias, args);

            return getPossibleCompletionsForGivenArgs(args, list);
        }
        catch (CommandException e) {

            throw e;
        }
        catch (Throwable throwable) {

            throw new CommandUnhandledException("The unhandled exception in " + commandClass + "#" + commandCompletionMethod.getName(), throwable);
        }
    }

    /**
     * 获取指定命令执行者是否拥有权限
     *
     * @param sender 命令执行者
     * @return 是否拥有权限
     */
    protected boolean hasPermission(CommandSender sender) {

        return commandPermissionAnnotation == null || sender.hasPermission(permission);
    }

    /**
     * 获取指定函数的指定参数索引的注解
     *
     * @param method 函数
     * @param index 索引
     * @param clazz 注解类
     * @param <A> 注解类
     * @return 注解
     */
    @SuppressWarnings("unchecked")
    protected <A extends Annotation> A getMethodParameterAnnotation(Method method, int index, Class<A> clazz) {

        Annotation[] annotations = method.getParameterAnnotations()[index];

        if(annotations != null) {

            for(final Annotation annotation : annotations) {

                if(clazz.isAssignableFrom(annotation.getClass())) {

                    return (A) annotation;
                }
            }
        }
        return null;
    }

    /**
     * 获取指定函数的指定参数的注解
     *
     * @param method 函数
     * @param clazz 注解类
     * @param <A> 注解类
     * @return 注解
     */
    @SuppressWarnings("unchecked")
    protected <A extends Annotation> A getMethodParameterAnnotation(Method method, Class<A> clazz) {

        Annotation[][] annotationss = method.getParameterAnnotations();

        if(annotationss != null) {

            for(final Annotation[] annotations : annotationss) {

                for(final Annotation annotation : annotations) {

                    if(clazz.isAssignableFrom(annotation.getClass())) {

                        return (A) annotation;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 处理此命令参数的补全
     *
     * @param args 命令参数
     * @param start 开始索引
     * @param arg 命令参数
     * @return 命令补全
     */
    protected String commandArguments(String[] args, int start, String arg) {

        Validate.notNull(start <= args.length, "The start index > args length exception.");

        StringBuilder argsBuilder = new StringBuilder();

        for(int i = start; i < args.length; i++) {

            if(i != start) {

                argsBuilder.append(arg);
            }
            argsBuilder.append(args[i]);
        }
        return argsBuilder.toString();
    }

    /**
     * 获取命令参数从指定索引剩余的参数
     *
     * @param args 命令参数
     * @param start 开始索引
     * @return 命令参数
     */
    protected String[] getLeftoverArguments(String[] args, int start) {

        String[] newArray = new String[args.length - start];

        for(int i = start; i < args.length; i++) {

            newArray[i - start] = args[i];
        }
        return newArray;
    }

    /**
     * 获取 Bukkit 的命令 Map 对象
     *
     * @return CommandMap
     * @throws NMSException 如果获取错误则抛出异常
     */
    protected CommandMap getCommandMap() throws NMSException {

        if(commandMap == null) {

            try {

                Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                field.setAccessible(true);

                commandMap = (CommandMap) field.get(Bukkit.getServer());
            }
            catch (Exception e) {

                throw new NMSException("The get command map reflect exception.", e);
            }
        }
        return commandMap;
    }

    /**
     * 解析命令参数
     *
     * @param parameterType 参数类型
     * @param argument 参数
     * @return 参数实例
     */
    protected Object parseArgument(Class<?> parameterType, String argument) {

        try {

            if(String.class.isAssignableFrom(parameterType)) {

                return argument;
            }
            if(Number.class.isAssignableFrom(parameterType)) {

                String parseName = parameterType.getSimpleName();

                if(Integer.class.equals(parameterType)) {

                    parseName = "Int";
                }
                return parameterType.getDeclaredMethod("parse" + parseName, String.class).invoke(null, argument);
            }
            if(Enum.class.isAssignableFrom(parameterType)) {

                return MinecraftReflection.enumOfNameAny(parameterType, argument);
            }
            throw new CommandArgumentParseException("The failed to parse argument '" + argument + "' to " + parameterType, argument, parameterType);
        }
        catch (ReflectiveOperationException e) {

            Throwable cause = e.getCause();

            if(cause instanceof NumberFormatException) {

                throw new CommandArgumentParseException("The could not parse number " + argument, argument, parameterType);
            }
            throw new CommandArgumentParseException("The exception while parsing argument '" + argument + "' to " + parameterType, e, argument, parameterType);
        }
    }

    /**
     * 将给予的参数处理可能的 TAB 补全
     *
     * @param args 命令参数
     * @param possibilities 可能的集合
     * @return 可能补全的集合
     */
    protected List<String> getPossibleCompletionsForGivenArgs(String[] args, List<String> possibilities) {

        final String argumentToFindCompletionFor = args[args.length - 1];
        final List<String> listOfPossibleCompletions = new ArrayList<>();

        for(final String possiblilitie : possibilities) {

            try {

                if (possiblilitie != null && possiblilitie.regionMatches(true, 0, argumentToFindCompletionFor, 0, argumentToFindCompletionFor.length())) {

                    listOfPossibleCompletions.add(possiblilitie);
                }
            }
            catch (Exception e) {

                throw new MoonLakeException("The get possible completions for given argument exception.", e);
            }
        }
        Collections.sort(listOfPossibleCompletions);

        return listOfPossibleCompletions;
    }

    /**
     * <h1>CommandBukkit</h1>
     * Bukkit 命令包装类
     *
     * @version 1.0
     * @author Month_Light
     */
    protected class CommandBukkit extends org.bukkit.command.Command {

        private CommandAnnotatedExpression executor;

        /**
         * Bukkit 命令包装类构造函数
         *
         * @param name 名称
         */
        protected CommandBukkit(String name) {

            super(name);
        }

        @Override
        public boolean execute(CommandSender sender, String label, String[] args) {

            return executor != null && executor.onCommand(sender, this, label, args);
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {

            List<String> result = executor != null ? executor.onTabComplete(sender, this, alias, args) : null;
            return result != null ? result : superTabComplete(sender, alias, args);
        }

        private List<String> superTabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {

            return super.tabComplete(sender, alias, args);
        }
    }
}
