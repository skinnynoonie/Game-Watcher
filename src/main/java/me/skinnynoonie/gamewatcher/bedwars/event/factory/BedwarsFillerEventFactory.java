package me.skinnynoonie.gamewatcher.bedwars.event.factory;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsFillerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * This factory is mainly to stop false {@link me.skinnynoonie.gamewatcher.bedwars.event.BedwarsDeathEvent} instances
 * from being created from the {@link BedwarsDeathEventFactory}.
 * This should be done by strictly allowing only one event for each chat message, and having this factory's priority
 * directly over {@link BedwarsDeathEventFactory}'s priority.
 */
public final class BedwarsFillerEventFactory implements BedwarsEventFactory<BedwarsFillerEvent> {

    @Override
    public BedwarsFillerEvent from(@NotNull String chatMessage, @NotNull BedwarsContext context) {
        String arrowHitRegex = "^\\w+ is on \\d+\\.\\d+ HP!$";
        if (chatMessage.matches(arrowHitRegex)) {
            return new BedwarsFillerEvent();
        }

        String afkRegex = "^\\w+ was kicked for being AFK!$";
        if (chatMessage.matches(afkRegex)) {
            return new BedwarsFillerEvent();
        }

        return null;
    }

}
