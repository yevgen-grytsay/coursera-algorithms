import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by yevgen on 09.02.17.
 */
public class Deque<Item> implements Iterable<Item> {
    private Node head;
    private Node tail;
    private int size;

    public Deque() {
    }

    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = head;
        head = newNode;
        if (tail == null) {
            tail = newNode;
        }
        ++size;
    }

    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        Node newNode = new Node();
        newNode.item = item;
        newNode.prev = tail;
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        ++size;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public Item removeFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        Node oldNode = head;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        --size;
        return oldNode.item;
    }

    public Item removeLast() {
        if (tail == null) {
            throw new NoSuchElementException();
        }
        Node oldNode = tail;
        tail = tail.prev;
        if (tail == null) {
            head = null;
        }
        --size;
        return oldNode.item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator(head);
    }

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node first;

        public DequeIterator(Node first) {
            this.first = first;
        }

        @Override
        public boolean hasNext() {
            return first !=null;
        }

        @Override
        public Item next() {
            if (first == null) throw new NoSuchElementException();
            Node current = first;
            first = first.next;
            return current.item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}