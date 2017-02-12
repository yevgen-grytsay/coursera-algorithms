import edu.princeton.cs.algs4.StdIn;
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
        ArrayList<LineSegment> segments = new ArrayList<>();
        for (int p = 0; p < points.length; p++) {
            for (int q = 0; q < points.length; q++) {
                if (lesse(points[p], points[q])) continue;
                for (int r = 0; r < points.length; r++) {
                    if (lesse(points[q], points[r])) continue;
                    for (int s = 0; s < points.length; s++) {
                        if (lesse(points[r], points[s])) continue;
                        Point a = points[p];
                        Point b = points[q];
                        Point c = points[r];
                        Point d = points[s];
                        double ab = a.slopeTo(b);
                        if (Double.compare(ab, a.slopeTo(c)) == 0 && Double.compare(ab, a.slopeTo(d)) == 0) {
                            segments.add(new LineSegment(a, d));
                        }
                    }
                }
            }
        }
        collinear = segments.toArray(new LineSegment[segments.size()]);
    }

    private static boolean lesse(Point a, Point b) {
        return a.compareTo(b) <= 0;
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


        String pathname = "/home/yevgen/IdeaProjects/coursera-algorithms/w3-collinear-points/collinear/input40.txt";
        Point[] points = fromStdIn();
//        Point[] points = fromFile(pathname);

        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        StdOut.println(Arrays.toString(bcp.segments()));
    }

    private static Point[] fromStdIn() {
        int n = StdIn.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(StdIn.readInt(), StdIn.readInt());
        }
        return points;
    }

    private static Point[] fromFile(String filename) throws FileNotFoundException {
        File file = new File(filename);
        FileInputStream f = new FileInputStream(file);
        Scanner s = new Scanner(f);
        int n = s.nextInt();
        Point[] points = new Point[n];
        int i = 0;
//        StdDraw.setPenRadius(100);
//        StdDraw.setPenColor(java.awt.Color.BLACK);
        while(s.hasNextInt()) {
            Point p = new Point(s.nextInt(), s.nextInt());
            points[i++] = p;
        }
        return points;
    }
}
