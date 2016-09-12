package com.minecraft.moonlake;

import com.minecraft.moonlake.api.MLogger;
import com.minecraft.moonlake.api.MoonLake;
import com.minecraft.moonlake.api.itemlib.Itemlib;
import com.minecraft.moonlake.api.lorelib.Lorelib;
import com.minecraft.moonlake.api.playerlib.Playerlib;
import com.minecraft.moonlake.util.item.ItemUtil;
import com.minecraft.moonlake.util.lore.LoreUtil;
import com.minecraft.moonlake.util.player.PlayerUtil;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

/**
 * <h1>Minecraft <a href="http://www.mcyszh.com">MoonLake</a> Core API Plugin</h1>
 * <h6>By Month_Light Q: 1327516533</h6>
 * @version 1.6.8
 * @author Month_Light
 */
public class MoonLakePlugin extends JavaPlugin implements MoonLake {

    private final MLogger mLogger;
    private final Itemlib itemlib;
    private final Lorelib lorelib;
    private final Playerlib playerlib;
    private final PluginDescriptionFile pdf;
    private final String prefix = "[MoonLake]";

    private static MoonLake MAIN;

    static { }

    public MoonLakePlugin() {

        MAIN = this;

        mLogger = new MLogger.Wrapped("MoonLake");
        pdf = this.getDescription();
        itemlib = new ItemUtil();
        lorelib = new LoreUtil();
        playerlib = new PlayerUtil();
    }

    @Override
    public void onEnable() {

        this.log("月色之湖核心 API 插件 v" + getPluginVersion() + " 成功加载.");
    }

    /**
     * 获取 MoonLake 实例对象
     *
     * @return MoonLake
     */
    public MoonLake getInstance() {
        return MAIN;
    }

    /**
     * 获取 MoonLake 实例对象 (静态函数获取实例不建议使用)
     *
     * @return MoonLake
     */
    @Deprecated
    public static MoonLake getInstances() {
        return MAIN;
    }

    /**
     * 给控制台输出日志
     *
     * @param log 日志
     */
    @Override
    public void log(String log) {

        mLogger.log(log);
    }

    /**
     * 获取控制台日志对象
     *
     * @return 日志对象
     */
    @Override
    public MLogger getMLogger() {

        return mLogger;
    }

    /**
     * 获取物品支持库实例对象
     *
     * @return Itemlib
     */
    @Override
    public Itemlib getItemlib() {

        return itemlib;
    }

    /**
     * 获取标签支持库实例对象
     *
     * @return Lorelib
     */
    @Override
    public Lorelib getLorelib() {

        return lorelib;
    }

    /**
     * 获取玩家支持库实例对象
     *
     * @return Playerlib
     */
    @Override
    public Playerlib getPlayerlib() {

        return playerlib;
    }

    /**
     * 获取插件的称号
     *
     * @return Prefix
     */
    @Override
    public String getPluginPrefix() {

        return pdf.getPrefix();
    }

    /**
     * 获取插件的名字
     *
     * @return Name
     */
    @Override
    public String getPluginName() {

        return pdf.getName();
    }

    /**
     * 获取插件的主类
     *
     * @return MainClass
     */
    @Override
    public String getPluginMain() {

        return pdf.getMain();
    }

    /**
     * 获取插件的版本
     *
     * @return Version
     */
    @Override
    public String getPluginVersion() {

        return pdf.getVersion();
    }

    /**
     * 获取插件的网站
     *
     * @return Website
     */
    @Override
    public String getPluginWebsite() {

        return pdf.getWebsite();
    }

    /**
     * 获取插件的简介
     *
     * @return Description
     */
    @Override
    public String getPluginDescription() {

        return pdf.getDescription();
    }

    /**
     * 获取插件的作者
     *
     * @return Auther
     */
    @Override
    public Set<String> getPluginAuthers() {

        return new HashSet<>(pdf.getAuthors());
    }

    /**
     * 获取插件的依赖
     *
     * @return Depend
     */
    @Override
    public Set<String> getPluginDepends() {

        return new HashSet<>(pdf.getDepend());
    }

    /**
     * 获取插件的软依赖
     *
     * @return SoftDepend
     */
    @Override
    public Set<String> getPluginSoftDepends() {

        return new HashSet<>(pdf.getSoftDepend());
    }

    /**
     * 获取 Bukkit 服务器的版本
     *
     * @return 版本
     */
    @Override
    public String getBukkitVersion() {

        String packageName = getServer().getClass().getPackage().getName();
        String[] packageSplit = packageName.split("\\.");
        return packageSplit[packageSplit.length - 1];
    }

    /**
     * 获取 Bukkit 服务器的版本号
     *
     * @return 版本号
     */
    @Override
    public int getReleaseNumber() {
        String version = getBukkitVersion();
        String[] versionSplit = version.split("_");
        String releaseVersion = versionSplit[1];
        return Integer.parseInt(releaseVersion);
    }
}
