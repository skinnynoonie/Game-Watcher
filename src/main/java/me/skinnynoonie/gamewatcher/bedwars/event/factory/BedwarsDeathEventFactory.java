package me.skinnynoonie.gamewatcher.bedwars.event.factory;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Creates {@link BedwarsDeathEvent} instances when a player dies.
 * This should have a last-resort priority because its parsing implementation is not accurate.
 * {@link BedwarsContext} is required.
 * If the victim is not in the provided {@link BedwarsContext}, then a {@link BedwarsDeathEvent} will not be created.
 * If the attacker is not provided in the context, then {@code null} will be used for the attacker in the returned
 * {@link BedwarsDeathEvent}.
 *
 * @see BedwarsFillerEventFactory
 */
public final class BedwarsDeathEventFactory implements BedwarsEventFactory<BedwarsDeathEvent> {

    @Override
    public BedwarsDeathEvent from(@NotNull String chatMessage, @NotNull BedwarsContext context) {
        try {
            String[] messageSplit = this.removeUselessMarks(chatMessage).split(" ");

            String victim = messageSplit[0];
            if (!context.getPlayers().contains(victim)) {
                return null;
            }

            String possibleAttacker = this.getFirstMatch(messageSplit, context.getPlayers(), 1);

            return new BedwarsDeathEvent(victim, possibleAttacker);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private String removeUselessMarks(String string) {
        return string.replace("'s", "").replaceAll("[!'.]", "");
    }

    private String getFirstMatch(String[] stringsOne, Set<String> stringsTwo, int stringsOneStartIndex) {
        for (int i = stringsOneStartIndex; i < stringsOne.length; i++) {
            if (stringsTwo.contains(stringsOne[i])) {
                return stringsOne[i];
            }
        }

        return null;
    }

}
