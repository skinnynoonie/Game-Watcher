package me.skinnynoonie.gamewatcher.bedwars.event;

public class BedwarsDisconnectEvent extends BedwarsEvent {

    private final String username;

    public BedwarsDisconnectEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

}
