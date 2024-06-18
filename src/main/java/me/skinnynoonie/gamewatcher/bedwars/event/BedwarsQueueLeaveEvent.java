package me.skinnynoonie.gamewatcher.bedwars.event;

import me.skinnynoonie.gamewatcher.util.Checks;
import org.jetbrains.annotations.NotNull;

public final class BedwarsQueueLeaveEvent extends BedwarsEvent {

    private final String player;

    public BedwarsQueueLeaveEvent(@NotNull String player) {
        Checks.notNullArg(player, "player");

        this.player = player;
    }

    public @NotNull String getPlayer() {
        return this.player;
    }

}
