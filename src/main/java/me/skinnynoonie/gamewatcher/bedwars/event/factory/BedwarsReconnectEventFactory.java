package me.skinnynoonie.gamewatcher.bedwars.event.factory;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsReconnectEvent;

public final class BedwarsReconnectEventFactory implements BedwarsEventFactory<BedwarsReconnectEvent> {

    @Override
    public BedwarsReconnectEvent from(String chatMessage, BedwarsContext context) {
        if (chatMessage.matches("^\\w+ reconnected\\.$")) {
            String username = chatMessage.split(" ")[0];
            return new BedwarsReconnectEvent(username);
        } else {
            return null;
        }
    }

}
