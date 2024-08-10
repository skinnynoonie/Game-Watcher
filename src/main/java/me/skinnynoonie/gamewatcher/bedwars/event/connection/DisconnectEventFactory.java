package me.skinnynoonie.gamewatcher.bedwars.event.connection;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsEventFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Creates {@link DisconnectEvent} instances when a player disconnects from an active bedwars game.
 * {@link BedwarsContext} is required.
 * If the user who disconnected is not in the context, {@code null} will be returned.
 */
public final class DisconnectEventFactory implements BedwarsEventFactory<DisconnectEvent> {

    @Override
    public DisconnectEvent from(@NotNull String chatMessage, @NotNull BedwarsContext context) {
        if (chatMessage.matches("^\\w+ disconnected\\.$")) {
            String username = chatMessage.split(" ")[0];
            return new DisconnectEvent(username);
        } else {
            return null;
        }
    }

}
