package me.skinnynoonie.gamewatcher.bedwars.event.factory;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsEvent;

public interface BedwarsEventFactory<E extends BedwarsEvent> {

    E from(String chatMessage, BedwarsContext context);

}
