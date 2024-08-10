package me.skinnynoonie.gamewatcher.bedwars.event.playerlist;

import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsEvent;
import me.skinnynoonie.gamewatcher.util.Arguments;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

public final class PlayerListEvent extends BedwarsEvent {

    private final Set<String> players;

    public PlayerListEvent(@NotNull Set<@NotNull String> players) {
        Arguments.notNull(players, "players");
        Arguments.notNullElements(players, "players has null elements");

        this.players = players;
    }

    public @NotNull Set<@NotNull String> getPlayers() {
        return Collections.unmodifiableSet(this.players);
    }

}
