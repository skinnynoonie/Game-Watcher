package me.skinnynoonie.gamewatcher.config;

import java.nio.file.Files;
import java.nio.file.Path;

public final class LogPathConfig implements Config {

    private String path;

    public LogPathConfig(String path) {
        this.path = path;
    }

    public Path getPath() {
        return Path.of(this.path);
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean isValid() {
        return this.path != null && Files.isRegularFile(this.getPath());
    }

}
