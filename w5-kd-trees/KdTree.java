import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;

import java.util.Collections;
import java.util.Comparator;


public class KdTree {
    private Node root;
    private int size = 0;

    class Node {
        private final Point2D key;
        private final int level;
        public Node left;
        public Node right;
        public final RectHV rect;

        public Node(Point2D key, int level, RectHV rect) {
            this.key = key;
            this.level = level;
            this.rect = rect;
        }

        public RectHV leftRect() {
            if (level % 2 == 0) {
                return new RectHV(rect.xmin(), rect.ymin(), key.x(), rect.ymax());
            } else {
                return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), key.y());
            }
        }

        public RectHV rightRect() {
            if (level % 2 == 0) {
                return new RectHV(key.x(), rect.ymin(), rect.xmax(), rect.ymax());
            } else {
                return new RectHV(rect.xmin(), key.y(), rect.xmax(), rect.ymax());
            }
        }

        public Comparator<Point2D> comparator() {
            return level%2 == 0 ? Point2D.X_ORDER : Point2D.Y_ORDER;
        }
    }

    public KdTree() {
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        root = insert(root, p, 0, new RectHV(0, 0, 1, 1));
        ++size;
    }

    private Node insert(Node x, Point2D key, int level, RectHV rect) {
        if (x == null) return new Node(key, level, rect);
        Comparator<Point2D> cmp = x.comparator();

        if (cmp.compare(key, x.key) == -1) x.left = insert(x.left, key,  level + 1, x.leftRect());
        else x.right = insert(x.right, key,  level + 1, x.rightRect());
        return x;
    }

    public boolean contains(Point2D p) {
        return contains(root, p, 0);
    }

    private boolean contains(Node x, Point2D p, int level) {
        if (x == null) return false;
        if (x.key.equals(p)) return true;

        Comparator<Point2D> cmp = level%2 == 0 ? Point2D.X_ORDER : Point2D.Y_ORDER;
        if (cmp.compare(p, x.key) == -1) return contains(x.left, p,  level + 1);
        else return contains(x.right, p,  level + 1);
    }

    public void draw() {
        draw(root);
    }

    private void draw(Node n) {
        if (n == null) return;
        n.key.draw();
        draw(n.left);
        draw(n.right);
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        return Collections.emptyList();
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        return nearest(root, p, null);
    }

    private Point2D nearest(Node x, Point2D p, Point2D nearest) {
        if (x == null) return null;
        double currentDistance = x.key.distanceTo(p);
        double bestDistance;
        if (nearest != null) {
            bestDistance = nearest.distanceTo(p);
        } else {
            bestDistance = currentDistance;
            nearest = x.key;
        }
        if (currentDistance < bestDistance) {
            nearest = x.key;
        }

        Node first, second;
        int cmp = x.comparator().compare(p, x.key);
        if (cmp == -1) {
            first = x.left;
            second = x.right;
        } else {
            first = x.right;
            second = x.left;
        }

        Point2D nf = nearest(first, p, nearest);
        if (nf != null && p.distanceTo(nf) < bestDistance && !nf.equals(p)) {
            nearest = nf;
            bestDistance = p.distanceTo(nf);
        }

        if (second != null && second.rect.distanceTo(p) < bestDistance) {
            Point2D ns = nearest(second, p, nearest);
            if (ns != null && p.distanceTo(ns) < bestDistance) {
                nearest = ns;
            }
        }

        return nearest;
    }

    public static void main(String[] args) {
//        KdTree tree = new KdTree();
//        tree.insert(new Point2D(.1,.7));
//        tree.insert(new Point2D(.3,.2));
//
//        StdOut.println(tree.nearest(new Point2D(.4,.6)));
        String filename = "/home/yevgen/IdeaProjects/coursera-algorithms/w5-kd-trees/kdtree/circle10.txt";
        In in = new In(filename);

        // initialize the two data structures with point from standard input
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }

//        Point2D key = new Point2D(0.03078, 0.595703);
        Point2D key = new Point2D(0.3125, 0.638672);

        Point2D nearestBrute = brute.nearest(key);
        Point2D nearestKd = kdtree.nearest(key);

        StdOut.println(String.format("Brute: %f %f", nearestBrute.x(), nearestBrute.y()));
        StdOut.println(String.format("Kd: %f %f", nearestKd.x(), nearestKd.y()));
//        StdOut.println(String.format("%f %f", x, y));
    }
}
