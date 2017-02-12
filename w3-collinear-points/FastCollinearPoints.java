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
//        LinkedList<LineSegment> collinear = new LinkedList<>();
        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Point[] search = new Point[points.length - 1];
            int k = 0;
            for (int j = 0; j < points.length; j++) {
                if (i == j) continue;
                search[k++] = points[j];
            }
            Arrays.sort(search, p.slopeOrder());
            int last = 0;
            while ((last = find(search, p, last)) > 0) {
//                collinear.add(new LineSegment(p, search[last]));
            }
        }
    }

    private int find(Point[] points, Point origin, int min) {
        double slope;
        for (int i = min; i < points.length;) {
            Point p = points[i];
            slope = origin.slopeTo(p);
            int j = i + 1;
            for (; j < points.length; j++) {
                boolean equals = Double.compare(slope, origin.slopeTo(points[j])) == 0;
                if (equals) {
                    continue;
                }
                break;
            }
            if (j - i >= 3) {
                segments.add(new LineSegment(origin, points[j - 1]));
            }
            i += j;
        }
        return 0;
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
