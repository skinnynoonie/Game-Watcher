package me.skinnynoonie.gamewatcher.watcher;

import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsEventBus;
import me.skinnynoonie.gamewatcher.bedwars.event.death.DeathEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.playerlist.PlayerListEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.queue.QueueJoinEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.queue.QueueLeaveEvent;
import me.skinnynoonie.gamewatcher.minecraft.MinecraftChatReader;
import me.skinnynoonie.gamewatcher.util.Arguments;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GameWatcher {

    public static @NotNull GameWatcher fromPath(@NotNull Path pathToLogFile) {
        Arguments.notNull(pathToLogFile, "pathToLogFile");

        BedwarsEventBus eventBus = new BedwarsEventBus(MinecraftChatReader.fromPath(pathToLogFile));
        return new GameWatcher(eventBus);
    }

    private final BedwarsEventBus bedwarsEventBus;
    private final Map<String, PlayerData> playerInfoMap;
    private final List<Runnable> onUpdateSubscribers;

    public GameWatcher(@NotNull BedwarsEventBus bedwarsEventBus) {
        Arguments.notNull(bedwarsEventBus, "bedwarsEventBus");

        this.bedwarsEventBus = bedwarsEventBus;
        this.playerInfoMap = new HashMap<>();
        this.onUpdateSubscribers = new ArrayList<>();
    }

    public void init() {
        this.bedwarsEventBus.init();
        this.refresh();

        this.bedwarsEventBus.subscribe(QueueJoinEvent.class, event -> {
            this.playerInfoMap.put(event.getPlayer(), PlayerData.createDefault());
            this.runUpdateListeners();
        });
        this.bedwarsEventBus.subscribe(QueueLeaveEvent.class, event -> {
            this.playerInfoMap.remove(event.getPlayer());
            this.runUpdateListeners();
        });
        this.bedwarsEventBus.subscribe(PlayerListEvent.class, event -> {
            this.playerInfoMap.clear();
            event.getPlayers().forEach(username -> this.playerInfoMap.put(username, PlayerData.createDefault()));
            this.runUpdateListeners();
        });
        this.bedwarsEventBus.subscribe(DeathEvent.class, event -> {
            this.playerInfoMap.get(event.getVictim()).incrementDeaths();
            if (event.getAttacker() != null) {
                this.playerInfoMap.get(event.getAttacker()).incrementKills();
            }
            this.runUpdateListeners();
        });
    }

    public void dispose() {
        this.bedwarsEventBus.dispose();
        this.playerInfoMap.clear();
        this.onUpdateSubscribers.clear();
    }

    public void tick() {
        this.bedwarsEventBus.tick();
    }

    public void refresh() {
        this.bedwarsEventBus.reset();
        this.playerInfoMap.clear();
        this.runUpdateListeners();
    }

    public void onUpdate(@NotNull Runnable runnable) {
        Arguments.notNull(runnable, "runnable");

        this.onUpdateSubscribers.add(runnable);
    }

    public @NotNull Map<String, PlayerData> getPlayerInfoMap() {
        return Collections.unmodifiableMap(this.playerInfoMap);
    }

    private void runUpdateListeners() {
        for (Runnable runnable : this.onUpdateSubscribers) {
            try {
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
