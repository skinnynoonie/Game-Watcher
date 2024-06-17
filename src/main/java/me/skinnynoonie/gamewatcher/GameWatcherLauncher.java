package me.skinnynoonie.gamewatcher;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsEventBus;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsDeathEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsQueueJoinEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsQueueLeaveEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsPlayerListEvent;
import me.skinnynoonie.gamewatcher.minecraft.MinecraftChatReader;

public final class GameWatcherLauncher {

    public static void main(String[] args) throws Exception {
        BedwarsEventBus eventBus = new BedwarsEventBus(MinecraftChatReader.fromPath("C:\\Users\\skinn\\AppData\\Roaming\\.minecraft\\logs\\blclient\\minecraft\\latest.log"));
        eventBus.init();
        eventBus.refresh();

        eventBus.subscribe(BedwarsQueueJoinEvent.class, event -> {
            System.out.println(event.getPlayer() + " has joined");
        });
        eventBus.subscribe(BedwarsQueueLeaveEvent.class, event -> {
            System.out.println(event.getPlayer() + " has left");
        });
        eventBus.subscribe(BedwarsPlayerListEvent.class, event -> {
            System.out.println(event.getPlayers());
        });
        eventBus.subscribe(BedwarsDeathEvent.class, event -> {
            System.out.println(event.getVictim() + " died to " + event.getAttacker());
        });

        while (true) {
            eventBus.update();
            Thread.sleep(20);
        }
    }

}
