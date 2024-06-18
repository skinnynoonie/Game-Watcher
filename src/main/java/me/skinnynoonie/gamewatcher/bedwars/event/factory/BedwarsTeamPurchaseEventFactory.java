package me.skinnynoonie.gamewatcher.bedwars.event.factory;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsTeamPurchaseEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Creates instances of {@link BedwarsTeamPurchaseEvent} whenever a team upgrade is purchased.
 * Requires {@link BedwarsContext}.
 * If the user who purchased a team upgrade is not included in the context, then {@code null} is returned.
 */
public final class BedwarsTeamPurchaseEventFactory implements BedwarsEventFactory<BedwarsTeamPurchaseEvent> {

    @Override
    public BedwarsTeamPurchaseEvent from(@NotNull String chatMessage, @NotNull BedwarsContext context) {
        if (chatMessage.contains("purchased")) {
            try {
                String username = chatMessage.split(" ")[0];
                if (context.getPlayers().contains(username)) {
                    return new BedwarsTeamPurchaseEvent(username);
                }
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }

        return null;
    }

}
