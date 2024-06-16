package me.skinnynoonie.gamewatcher.bedwars.event.factory;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsQueueLeaveEvent;

public final class BedwarsQueueLeaveEventFactory implements BedwarsEventFactory<BedwarsQueueLeaveEvent> {

    @Override
    public BedwarsQueueLeaveEvent from(String chatMessage, BedwarsContext context) {
        if (chatMessage.matches("^\\w+ has quit!$")) {
            String player = chatMessage.split(" ")[0];
            return new BedwarsQueueLeaveEvent(player);
        } else {
            return null;
        }
    }

}
