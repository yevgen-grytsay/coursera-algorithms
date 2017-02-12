import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class BruteCollinearPoints {
    private LineSegment[] collinear;

    public BruteCollinearPoints(Point[] points) {
        int[] idx = new int[4];
        ArrayList<LineSegment> segments = new ArrayList<>();
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
                segments.add(new LineSegment(a, d));
            }

        } while(advance(idx, 4));
        collinear = segments.toArray(new LineSegment[segments.size()]);
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

    public int numberOfSegments() {
        return collinear.length;
    }

    public LineSegment[] segments() {
        return collinear;
    }

    public static void main(String[] args) throws FileNotFoundException {
//        int[] idx = new int[2];
//        while(advance(idx, 3)) {
//            StdOut.println(Arrays.toString(idx));
//        }
//        int n = StdIn.readInt();
//        Point[] points = new Point[n];
//        for (int i = 0; i < n; i++) {
//            points[i] = new Point(StdIn.readInt(), StdIn.readInt());
//        }

        String pathname = "/home/yevgen/IdeaProjects/coursera-algorithms/w3-collinear-points/collinear/input6.txt";
        File file = new File(pathname);
        FileInputStream f = new FileInputStream(file);
        Scanner s = new Scanner(f);
        int n = s.nextInt();
        Point[] points = new Point[n];
        int i = 0;
        Point prev = null;
        StdDraw.setPenRadius(100);
        StdDraw.setPenColor(java.awt.Color.BLACK);
//        new Point(1, 1).draw();
        while(s.hasNextInt()) {
            Point p = new Point(s.nextInt(), s.nextInt());
            if (prev != null) {
                prev.drawTo(p);
            }
            points[i++] = prev = p;
        }
//        StdDraw.save(file.getName().concat(".png"));
//        StdDraw.show();

        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        StdOut.println(Arrays.toString(bcp.segments()));

    }
}
