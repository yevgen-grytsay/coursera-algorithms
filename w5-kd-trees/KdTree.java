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
        private Point2D key;
        public Node left;
        public Node right;

        public Node(Point2D key) {
            this.key = key;
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
        root = insert(root, p, 0);
        ++size;
    }

    private Node insert(Node x, Point2D key, int level) {
        if (x == null) return new Node(key);
        Comparator<Point2D> cmp = level%2 == 0 ? Point2D.X_ORDER : Point2D.Y_ORDER;
        if (cmp.compare(key, x.key) == -1) x.left = insert(x.left, key,  level + 1);
        else x.right = insert(x.right, key,  level + 1);
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
        return nearest(root, p, 0, null);
    }

    private Point2D nearest(Node x, Point2D p, int level, Point2D nearest) {
        if (x == null) return null;
        double distance = x.key.distanceTo(p);
        double nearestDistance;
        if (nearest != null) {
            nearestDistance = nearest.distanceTo(p);
        } else {
            nearestDistance = distance;
            nearest = x.key;
        }
        if (distance < nearestDistance) {
            nearest = x.key;
        }

        Comparator<Point2D> cmp = level%2 == 0 ? Point2D.X_ORDER : Point2D.Y_ORDER;
        Node first, second;
        if (cmp.compare(p, x.key) == -1) {
            first = x.left;
            second = x.right;
        } else {
            first = x.right;
            second = x.left;
        }

        Point2D nf = nearest(first, p, level + 1, nearest);
        if (nf != null && p.distanceTo(nf) < nearestDistance) {
            nearest = nf;
            nearestDistance = p.distanceTo(nf);
        }
//        if (nc != null) return nc;

        Point2D ns = nearest(second, p, level + 1, nearest);
//        if (nc != null) return nc;
        if (ns != null && p.distanceTo(ns) < nearestDistance) {
            nearest = ns;
            nearestDistance = p.distanceTo(ns);
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
