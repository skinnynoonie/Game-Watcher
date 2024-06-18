package me.skinnynoonie.gamewatcher.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.skinnynoonie.gamewatcher.config.loader.ConfigLoader;
import me.skinnynoonie.gamewatcher.config.loader.UnSafeLocalJsonConfigLoader;

import java.nio.file.Path;

public final class GameWatcherGui extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Path workingPath = Path.of(System.getProperty("user.dir"));
        ConfigLoader configLoader = UnSafeLocalJsonConfigLoader.withPath(workingPath);

        @SuppressWarnings("ConstantConditions")
        FXMLLoader loader = new FXMLLoader(super.getClass().getClassLoader().getResource("game_watcher.fxml"));
        loader.setController(new GameWatcherGuiController(stage, configLoader));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Game Watcher");
        stage.show();
    }

}