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


package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.player.depend.DependPermissionsEx;
import com.minecraft.moonlake.api.player.depend.DependPluginPlayerAbstract;
import com.minecraft.moonlake.exception.CannotDependException;
import com.minecraft.moonlake.exception.CannotDependVersionException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>DependPermissionsExPlayer</h1>
 * 依赖权限插件玩家实现类 # 依赖 <a href="https://github.com/PEXPlugins/PermissionsEx" target="_blank">PermissionsEx</a> 插件
 *
 * @version 1.0
 * @author Month_Light
 */
class DependPermissionsExPlayer extends DependPluginPlayerAbstract<PermissionsEx> implements DependPermissionsEx {

    private PermissionManager permissionManager;

    /**
     * 依赖权限插件玩家实现类构造函数
     *
     * @throws CannotDependException 如果无法加载依赖插件则抛出异常
     * @throws CannotDependVersionException 如果加载依赖插件的版本不符合则抛出异常
     */
    public DependPermissionsExPlayer() throws CannotDependException, CannotDependVersionException {

        super((PermissionsEx) Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx"));

        if(getOwn() == null) {

            throw new CannotDependException("The cannot depend 'PermissionsEx' plugin exception.");
        }
        // 检查 PermissionsEx 插件版本, 本依赖最低需要 1.23 版本
        String version = getPluginVersion();

        if(version.compareTo("1.23") < 0) {
            // 服务端加载的 PermissionsEx 插件版本小于 1.23 则抛出异常
            throw new CannotDependVersionException("The depend 'PermissionsEx' plugin, but version less than 1.23 exception.");
        }
        this.permissionManager = getOwn().getPermissionsManager();

        MoonLakeAPI.getLogger().info("Success hook 'PermissionsEx' plugin, 'PermissionsExPlayer' interface be use.");
    }

    /**
     * 获取指定玩家的 PermissionUser 权限用户对象
     *
     * @param player 玩家
     * @return PermissionUser
     */
    protected PermissionUser getPermissionsExUser(Player player) {

        return permissionManager.getUser(player);
    }

    /**
     * 获取指定玩家的 PermissionUser 权限用户对象
     *
     * @param moonLakePlayer 月色之湖玩家
     * @return PermissionUser
     */
    protected PermissionUser getPermissionsExUser(MoonLakePlayer moonLakePlayer) {

        return getPermissionsExUser(moonLakePlayer.getBukkitPlayer());
    }

    /**
     * 将指定玩家添加指定权限组
     *
     * @param moonLakePlayer 月色之湖玩家
     * @param groupName 权限组名
     */
    public void addPermissionsExGroup(MoonLakePlayer moonLakePlayer, String groupName) {

        addPermissionsExGroup(moonLakePlayer, groupName, null);
    }

    /**
     * 将指定玩家添加指定权限组在指定世界
     *
     * @param moonLakePlayer 月色之湖玩家
     * @param groupName 权限组名
     * @param worldName 世界名
     */
    public void addPermissionsExGroup(MoonLakePlayer moonLakePlayer, String groupName, String worldName) {

        PermissionUser user = getPermissionsExUser(moonLakePlayer);

        if(user != null) {

            user.addGroup(groupName, worldName);
        }
    }

    /**
     * 将指定玩家删除指定权限组
     *
     * @param moonLakePlayer 月色之湖玩家
     * @param groupName 权限组名
     */
    public void removePermissionsExGroup(MoonLakePlayer moonLakePlayer, String groupName) {

        removePermissionsExGroup(moonLakePlayer, groupName, null);
    }

    /**
     * 将指定玩家删除指定权限组在指定世界
     *
     * @param moonLakePlayer 月色之湖玩家
     * @param groupName 权限组名
     * @param worldName 世界名
     */
    public void removePermissionsExGroup(MoonLakePlayer moonLakePlayer, String groupName, String worldName) {

        PermissionUser user = getPermissionsExUser(moonLakePlayer);

        if(user != null) {

            user.removeGroup(groupName, worldName);
        }
    }

    /**
     * 获取指定玩家是否在指定权限组
     *
     * @param moonLakePlayer 月色之湖玩家
     * @param groupName 权限组名
     * @return 是否在指定权限组
     */
    public boolean inPermissionsExGroup(MoonLakePlayer moonLakePlayer, String groupName) {

        return inPermissionsExGroup(moonLakePlayer, groupName, null);
    }

    /**
     * 获取指定玩家是否在指定世界的指定权限组
     *
     * @param moonLakePlayer 月色之湖玩家
     * @param groupName 权限组名
     * @param worldName 世界名
     * @return 是否在指定世界的指定权限组
     */
    public boolean inPermissionsExGroup(MoonLakePlayer moonLakePlayer, String groupName, String worldName) {

        return inPermissionsExGroup(moonLakePlayer, groupName, worldName, true);
    }

    /**
     * 获取指定玩家是否在指定权限组
     *
     * @param moonLakePlayer 月色之湖玩家
     * @param groupName 权限组名
     * @param checkInheritance 是否子权限组也进行检查
     * @return 是否在指定权限组
     */
    public boolean inPermissionsExGroup(MoonLakePlayer moonLakePlayer, String groupName, boolean checkInheritance) {

        return inPermissionsExGroup(moonLakePlayer, groupName, null, checkInheritance);
    }

    /**
     * 获取指定玩家是否在指定世界的指定权限组
     *
     * @param moonLakePlayer 月色之湖玩家
     * @param groupName 权限组名
     * @param worldName 世界名
     * @param checkInheritance 是否子权限组也进行检查
     * @return 是否在指定世界的指定权限组
     */
    public boolean inPermissionsExGroup(MoonLakePlayer moonLakePlayer, String groupName, String worldName, boolean checkInheritance) {

        PermissionUser user = getPermissionsExUser(moonLakePlayer);

        return user != null && user.inGroup(groupName, worldName, checkInheritance);
    }

    /**
     * 获取指定玩家拥有的权限列表
     *
     * @param moonLakePlayer 月色之湖玩家
     * @return 权限列表
     */
    public List<String> getPermissionsExPer(MoonLakePlayer moonLakePlayer) {

        return getPermissionsExPer(moonLakePlayer, null);
    }

    /**
     * 获取指定玩家在指定世界拥有的权限列表
     *
     * @param moonLakePlayer 月色之湖玩家
     * @return 权限列表
     */
    public List<String> getPermissionsExPer(MoonLakePlayer moonLakePlayer, String worldName) {

        PermissionUser user = getPermissionsExUser(moonLakePlayer);

        return user != null ? new ArrayList<>(user.getPermissions(worldName)) : new ArrayList<>();
    }

    /**
     * 获取指定玩家的权限前缀
     *
     * @param moonLakePlayer 月色之湖玩家
     * @return 前缀
     */
    public String getPermissionsExPrefix(MoonLakePlayer moonLakePlayer) {

        return getPermissionsExPrefix(moonLakePlayer, null);
    }

    /**
     * 获取指定玩家在指定世界的权限前缀
     *
     * @param moonLakePlayer 月色之湖玩家
     * @param worldName 世界名
     * @return 前缀
     */
    public String getPermissionsExPrefix(MoonLakePlayer moonLakePlayer, String worldName) {

        PermissionUser user = getPermissionsExUser(moonLakePlayer);

        return user != null ? user.getPrefix(worldName) : "";
    }

    /**
     * 获取指定玩家的权限后缀
     *
     * @param moonLakePlayer 月色之湖玩家
     * @return 后缀
     */
    public String getPermissionsExSuffix(MoonLakePlayer moonLakePlayer) {

        return getPermissionsExSuffix(moonLakePlayer, null);
    }

    /**
     * 获取指定玩家在指定世界的权限后缀
     *
     * @param moonLakePlayer 月色之湖玩家
     * @param worldName 世界名
     * @return 后缀
     */
    public String getPermissionsExSuffix(MoonLakePlayer moonLakePlayer, String worldName) {

        PermissionUser user = getPermissionsExUser(moonLakePlayer);

        return user != null ? user.getSuffix(worldName) : "";
    }

    /**
     * 获取指定玩家的自己权限前缀
     *
     * @param moonLakePlayer 月色之湖玩家
     * @return 前缀
     */
    public String getPermissionsExOwnPerfix(MoonLakePlayer moonLakePlayer) {

        return getPermissionsExOwnPerfix(moonLakePlayer, null);
    }

    /**
     * 获取指定玩家在指定世界的自己权限前缀
     *
     * @param moonLakePlayer 月色之湖玩家
     * @param worldName 世界名
     * @return 前缀
     */
    public String getPermissionsExOwnPerfix(MoonLakePlayer moonLakePlayer, String worldName) {

        PermissionUser user = getPermissionsExUser(moonLakePlayer);

        return user != null ? user.getOwnPrefix(worldName) : "";
    }

    /**
     * 获取指定玩家的自己权限后缀
     *
     * @param moonLakePlayer 月色之湖玩家
     * @return 后缀
     */
    public String getPermissionsExOwnSuffix(MoonLakePlayer moonLakePlayer) {

        return getPermissionsExOwnSuffix(moonLakePlayer, null);
    }

    /**
     * 获取指定玩家在指定世界的自己权限后缀
     *
     * @param moonLakePlayer 月色之湖玩家
     * @param worldName 世界名
     * @return 后缀
     */
    public String getPermissionsExOwnSuffix(MoonLakePlayer moonLakePlayer, String worldName) {

        PermissionUser user = getPermissionsExUser(moonLakePlayer);

        return user != null ? user.getOwnSuffix(worldName) : "";
    }

    /**
     * 获取指定玩家拥有的权限组名数组
     *
     * @param moonLakePlayer 月色之湖玩家
     * @return 权限组名数组
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    public String[] getPermissionsExGroupNames(MoonLakePlayer moonLakePlayer) {

        return getPermissionsExGroupNames(moonLakePlayer, null);
    }

    /**
     * 获取指定玩家在指定世界拥有的权限组名数组
     *
     * @param moonLakePlayer 月色之湖玩家
     * @param worldName 世界名
     * @return 权限组名数组
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    public String[] getPermissionsExGroupNames(MoonLakePlayer moonLakePlayer, String worldName) {

        PermissionUser user = getPermissionsExUser(moonLakePlayer);

        return user != null ? user.getGroupNames(worldName) : new String[0];
    }

    /**
     * 设置指定玩家的权限组
     *
     * @param moonLakePlayer 月色之湖玩家
     * @param groupName 权限组名
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    public void setPermissionsExGroup(MoonLakePlayer moonLakePlayer, String groupName) {

        setPermissionsExGroup(moonLakePlayer, groupName, null);
    }

    /**
     * 设置指定玩家在指定世界的权限组
     *
     * @param moonLakePlayer 月色之湖玩家
     * @param groupName 权限组名
     * @param worldName 世界名
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    public void setPermissionsExGroup(MoonLakePlayer moonLakePlayer, String groupName, String worldName) {

        setPermissionsExGroups(moonLakePlayer, new String[] { groupName }, worldName);
    }

    /**
     * 设置指定玩家的权限组数组
     *
     * @param moonLakePlayer 月色之湖玩家
     * @param groupNames 权限组名数组
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    public void setPermissionsExGroups(MoonLakePlayer moonLakePlayer, String[] groupNames) {

        setPermissionsExGroups(moonLakePlayer, groupNames, null);
    }

    /**
     * 设置指定玩家在指定世界的权限组数组
     *
     * @param moonLakePlayer 月色之湖玩家
     * @param groupNames 权限组名数组
     * @param worldName 世界名
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    public void setPermissionsExGroups(MoonLakePlayer moonLakePlayer, String[] groupNames, String worldName) {

        PermissionUser user = getPermissionsExUser(moonLakePlayer);

        if(user != null) {

            user.setGroups(groupNames, worldName);
        }
    }

    /**
     * 获取指定玩家拥有的权限组对象数组
     *
     * @param moonLakePlayer 月色之湖玩家
     * @return 权限组对象数组
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    protected PermissionGroup[] getPermissionsExGroups(MoonLakePlayer moonLakePlayer) {

        return getPermissionsExGroups(moonLakePlayer, null);
    }

    /**
     * 获取指定玩家在指定世界拥有的权限组对象数组
     *
     * @param moonLakePlayer 月色之湖玩家
     * @param worldName 世界名
     * @return 权限组对象数组
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    protected PermissionGroup[] getPermissionsExGroups(MoonLakePlayer moonLakePlayer, String worldName) {

        PermissionUser user = getPermissionsExUser(moonLakePlayer);

        return user != null ? user.getGroups(worldName) : new PermissionGroup[0];
    }
}
