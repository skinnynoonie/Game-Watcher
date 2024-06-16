package me.skinnynoonie.gamewatcher.bedwars.event.factory;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsQueueJoinEvent;

public final class BedwarsQueueJoinEventFactory implements BedwarsEventFactory<BedwarsQueueJoinEvent> {

    @Override
    public BedwarsQueueJoinEvent from(String chatMessage, BedwarsContext context) {
        if (chatMessage.matches("^\\w+ has joined \\(\\d+/\\d+\\)!$")) {
            String player = chatMessage.split(" ")[0];
            return new BedwarsQueueJoinEvent(player);
        } else {
            return null;
        }
    }

}
