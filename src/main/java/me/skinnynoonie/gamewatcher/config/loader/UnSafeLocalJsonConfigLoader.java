package me.skinnynoonie.gamewatcher.config.loader;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import me.skinnynoonie.gamewatcher.config.Config;
import me.skinnynoonie.gamewatcher.config.ConfigException;
import me.skinnynoonie.gamewatcher.util.Checks;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class UnSafeLocalJsonConfigLoader implements ConfigLoader {

    public static @NotNull UnSafeLocalJsonConfigLoader withPath(@NotNull Path pathToConfigFolder) {
        Gson defaultGson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().serializeNulls().create();
        return new UnSafeLocalJsonConfigLoader(pathToConfigFolder, defaultGson);
    }

    private final Path pathToConfigFolder;
    private final Gson gson;
    private final Lock lock;

    public UnSafeLocalJsonConfigLoader(@NotNull Path pathToConfigFolder, @NotNull Gson gson) {
        Checks.notNullArg(pathToConfigFolder, "pathToConfigFolder");
        Checks.notNullArg(gson, "gson");

        this.pathToConfigFolder = pathToConfigFolder;
        this.gson = gson;
        this.lock = new ReentrantLock();
    }

    @Override
    public void init() {
        this.lock.lock();
        try {
            Files.createDirectories(this.pathToConfigFolder);
        } catch (IOException e) {
            throw new ConfigException("failed to create directory " + this.pathToConfigFolder, e);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public <T extends Config> @NotNull T load(@NotNull String configId, @NotNull Class<T> configClass) {
        Checks.notNullArg(configId, "configId");
        Checks.notNullArg(configClass, "configClass");

        this.lock.lock();
        try {
            Path pathToConfig = this.getPathToConfig(configId);
            try {
                return this.gson.fromJson(Files.readString(pathToConfig), configClass);
            } catch (IOException e) {
                throw new ConfigException("failed to read config with path " + pathToConfig, e);
            } catch (JsonParseException e) {
                throw new ConfigException("failed to parse json with path " + pathToConfig, e);
            }
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void save(@NotNull String configId, @NotNull Config config) {
        Checks.notNullArg(configId, "configId");
        Checks.notNullArg(config, "config");

        this.lock.lock();
        try {
            Path pathToConfig = this.getPathToConfig(configId);
            try {
                Files.write(pathToConfig, this.gson.toJson(config).getBytes());
            } catch (IOException e) {
                throw new ConfigException("failed to write to path " + pathToConfig, e);
            } catch (JsonParseException e) {
                throw new ConfigException("failed to serialize config with id" + configId, e);
            }
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean isSaved(@NotNull String configId) {
        Checks.notNullArg(configId, "configId");

        this.lock.lock();
        try {
            return Files.exists(this.getPathToConfig(configId));
        } finally {
            this.lock.unlock();
        }
    }

    private Path getPathToConfig(String configId) {
        return this.pathToConfigFolder.resolve(configId + ".json");
    }

}
