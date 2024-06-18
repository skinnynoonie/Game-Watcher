package me.skinnynoonie.gamewatcher.bedwars.event.factory;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsFillerEvent;

public class BedwarsFillerEventFactory implements BedwarsEventFactory<BedwarsFillerEvent> {

    @Override
    public BedwarsFillerEvent from(String chatMessage, BedwarsContext context) {
        String arrayHitRegex = "^\\w+ is on \\d+\\.\\d+ HP!$";
        if (chatMessage.matches(arrayHitRegex)) {
            return new BedwarsFillerEvent();
        }

        String afkRegex = "^\\w+ was kicked for being AFK!$";
        if (chatMessage.matches(afkRegex)) {
            return new BedwarsFillerEvent();
        }

        return null;
    }

}
