package me.skinnynoonie.gamewatcher;

import me.skinnynoonie.gamewatcher.watcher.GameWatcher;

import java.nio.file.Path;

public final class GameWatcherLauncher {

    public static void main(String[] args) throws Exception {
        GameWatcher gameWatcher = GameWatcher.fromPath(Path.of("C:\\Users\\skinn\\AppData\\Roaming\\.minecraft\\logs\\blclient\\minecraft\\latest.log"));
        gameWatcher.init();

        gameWatcher.onUpdate(() -> {
            System.out.println(gameWatcher.getUserInfoMap());
        });

        while (true) {
            gameWatcher.update();
            Thread.sleep(20);
        }

    }

}
