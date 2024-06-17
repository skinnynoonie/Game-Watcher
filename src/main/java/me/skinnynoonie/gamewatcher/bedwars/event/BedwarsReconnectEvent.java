package me.skinnynoonie.gamewatcher.bedwars.event;

public class BedwarsReconnectEvent extends BedwarsEvent {

    private final String username;

    public BedwarsReconnectEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

}
