package me.skinnynoonie.gamewatcher.gui;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import me.skinnynoonie.gamewatcher.watcher.GameWatcher;
import me.skinnynoonie.gamewatcher.watcher.GameWatcherUserInfo;

import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.Map;

public final class GameWatcherGuiController {

    private GameWatcher gameWatcher;
    private final ObservableList<GameWatcherUserInfoProperties> gameWatcherData = FXCollections.observableArrayList();

    @FXML private TableView<GameWatcherUserInfoProperties> trackerTableView;
    @FXML private TableColumn<GameWatcherUserInfoProperties, String> usernameColumn;
    @FXML private TableColumn<GameWatcherUserInfoProperties, Integer> killsColumn;
    @FXML private TableColumn<GameWatcherUserInfoProperties, Integer> deathsColumn;
    @FXML private TableColumn<GameWatcherUserInfoProperties, Double> kdColumn;

    public GameWatcherGuiController() {
        this.gameWatcher = GameWatcher.fromPath(Path.of("C:\\Users\\skinn\\AppData\\Roaming\\.minecraft\\logs\\blclient\\minecraft\\latest.log"));
    }

    public void initialize() {
        this.gameWatcher.init();

        this.usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        this.killsColumn.setCellValueFactory(new PropertyValueFactory<>("kills"));
        this.deathsColumn.setCellValueFactory(new PropertyValueFactory<>("deaths"));
        this.kdColumn.setCellValueFactory(new PropertyValueFactory<>("kd"));
        this.trackerTableView.setItems(this.gameWatcherData);

        this.gameWatcher.onUpdate(() -> {
            this.gameWatcherData.clear();

            Map<String, GameWatcherUserInfo> userInfoMap = this.gameWatcher.getUserInfoMap();
            for (String username : userInfoMap.keySet()) {
                GameWatcherUserInfo gameWatcherUserInfo = userInfoMap.get(username);
                GameWatcherUserInfoProperties userInfo = new GameWatcherUserInfoProperties(
                        username,
                        gameWatcherUserInfo.getKills(),
                        gameWatcherUserInfo.getDeaths()
                );
                this.gameWatcherData.add(userInfo);
            }
        });

        new Thread(() -> {
            while (true) {
                Platform.runLater(this.gameWatcher::update);

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static class GameWatcherUserInfoProperties {
        private final SimpleStringProperty username;
        private final SimpleIntegerProperty kills;
        private final SimpleIntegerProperty deaths;
        private final SimpleStringProperty kd;

        public GameWatcherUserInfoProperties(String username, int kills, int deaths) {
            this.username = new SimpleStringProperty(username);
            this.kills = new SimpleIntegerProperty(kills);
            this.deaths = new SimpleIntegerProperty(deaths);

            String decimalFormatKd = new DecimalFormat("0.0").format(kills / Math.max(1.0, deaths));
            this.kd = new SimpleStringProperty(decimalFormatKd);
        }

        public String getUsername() {
            return username.get();
        }

        public int getKills() {
            return kills.get();
        }

        public int getDeaths() {
            return deaths.get();
        }

        public String getKd() {
            return kd.get();
        }
    }

}
