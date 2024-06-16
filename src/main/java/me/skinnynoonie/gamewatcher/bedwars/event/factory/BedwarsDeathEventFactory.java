package me.skinnynoonie.gamewatcher.bedwars.event.factory;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsDeathEvent;

public final class BedwarsDeathEventFactory implements BedwarsEventFactory<BedwarsDeathEvent> {

    @Override
    public BedwarsDeathEvent from(String chatMessage, BedwarsContext context) {
        try {
            String[] messageSplit = chatMessage.replace(".", "").replace(" FINAL KILL!", "").split(" ");

            String victim = messageSplit[0];
            if (!context.getPlayers().contains(victim)) {
                return null;
            }

            String possibleAttacker = messageSplit[messageSplit.length - 1];
            if (!context.getPlayers().contains(possibleAttacker)) {
                possibleAttacker = null;
            }

            return new BedwarsDeathEvent(victim, possibleAttacker);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

}
