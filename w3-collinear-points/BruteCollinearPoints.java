import edu.princeton.cs.algs4.StdIn;

import java.util.Arrays;


public class BruteCollinearPoints {
    public BruteCollinearPoints(Point[] points) {
        int[] idx = new int[4];
        do {
            if (!(idx[0] < idx[1] && idx[1] < idx[2] && idx[2] < idx[3])) {
                continue;
            }
            Point a = points[idx[0]];
            Point b = points[idx[1]];
            Point c = points[idx[2]];
            Point d = points[idx[3]];
            double ab = a.slopeTo(b);
            if (Double.compare(ab, a.slopeTo(c)) == 0 && Double.compare(ab, a.slopeTo(d)) == 0) {
                System.out.println(Arrays.toString(new String[] {a.toString(), b.toString(), c.toString(), d.toString()}));
            }

        } while(advance(idx, 4));
    }

    private static boolean advance(int[] idx, int max) {
        for (int i = idx.length - 1; i >= 0; i--) {
            ++idx[i];
            if (idx[i] > max) {
                idx[i] = 0;
                if (i == 0) return false;
            } else {
                return true;
            }
        }
        return true;
    }

//    public int numberOfSegments() {
//
//    }
//
//    public LineSegment[] segments() {
//
//    }
    public static void main(String[] args) {
//        int[] idx = new int[2];
//        while(advance(idx, 3)) {
//            StdOut.println(Arrays.toString(idx));
//        }
        int n = StdIn.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(StdIn.readInt(), StdIn.readInt());
        }

        new BruteCollinearPoints(points);
    }
}
