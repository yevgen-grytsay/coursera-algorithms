import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by yevgen on 22.02.17.
 */
public class BoardManhattanTest {
    @Test
    public void testAllInPlace() {
        Board b = new Board(new int[][] {
                {1, 2},
                {3, 0},
        });
        assertEquals(0, b.manhattan());
    }

    @Test
    public void testVerticalManhattan() {
        Board b = new Board(new int[][] {
                {3, 2},
                {1, 0},
        });
        assertEquals(2, b.manhattan());
    }

    @Test
    public void testHorizontalManhattan() {
        Board b = new Board(new int[][] {
                {2, 1},
                {3, 0},
        });
        assertEquals(2, b.manhattan());
    }

    @Test
    public void testAllNotInPlaceManhattan() {
        Board b = new Board(new int[][] {
                {3, 1},
                {0, 2},
        });
        assertEquals(4, b.manhattan());
    }
}