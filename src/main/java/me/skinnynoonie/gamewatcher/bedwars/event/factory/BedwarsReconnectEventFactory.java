package me.skinnynoonie.gamewatcher.bedwars.event.factory;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsReconnectEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Creates instances of {@link BedwarsReconnectEvent}, when a player reconnects to an active bedwars game.
 * {@link BedwarsContext} is required.
 * If the player who reconnected is not provided in the context, {@code null} is returned.
 */
public final class BedwarsReconnectEventFactory implements BedwarsEventFactory<BedwarsReconnectEvent> {

    @Override
    public BedwarsReconnectEvent from(@NotNull String chatMessage, @NotNull BedwarsContext context) {
        if (chatMessage.matches("^\\w+ reconnected\\.$")) {
            String username = chatMessage.split(" ")[0];
            return new BedwarsReconnectEvent(username);
        } else {
            return null;
        }
    }

}
