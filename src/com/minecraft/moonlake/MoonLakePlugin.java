package com.minecraft.moonlake;

import com.minecraft.moonlake.api.MoonLake;
import com.minecraft.moonlake.api.itemlib.Itemlib;
import com.minecraft.moonlake.api.lorelib.Lorelib;
import com.minecraft.moonlake.api.playerlib.Playerlib;
import com.minecraft.moonlake.api.potionlib.CustomPotionEffect;
import com.minecraft.moonlake.type.potion.PotionEffectEnum;
import com.minecraft.moonlake.type.potion.PotionEnum;
import com.minecraft.moonlake.util.item.ItemUtil;
import com.minecraft.moonlake.util.lore.LoreUtil;
import com.minecraft.moonlake.util.player.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

/**
 * <h1>Minecraft <a href="http://www.mcyszh.com">MoonLake</a> Core API Plugin</h1>
 * <h6>By Month_Light Q: 1327516533</h6>
 * @version 1.2
 * @author Month_Light
 */
public class MoonLakePlugin extends JavaPlugin implements MoonLake {

    private final MoonLake instance;
    private final Itemlib itemlib;
    private final Lorelib lorelib;
    private final Playerlib playerlib;
    private final PluginDescriptionFile pdf;
    private final String prefix = "[MoonLake]";
    private final ConsoleCommandSender console;

    private static MoonLake staticInstance;

    static { }

    public MoonLakePlugin() {
        // 构造函数
        instance = this;
        staticInstance = this;
        pdf = this.getDescription();
        console = this.getServer().getConsoleSender();
        itemlib = new ItemUtil();
        lorelib = new LoreUtil();
        playerlib = new PlayerUtil();
    }

    /**
     * 获取 MoonLake 实例对象
     *
     * @return MoonLake
     */
    public MoonLake getInstance() {
        return instance;
    }

    /**
     * 获取 MoonLake 实例对象 (静态函数获取实例不建议使用)
     *
     * @return MoonLake
     */
    @Deprecated
    public static MoonLake getInstances() {
        return staticInstance;
    }

    /**
     * 给控制台输出日志
     *
     * @param log 日志
     */
    @Override
    public void log(String log) {
        console.sendMessage(prefix + " " + log);
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
        return new HashSet<String>(pdf.getAuthors());
    }

    /**
     * 获取插件的依赖
     *
     * @return Depend
     */
    @Override
    public Set<String> getPluginDepends() {
        return new HashSet<String>(pdf.getDepend());
    }

    /**
     * 获取插件的软依赖
     *
     * @return SoftDepend
     */
    @Override
    public Set<String> getPluginSoftDepends() {
        return new HashSet<String>(pdf.getSoftDepend());
    }
}
