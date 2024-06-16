package me.skinnynoonie.gamewatcher.bedwars.event.factory;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsPlayerListEvent;

import java.util.Set;

public final class BedwarsPlayerListEventFactory implements BedwarsEventFactory<BedwarsPlayerListEvent> {

    @Override
    public BedwarsPlayerListEvent from(String chatMessage, BedwarsContext context) {
        if (chatMessage.startsWith("ONLINE: ")) {
            String playersListedString = chatMessage.replace("ONLINE: ", "");
            String[] players = playersListedString.split(", ");
            return new BedwarsPlayerListEvent(Set.of(players));
        } else {
            return null;
        }
    }

}
