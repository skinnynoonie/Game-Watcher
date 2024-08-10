package me.skinnynoonie.gamewatcher.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.skinnynoonie.gamewatcher.config.repository.ConfigRepository;
import me.skinnynoonie.gamewatcher.watcher.GameWatcherService;

public final class GameWatcherApplication extends Application {

    private static ConfigRepository configRepository;

    public static void setConfigRepository(ConfigRepository configRepository) {
        GameWatcherApplication.configRepository = configRepository;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(super.getClass().getClassLoader().getResource("game_watcher.fxml"));

        GameWatcherService gameWatcherService = new GameWatcherService();
        GameWatcherApplicationController controller = new GameWatcherApplicationController(stage, configRepository, gameWatcherService);
        loader.setController(controller);

        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Game Watcher");
        stage.show();

        new Thread(() -> {
            while (true) {
                Platform.runLater(gameWatcherService::tick);

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}