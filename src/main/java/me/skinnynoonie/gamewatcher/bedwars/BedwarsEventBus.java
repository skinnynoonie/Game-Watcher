package me.skinnynoonie.gamewatcher.bedwars;

import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsQueueJoinEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsQueueLeaveEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsPlayerListEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.factory.BedwarsDeathEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.factory.BedwarsDisconnectEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.factory.BedwarsEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.factory.BedwarsQueueJoinEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.factory.BedwarsQueueLeaveEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.factory.BedwarsPlayerListEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.factory.BedwarsReconnectEventFactory;
import me.skinnynoonie.gamewatcher.minecraft.MinecraftChatReader;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

public final class BedwarsEventBus {

    private final MinecraftChatReader chatReader;
    private final List<Consumer<BedwarsEvent>> subscribers;
    private final Queue<ComparableBedwarsEventFactoryContainer> eventFactories;
    private final BedwarsContext context;

    public BedwarsEventBus(MinecraftChatReader chatReader) {
        this.chatReader = chatReader;
        this.subscribers = new ArrayList<>();
        this.eventFactories = new PriorityQueue<>();
        this.context = BedwarsContext.createDefault();

        this.registerEventFactory(new BedwarsQueueJoinEventFactory());
        this.registerEventFactory(new BedwarsQueueLeaveEventFactory());
        this.registerEventFactory(new BedwarsPlayerListEventFactory());
        this.registerEventFactory(new BedwarsDisconnectEventFactory());
        this.registerEventFactory(new BedwarsReconnectEventFactory());
        this.registerEventFactory(new BedwarsDeathEventFactory(), Integer.MAX_VALUE);

        this.subscribe(BedwarsQueueJoinEvent.class, event -> {
            String player = event.getPlayer();
            this.context.addPlayer(player);
        });
        this.subscribe(BedwarsQueueLeaveEvent.class, event -> {
            String player = event.getPlayer();
            this.context.addPlayer(player);
        });
        this.subscribe(BedwarsPlayerListEvent.class, event -> {
            Set<String> players = event.getPlayers();
            this.context.setPlayers(players);
        });
    }

    public void init() {
        this.registerEventFactory(new BedwarsQueueJoinEventFactory());
        this.registerEventFactory(new BedwarsQueueLeaveEventFactory());
        this.registerEventFactory(new BedwarsPlayerListEventFactory());
        this.registerEventFactory(new BedwarsDisconnectEventFactory());
        this.registerEventFactory(new BedwarsReconnectEventFactory());
        this.registerEventFactory(new BedwarsDeathEventFactory(), Integer.MAX_VALUE);

        this.subscribe(BedwarsQueueJoinEvent.class, event -> this.context.addPlayer(event.getPlayer()));
        this.subscribe(BedwarsQueueLeaveEvent.class, event -> this.context.addPlayer(event.getPlayer()));
        this.subscribe(BedwarsPlayerListEvent.class, event -> this.context.setPlayers(event.getPlayers()));
    }

    public <T extends BedwarsEvent> void subscribe(Class<T> eventClass, Consumer<T> eventConsumer) {
        this.subscribers.add(event -> {
            if (eventClass.isInstance(event)) {
                eventConsumer.accept(eventClass.cast(event));
            }
        });
    }

    public void refresh() {
        this.context.setPlayers(Collections.emptySet());
        this.chatReader.skip();
    }

    public void update() {
        try {
            String chatMessage = this.chatReader.nextChatMessage();
            if (chatMessage == null) {
                return;
            }

            BedwarsEvent event = null;
            for (ComparableBedwarsEventFactoryContainer eventFactoryContainer : this.eventFactories) {
                event = eventFactoryContainer.getBedwarsEventFactory().from(chatMessage, this.context);
                if (event != null) {
                    break;
                }
            }

            if (event == null) {
                return;
            }

            this.dispatch(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dispatch(BedwarsEvent event) {
        for (Consumer<BedwarsEvent> subscriber : this.subscribers) {
            try {
                subscriber.accept(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void registerEventFactory(BedwarsEventFactory<?> bedwarsEventFactory, int priority) {
        this.eventFactories.add(new ComparableBedwarsEventFactoryContainer(bedwarsEventFactory, priority));
    }

    public void registerEventFactory(BedwarsEventFactory<?> bedwarsEventFactory) {
        this.eventFactories.add(new ComparableBedwarsEventFactoryContainer(bedwarsEventFactory, 50));
    }

    private static class ComparableBedwarsEventFactoryContainer implements Comparable<ComparableBedwarsEventFactoryContainer> {
        private final BedwarsEventFactory<?> bedwarsEventFactory;
        private final int priority;

        private ComparableBedwarsEventFactoryContainer(BedwarsEventFactory<?> bedwarsEventFactory, int priority) {
            this.bedwarsEventFactory = bedwarsEventFactory;
            this.priority = priority;
        }

        @Override
        public int compareTo(@NotNull BedwarsEventBus.ComparableBedwarsEventFactoryContainer o) {
            return Integer.compare(this.priority, o.priority);
        }

        public BedwarsEventFactory<?> getBedwarsEventFactory() {
            return this.bedwarsEventFactory;
        }
    }

}
