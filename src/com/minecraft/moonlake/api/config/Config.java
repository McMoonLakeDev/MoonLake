package com.minecraft.moonlake.api.config;

import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.ReadOnlyObjectProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Created by MoonLake on 2016/9/17.
 */
public class Config<T> {

    private ReadOnlyObjectProperty<Plugin> plugin;
    private ReadOnlyObjectProperty<Class<T>> config;
    private ReadOnlyObjectProperty<Configuration> configuration;
    private ObjectProperty<YamlConfiguration> yaml;
    private ObjectProperty<File> file;

    public Config(Plugin plugin, Class<T> config) {

        Validate.notNull(plugin, "The java plugin object is null.");
        Validate.notNull(config, "The config class object is null.");

        Configuration configuration = config.getAnnotation(Configuration.class);

        if(configuration == null) {

            throw new NullPointerException("The config not has Configuration annotation.");
        }
        this.configuration = new SimpleObjectProperty<>(configuration);
        this.plugin = new SimpleObjectProperty<>(plugin);
        this.config = new SimpleObjectProperty<>(config);
        this.file = new SimpleObjectProperty<>();
        this.yaml = new SimpleObjectProperty<>();

        this.initYamlConfiguration();
    }

    protected ReadOnlyObjectProperty<Plugin> getPlugin() {

        return plugin;
    }

    protected ReadOnlyObjectProperty<Class<T>> getConfig() {

        return config;
    }

    protected ReadOnlyObjectProperty<Configuration> getConfiguration() {

        return configuration;
    }

    protected ObjectProperty<YamlConfiguration> getYaml() {

        return yaml;
    }

    protected ObjectProperty<File> getFile() {

        return file;
    }

    protected void initYamlConfiguration() {

        File config = new File(getPlugin().get().getDataFolder(), getConfiguration().get().path() + getConfiguration().get().name());

        if(config.exists()) {

            return;
        }
        if(config.getParentFile() != null && !config.getParentFile().exists()) {

            config.getParentFile().mkdirs();
        }
        if(!config.exists()) {

            try {

                config.createNewFile();
            }
            catch (Exception e) {

                e.printStackTrace();
            }
        }
        getYaml().set(YamlConfiguration.loadConfiguration(config));

        try {

            T configInstance = getConfig().get().getConstructor().newInstance();

            for(final Field field : getConfig().get().getDeclaredFields()) {

                if(!field.isAccessible()) {

                    field.setAccessible(true);
                }
                ConfigurationPath configPath = field.getAnnotation(ConfigurationPath.class);

                if(configPath != null) {

                }
            }
            getYaml().get().save(config);
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }
}