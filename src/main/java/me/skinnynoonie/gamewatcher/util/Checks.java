package me.skinnynoonie.gamewatcher.util;

public final class Checks {

    public static void notNullArg(Object arg, String argName) {
        if (arg == null) {
            throw new IllegalArgumentException(argName + " is null");
        }
    }

    public static void legalArg(boolean condition, String exceptionMessage) {
        if (!condition) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    public static void noNullElementsArg(Iterable<?> iterable, String argName) {
        for (Object object : iterable) {
            if (object == null) {
                throw new IllegalArgumentException(argName + "has null elements");
            }
        }
    }

    public static void legalState(boolean condition, String exceptionMessage) {
        if (!condition) {
            throw new IllegalStateException(exceptionMessage);
        }
    }

    private Checks() {
    }

}
