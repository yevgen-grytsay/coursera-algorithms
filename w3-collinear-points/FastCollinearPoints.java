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
        Arrays.sort(points);
        Point[] search = Arrays.copyOf(points, points.length);
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Arrays.sort(search);
            Arrays.sort(search, p.slopeOrder());
            find(search, p);
        }
    }

    private int find(Point[] points, Point origin) {
        double slope = 0;
        int n = 0;
        Point last = null;

        for (int i = 0; i < points.length;) {
            Point p = points[i];
            boolean inOrder = isInOrder(origin, p);
            if (!inOrder) {
                ++i;
                continue;
            }
            int j = i + 1;
            n = 1;
            slope = origin.slopeTo(p);
            last = null;
            for (; j < points.length; j++) {
                if (Double.compare(slope, origin.slopeTo(points[j])) != 0
                        || !isInOrder(points[j - 1], points[j])) {
                    break;
                }
                ++n;
                if (n >= 3) {
                    last = points[j];
                }
            }
            flush(origin, last);
            i = j;
        }

//        for (int i = 0; i < points.length; i++) {
//            Point p = points[i];
//            Point prev = i > 0 ? points[i - 1] : origin;
//            boolean inOrder = isInOrder(prev, p);
//            if (n == 0 && !inOrder) {
//                continue;
//            }
//            if (n == 0) {
//                slope = origin.slopeTo(p);
//                n = 1;
//                continue;
//            }
//            boolean equals = Double.compare(slope, origin.slopeTo(p)) == 0;
//            if (equals && !inOrder) {
//                n = 0;
//                last = null;
//                continue;
//            }
//            if (equals && inOrder) {
//                ++n;
//            }
//            if (n >= 3 && equals && inOrder) {
//                last = p;
//            }
//
//            if (!equals || !inOrder || i == points.length - 1) {
//                flush(origin, last);
//                last = null;
//                n = 0;
//            }
//        }
//        flush(origin, last);
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
        fromFile("/home/yevgen/IdeaProjects/coursera-algorithms/w3-collinear-points/example.txt");

        // print and draw the line segments
//        FastCollinearPoints collinear = new FastCollinearPoints(fromStdIn());
//        for (LineSegment segment : collinear.segments()) {
//            StdOut.println(segment);
//            segment.draw();
//        }
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
