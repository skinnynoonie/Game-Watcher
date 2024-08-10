package me.skinnynoonie.gamewatcher.config.serialize;

import me.skinnynoonie.gamewatcher.config.Config;
import org.jetbrains.annotations.NotNull;

public interface ConfigSerializer {

    @NotNull String serialize(@NotNull Config config) throws ConfigSerializationException;

    <C extends Config> @NotNull C deserialize(@NotNull String string, @NotNull Class<C> configClass) throws ConfigSerializationException;

    @NotNull String getFormatAcronym();

}
