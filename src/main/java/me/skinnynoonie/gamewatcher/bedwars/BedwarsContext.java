package me.skinnynoonie.gamewatcher.bedwars;

import me.skinnynoonie.gamewatcher.util.Arguments;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class BedwarsContext {

    public static @NotNull BedwarsContext createDefault() {
        return new BedwarsContext(new HashSet<>());
    }

    private final Set<String> players;

    public BedwarsContext(@NotNull Set<@NotNull String> players) {
        this.players = new HashSet<>();
        this.setPlayers(players);
    }

    public @NotNull Set<@NotNull String> getPlayers() {
        return Collections.unmodifiableSet(this.players);
    }

    public void setPlayers(@NotNull Set<@NotNull String> players) {
        Arguments.notNull(players, "players");
        Arguments.notNullElements(players, "players");

        this.players.clear();
        this.players.addAll(players);
    }

    public void addPlayer(@NotNull String player) {
        Arguments.notNull(player, "player");

        this.players.add(player);
    }

    public void removePlayer(@NotNull String player) {
        Arguments.notNull(player, "player");

        this.players.remove(player);
    }

}
