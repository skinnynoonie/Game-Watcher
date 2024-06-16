package me.skinnynoonie.gamewatcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class GameWatcher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        @SuppressWarnings("ConstantConditions")
        Parent root = FXMLLoader.load(super.getClass().getClassLoader().getResource("game_watcher.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Game Watcher");
        stage.show();
    }

}