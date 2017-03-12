import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;
import java.util.LinkedList;


public class KdTree {
    private Node root;
    private int size = 0;

    private class Node {
        private final Point2D key;
        private final int level;
        private Node left;
        private Node right;
        private final RectHV rect;
        private final Node parent;

        public Node(Point2D key, int level, RectHV rect, Node parent) {
            this.key = key;
            this.level = level;
            this.rect = rect;
            this.parent = parent;
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
            return level % 2 == 0 ? Point2D.X_ORDER : Point2D.Y_ORDER;
        }

        public void draw() {
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.RED);
            if (parent == null) {
                StdDraw.line(key.x(), 0, key.x(), 1);
            } else {
                if (level % 2 == 0) {
                    StdDraw.line(key.x(), rect.ymin(), key.x(), rect.ymax());
                } else {
                    StdDraw.setPenColor(StdDraw.BLUE);
                    StdDraw.line(rect.xmin(), key.y(), rect.xmax(), key.y());
                }
            }

            if (left != null) left.draw();
            if (right != null) right.draw();

            StdDraw.setPenRadius(0.02);
            StdDraw.setPenColor(StdDraw.BLACK);
            key.draw();
        }

        public void range(LinkedList<Point2D> points, RectHV searchRect) {
            if (searchRect.contains(key)) {
                points.add(key);
            }
            if (left != null && left.rect.intersects(searchRect)) {
                left.range(points, searchRect);
            }
            if (right != null && right.rect.intersects(searchRect)) {
                right.range(points, searchRect);
            }
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
        root = insert(root, p, 0, new RectHV(0, 0, 1, 1), root);
        ++size;
    }

    private Node insert(Node x, Point2D key, int level, RectHV rect, Node parent) {
        if (x == null) return new Node(key, level, rect, parent);
        Comparator<Point2D> cmp = x.comparator();

        if (cmp.compare(key, x.key) < 0) x.left = insert(x.left, key,  level + 1, x.leftRect(), x);
        else x.right = insert(x.right, key,  level + 1, x.rightRect(), x);
        return x;
    }

    public boolean contains(Point2D p) {
        return contains(root, p, 0);
    }

    private boolean contains(Node x, Point2D p, int level) {
        if (x == null) return false;
        if (x.key.equals(p)) return true;

        Comparator<Point2D> cmp = x.comparator();
        if (cmp.compare(p, x.key) < 0) return contains(x.left, p,  level + 1);
        else return contains(x.right, p,  level + 1);
    }

    public void draw() {
        if (root != null) root.draw();
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        LinkedList<Point2D> points = new LinkedList<>();
        root.range(points, rect);
        return points;
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
        if (cmp < 0) {
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

    }
}
