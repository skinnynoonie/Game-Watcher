package me.skinnynoonie.gamewatcher.bedwars.event;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class BedwarsPlayerListEvent extends BedwarsEvent {

    private final Set<String> players;

    public BedwarsPlayerListEvent(Set<String> players) {
        this.players = players;
    }

    public Set<String> getPlayers() {
        return Collections.unmodifiableSet(this.players);
    }

}
