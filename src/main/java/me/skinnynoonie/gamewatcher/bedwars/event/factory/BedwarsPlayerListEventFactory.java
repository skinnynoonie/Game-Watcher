package me.skinnynoonie.gamewatcher.bedwars.event.factory;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsPlayerListEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Creates instances of {@link BedwarsPlayerListEvent} when a player executes the command "/who"
 * in a bedwars queue or game.
 * Does not require {@link BedwarsContext}.
 */
public final class BedwarsPlayerListEventFactory implements BedwarsEventFactory<BedwarsPlayerListEvent> {

    @Override
    public BedwarsPlayerListEvent from(@NotNull String chatMessage, @NotNull BedwarsContext context) {
        if (chatMessage.startsWith("ONLINE: ")) {
            String playersListedString = chatMessage.replace("ONLINE: ", "");
            String[] players = playersListedString.split(", ");
            return new BedwarsPlayerListEvent(Set.of(players));
        } else {
            return null;
        }
    }

}
