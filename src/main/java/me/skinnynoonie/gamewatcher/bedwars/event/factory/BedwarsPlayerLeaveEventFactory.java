package me.skinnynoonie.gamewatcher.bedwars.event.factory;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsPlayerLeaveEvent;

public final class BedwarsPlayerLeaveEventFactory implements BedwarsEventFactory<BedwarsPlayerLeaveEvent> {

    @Override
    public BedwarsPlayerLeaveEvent from(String chatMessage, BedwarsContext context) {
        return null;
    }

}
