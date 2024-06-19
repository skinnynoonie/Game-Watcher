package me.skinnynoonie.gamewatcher.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import me.skinnynoonie.gamewatcher.config.LogPathConfig;
import me.skinnynoonie.gamewatcher.config.loader.ConfigLoader;
import me.skinnynoonie.gamewatcher.watcher.GameWatcher;
import me.skinnynoonie.gamewatcher.watcher.GameWatcherUserInfo;

import java.io.File;
import java.util.Map;

public final class GameWatcherGuiController {

    private final Stage stage;

    private final ConfigLoader configLoader;
    private LogPathConfig config;

    private GameWatcher gameWatcher;
    private final ObservableList<GameWatcherUserInfoProperties> gameWatcherData = FXCollections.observableArrayList();

    @FXML private TableView<GameWatcherUserInfoProperties> trackerTableView;
    @FXML private TableColumn<GameWatcherUserInfoProperties, String> usernameColumn;
    @FXML private TableColumn<GameWatcherUserInfoProperties, Integer> killsColumn;
    @FXML private TableColumn<GameWatcherUserInfoProperties, Integer> deathsColumn;
    @FXML private TableColumn<GameWatcherUserInfoProperties, Double> kdColumn;
    @FXML private Button pathButton;

    public GameWatcherGuiController(Stage stage, ConfigLoader configLoader) {
        this.stage = stage;
        this.configLoader = configLoader;
    }

    @FXML
    public void initialize() {
        this.setUpConfigLoaderAndConfig();
        this.setUpGameWatcher();
        this.setUpGui();
        this.stage.setOnCloseRequest(this::onClose);

        new Thread(() -> {
            while (true) {
                Platform.runLater(() -> {
                    if (this.gameWatcher != null) {
                        this.gameWatcher.update();
                    }
                });

                this.sleep(20);
            }
        }).start();
    }

    public void setUpConfigLoaderAndConfig() {
        this.configLoader.init();
        if (this.configLoader.isSaved("config")) {
            this.config = this.configLoader.load("config", LogPathConfig.class);
        } else {
            this.config = new LogPathConfig(null);
            this.configLoader.save("config", this.config);
        }
    }

    private void setUpGameWatcher() {
        if (this.gameWatcher != null) {
            this.gameWatcher.dispose();
            this.gameWatcher = null;
        }

        if (!this.config.isValid()) {
            return;
        }

        this.gameWatcher = GameWatcher.fromPath(this.config.getPath());
        this.gameWatcher.init();

        this.gameWatcher.onUpdate(() -> {
            Map<String, GameWatcherUserInfo> userInfoMap = this.gameWatcher.getUserInfoMap();
            this.updateGameWatcherData(userInfoMap);
        });
    }

    private void updateGameWatcherData(Map<String, GameWatcherUserInfo> userInfoMap) {
        this.gameWatcherData.clear();
        userInfoMap.forEach((username, data) -> {
            GameWatcherUserInfoProperties userInfoProperties = GameWatcherUserInfoProperties.fromGameWatcherInfo(username, data);
            this.gameWatcherData.add(userInfoProperties);
        });
    }

    private void setUpGui() {
        this.usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        this.usernameColumn.setReorderable(false);

        this.killsColumn.setCellValueFactory(new PropertyValueFactory<>("kills"));
        this.killsColumn.setReorderable(false);

        this.deathsColumn.setCellValueFactory(new PropertyValueFactory<>("deaths"));
        this.deathsColumn.setReorderable(false);

        this.kdColumn.setCellValueFactory(new PropertyValueFactory<>("kd"));
        this.kdColumn.setReorderable(false);

        this.trackerTableView.setItems(this.gameWatcherData);
        this.pathButton.setOnMouseReleased(this::onPathButton);
    }

    private void onPathButton(MouseEvent event) {
        File selectedDir = new FileChooser().showOpenDialog(this.stage);
        if (selectedDir != null) {
            this.config.setPath(selectedDir.getPath());
            this.setUpGameWatcher();
        }
    }

    private void onClose(WindowEvent event) {
        this.configLoader.save("config", this.config);
        System.exit(1);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
