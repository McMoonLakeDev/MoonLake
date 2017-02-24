/*
 * Copyright (C) 2017 The MoonLake Authors
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


package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.event.MoonLakeListener;
import com.minecraft.moonlake.event.EventHelper;
import com.minecraft.moonlake.exception.CannotDependException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

class DependPlayerPluginListener {

    private static DependEconomyPlayer dependEconomyPlayer;
    private static Boolean dependEconomyPlayerHook = null;
    private static DependPermissionsExPlayer dependPermissionsExPlayer;
    private static Boolean dependPermissionsExPlayerHook = null;
    private static DependEconomyVaultPlayer dependEconomyVaultPlayer;
    private static Boolean dependEconomyVaultPlayerHook = null;
    private static DependWorldEditPlayer dependWorldEditPlayer;
    private static Boolean dependWorldEditPlayerHook = null;

    static {

        // 初始化插件重载事件监听器
        // 单一实例里面注册事件, 那么不让 jvm 加载这个类的话这个事件不会被注册
        // 这样让用户调用一次后再加载事件, 这样如果他用插件管理之类的插件
        // 加载或者卸载其他插件, 这里就能让 API 接口的功能重新去 Hook
        EventHelper.registerEvent(new MoonLakeListener() {

            @EventHandler(priority = EventPriority.LOWEST)
            public void onEnable(PluginEnableEvent event) {

                Plugin plugin = event.getPlugin();
                String pluginName = plugin.getName();

                try {

                    if(pluginName.equals("MoonLakeEconomy") && dependEconomyPlayerHook == null) {
                        // Hook MoonLakeEconomy
                        economyPlayer();
                    }
                    else if(pluginName.equals("PermissionsEx") && dependPermissionsExPlayerHook == null) {
                        // Hook PermissionsEx
                        permissionsExPlayer();
                    }
                    else if(pluginName.equals("Vault") && dependEconomyVaultPlayerHook == null) {
                        // Hook Vault Economy
                        economyVaultPlayer();
                    }
                    else if(pluginName.equals("WorldEdit") && dependWorldEditPlayerHook == null) {
                        // Hook WorldEdit
                        worldEditPlayer();
                    }
                }
                catch (Exception e) {

                    MoonLakeAPI.getLogger().log(Level.SEVERE, "The hook '" + pluginName + "' plugin failed, exception info: ", e);
                }
            }

            @EventHandler(priority = EventPriority.LOWEST)
            public void onDisable(PluginDisableEvent event) {

                Plugin plugin = event.getPlugin();
                String pluginName = plugin.getName();

                if(pluginName.equals("MoonLakeEconomy") && dependEconomyPlayerHook != null) {
                    // Unhook MoonLakeEconomy
                    dependEconomyPlayer = null;
                    dependEconomyPlayerHook = null;

                    MoonLakeAPI.getLogger().warning("The unhook 'MoonLakeEconomy' plugin, now 'EconomyPlayer' interface is unavailable.");
                }
                else if(pluginName.equals("PermissionsEx") && dependPermissionsExPlayerHook != null) {
                    // Unhook PermissionsEx
                    dependPermissionsExPlayer = null;
                    dependPermissionsExPlayerHook = null;

                    MoonLakeAPI.getLogger().warning("The unhook 'PermissionsEx' plugin, now 'PermissionsExPlayer' interface is unavailable.");
                }
                else if(pluginName.equals("Vault") && dependEconomyVaultPlayerHook != null) {
                    // Unhook Vault Economy
                    dependEconomyVaultPlayer = null;
                    dependEconomyVaultPlayerHook = null;

                    MoonLakeAPI.getLogger().warning("The unhook 'Vault' plugin 'Economy' module, now 'EconomyVaultPlayer' interface is unavailable.");
                }
                else if(pluginName.equals("WorldEdit") && dependWorldEditPlayerHook != null) {
                    // Unhook WorldEdit
                    dependWorldEditPlayer = null;
                    dependWorldEditPlayerHook = null;

                    MoonLakeAPI.getLogger().warning("The unhook 'WorldEdit' plugin, now 'WorldEditPlayer' interface is unavailable.");
                }
            }
        }, MoonLakeAPI.getMoonLake());
        ///
        MoonLakeAPI.getLogger().info("月色之湖玩家支持库的依赖监听器成功加载.");
    }

    /**
     * 获取依赖经济插件玩家 DependEconomyPlayer 对象
     *
     * @return DependEconomyPlayer
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    public static DependEconomyPlayer economyPlayer() throws CannotDependException {

        if(dependEconomyPlayer == null && dependEconomyPlayerHook == null) {

            try {

                dependEconomyPlayer = new DependEconomyPlayer();
                dependEconomyPlayerHook = true;
            }
            catch (Exception e) {

                dependEconomyPlayerHook = false;

                throw new CannotDependException("The hook 'MoonLakeEconomy' plugin failed exception.", e);
            }
        }
        return dependEconomyPlayer;
    }

    /**
     * 获取是否成功依赖经济插件玩家
     *
     * @return 是否成功依赖
     */
    public static boolean isDependEconomyPlayerHook() {

        return dependEconomyPlayerHook;
    }

    /**
     * 获取依赖权限插件玩家 DependPermissionsExPlayer 对象
     *
     * @return DependPermissionsExPlayer
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    public static DependPermissionsExPlayer permissionsExPlayer() throws CannotDependException {

        if(dependPermissionsExPlayer == null && dependPermissionsExPlayerHook == null) {

            try {

                dependPermissionsExPlayer = new DependPermissionsExPlayer();
                dependPermissionsExPlayerHook = true;
            }
            catch (Exception e) {

                dependPermissionsExPlayerHook = false;

                throw new CannotDependException("The hook 'PermissionsEx' plugin failed exception.", e);
            }
        }
        return dependPermissionsExPlayer;
    }

    /**
     * 获取是否成功依赖权限插件玩家
     *
     * @return 是否成功依赖
     */
    public static boolean isDependPermissionsExPlayerHook() {

        return dependPermissionsExPlayerHook;
    }

    /**
     * 获取依赖 Vault 经济插件玩家 DependEconomyVaultPlayer 对象
     *
     * @return DependEconomyVaultPlayer
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    public static DependEconomyVaultPlayer economyVaultPlayer() throws CannotDependException {

        if(dependEconomyVaultPlayer == null && dependEconomyVaultPlayerHook == null) {

            try {

                dependEconomyVaultPlayer = new DependEconomyVaultPlayer();
                dependEconomyVaultPlayerHook = true;
            }
            catch (Exception e) {

                dependEconomyVaultPlayerHook = false;

                throw new CannotDependException("The hook 'Vault' plugin 'Economy' module failed exception.", e);
            }
        }
        return dependEconomyVaultPlayer;
    }

    /**
     * 获取是否成功依赖 Vault 经济插件玩家
     *
     * @return 是否成功依赖
     */
    public static boolean isDependEconomyVaultPlayerHook() {

        return dependEconomyVaultPlayerHook;
    }

    /**
     * 获取依赖传送书插件玩家 DependWorldEditPlayer 对象
     *
     * @return DependWorldEditPlayer
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     */
    public static DependWorldEditPlayer worldEditPlayer() throws CannotDependException {

        if(dependWorldEditPlayer == null && dependWorldEditPlayerHook == null) {

            try {

                dependWorldEditPlayer = new DependWorldEditPlayer();
                dependWorldEditPlayerHook = true;
            }
            catch (Exception e) {

                dependWorldEditPlayerHook = false;

                throw new CannotDependException("The hook 'WorldEdit' plugin failed exception.", e);
            }
        }
        return dependWorldEditPlayer;
    }

    /**
     * 获取是否成功依赖 WorldEdit 创世神插件玩家
     *
     * @return 是否成功依赖
     */
    public static boolean isDependWorldEditPlayerHook() {

        return dependWorldEditPlayerHook;
    }
}
