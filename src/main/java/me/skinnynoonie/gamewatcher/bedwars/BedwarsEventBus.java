package me.skinnynoonie.gamewatcher.bedwars;

import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsPlayerJoinEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsPlayerLeaveEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.BedwarsPlayerListEvent;
import me.skinnynoonie.gamewatcher.bedwars.event.factory.BedwarsDeathEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.factory.BedwarsEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.factory.BedwarsPlayerJoinEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.factory.BedwarsPlayerLeaveEventFactory;
import me.skinnynoonie.gamewatcher.bedwars.event.factory.BedwarsPlayerListEventFactory;
import me.skinnynoonie.gamewatcher.minecraft.MinecraftChatReader;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class BedwarsEventBus {

    private final MinecraftChatReader chatReader;
    private final List<Consumer<BedwarsEvent>> subscribers;
    private final List<BedwarsEventFactory<?>> eventFactories;
    private final BedwarsContext context;

    public BedwarsEventBus(MinecraftChatReader chatReader) {
        this.chatReader = chatReader;
        this.subscribers = new ArrayList<>();
        this.eventFactories = new ArrayList<>();
        this.context = BedwarsContext.createDefault();

        this.registerEventFactory(new BedwarsPlayerJoinEventFactory());
        this.registerEventFactory(new BedwarsPlayerLeaveEventFactory());
        this.registerEventFactory(new BedwarsPlayerListEventFactory());
        this.registerEventFactory(new BedwarsDeathEventFactory());

        this.subscribe(BedwarsPlayerJoinEvent.class, event -> {
            String player = event.getPlayer();
            this.context.addPlayer(player);
        });
        this.subscribe(BedwarsPlayerLeaveEvent.class, event -> {
            String player = event.getPlayer();
            this.context.addPlayer(player);
        });
        this.subscribe(BedwarsPlayerListEvent.class, event -> {
            List<String> players = event.getPlayers();
            this.context.setPlayers(players);
        });
    }

    public <T extends BedwarsEvent> void subscribe(Class<T> eventClass, Consumer<T> eventConsumer) {
        this.subscribers.add(event -> {
            if (event.getClass().isInstance(eventClass)) {
                eventConsumer.accept(eventClass.cast(event));
            }
        });
    }

    public void update() {
        try {
            String chatMessage = this.chatReader.nextChatMessage();
            if (chatMessage == null) {
                return;
            }

            BedwarsEvent event = null;
            for (BedwarsEventFactory<?> eventFactory : this.eventFactories) {
                event = eventFactory.from(chatMessage, this.context);
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

    public void registerEventFactory(BedwarsEventFactory<?> bedwarsEventFactory) {
        this.eventFactories.add(bedwarsEventFactory);
    }

}
