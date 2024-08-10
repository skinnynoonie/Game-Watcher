package me.skinnynoonie.gamewatcher.bedwars.event.connection;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsEventFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Creates instances of {@link ReconnectEvent}, when a player reconnects to an active bedwars game.
 * {@link BedwarsContext} is required.
 * If the player who reconnected is not provided in the context, {@code null} is returned.
 */
public final class ReconnectEventFactory implements BedwarsEventFactory<ReconnectEvent> {

    @Override
    public ReconnectEvent from(@NotNull String chatMessage, @NotNull BedwarsContext context) {
        if (chatMessage.matches("^\\w+ reconnected\\.$")) {
            String username = chatMessage.split(" ")[0];
            return new ReconnectEvent(username);
        } else {
            return null;
        }
    }

}
