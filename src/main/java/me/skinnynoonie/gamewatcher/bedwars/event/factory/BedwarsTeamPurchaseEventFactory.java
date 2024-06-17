package me.skinnynoonie.gamewatcher.bedwars.event.factory;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsTeamPurchaseEvent;

public final class BedwarsTeamPurchaseEventFactory implements BedwarsEventFactory<BedwarsTeamPurchaseEvent> {

    @Override
    public BedwarsTeamPurchaseEvent from(String chatMessage, BedwarsContext context) {
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
