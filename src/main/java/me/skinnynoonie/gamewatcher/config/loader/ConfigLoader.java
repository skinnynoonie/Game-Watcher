package me.skinnynoonie.gamewatcher.config.loader;

import me.skinnynoonie.gamewatcher.config.Config;
import org.jetbrains.annotations.NotNull;

public interface ConfigLoader {

    void init();

    <T extends Config> @NotNull T load(@NotNull String configId, @NotNull Class<T> configClass);

    void save(@NotNull String configId, @NotNull Config config);

    boolean isSaved(@NotNull String configId);

}
