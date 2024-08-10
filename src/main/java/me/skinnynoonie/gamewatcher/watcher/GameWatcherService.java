package me.skinnynoonie.gamewatcher.watcher;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class GameWatcherService {

    private final List<Consumer<Map<String, PlayerData>>> onUpdateListeners;
    private GameWatcher gameWatcher;

    public GameWatcherService() {
        this.onUpdateListeners = new ArrayList<>();
    }

    public void setUp(Path pathToLogs) {
        if (this.gameWatcher != null) {
            this.gameWatcher.dispose();
            this.gameWatcher = null;
        }

        this.gameWatcher = GameWatcher.fromPath(pathToLogs);
        this.gameWatcher.init();
        this.gameWatcher.onUpdate(() -> {
            for (Consumer<Map<String, PlayerData>> listener : onUpdateListeners) {
                try {
                    listener.accept(this.gameWatcher.getPlayerInfoMap());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void tick() {
        if (this.gameWatcher != null) {
            this.gameWatcher.tick();
        }
    }

    public void onUpdate(Consumer<Map<String, PlayerData>> listener) {
        this.onUpdateListeners.add(listener);
    }

    public GameWatcher getGameWatcher() {
        return this.gameWatcher;
    }

}
