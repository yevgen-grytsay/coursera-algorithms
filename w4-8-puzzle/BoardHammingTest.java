import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by yevgen on 22.02.17.
 */
public class BoardHammingTest {
    @Test
    public void testAllInPlace() {
        Board b = new Board(new int[][] {
                {1, 2},
                {3, 0},
        });
        assertEquals(0, b.hamming());
    }

    @Test
    public void testVerticalDistance() {
        Board b = new Board(new int[][] {
                {3, 2},
                {1, 0},
        });
        assertEquals(2, b.hamming());
    }

    @Test
    public void testHorizontalDistance() {
        Board b = new Board(new int[][] {
                {2, 1},
                {3, 0},
        });
        assertEquals(2, b.hamming());
    }

    @Test
    public void testAllNotInPlace() {
        Board b = new Board(new int[][] {
                {0, 1},
                {4, 2},
        });
        assertEquals(4, b.hamming());
    }
}