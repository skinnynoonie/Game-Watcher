package me.skinnynoonie.gamewatcher.bedwars.event;

import me.skinnynoonie.gamewatcher.bedwars.BedwarsContext;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.playerlist.PlayerListEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.queue.QueueJoinEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.queue.QueueLeaveEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.death.DeathEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.connection.DisconnectEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.filler.FillerEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.playerlist.PlayerListEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.queue.QueueJoinEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.queue.QueueLeaveEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.connection.ReconnectEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.team.TeamPurchaseEventFactory;
import me.skinnynoonie.gamewatcher.minecraft.MinecraftChatReader;
import me.skinnynoonie.gamewatcher.util.Arguments;
import me.skinnynoonie.gamewatcher.util.PriorityQueueV2;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public final class BedwarsEventBus {

    private static final int DEFAULT_PRIORITY = 50;
    private final MinecraftChatReader chatReader;
    private final List<Consumer<BedwarsEvent>> subscribers;
    private final PriorityQueueV2<BedwarsEventFactory<?>> eventFactories;
    private final BedwarsContext context;

    public BedwarsEventBus(@NotNull MinecraftChatReader chatReader) {
        Arguments.notNull(chatReader, "chatReader");

        this.chatReader = chatReader;
        this.subscribers = new ArrayList<>();
        this.eventFactories = new PriorityQueueV2<>(DEFAULT_PRIORITY);
        this.context = BedwarsContext.createDefault();
    }

    public void init() {
        this.registerEventFactory(new QueueJoinEventFactory());
        this.registerEventFactory(new QueueLeaveEventFactory());
        this.registerEventFactory(new PlayerListEventFactory());
        this.registerEventFactory(new DisconnectEventFactory());
        this.registerEventFactory(new ReconnectEventFactory());
        this.registerEventFactory(new TeamPurchaseEventFactory());
        this.registerEventFactory(new FillerEventFactory(), Integer.MAX_VALUE - 1);
        this.registerEventFactory(new DeathEventFactory(), Integer.MAX_VALUE);

        this.subscribe(QueueJoinEvent.class, event -> this.context.addPlayer(event.getPlayer()));
        this.subscribe(QueueLeaveEvent.class, event -> this.context.removePlayer(event.getPlayer()));
        this.subscribe(PlayerListEvent.class, event -> this.context.setPlayers(event.getPlayers()));
    }

    public void dispose() {
        this.chatReader.dispose();
        this.subscribers.clear();
        this.eventFactories.clear();
    }

    public <T extends BedwarsEvent> void subscribe(@NotNull Class<T> eventClass, @NotNull Consumer<@NotNull T> eventConsumer) {
        Arguments.notNull(eventClass, "eventClass");
        Arguments.notNull(eventConsumer, "eventConsumer");

        this.subscribers.add(event -> {
            if (eventClass.isInstance(event)) {
                eventConsumer.accept(eventClass.cast(event));
            }
        });
    }

    public void reset() {
        this.context.setPlayers(Collections.emptySet());
        this.chatReader.skip();
    }

    public void tick() {
        try {
            String chatMessage = this.chatReader.nextChatMessage();
            if (chatMessage == null) {
                return;
            }

            BedwarsEvent event = this.getFirstEventFromChatMessage(chatMessage);
            if (event == null) {
                return;
            }

            this.dispatch(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dispatch(@NotNull BedwarsEvent event) {
        Arguments.notNull(event, "event");

        for (Consumer<BedwarsEvent> subscriber : this.subscribers) {
            try {
                subscriber.accept(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void registerEventFactory(@NotNull BedwarsEventFactory<?> bedwarsEventFactory, int priority) {
        Arguments.notNull(bedwarsEventFactory, "bedwarsEventFactory");

        this.eventFactories.add(bedwarsEventFactory, priority);
    }

    public void registerEventFactory(@NotNull BedwarsEventFactory<?> bedwarsEventFactory) {
        Arguments.notNull(bedwarsEventFactory, "bedwarsEventFactory");

        this.eventFactories.add(bedwarsEventFactory);
    }

    private BedwarsEvent getFirstEventFromChatMessage(String chatMessage) {
        for (BedwarsEventFactory<?> eventFactory : this.eventFactories) {
            BedwarsEvent event = eventFactory.from(chatMessage, this.context);
            if (event != null) {
                return event;
            }
        }
        return null;
    }

}
