package me.skinnynoonie.gamewatcher.bedwars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class BedwarsContext {

    public static BedwarsContext createDefault() {
        return new BedwarsContext(new HashSet<>());
    }

    private final Set<String> players;

    public BedwarsContext(Set<String> players) {
        this.players = new HashSet<>(players);
    }

    public Set<String> getPlayers() {
        return Collections.unmodifiableSet(this.players);
    }

    public void setPlayers(Set<String> players) {
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
