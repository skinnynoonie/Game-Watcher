package me.skinnynoonie.gamewatcher.bedwars.event;

public final class BedwarsPlayerJoinEvent extends BedwarsEvent {

    private final String player;

    public BedwarsPlayerJoinEvent(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return this.player;
    }

}
