import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by yevgen on 09.02.17.
 */
public class DequeTest {
    @Test
    public void fifoSingle() {
        Deque<String> d = new Deque<>();
        d.addFirst("one");
        assertEquals("one", d.removeFirst());
    }

    @Test
    public void filoSingle() {
        Deque<String> d = new Deque<>();
        d.addFirst("one");
        assertEquals("one", d.removeLast());
    }

    @Test
    public void fifoNonEmpty() {
        Deque<String> d = new Deque<>();
        d.addFirst("one");
        d.addFirst("two");
        assertEquals("two", d.removeFirst());
    }

    @Test
    public void filoNonEmpty() {
        Deque<String> d = new Deque<>();
        d.addFirst("one");
        d.addFirst("two");
        assertEquals("one", d.removeLast());
    }

    @Test
    public void size() {
        Deque<String> d = new Deque<>();
        assertEquals(0, d.size());

        d.addFirst("one");
        assertEquals(1, d.size());

        d.removeFirst();
        assertEquals(0, d.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void failRemoveFirstFromEmptyDeque() {
        Deque<String> d = new Deque<>();
        d.removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void failRemoveLastFromEmptyDeque() {
        Deque<String> d = new Deque<>();
        d.removeLast();
    }

    @Test(expected = NullPointerException.class)
    public void failAddLastNull() {
        Deque<String> d = new Deque<>();
        d.addFirst(null);
    }

    @Test(expected = NullPointerException.class)
    public void failAddFirstNull() {
        Deque<String> d = new Deque<>();
        d.addLast(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void failRemoveFromIterator() {
        Deque<String> d = new Deque<>();
        d.iterator().remove();
    }
}