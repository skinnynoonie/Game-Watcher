package me.skinnynoonie.gamewatcher.bedwars.event;

import java.util.Collections;
import java.util.List;

public final class BedwarsPlayerListEvent extends BedwarsEvent {

    private final List<String> players;

    public BedwarsPlayerListEvent(List<String> players) {
        this.players = players;
    }

    public List<String> getPlayers() {
        return Collections.unmodifiableList(this.players);
    }

}
