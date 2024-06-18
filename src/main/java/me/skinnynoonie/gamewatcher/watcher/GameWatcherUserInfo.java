package me.skinnynoonie.gamewatcher.watcher;

import org.jetbrains.annotations.NotNull;

public final class GameWatcherUserInfo {

    public static @NotNull GameWatcherUserInfo createDefault() {
        return new GameWatcherUserInfo(0, 0);
    }

    private int kills;
    private int deaths;

    public GameWatcherUserInfo(int kills, int deaths) {
        this.kills = kills;
        this.deaths = deaths;
    }

    public int getKills() {
        return this.kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void incrementKills() {
        this.kills++;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void incrementDeaths() {
        this.deaths++;
    }

}
