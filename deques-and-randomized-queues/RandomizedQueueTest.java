import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by yevgen on 09.02.17.
 */
public class RandomizedQueueTest {
    private RandomizedQueue<String> q;

    @Before
    public void setUp() {
        q = new RandomizedQueue<>();
    }

    @Test
    public void fifoSingle() {
        q.enqueue("one");
        assertEquals("one", q.dequeue());
    }

    @Test
    public void enqueueDequeueTwo() {
        q.enqueue("one");
        q.enqueue("two");
        String[] actual = {q.dequeue(), q.dequeue()};
        Arrays.sort(actual);
        assertArrayEquals(new String[]{"one", "two"}, actual);
    }

    @Test
    public void sample() {
        q.enqueue("one");
        assertEquals("one", q.sample());
        assertEquals("one", q.sample());
    }

    @Test(expected = NullPointerException.class)
    public void failEnqueueNull() {
        q.enqueue(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void failDequeueFromEmpty() {
        q.dequeue();
    }

    @Test(expected = NoSuchElementException.class)
    public void failSampleFromEmpty() {
        q.sample();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void failRemoveFromIterator() {
        q.iterator().remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void failNextOnEmptyIterator() {
        q.iterator().next();
    }
}