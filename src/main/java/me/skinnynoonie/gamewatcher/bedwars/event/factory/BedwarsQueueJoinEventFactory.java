package me.skinnynoonie.gamewatcher.bedwars.event.factory;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsQueueJoinEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Creates instances of {@link BedwarsQueueJoinEvent} when a player joins the bedwars queue.
 * Does not require {@link BedwarsContext}.
 */
public final class BedwarsQueueJoinEventFactory implements BedwarsEventFactory<BedwarsQueueJoinEvent> {

    @Override
    public BedwarsQueueJoinEvent from(@NotNull String chatMessage, @NotNull BedwarsContext context) {
        if (chatMessage.matches("^\\w+ has joined \\(\\d+/\\d+\\)!$")) {
            String player = chatMessage.split(" ")[0];
            return new BedwarsQueueJoinEvent(player);
        } else {
            return null;
        }
    }

}
