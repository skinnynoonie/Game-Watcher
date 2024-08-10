package me.skinnynoonie.gamewatcher.config.serialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import me.skinnynoonie.gamewatcher.config.Config;
import me.skinnynoonie.gamewatcher.util.Arguments;
import org.jetbrains.annotations.NotNull;

public final class JsonConfigSerializer implements ConfigSerializer {

    public static @NotNull JsonConfigSerializer createDefault() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .serializeNulls()
                .create();
        return new JsonConfigSerializer(gson);
    }

    private final Gson gson;

    public JsonConfigSerializer(@NotNull Gson gson) {
        Arguments.notNull(gson, "gson");

        this.gson = gson;
    }

    @Override
    public @NotNull String serialize(@NotNull Config config) {
        Arguments.notNull(config, "config");

        try {
            return this.gson.toJson(config);
        } catch (JsonParseException e) {
            throw new ConfigSerializationException(e);
        }
    }

    @Override
    public <C extends Config> @NotNull C deserialize(@NotNull String string, @NotNull Class<C> configClass) {
        Arguments.notNull(string, "string");
        Arguments.notNull(configClass, "configClass");

        try {
            return this.gson.fromJson(string, configClass);
        } catch (JsonParseException e) {
            throw new ConfigSerializationException(e);
        }
    }

    @Override
    public @NotNull String getFormatAcronym() {
        return "JSON";
    }

    public @NotNull Gson getGson() {
        return this.gson;
    }

}
