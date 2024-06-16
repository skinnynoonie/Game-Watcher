package me.skinnynoonie.gamewatcher.bedwars.event;

public class BedwarsDeathEvent extends BedwarsEvent {

    private final String victim;
    private final String attacker;

    public BedwarsDeathEvent(String victim, String attacker) {
        this.victim = victim;
        this.attacker = attacker;
    }

    public String getVictim() {
        return this.victim;
    }

    public String getAttacker() {
        return this.attacker;
    }

}
