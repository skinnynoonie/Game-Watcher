package me.skinnynoonie.gamewatcher.bedwars.event.factory;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsPlayerJoinEvent;

public final class BedwarsPlayerJoinEventFactory implements BedwarsEventFactory<BedwarsPlayerJoinEvent> {

    @Override
    public BedwarsPlayerJoinEvent from(String chatMessage, BedwarsContext context) {
        return null;
    }

}
