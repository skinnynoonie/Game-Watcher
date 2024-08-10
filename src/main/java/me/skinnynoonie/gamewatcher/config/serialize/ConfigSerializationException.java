package me.skinnynoonie.gamewatcher.config.serialize;


import me.skinnynoonie.gamewatcher.config.ConfigException;

public final class ConfigSerializationException extends ConfigException {

    public ConfigSerializationException() {
    }

    public ConfigSerializationException(String message) {
        super(message);
    }

    public ConfigSerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigSerializationException(Throwable cause) {
        super(cause);
    }

}
