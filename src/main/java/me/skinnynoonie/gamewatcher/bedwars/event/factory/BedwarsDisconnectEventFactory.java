package me.skinnynoonie.gamewatcher.bedwars.event.factory;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsDisconnectEvent;

public final class BedwarsDisconnectEventFactory implements BedwarsEventFactory<BedwarsDisconnectEvent> {

    @Override
    public BedwarsDisconnectEvent from(String chatMessage, BedwarsContext context) {
        if (chatMessage.matches("^\\w+ disconnected\\.$")) {
            String username = chatMessage.split(" ")[0];
            return new BedwarsDisconnectEvent(username);
        } else {
            return null;
        }
    }

}
