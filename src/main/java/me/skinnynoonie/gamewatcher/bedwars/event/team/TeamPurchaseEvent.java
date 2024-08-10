package me.skinnynoonie.gamewatcher.bedwars.event.team;

import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsEvent;
import me.skinnynoonie.gamewatcher.util.Arguments;
import org.jetbrains.annotations.NotNull;

public final class TeamPurchaseEvent extends BedwarsEvent {

    private final String username;

    public TeamPurchaseEvent(@NotNull String username) {
        Arguments.notNull(username, "username");

        this.username = username;
    }

    public @NotNull String getUsername() {
        return this.username;
    }

}
