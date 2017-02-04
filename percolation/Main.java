import edu.princeton.cs.algs4.StdIn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by yevgen on 04.02.2017.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        /*
         * Test 4: check for backwash with predetermined sites
         * filename = input20.txt
         * filename = input10.txt - percolates() returns wrong value [after 56 sites opened]
         * - student = false
         * - reference = true
         * - failed after call 56 to isOpen()
         */
        String pathname = "d:\\Education\\Coursera - Algorithms\\percolation\\input10.txt";
        FileInputStream f = new FileInputStream(new File(pathname));
        Scanner s = new Scanner(f);

        int n = s.nextInt();
        Percolation p = new Percolation(n);
        int i = 1;
        while (s.hasNext()) {
            int row = s.nextInt();
            int col = s.nextInt();
            p.open(row, col);
            System.out.println(String.format("%d: %s, isFull(%d, %d): %s",
                    i, Arrays.asList(row, col), row, col, p.isFull(row, col)));
            ++i;
        }


        System.out.println(p.percolates());
//        Percolation p = new Percolation(2);
//        System.out.println(p.percolates());
//
//        p.open(1, 1);
//        System.out.println(p.percolates());
//
//        p.open(2, 2);
//        System.out.println(String.format("Percolates: %s, isFull(1, 1): %s", p.percolates(), p.isFull(2, 2)));
//
//        p.open(1, 2);
//        System.out.println(String.format("Percolates: %s, isFull(1, 1): %s", p.percolates(), p.isFull(2, 2)));
    }
}
