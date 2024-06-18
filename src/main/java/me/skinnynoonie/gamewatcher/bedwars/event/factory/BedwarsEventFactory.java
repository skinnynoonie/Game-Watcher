package me.skinnynoonie.gamewatcher.bedwars.event.factory;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An interface for creating {@link BedwarsEvent} instances.
 *
 * @param <E> The {@link BedwarsEvent} that is created.
 */
public interface BedwarsEventFactory<E extends BedwarsEvent> {

    /**
     * Creates a {@link BedwarsEvent} from a chat message with given context.
     *
     * @param chatMessage The chat message.
     * @param context The context.
     * @return {@code null} if no event could be parsed, otherwise a {@link BedwarsEvent}.
     */
    @Nullable E from(@NotNull String chatMessage, @NotNull BedwarsContext context);

}
