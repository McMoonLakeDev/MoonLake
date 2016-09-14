package com.minecraft.moonlake;

import com.minecraft.moonlake.api.MoonLake;
import com.minecraft.moonlake.logger.MLogger;
import com.minecraft.moonlake.logger.MLoggerWrapped;
import com.minecraft.moonlake.property.ReadOnlyIntegerProperty;
import com.minecraft.moonlake.property.ReadOnlyStringProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import com.minecraft.moonlake.property.SimpleStringProperty;
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
    private final PluginDescriptionFile description;

    private static MoonLake MAIN;

    public MoonLakePlugin() {

        this.description = getDescription();
        this.mLogger = new MLoggerWrapped("MoonLake");
    }

    @Override
    public void onEnable() {

        MAIN = this;

        this.getMLogger().log("月色之湖核心 API 插件 v" + getPluginVersion().get() + " 成功加载.");
    }

    public MoonLake getInstance() {

        return MAIN;
    }

    @Deprecated
    public static MoonLake getInstances() {

        return MAIN;
    }

    @Override
    public MLogger getMLogger() {

        return mLogger;
    }

    @Override
    public ReadOnlyStringProperty getPluginPrefix() {

        return new SimpleStringProperty(description.getPrefix());
    }

    @Override
    public ReadOnlyStringProperty getPluginName() {

        return new SimpleStringProperty(description.getName());
    }

    @Override
    public ReadOnlyStringProperty getPluginMain() {

        return new SimpleStringProperty(description.getMain());
    }

    @Override
    public ReadOnlyStringProperty getPluginVersion() {

        return new SimpleStringProperty(description.getVersion());
    }

    @Override
    public ReadOnlyStringProperty getPluginWebsite() {

        return new SimpleStringProperty(description.getWebsite());
    }

    @Override
    public ReadOnlyStringProperty getPluginDescription() {

        return new SimpleStringProperty(description.getDescription());
    }

    @Override
    public Set<String> getPluginAuthers() {

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
    public ReadOnlyStringProperty getBukkitVersion() {

        String packageName = getServer().getClass().getPackage().getName();
        String[] packageSplit = packageName.split("\\.");
        return new SimpleStringProperty(packageSplit[packageSplit.length - 1]);
    }

    @Override
    public ReadOnlyIntegerProperty getReleaseNumber() {

        String version = getBukkitVersion().get();
        String[] versionSplit = version.split("_");
        String releaseVersion = versionSplit[1];
        return new SimpleIntegerProperty(Integer.parseInt(releaseVersion));
    }
}
