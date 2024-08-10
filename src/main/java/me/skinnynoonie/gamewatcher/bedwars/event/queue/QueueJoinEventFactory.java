package me.skinnynoonie.gamewatcher.bedwars.event.queue;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsEventFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Creates instances of {@link QueueJoinEvent} when a player joins the bedwars queue.
 * Does not require {@link BedwarsContext}.
 */
public final class QueueJoinEventFactory implements BedwarsEventFactory<QueueJoinEvent> {

    @Override
    public QueueJoinEvent from(@NotNull String chatMessage, @NotNull BedwarsContext context) {
        if (chatMessage.matches("^\\w+ has joined \\(\\d+/\\d+\\)!$")) {
            String player = chatMessage.split(" ")[0];
            return new QueueJoinEvent(player);
        } else {
            return null;
        }
    }

}
