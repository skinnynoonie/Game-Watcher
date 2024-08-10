package me.skinnynoonie.gamewatcher.bedwars.event.queue;

import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsEvent;
import me.skinnynoonie.gamewatcher.util.Arguments;
import org.jetbrains.annotations.NotNull;

public final class QueueJoinEvent extends BedwarsEvent {

    private final String player;

    public QueueJoinEvent(@NotNull String player) {
        Arguments.notNull(player, "player");

        this.player = player;
    }

    public @NotNull String getPlayer() {
        return this.player;
    }

}
