package me.skinnynoonie.gamewatcher.bedwars.event.connection;

import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsEvent;
import me.skinnynoonie.gamewatcher.util.Arguments;
import org.jetbrains.annotations.NotNull;

public final class DisconnectEvent extends BedwarsEvent {

    private final String username;

    public DisconnectEvent(@NotNull String username) {
        Arguments.notNull(username, "username");

        this.username = username;
    }

    public @NotNull String getUsername() {
        return this.username;
    }

}
