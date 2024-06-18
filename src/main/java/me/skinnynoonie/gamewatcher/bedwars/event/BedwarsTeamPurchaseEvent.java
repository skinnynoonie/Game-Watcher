package me.skinnynoonie.gamewatcher.bedwars.event;

import me.skinnynoonie.gamewatcher.util.Checks;
import org.jetbrains.annotations.NotNull;

public final class BedwarsTeamPurchaseEvent extends BedwarsEvent {

    private final String username;

    public BedwarsTeamPurchaseEvent(@NotNull String username) {
        Checks.notNullArg(username, "username");

        this.username = username;
    }

    public @NotNull String getUsername() {
        return this.username;
    }

}
