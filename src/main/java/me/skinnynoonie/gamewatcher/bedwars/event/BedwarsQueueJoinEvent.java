package me.skinnynoonie.gamewatcher.bedwars.event;

public final class BedwarsQueueJoinEvent extends BedwarsEvent {

    private final String player;

    public BedwarsQueueJoinEvent(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return this.player;
    }

}
