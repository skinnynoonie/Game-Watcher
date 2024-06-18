package me.skinnynoonie.gamewatcher.bedwars.event;

import me.skinnynoonie.gamewatcher.util.Checks;
import org.jetbrains.annotations.NotNull;

public final class BedwarsDisconnectEvent extends BedwarsEvent {

    private final String username;

    public BedwarsDisconnectEvent(@NotNull String username) {
        Checks.notNullArg(username, "username");

        this.username = username;
    }

    public @NotNull String getUsername() {
        return this.username;
    }

}
