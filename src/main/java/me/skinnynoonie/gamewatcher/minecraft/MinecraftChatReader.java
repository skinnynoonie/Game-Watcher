package me.skinnynoonie.gamewatcher.minecraft;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

public final class MinecraftChatReader {

    public static MinecraftChatReader fromPath(Path pathToLogFile) {
        try {
            return new MinecraftChatReader(Files.newBufferedReader(pathToLogFile, StandardCharsets.ISO_8859_1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static MinecraftChatReader fromPath(String pathToLogFile) {
        return fromPath(Path.of(pathToLogFile));
    }

    private final BufferedReader chatReader;

    public MinecraftChatReader(BufferedReader chatReader) {
        this.chatReader = chatReader;
    }

    public void skip() {
        while (true) {
            try {
                if (this.chatReader.readLine() == null) {
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public @Nullable String nextChatMessage() {
        while (true) {
            try {
                String nextLog = this.chatReader.readLine();
                if (nextLog == null) {
                    return null;
                }

                if (!nextLog.contains("[CHAT] ")) {
                    continue;
                }

                return nextLog.split(Pattern.quote("[CHAT] "), 2)[1].strip();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
