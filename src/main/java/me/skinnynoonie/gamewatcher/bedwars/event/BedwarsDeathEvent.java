package me.skinnynoonie.gamewatcher.bedwars.event;

import me.skinnynoonie.gamewatcher.util.Checks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BedwarsDeathEvent extends BedwarsEvent {

    private final String victim;
    private final String attacker;

    public BedwarsDeathEvent(@NotNull String victim, @Nullable String attacker) {
        Checks.notNullArg(victim, "victim");
        Checks.notNullArg(attacker, "attacker");

        this.victim = victim;
        this.attacker = attacker;
    }

    public @NotNull String getVictim() {
        return this.victim;
    }

    public @Nullable String getAttacker() {
        return this.attacker;
    }
}
