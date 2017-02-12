import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Created by yevgen on 12.02.17.
 */
public class FastCollinearPoints {
    private LinkedList<LineSegment> segments = new LinkedList<>();

    public FastCollinearPoints(Point[] points) {
        validate(points);
        Point[] search = Arrays.copyOf(points, points.length);
        Arrays.sort(search);
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Arrays.sort(search);
            Arrays.sort(search, p.slopeOrder());
            find(search);
        }
    }

    private int find(Point[] points) {
        Point origin = points[0];
        for (int i = 1; i < points.length;) {
            Point p = points[i];
            int j = i + 1;
            double slope = origin.slopeTo(p);
            while (j < points.length && Double.compare(slope, origin.slopeTo(points[j])) == 0) {
                j++;
            }
            if (j - i >= 3 && isInOrder(origin, p)) {
                flush(origin, points[j - 1]);
            }
            i = j;
        }

        return 0;
    }

    private void flush(Point origin, Point b) {
        if (b != null) {
            segments.add(new LineSegment(origin, b));
        }
    }

    private static boolean isInOrder(Point a, Point b) {
        return a.compareTo(b) == -1;
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    public static void main(String[] args) {
//        fromFile(args[0]);
        fromFile("/home/yevgen/IdeaProjects/coursera-algorithms/w3-collinear-points/collinear/rs1423.txt");

        // print and draw the line segments
//        FastCollinearPoints collinear = new FastCollinearPoints(fromStdIn());
//        for (LineSegment segment : collinear.segments()) {
//            StdOut.println(segment);
//            segment.draw();
//        }
    }

    private void validate(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new NullPointerException();
            }
        }
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    private static Point[] fromStdIn() {
        int n = StdIn.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(StdIn.readInt(), StdIn.readInt());
        }
        return points;
    }

    private static void fromFile(String arg) {
        // read the n points from a file
        In in = new In(arg);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
