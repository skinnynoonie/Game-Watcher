package me.skinnynoonie.gamewatcher.util;

import java.util.Map;
import java.util.function.Function;

public final class Arguments {

    public static void notNull(Object arg, String argName) {
        if (arg == null) {
            throwIllegalArgumentException("argument %s is null", argName);
        }
    }

    public static void notNullElements(Iterable<?> iterable, String argName) {
        for (Object e : iterable) {
            if (e == null) {
                throwIllegalArgumentException("argument %s has a null element", argName);
            }
        }
    }

    public static void notNullMap(Map<?, ?> map, String argName) {
        for (Object e : map.keySet()) {
            if (e == null) {
                throwIllegalArgumentException("argument %s has a null key", argName);
            }
        }
        for (Object e : map.values()) {
            if (e == null) {
                throwIllegalArgumentException("argument %s has a null value", argName);
            }
        }
    }

    public static <K, V> void mapCorrectlyPaired(Map<K, V> map, Function<V, K> valueKeyFunction, String argName) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            K valueKey = valueKeyFunction.apply(entry.getValue());
            if (!entry.getKey().equals(valueKey)) {
                throwIllegalArgumentException("argument %s has a value which does not associate with its key", argName);
            }
        }
    }

    public static void inRange(double number, double min, double max, String argName) {
        if (number < min || number > max) {
            throwIllegalArgumentException("argument %s (value: %s) is not in range %s to %s (inclusive)",
                                                    argName,   number,             min,  max);
        }
    }

    public static void legal(boolean condition, String exceptionMessage, Object... arguments) {
        if (!condition) {
            throwIllegalArgumentException(exceptionMessage, arguments);
        }
    }

    private static void throwIllegalArgumentException(String exceptionMessage, Object... arguments) {
        throw new IllegalArgumentException(String.format(exceptionMessage, arguments));
    }

    private Arguments() {
    }

}
