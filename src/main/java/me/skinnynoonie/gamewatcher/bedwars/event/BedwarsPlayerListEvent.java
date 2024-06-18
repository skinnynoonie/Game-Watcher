package me.skinnynoonie.gamewatcher.bedwars.event;

import me.skinnynoonie.gamewatcher.util.Checks;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

public final class BedwarsPlayerListEvent extends BedwarsEvent {

    private final Set<String> players;

    public BedwarsPlayerListEvent(@NotNull Set<@NotNull String> players) {
        Checks.notNullArg(players, "players");
        Checks.noNullElementsArg(players, "players has null elements");

        this.players = players;
    }

    public @NotNull Set<@NotNull String> getPlayers() {
        return Collections.unmodifiableSet(this.players);
    }

}
