package me.skinnynoonie.gamewatcher.config.repository;

import me.skinnynoonie.gamewatcher.config.Config;
import me.skinnynoonie.gamewatcher.config.ConfigException;
import me.skinnynoonie.gamewatcher.util.Arguments;
import org.jetbrains.annotations.NotNull;

public interface ConfigRepository {

    void init() throws ConfigException;

    @NotNull <C extends Config> C load(@NotNull String configId, @NotNull Class<C> configClass) throws ConfigException;

    @SuppressWarnings("unchecked")
    default <C extends Config> @NotNull C load(@NotNull String configId, @NotNull C fallback) throws ConfigException {
        Arguments.notNull(fallback, "fallback");

        if (this.isSaved(configId)) {
            return (C) this.load(configId, fallback.getClass());
        } else {
            return fallback;
        }
    }

    void save(@NotNull String configId, @NotNull Config config) throws ConfigException;

    boolean isSaved(@NotNull String configId) throws ConfigException;

}
