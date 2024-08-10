package me.skinnynoonie.gamewatcher.bedwars.event.death;

import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsEvent;
import me.skinnynoonie.gamewatcher.util.Arguments;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DeathEvent extends BedwarsEvent {

    private final String victim;
    private final String attacker;

    public DeathEvent(@NotNull String victim, @Nullable String attacker) {
        Arguments.notNull(victim, "victim");

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
