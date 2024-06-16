package me.skinnynoonie.gamewatcher.bedwars.event.factory;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsPlayerListEvent;

public final class BedwarsPlayerListEventFactory implements BedwarsEventFactory<BedwarsPlayerListEvent> {

    @Override
    public BedwarsPlayerListEvent from(String chatMessage, BedwarsContext context) {
        return null;
    }

}
