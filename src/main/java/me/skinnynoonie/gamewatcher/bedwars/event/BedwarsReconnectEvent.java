package me.skinnynoonie.gamewatcher.bedwars.event;

import me.skinnynoonie.gamewatcher.util.Checks;
import org.jetbrains.annotations.NotNull;

public final class BedwarsReconnectEvent extends BedwarsEvent {

    private final @NotNull String username;

    public BedwarsReconnectEvent(@NotNull String username) {
        Checks.notNullArg(username, "username");

        this.username = username;
    }

    public @NotNull String getUsername() {
        return this.username;
    }

}
