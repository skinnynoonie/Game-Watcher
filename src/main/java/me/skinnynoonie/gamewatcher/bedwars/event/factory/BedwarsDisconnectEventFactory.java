package me.skinnynoonie.gamewatcher.bedwars.event.factory;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsDisconnectEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Creates {@link BedwarsDisconnectEvent} instances when a player disconnects from an active bedwars game.
 * {@link BedwarsContext} is required.
 * If the user who disconnected is not in the context, {@code null} will be returned.
 */
public final class BedwarsDisconnectEventFactory implements BedwarsEventFactory<BedwarsDisconnectEvent> {

    @Override
    public BedwarsDisconnectEvent from(@NotNull String chatMessage, @NotNull BedwarsContext context) {
        if (chatMessage.matches("^\\w+ disconnected\\.$")) {
            String username = chatMessage.split(" ")[0];
            return new BedwarsDisconnectEvent(username);
        } else {
            return null;
        }
    }

}
