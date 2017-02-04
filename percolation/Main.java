import edu.princeton.cs.algs4.StdIn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by yevgen on 04.02.2017.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        String pathname = "d:\\Education\\Coursera - Algorithms\\percolation\\input8-no.txt";
        FileInputStream f = new FileInputStream(new File(pathname));
        Scanner s = new Scanner(f);
        String line;
        ArrayList<String> lines = new ArrayList<String>();
        while (s.hasNext()) {
            line = s.nextLine();
            if (line.equals("")) {
                break;
            }
            lines.add(line);
        }
        System.out.println(lines);
        int n = Integer.parseInt(lines.remove(0));
        Percolation p = new Percolation(n);
        int i = 1;
        for (String ln: lines) {
            String[] parts = ln.split(" ");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            p.open(row, col);
            System.out.println(String.format("%d: %s", i, Arrays.asList(row, col)));
            System.out.println(p.isFull(row, col));
            ++i;
//            if (i == 15) {
//                 break;
//            }
        }

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
