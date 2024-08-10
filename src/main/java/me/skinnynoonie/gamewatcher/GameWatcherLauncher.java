package me.skinnynoonie.gamewatcher;

import javafx.application.Application;
import me.skinnynoonie.gamewatcher.app.GameWatcherApplication;
import me.skinnynoonie.gamewatcher.config.repository.ConcurrentLocalFileConfigRepository;
import me.skinnynoonie.gamewatcher.config.repository.ConfigRepository;
import me.skinnynoonie.gamewatcher.config.serialize.ConfigSerializer;
import me.skinnynoonie.gamewatcher.config.serialize.JsonConfigSerializer;

import java.nio.file.Path;

public final class GameWatcherLauncher {

    public static void main(String[] args) {
        Path workingDir = Path.of(System.getProperty("user.dir"));
        ConfigSerializer configSerializer = JsonConfigSerializer.createDefault();
        ConfigRepository configRepository = new ConcurrentLocalFileConfigRepository(workingDir, configSerializer);
        GameWatcherApplication.setConfigRepository(configRepository);

        Application.launch(GameWatcherApplication.class);
    }

}
