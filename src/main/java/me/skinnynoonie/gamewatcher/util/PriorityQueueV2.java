package me.skinnynoonie.gamewatcher.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

public final class PriorityQueueV2<E> extends AbstractQueue<E> implements Queue<E> {

    private final PriorityQueue<PriorityContainer<E>> baseQueue;
    private final int defaultPriority;

    public PriorityQueueV2(int defaultPriority) {
        this.defaultPriority = defaultPriority;
        this.baseQueue = new PriorityQueue<>();
    }

    public void add(@Nullable E e, int priority) {
       this.baseQueue.add(new PriorityContainer<>(e, priority));
    }

    @Override
    public @NotNull Iterator<E> iterator() {
        @SuppressWarnings("unchecked")
        PriorityContainer<E>[] naturalOrderArray = this.baseQueue.toArray(PriorityContainer[]::new);
        Arrays.sort(naturalOrderArray);
        Iterator<PriorityContainer<E>> baseIterator = Arrays.stream(naturalOrderArray).iterator();
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return baseIterator.hasNext();
            }

            @Override
            public E next() {
                return baseIterator.next().getElement();
            }
        };
    }

    @Override
    public int size() {
        return this.baseQueue.size();
    }

    @Override
    public boolean offer(@Nullable E e) {
        return this.baseQueue.offer(new PriorityContainer<>(e, this.defaultPriority));
    }

    @Override
    public @Nullable E poll() {
        PriorityContainer<E> poll = this.baseQueue.poll();
        return poll != null ? poll.getElement() : null;
    }

    @Override
    public @Nullable E peek() {
        PriorityContainer<E> peak = this.baseQueue.peek();
        return peak != null ? peak.getElement() : null;
    }

    private static class PriorityContainer<E> implements Comparable<PriorityContainer<E>> {
        private final E element;
        private final int priority;

        private PriorityContainer(E element, int priority) {
            this.element = element;
            this.priority = priority;
        }

        @Override
        public int compareTo(@NotNull PriorityQueueV2.PriorityContainer<E> o) {
            return Integer.compare(this.priority, o.priority);
        }

        public E getElement() {
            return this.element;
        }
    }

}
