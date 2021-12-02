package dev.arctic.core.api.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by Zvijer on 11.8.2017..
 */
public class FileUtil {

    private File file;
    private FileConfiguration config;

    public FileUtil(final File file) {
        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);

        init();
    }

    final void init() {
        try {
            if (!file.exists())
                config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> void set(final String path, T value) {
        try {
            if (config == null || !file.exists()) {
                throw new NullPointerException("Invalid configuration destination.");
            }

            if (config.get(path) == null) {
                config.set(path, value);
                config.save(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> T get(final String path) {
        T result = null;
        try {
            result = (T) config.get(path);
        } catch (ClassCastException | NullPointerException e) {
            e.printStackTrace();
        }

        return result;
    }
}
