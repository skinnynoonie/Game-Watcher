package me.skinnynoonie.gamewatcher.bedwars.event.queue;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsEventFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Creates instances of {@link QueueLeaveEvent} when a player leaves the bedwars queue.
 * Does not require {@link BedwarsContext}.
 */
public final class QueueLeaveEventFactory implements BedwarsEventFactory<QueueLeaveEvent> {

    @Override
    public QueueLeaveEvent from(@NotNull String chatMessage, @NotNull BedwarsContext context) {
        if (chatMessage.matches("^\\w+ has quit!$")) {
            String player = chatMessage.split(" ")[0];
            return new QueueLeaveEvent(player);
        } else {
            return null;
        }
    }

}
