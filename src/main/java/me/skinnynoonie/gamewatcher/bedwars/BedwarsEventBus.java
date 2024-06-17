package me.skinnynoonie.gamewatcher.bedwars;

import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsPlayerListEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsQueueJoinEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsQueueLeaveEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.factory.BedwarsDeathEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.factory.BedwarsDisconnectEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.factory.BedwarsEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.factory.BedwarsFillerEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.factory.BedwarsPlayerListEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.factory.BedwarsQueueJoinEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.factory.BedwarsQueueLeaveEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.factory.BedwarsReconnectEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.factory.BedwarsTeamPurchaseEventFactory;
import me.skinnynoonie.gamewatcher.minecraft.MinecraftChatReader;
import me.skinnynoonie.gamewatcher.util.PriorityQueueV2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public final class BedwarsEventBus {

    private final MinecraftChatReader chatReader;
    private final List<Consumer<BedwarsEvent>> subscribers;
    private final PriorityQueueV2<BedwarsEventFactory<?>> eventFactories;
    private final BedwarsContext context;

    public BedwarsEventBus(MinecraftChatReader chatReader) {
        this.chatReader = chatReader;
        this.subscribers = new ArrayList<>();
        this.eventFactories = new PriorityQueueV2<>(50);
        this.context = BedwarsContext.createDefault();
    }

    public void init() {
        this.registerEventFactory(new BedwarsQueueJoinEventFactory());
        this.registerEventFactory(new BedwarsQueueLeaveEventFactory());
        this.registerEventFactory(new BedwarsPlayerListEventFactory());
        this.registerEventFactory(new BedwarsDisconnectEventFactory());
        this.registerEventFactory(new BedwarsReconnectEventFactory());
        this.registerEventFactory(new BedwarsTeamPurchaseEventFactory());
        this.registerEventFactory(new BedwarsFillerEventFactory(), Integer.MAX_VALUE - 1);
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

            BedwarsEvent event = this.getFirstEventFromChatMessage(chatMessage);
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
        this.eventFactories.add(bedwarsEventFactory, priority);
    }

    public void registerEventFactory(BedwarsEventFactory<?> bedwarsEventFactory) {
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
