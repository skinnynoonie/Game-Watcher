package me.skinnynoonie.gamewatcher.app;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import me.skinnynoonie.gamewatcher.watcher.PlayerData;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public final class PlayerDataProperties implements Comparable<PlayerDataProperties> {

    public static PlayerDataProperties fromPlayerData(String username, PlayerData userInfo) {
        return new PlayerDataProperties(
                username,
                userInfo.getKills(),
                userInfo.getDeaths()
        );
    }

    private final SimpleStringProperty username;
    private final SimpleIntegerProperty kills;
    private final SimpleIntegerProperty deaths;
    private final SimpleStringProperty kd;

    public PlayerDataProperties(String username, int kills, int deaths) {
        this.username = new SimpleStringProperty(username);
        this.kills = new SimpleIntegerProperty(kills);
        this.deaths = new SimpleIntegerProperty(deaths);
        String decimalFormatKd = new DecimalFormat("0.0").format(kills / Math.max(1.0, deaths));
        this.kd = new SimpleStringProperty(decimalFormatKd);
    }

    public String getUsername() {
        return this.username.get();
    }

    public int getKills() {
        return this.kills.get();
    }

    public int getDeaths() {
        return this.deaths.get();
    }

    public String getKd() {
        return this.kd.get();
    }

    @Override
    public int compareTo(@NotNull PlayerDataProperties o) {
        return -Integer.compare(this.kills.get(), o.kills.get());
    }

}
