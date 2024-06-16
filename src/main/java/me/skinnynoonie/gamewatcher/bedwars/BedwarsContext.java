package me.skinnynoonie.gamewatcher.bedwars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class BedwarsContext {

    public static BedwarsContext createDefault() {
        return new BedwarsContext(new ArrayList<>());
    }

    private final List<String> players;

    public BedwarsContext(List<String> players) {
        this.players = new ArrayList<>(players);
    }

    public List<String> getPlayers() {
        return Collections.unmodifiableList(this.players);
    }

    public void setPlayers(List<String> players) {
        this.players.clear();
        this.players.addAll(players);
    }

    public void addPlayer(String player) {
        this.players.add(player);
    }

    public void removePlayer(String player) {
        this.players.remove(player);
    }

}
