package me.skinnynoonie.gamewatcher.util;

public final class Checks {

    public static void notNullArg(Object arg, String exceptionMessage) {
        if (arg == null) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    public static void legalArg(boolean condition, String exceptionMessage) {
        if (!condition) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    private Checks() {
    }

}
