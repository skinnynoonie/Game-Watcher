package me.skinnynoonie.gamewatcher.bedwars.event;

public final class BedwarsQueueLeaveEvent extends BedwarsEvent {

    private final String player;

    public BedwarsQueueLeaveEvent(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return this.player;
    }

}
