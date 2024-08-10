package me.skinnynoonie.gamewatcher.app;

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
import me.skinnynoonie.gamewatcher.config.repository.ConfigRepository;
import me.skinnynoonie.gamewatcher.watcher.GameWatcherService;

import java.io.File;
import java.util.Collections;

public final class GameWatcherApplicationController {

    private final Stage stage;
    private final ConfigRepository configRepository;
    private LogPathConfig config;
    private final GameWatcherService gameWatcherService;
    private final ObservableList<PlayerDataProperties> gameWatcherData;

    @FXML private TableView<PlayerDataProperties> trackerTableView;
    @FXML private TableColumn<PlayerDataProperties, String> usernameColumn;
    @FXML private TableColumn<PlayerDataProperties, Integer> killsColumn;
    @FXML private TableColumn<PlayerDataProperties, Integer> deathsColumn;
    @FXML private TableColumn<PlayerDataProperties, Double> kdColumn;
    @FXML private Button pathButton;

    public GameWatcherApplicationController(
            Stage stage,
            ConfigRepository configRepository,
            GameWatcherService gameWatcherService
    ) {
        this.stage = stage;
        this.configRepository = configRepository;
        this.gameWatcherService = gameWatcherService;
        this.gameWatcherData = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        this.setUpConfig();

        this.reloadGameWatcherService();
        this.gameWatcherService.onUpdate(playerDataMap -> {
            // Not very optimized.
            this.gameWatcherData.clear();
            playerDataMap.forEach((username, data) -> {
                PlayerDataProperties playerDataProperties = PlayerDataProperties.fromPlayerData(username, data);
                this.gameWatcherData.add(playerDataProperties);
            });
            Collections.sort(this.gameWatcherData);
        });

        this.setUpGui();
        this.stage.setOnCloseRequest(this::onClose);
    }

    public void setUpConfig() {
        this.configRepository.init();
        this.config = this.configRepository.load("config", new LogPathConfig(null));
        this.configRepository.save("config", this.config);
    }

    private void reloadGameWatcherService() {
        if (!this.config.isValid()) {
            return;
        }

        this.gameWatcherService.setUp(this.config.getPath());
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
            this.reloadGameWatcherService();
        }
    }

    private void onClose(WindowEvent event) {
        this.configRepository.save("config", this.config);
        System.exit(1);
    }

}
