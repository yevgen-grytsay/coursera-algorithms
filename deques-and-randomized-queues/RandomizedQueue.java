import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by yevgen on 09.02.17.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int n;

    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
    }
    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (n == queue.length) resize(2 * queue.length);
        queue[n++] = item;
    }

    private void resize(int size) {
        Item[] newQueue = (Item[]) new Object[size];
        for (int i = 0; i < n; i++) {
            newQueue[i] = queue[i];
        }
        queue = newQueue;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int i = StdRandom.uniform(n);
        Item item = queue[i];
        if (i < n - 1) {
            queue[i] = queue[n - 1];
        }
        queue[n - 1] = null;
        --n;
        if (n > 0 && queue.length / 4 == n) resize(queue.length / 2);
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return queue[StdRandom.uniform(n)];
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] indexes = new int[n];
        private int current = 0;

        public RandomizedQueueIterator() {
            for (int i = 0; i < n; i++) {
                indexes[i] = i;
            }
            StdRandom.shuffle(indexes);
        }

        @Override
        public boolean hasNext() {
            return current < n;
        }

        @Override
        public Item next() {
            if (n == 0) throw new NoSuchElementException();
            return queue[current++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
