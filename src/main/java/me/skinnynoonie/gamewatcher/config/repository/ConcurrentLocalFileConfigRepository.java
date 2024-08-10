package me.skinnynoonie.gamewatcher.config.repository;

import me.skinnynoonie.gamewatcher.config.Config;
import me.skinnynoonie.gamewatcher.config.ConfigException;
import me.skinnynoonie.gamewatcher.config.serialize.ConfigSerializer;
import me.skinnynoonie.gamewatcher.util.Arguments;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class ConcurrentLocalFileConfigRepository implements ConfigRepository {

    private final Path configFolder;
    private final ConfigSerializer serializer;
    private final Lock lock;

    public ConcurrentLocalFileConfigRepository(@NotNull Path configFolder, @NotNull ConfigSerializer serializer) {
        Arguments.notNull(configFolder, "configFolder");
        Arguments.notNull(serializer, "serializer");

        this.configFolder = configFolder;
        this.serializer = serializer;
        this.lock = new ReentrantLock();
    }

    @Override
    public void init() {
        this.lock.lock();
        try {
            Files.createDirectories(this.configFolder);
        } catch (IOException e) {
            throw new ConfigException(e);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public <C extends Config> @NotNull C load(@NotNull String configId, @NotNull Class<C> configClass) {
        Arguments.notNull(configId, "configId");
        Arguments.notNull(configClass, "configClass");

        this.lock.lock();
        try {
            Path configPath = this.getConfigPath(configId);
            if (this.isSaved(configId)) {
                String configSerialized = Files.readString(configPath);
                return this.serializer.deserialize(configSerialized, configClass);
            } else {
                throw new ConfigNotFoundException("config with id " + configId + " does not exist");
            }
        } catch (IOException e) {
            throw new ConfigException(e);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void save(@NotNull String configId, @NotNull Config config) {
        Arguments.notNull(configId, "configId");
        Arguments.notNull(config, "config");

        this.lock.lock();
        try {
            String serializedConfig = this.serializer.serialize(config);
            Files.write(this.getConfigPath(configId), serializedConfig.getBytes());
        } catch (IOException e) {
            throw new ConfigException(e);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean isSaved(@NotNull String configId) {
        Arguments.notNull(configId, "configId");

        this.lock.lock();
        try {
            return Files.exists(this.getConfigPath(configId));
        } catch (SecurityException e) {
            throw new ConfigException(e);
        } finally {
            this.lock.unlock();
        }
    }

    private Path getConfigPath(String configId) {
        return this.configFolder.resolve(configId + "." + this.serializer.getFormatAcronym().toLowerCase());
    }

}
