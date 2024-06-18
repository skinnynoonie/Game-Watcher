package me.skinnynoonie.gamewatcher.watcher;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsEventBus;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsDeathEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsPlayerListEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsQueueJoinEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsQueueLeaveEvent;
import me.skinnynoonie.gamewatcher.minecraft.MinecraftChatReader;
import me.skinnynoonie.gamewatcher.util.Checks;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GameWatcher {

    public static @NotNull GameWatcher fromPath(@NotNull Path pathToLogFile) {
        Checks.notNullArg(pathToLogFile, "pathToLogFile");

        BedwarsEventBus eventBus = new BedwarsEventBus(MinecraftChatReader.fromPath(pathToLogFile));
        return new GameWatcher(eventBus);
    }

    private final BedwarsEventBus bedwarsEventBus;
    private final Map<String, GameWatcherUserInfo> userInfoMap;
    private final List<Runnable> onUpdateSubscribers;

    public GameWatcher(@NotNull BedwarsEventBus bedwarsEventBus) {
        Checks.notNullArg(bedwarsEventBus, "bedwarsEventBus");

        this.bedwarsEventBus = bedwarsEventBus;
        this.userInfoMap = new HashMap<>();
        this.onUpdateSubscribers = new ArrayList<>();
    }

    public void init() {
        this.bedwarsEventBus.init();
        this.refresh();

        this.bedwarsEventBus.subscribe(BedwarsQueueJoinEvent.class, event -> {
            this.userInfoMap.put(event.getPlayer(), GameWatcherUserInfo.createDefault());
            this.dispatchUpdate();
        });
        this.bedwarsEventBus.subscribe(BedwarsQueueLeaveEvent.class, event -> {
            this.userInfoMap.remove(event.getPlayer());
            this.dispatchUpdate();
        });
        this.bedwarsEventBus.subscribe(BedwarsPlayerListEvent.class, event -> {
            this.userInfoMap.clear();
            event.getPlayers().forEach(username -> {
                this.userInfoMap.put(username, GameWatcherUserInfo.createDefault());
            });
            this.dispatchUpdate();
        });
        this.bedwarsEventBus.subscribe(BedwarsDeathEvent.class, event -> {
            this.userInfoMap.get(event.getVictim()).incrementDeaths();
            if (event.getAttacker() != null) {
                this.userInfoMap.get(event.getAttacker()).incrementKills();
            }
            this.dispatchUpdate();
        });
    }

    public void dispose() {
        this.bedwarsEventBus.dispose();
        this.userInfoMap.clear();
        this.onUpdateSubscribers.clear();
    }

    public void update() {
        this.bedwarsEventBus.update();
    }

    public void refresh() {
        this.bedwarsEventBus.refresh();
        this.userInfoMap.clear();
        this.dispatchUpdate();
    }

    public void onUpdate(@NotNull Runnable runnable) {
        Checks.notNullArg(runnable, "runnable");

        this.onUpdateSubscribers.add(runnable);
    }

    private void dispatchUpdate() {
        for (Runnable runnable : this.onUpdateSubscribers) {
            try {
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public @NotNull Map<@NotNull String, @NotNull GameWatcherUserInfo> getUserInfoMap() {
        return Collections.unmodifiableMap(this.userInfoMap);
    }

}
