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
 
 
package com.minecraft.moonlake;

import com.minecraft.moonlake.api.MoonLake;
import com.minecraft.moonlake.api.player.CachedMoonLakePlayer;
import com.minecraft.moonlake.api.utility.MinecraftBukkitVersion;
import com.minecraft.moonlake.api.utility.MinecraftVersion;
import com.minecraft.moonlake.manager.ClassManager;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

/**
 * <hr />
 * <div>
 *     <h1>Minecraft MoonLake Core API Plugin</h1>
 *     <p>By Month_Light Ver: 1.9-a5.4</p>
 *     <p>Website: <a href="http://www.mcmoonlake.com" target="_blank" style="text-decoration: none;">MoonLake Website</a></p>
 *     <p>QQ Group: 377607025 -> <a href="http://jq.qq.com/?_wv=1027&k=2IfPFrH" target="_blank">Jump</a></p>
 *     <hr />
 *     <div>
 *         <h2>目前插件支持的服务端版本有:</h2>
 *         <ul>
 *             <li>Bukkit [1.8.x - 1.12.2] <span style="color: red">✔</span> 不完全支持</li>
 *             <li>Spigot [1.8.x - 1.12.2] <span style="color: rgb(85, 255, 85)">✔</span> 完完全全支持</li>
 *             <li>PaperSpigot [1.8.x - 1.12.2] <span style="color: rgb(85, 255, 85)">✔</span> 完完全全支持</li>
 *             <li>Cauldron | KCauldron [模组服务端] <span style="color: red">✘</span> 完全不支持</li>
 *         </ul>
 *     </div>
 * </div>
 * <hr />
 * <div>
 *     <h1>目前已经实现的功能有:</h1>
 *     <ul>
 *          <li>月色之湖玩家 {@link com.minecraft.moonlake.api.player.MoonLakePlayer}</li>
 *          <li>物品栈支持库 {@link com.minecraft.moonlake.api.item.ItemLibrary}</li>
 *          <li>数据库支持库 {@link com.minecraft.moonlake.mysql.MySQLConnection}</li>
 *          <li><s>花式消息支持库 {@link com.minecraft.moonlake.api.fancy.FancyMessage}</s></li>
 *          <li>聊天组件支持库 {@link com.minecraft.moonlake.api.chat.ChatComponent}</li>
 *          <li>NMS 数据包发送 {@link com.minecraft.moonlake.api.packet.Packet}</li>
 *          <li>NBT 操作支持库 {@link com.minecraft.moonlake.api.nbt.NBTLibrary}</li>
 *          <li>NBT 组件支持库 {@link com.minecraft.moonlake.nbt.NBTTagCompound}</li>
 *          <li>插件注解支持库 {@link com.minecraft.moonlake.api.annotation.plugin.PluginAnnotation}</li>
 *          <li>数据包通道监听器 {@link com.minecraft.moonlake.api.packet.listener.PacketListener}</li>
 *      </ul>
 *      <p>更多功能开发中 _(:з」∠)_</p>
 * </div>
 * <hr />
 * <div>
 *     <h1>此项目完全属于开源项目，如需修改和添加功能请到 <a href="https://github.com/u2g/MoonLake" target="_blank">GitHub</a> 进行 Fork 操作.</h1>
 *     <h1>修改操作请您遵守 <a href="https://github.com/u2g/MoonLake/blob/master/LICENSE" target="_blank">GPLv3</a> 协议，您必须公开修改过的所有代码！</h1>
 * </div>
 *
 * @version 1.9-a5.4
 * @author Month_Light
 */
public class MoonLakePlugin extends JavaPlugin implements MoonLake {

    private final PluginDescriptionFile description;
    private MoonLakePluginConfig configuration;

    private static MoonLake MAIN;

    /**
     * 月色之湖插件类构造函数
     */
    public MoonLakePlugin() {

        this.description = getDescription();
    }

    @Override
    public void onEnable() {

        MAIN = this;
        // set MoonLake API Object
        MoonLakeAPI.setMoonlake(this);
        // reload configuration
        this.configuration = new MoonLakePluginConfig(this);
        this.configuration.reload();
        // log mc and bukkit version info
        this.logServerVersion();
        // load library class
        this.loadLibraryClass();
        // print plugin enabled info
        this.getLogger().info("月色之湖核心 API 插件 v" + getPluginVersion() + " 成功加载.");
    }

    @Override
    public void onDisable() {
        // clear moonlake player cached
        CachedMoonLakePlayer.getInstance().clear();
    }

    private void loadLibraryClass() {
        // load depend player class to register listener
        ClassManager.forName("com.minecraft.moonlake.api.player.DependPlayerPluginListener", "The load depend player plugin listener exception.");
        // load PacketListenerFactory class
        if(getConfiguration().isPacketChannelListener())
            ClassManager.forName("com.minecraft.moonlake.api.packet.listener.PacketListenerFactory", "The load packet channel listener library class exception.");
    }

    private void logServerVersion() {
        // log mc and bukkit version info
        this.getLogger().log(Level.INFO, "服务器 {0} NMS: {1}", new Object[] { currentMCVersion(), currentBukkitVersion().getVersion() });
    }

    @Deprecated
    @SuppressWarnings("deprecation")
    public MoonLake getInstance() {

        return MAIN;
    }

    @Override
    public MoonLake getMoonLake() {

        return MAIN;
    }

    @Deprecated
    public static MoonLake getInstances() {

        return MAIN;
    }

    @Override
    public MoonLakePlugin getPlugin() {

        return this;
    }

    @Override
    public MoonLakePluginConfig getConfiguration() {

        return configuration;
    }

    @Override
    public String getPluginPrefix() {

        return description.getPrefix();
    }

    @Override
    public String getPluginName() {

        return description.getName();
    }

    @Override
    public String getPluginMain() {

        return description.getMain();
    }

    @Override
    public String getPluginVersion() {

        return description.getVersion();
    }

    @Override
    public String getPluginWebsite() {

        return description.getWebsite();
    }

    @Override
    public String getPluginDescription() {

        return description.getDescription();
    }

    @Override
    public Set<String> getPluginAuthors() {

        return new HashSet<>(description.getAuthors());
    }

    @Override
    public Set<String> getPluginDepends() {

        return new HashSet<>(description.getDepend());
    }

    @Override
    public Set<String> getPluginSoftDepends() {

        return new HashSet<>(description.getSoftDepend());
    }

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    public String getBukkitVersion() {

        return MoonLakeAPI.currentBukkitVersionString();
    }

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    public int getReleaseNumber() {

        return MoonLakeAPI.getReleaseNumber();
    }

    @Override
    public MinecraftBukkitVersion currentBukkitVersion() {

        return MoonLakeAPI.currentBukkitVersion();
    }

    @Override
    public MinecraftVersion currentMCVersion() {

        return MoonLakeAPI.currentMCVersion();
    }
}
