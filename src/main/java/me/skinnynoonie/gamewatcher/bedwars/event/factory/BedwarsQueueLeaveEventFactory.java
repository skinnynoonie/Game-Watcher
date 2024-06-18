package me.skinnynoonie.gamewatcher.bedwars.event.factory;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsQueueLeaveEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Creates instances of {@link BedwarsQueueLeaveEvent} when a player leaves the bedwars queue.
 * Does not require {@link BedwarsContext}.
 */
public final class BedwarsQueueLeaveEventFactory implements BedwarsEventFactory<BedwarsQueueLeaveEvent> {

    @Override
    public BedwarsQueueLeaveEvent from(@NotNull String chatMessage, @NotNull BedwarsContext context) {
        if (chatMessage.matches("^\\w+ has quit!$")) {
            String player = chatMessage.split(" ")[0];
            return new BedwarsQueueLeaveEvent(player);
        } else {
            return null;
        }
    }

}
