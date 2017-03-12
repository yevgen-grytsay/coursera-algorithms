import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.LinkedList;
import java.util.Objects;
import java.util.TreeSet;


public class PointSET {
    private TreeSet<Point2D> tree;

    public PointSET() {
        tree = new TreeSet<>();
    }

    public boolean isEmpty() {
        return tree.isEmpty();
    }

    public int size() {
        return tree.size();
    }

    public void insert(Point2D p) {
        Objects.requireNonNull(p);
        tree.add(p);
    }

    public boolean contains(Point2D p) {
        Objects.requireNonNull(p);
        return tree.contains(p);
    }

    public void draw() {
        for (Point2D p: tree) {
            p.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Objects.requireNonNull(rect);
        LinkedList<Point2D> points = new LinkedList<>();
        for (Point2D p: tree) {
            if (rect.contains(p)) {
                points.add(p);
            }
        }
        return points;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        Objects.requireNonNull(p);
        Point2D nearest = null;
        double min = 1;
        for (Point2D tp: tree) {
            double dist = tp.distanceTo(p);
            if (dist < min) {
                nearest = tp;
                min = dist;
            }
        }
        return nearest;
    }

    public static void main(String[] args) {

    }
}
