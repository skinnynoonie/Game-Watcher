package me.skinnynoonie.gamewatcher.bedwars.event.team;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsEventFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Creates instances of {@link TeamPurchaseEvent} whenever a team upgrade is purchased.
 * Requires {@link BedwarsContext}.
 * If the user who purchased a team upgrade is not included in the context, then {@code null} is returned.
 */
public final class TeamPurchaseEventFactory implements BedwarsEventFactory<TeamPurchaseEvent> {

    @Override
    public TeamPurchaseEvent from(@NotNull String chatMessage, @NotNull BedwarsContext context) {
        if (chatMessage.contains("purchased")) {
            try {
                String username = chatMessage.split(" ")[0];
                if (context.getPlayers().contains(username)) {
                    return new TeamPurchaseEvent(username);
                }
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }

        return null;
    }

}
