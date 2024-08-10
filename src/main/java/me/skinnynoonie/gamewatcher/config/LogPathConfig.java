package me.skinnynoonie.gamewatcher.config;

import me.skinnynoonie.gamewatcher.util.Arguments;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Files;
import java.nio.file.Path;

public final class LogPathConfig implements Config {

    private String path;

    public LogPathConfig(@Nullable String path) {
        this.path = path;
    }

    public @NotNull Path getPath() {
        Arguments.legal(this.isValid(), "the config is invalid");

        return Path.of(this.path);
    }

    public void setPath(@Nullable String path) {
        this.path = path;
    }

    @Override
    public boolean isValid() {
        return this.path != null && Files.isRegularFile(Path.of(this.path));
    }

}
