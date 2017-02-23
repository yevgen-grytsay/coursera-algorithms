import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by yevgen on 22.02.17.
 */
public class BoardTest {
    @Test
    public void testEquals() {
        Board a = new Board(new int[][] {
                {1, 2},
                {3, 0}
        });
        Board b = new Board(new int[][] {
                {1, 2},
                {3, 0}
        });
        assertTrue(a.equals(b));
    }

    @Test
    public void testNotEquals() {
        Board a = new Board(new int[][] {
                {1, 2},
                {3, 0}
        });
        Board b = new Board(new int[][] {
                {1, 2},
                {0, 3}
        });
        assertFalse(a.equals(b));
    }

    @Test
    public void testIteratorSpaceInCorner() {
        Board a = new Board(new int[][] {
                {1, 2},
                {3, 0}
        });
        Iterator<Board> it = a.neighbors().iterator();

        Board n1 = new Board(new int[][] {
                {1, 2},
                {0, 3}
        });
        assertTrue(it.next().equals(n1));

        Board n2 = new Board(new int[][] {
                {1, 0},
                {3, 2}
        });
        assertTrue(it.next().equals(n2));
    }

    @Test
    public void testIteratorSpaceInTheMiddleCorner() {
        Board a = new Board(new int[][] {
                {1, 2, 3},
                {4, 0, 6},
                {7, 5, 8}
        });
        Iterator<Board> it = a.neighbors().iterator();

        Board ne = new Board(new int[][] {
                {1, 2, 3},
                {4, 6, 0},
                {7, 5, 8}
        });
        assertTrue(it.next().equals(ne));

        Board ns = new Board(new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 0, 8}
        });
        assertTrue(it.next().equals(ns));

        Board nw = new Board(new int[][] {
                {1, 2, 3},
                {0, 4, 6},
                {7, 5, 8}
        });
        assertTrue(it.next().equals(nw));

        Board nn = new Board(new int[][] {
                {1, 0, 3},
                {4, 2, 6},
                {7, 5, 8}
        });
        assertTrue(it.next().equals(nn));
    }

    @Test
    public void testToString() {
        Board b = new Board(new int[][] {
                {1, 2},
                {3, 0}
        });
        String expected = "2\n1  2\n3  0";
        assertEquals(expected, b.toString());
    }
}