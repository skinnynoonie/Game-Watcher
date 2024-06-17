package me.skinnynoonie.gamewatcher.bedwars.event;

public class BedwarsTeamPurchaseEvent extends BedwarsEvent {

    private final String username;

    public BedwarsTeamPurchaseEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

}
