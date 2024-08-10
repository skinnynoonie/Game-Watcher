package me.skinnynoonie.gamewatcher.bedwars.event.playerlist;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsEventFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Creates instances of {@link PlayerListEvent} when a player executes the command "/who"
 * in a bedwars queue or game.
 * Does not require {@link BedwarsContext}.
 */
public final class PlayerListEventFactory implements BedwarsEventFactory<PlayerListEvent> {

    @Override
    public PlayerListEvent from(@NotNull String chatMessage, @NotNull BedwarsContext context) {
        if (chatMessage.startsWith("ONLINE: ")) {
            String playersListedString = chatMessage.replace("ONLINE: ", "");
            String[] players = playersListedString.split(", ");
            return new PlayerListEvent(Set.of(players));
        } else {
            return null;
        }
    }

}
