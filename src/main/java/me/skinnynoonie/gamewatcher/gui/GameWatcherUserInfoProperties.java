package me.skinnynoonie.gamewatcher.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import me.skinnynoonie.gamewatcher.watcher.GameWatcherUserInfo;

import java.text.DecimalFormat;

public final class GameWatcherUserInfoProperties {

    public static GameWatcherUserInfoProperties fromGameWatcherInfo(String username, GameWatcherUserInfo userInfo) {
        return new GameWatcherUserInfoProperties(
                username,
                userInfo.getKills(),
                userInfo.getDeaths()
        );
    }

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
