package me.skinnynoonie.gamewatcher.bedwars.event;

public final class BedwarsPlayerLeaveEvent extends BedwarsEvent {

    private final String player;

    public BedwarsPlayerLeaveEvent(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return this.player;
    }

}
