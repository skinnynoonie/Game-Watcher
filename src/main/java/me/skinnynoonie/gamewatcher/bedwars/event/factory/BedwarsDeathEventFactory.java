package me.skinnynoonie.gamewatcher.bedwars.event.factory;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsDeathEvent;

import java.util.Set;

public final class BedwarsDeathEventFactory implements BedwarsEventFactory<BedwarsDeathEvent> {

    @Override
    public BedwarsDeathEvent from(String chatMessage, BedwarsContext context) {
        try {
            String[] messageSplit = this.removeUselessMarks(chatMessage).split(" ");

            String victim = messageSplit[0];
            if (!context.getPlayers().contains(victim)) {
                return null;
            }

            String possibleAttacker = getFirstMatch(messageSplit, context.getPlayers(), 1);

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
