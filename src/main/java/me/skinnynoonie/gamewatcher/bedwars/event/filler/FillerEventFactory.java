package me.skinnynoonie.gamewatcher.bedwars.event.filler;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.death.DeathEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.death.DeathEventFactory;
import org.jetbrains.annotations.NotNull;

/**
 * This factory is mainly to stop false {@link DeathEvent} instances
 * from being created from the {@link DeathEventFactory}.
 * This should be done by strictly allowing only one event for each chat message, and having this factory's priority
 * directly over {@link DeathEventFactory}'s priority.
 */
public final class FillerEventFactory implements BedwarsEventFactory<FillerEvent> {

    @Override
    public FillerEvent from(@NotNull String chatMessage, @NotNull BedwarsContext context) {
        String arrowHitRegex = "^\\w+ is on \\d+\\.\\d+ HP!$";
        if (chatMessage.matches(arrowHitRegex)) {
            return new FillerEvent();
        }

        String afkRegex = "^\\w+ was kicked for being AFK!$";
        if (chatMessage.matches(afkRegex)) {
            return new FillerEvent();
        }

        return null;
    }

}
